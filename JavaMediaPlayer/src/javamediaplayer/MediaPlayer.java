
package javamediaplayer;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.windows.Win32FullScreenStrategy;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

public class MediaPlayer {
    private EmbeddedMediaPlayer emp;
    private JFrame frame;
    private Canvas canvas;
    private JPanel panel;
    public void Load()
    {
        /****************************************
        * 建立GUI介面
        */
        frame = new JFrame();
        frame.setLocation(100, 100);
        frame.setSize(1000, 600);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
        /*
         * Canvas用來顯示影像
         */
        canvas = new Canvas();
        canvas.setBackground(Color.black);
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(canvas);
        frame.add(canvas);
        /*************************************
         * 讀取影像檔,使用vlcj
         */
        //讀取VLC安裝位置
        NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), "C:\\Program Files\\VideoLAN\\VLC");
        Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);

        initMediaPlayer();        
        /********************************************
        * 
        */
    }
    private void initMediaPlayer(){
        //初始化播放器
        MediaPlayerFactory mpf = new MediaPlayerFactory();
        emp = mpf.newEmbeddedMediaPlayer(new Win32FullScreenStrategy(frame));
        emp.setVideoSurface(mpf.newVideoSurface(canvas));
        //emp.toggleFullScreen();
        emp.setEnableMouseInputHandling(false);
        emp.setEnableKeyInputHandling(false);
    }
    public void play(){
        String file="cache.mp4";
        emp.prepareMedia(file);
        emp.play();
    }
    public void play(String file_name){
        String file=file_name;
        emp.prepareMedia(file);
        emp.play();
    }
}
