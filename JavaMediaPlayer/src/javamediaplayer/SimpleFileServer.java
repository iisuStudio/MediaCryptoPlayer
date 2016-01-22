/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javamediaplayer;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleFileServer {
    public static int SOCKET_PORT = 8888;
    public static String FILE_TO_SEND = "cache.mp4";
    public static char[] PASSWORD = null;
    public SimpleFileServer(){}
    public SimpleFileServer(int port, String file, char[] password) {
        SOCKET_PORT = port;
        FILE_TO_SEND = file;
        PASSWORD = password;
    }
    public void Load () throws IOException {
      FileInputStream fis = null;
      BufferedInputStream bis = null;
      OutputStream os = null;
      ServerSocket servsock = null;
      Socket sock = null;
      try {     
        servsock = new ServerSocket(SOCKET_PORT);
        System.out.println("The Current Port is "+SOCKET_PORT);
        System.out.println("Waiting...");
        try {
            sock = servsock.accept();
            System.out.println("Accepted connection : " + sock);
            // send file
            File myFile = new File (FILE_TO_SEND);
            byte [] mybytearray  = new byte [(int)myFile.length()];
            fis = new FileInputStream(myFile);
            bis = new BufferedInputStream(fis);
            bis.read(mybytearray,0,mybytearray.length);
            //加密
            for (int i=0; i<(int)mybytearray.length; i++){
                for (int j=0; j<(int)PASSWORD.length; j++){
                        mybytearray[i] = 
                            (byte)((int)mybytearray[i] ^ (int)PASSWORD[j]);
                }
            }
          os = sock.getOutputStream();
          System.out.println("Sending " + FILE_TO_SEND 
                + "(" + mybytearray.length + " bytes)");
          os.write(mybytearray,0,mybytearray.length);
          os.flush();
          System.out.println("Done.");
        }
        finally {
          if (bis != null) bis.close();
          if (os != null) os.close();
          if (sock!=null) sock.close();
        }
      }
      finally {
        if (servsock != null) servsock.close();
      }
    }
}
