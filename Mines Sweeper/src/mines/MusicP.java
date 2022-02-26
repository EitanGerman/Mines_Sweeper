package mines;


import java.nio.file.Paths;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MusicP {
	MediaPlayer mediaPlayer;
	public MusicP(String path) {
        Media media = new Media(Paths.get(path).toUri().toString());  
        mediaPlayer = new MediaPlayer(media);  
        mediaPlayer.setAutoPlay(true); 
        mediaPlayer.play();
	}
	
	public void stop() {
		mediaPlayer.stop();
	}
	
	public void start() {
		mediaPlayer.play();
	}
	
	public void setVolume(double value) {
		mediaPlayer.setVolume(value);
	}
}
