package mines;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

//this class is the main FX layer for the game of mines sweeper
public class MinesFX extends Application{
	private HBox hbox;
	@Override
	public void start(Stage primaryStage) throws Exception {
		MineSweeperController controller;
		MusicP music = new MusicP("musicmp3.mp3");
		try {
			FXMLLoader root = new FXMLLoader(); 
			root.setLocation(getClass().getResource("MineSweeper.fxml"));//set location for the fxml file
			hbox=root.load();//load the fxml file
			controller=root.getController();//get the controller from the fxml file
			controller.SetHboxStage(hbox,primaryStage);//sent the HBox and the stage to the controller 
			hbox.getChildren().add(controller.getNewGrid());//add a grid form the controller
			Scene scene = new Scene(hbox);//create new scene with a new root
	        Image img = new Image("mines/background.png");
	        BackgroundImage bImg = new BackgroundImage(img,
	                                                   BackgroundRepeat.REPEAT,
	                                                   BackgroundRepeat.REPEAT,
	                                                   BackgroundPosition.DEFAULT,
	                                                   BackgroundSize.DEFAULT);
	        Background bGround = new Background(bImg);//generate background
	        hbox.setBackground(bGround);//set background
	        primaryStage.getIcons().add(new Image("mines/Bomb.png"));
			primaryStage.setTitle("Mines Sweeper");//set name for the window
			primaryStage.setScene(scene);//set primary scene
			primaryStage.show();//show primary scene
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
