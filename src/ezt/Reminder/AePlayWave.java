package ezt.Reminder;
import java.io.File; 
import java.io.IOException; 
import javax.sound.sampled.AudioFormat; 
import javax.sound.sampled.AudioInputStream; 
import javax.sound.sampled.AudioSystem; 
import javax.sound.sampled.DataLine; 
import javax.sound.sampled.FloatControl; 
import javax.sound.sampled.LineUnavailableException; 
import javax.sound.sampled.SourceDataLine; 
import javax.sound.sampled.UnsupportedAudioFileException; 

//play the reminder wave
public class AePlayWave extends Thread { 
 
    private String filename;
 
    private Position curPosition;
 
    private final int EXTERNAL_BUFFER_SIZE = 524288; // 128Kb 
 
    enum Position { 
        LEFT, RIGHT, NORMAL
    };
 
    //get the wave file
    public AePlayWave(String wavfile) { 
    	
        filename = wavfile;
        
        curPosition = Position.NORMAL;
        
    } 
 
  //get the wave file
    public AePlayWave(String wavfile, Position p) {
    	
        filename = wavfile;
        
        curPosition = p;
        
    } 
 
    //play the wave file
    public void run() { 
 
        File soundFile = new File(filename);
        
        if (!soundFile.exists()) { //if the wave file is not found
            System.err.println("Wave file not found: " + filename);
            return;
        } 
 
        AudioInputStream audioInputStream = null;
        
        try {
        	
            audioInputStream = AudioSystem.getAudioInputStream(soundFile);//assign the wave file into audiostream
            
        } catch (UnsupportedAudioFileException e1) {
        	
            e1.printStackTrace();
            
            return;
            
        } catch (IOException e1) {
        	
            e1.printStackTrace();
            
            return;
        } 
 
        AudioFormat format = audioInputStream.getFormat();
        
        SourceDataLine auline = null;
        
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);//assign audio format to the wave
 
        try { 
            
        	auline = (SourceDataLine) AudioSystem.getLine(info);
            
            auline.open(format);
            
        } catch (LineUnavailableException e) { 
            
        	e.printStackTrace();
            
        	return;
        	
        } catch (Exception e) { 
            
        	e.printStackTrace();
            
            return;
        } 
 
        if (auline.isControlSupported(FloatControl.Type.PAN)) { //to check whether the wave file supported in platform
            
        	FloatControl pan = (FloatControl) auline
                    .getControl(FloatControl.Type.PAN);
            
        	if (curPosition == Position.RIGHT) 
                pan.setValue(1.0f);
            else if (curPosition == Position.LEFT) 
                pan.setValue(-1.0f);
        } 
 
        auline.start();//play the wave
        
        int nBytesRead = 0;
        
        byte[] abData = new byte[EXTERNAL_BUFFER_SIZE];
 
        try { 
            while (nBytesRead != -1) { 
                nBytesRead = audioInputStream.read(abData, 0, abData.length);
                if (nBytesRead >= 0) 
                    auline.write(abData, 0, nBytesRead);
            } 
        } catch (IOException e) { 
            e.printStackTrace();
            return;
        } finally { 
            auline.drain();
            auline.close();
        } 
 
    } 
} 