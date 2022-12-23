
import javax.swing.JFileChooser;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class layout{
    
    public static void main(String[] args) {
        
        // Choose file
        JFileChooser fc = new JFileChooser();
        int result = fc.showOpenDialog(null);

        // Make sure that a file was chosen, else exit
        if (result != JFileChooser.APPROVE_OPTION) {
            System.exit(0);
        }

        // Get file path
        String path = fc.getSelectedFile().getAbsolutePath();
        String destination = "./images/" + fc.getSelectedFile().getName();
        
        try {
            // Copy file from source to destination
            FileChannel source = new FileInputStream(path).getChannel();
            FileChannel dest = new FileOutputStream(destination).getChannel();
            dest.transferFrom(source, 0, source.size());

            // Close
            source.close();
            dest.close();
         } catch (IOException e) {
             e.printStackTrace();
        }
    }
}
