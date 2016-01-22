
package javamediaplayer;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;



public class JavaMediaPlayer extends javax.swing.JFrame{
    /**
 * Returns the class name of the installed LookAndFeel with a name
 * containing the name snippet or null if none found.
 * 
 * @param nameSnippet a snippet contained in the Laf's name
 * @return the class name if installed, or null
 */
    public static String getLookAndFeelClassName(String nameSnippet) {
        LookAndFeelInfo[] plafs = UIManager.getInstalledLookAndFeels();
        for (LookAndFeelInfo info : plafs) {
            if (info.getName().contains(nameSnippet)) {
                return info.getClassName();
            }
        }
        return null;
    }
    public static void main(String[] args) {
    String className = getLookAndFeelClassName("Nimbus");
    try{
    UIManager.setLookAndFeel(className);}
    catch(Exception e){}
    HomeJFrame homepage = new HomeJFrame();
    homepage.setVisible(true);
    
    }
    
}
