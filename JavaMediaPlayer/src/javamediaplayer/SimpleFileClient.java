
package javamediaplayer;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class SimpleFileClient {
    public static int SOCKET_PORT = 8888;       // you may change this
    public static String SERVER = "127.0.0.1";  // localhost
    public static String FILE_TO_RECEIVED = "cache.mp4";  
    // you may change this, I give a
    // different name because i don't want to
    // overwrite the one used by server...
    public static char[] PASSWORD = null;
    public final static int FILE_SIZE = 67108864;    
    // file size temporary hard coded
    // should bigger than the file to be downloaded
    // if want to send the big file need change this limit
    public SimpleFileClient() {}
    public SimpleFileClient(String address, int port, char[] password) {
        SERVER = address;
        SOCKET_PORT = port;
        PASSWORD = password;
    }
    public void Load() throws IOException {
        int bytesRead;
        int current;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        Socket sock = null;

        try {
            sock = new Socket(SERVER, SOCKET_PORT);
            System.out.println("Connecting...");
            byte [] mybytearray  = new byte [FILE_SIZE];
            InputStream is = sock.getInputStream();

            fos = new FileOutputStream(FILE_TO_RECEIVED);
            bos = new BufferedOutputStream(fos);
            bytesRead = is.read(mybytearray,0,mybytearray.length);

            current = bytesRead;
            do {
                bytesRead = 
                    is.read(mybytearray, current, (mybytearray.length-current));
                if(bytesRead >= 0) current += bytesRead;
            } while(bytesRead > -1);
            //解密
            for (int i=0; i<(int)mybytearray.length; i++){
                for (int j=(int)PASSWORD.length-1; j>=0; j--){
                    mybytearray[i] = 
                        (byte)((int)mybytearray[i] ^ (int)PASSWORD[j]);
                }
            }
            //寫檔
            bos.write(mybytearray, 0 , current);
            bos.flush();
            System.out.println("File " + FILE_TO_RECEIVED
                + " downloaded (" + current + " bytes read)");
        }
        finally {
          if (fos != null) fos.close();
          if (bos != null) bos.close();
          if (sock != null) sock.close();
        }
    }
}
