package mines;

import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MusicP {
	public MusicP(String path) {
        Media media = new Media(new File(path).toURI().toString());  
        MediaPlayer mediaPlayer = new MediaPlayer(media);  
        //mediaPlayer.setAutoPlay(true); 
        mediaPlayer.play();
	}
}
