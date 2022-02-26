package mines;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
//this class is used to control the entire game of mines sweeper
public class MineSweeperController {
	private Mines mine = new Mines(10, 10, 10);// default mine
	private int height = 10, width = 10, numMines = 10;// default parameters for the mine
	private GridPane mineGrid = new GridPane();// main mine grid pane
	private HBox hbox; // need to save in order to resize the stage whenever the board gets bigger or
						// smaller
	private Stage primaryStage;// need to save in order to resize the stage whenever the board gets bigger or
								// smaller
	private MusicP music = new MusicP("src/mines/musicmp3.mp3");//music player for the game
	
	private boolean gameflag = true;
	@FXML
	private TextField heightBox;

	@FXML
	private TextField minesBox;
	
	@FXML
	private TextField widthBox;
	
	@FXML
	private Button reset;

    @FXML
    private Button muteButton;
    
    @FXML
    private Slider volumeSlider;
	
    @FXML
    void muteMine(ActionEvent event) {
    	if(muteButton.getText().equals("Mute Music")) {//if button is pressed and music is playing
    		muteButton.setText("Unmute Music");//the change to unmute
    		music.stop();   	//stop the music
		}
    	else {
    		muteButton.setText("Mute Music");//set text to Mute
    		music.start();	//start the music from the same point
    	}
    }
    
	@FXML
	void resetMine(ActionEvent event) {
		height = Integer.parseInt(heightBox.getText());// get text form text box
		width = Integer.parseInt(widthBox.getText());// get text form text box
		numMines = Integer.parseInt(minesBox.getText()); // get text form text box
		mine = new Mines( width,height, numMines);// generate new mine logic
		mineGrid = addGrid();// generate new mine grid
	}
	
    @FXML
    void adjustVolume(MouseEvent event) {
    	music.setVolume(volumeSlider.getValue());//sets the volume for the game
    }
    
	// used from the MinesFx class to to get the main GridPane
	protected GridPane getNewGrid() {
		return addGrid();// call add grid to generate a grid and return it
	}
	// this method is necessary to allow us to resize the window when trying to
	// reset the game
	public void SetHboxStage(HBox hbox, Stage primaryStage) {
		this.hbox = hbox;// get the main HBox form the main
		this.primaryStage = primaryStage;// get the main stage form the main
	}

	// this method creates and return a grid with all the buttons
	private GridPane addGrid() {
		mineGrid.setAlignment(Pos.CENTER);// set grid position to the center of the HBox
		Button[][] bArr = new Button[mine.width][mine.height];// button array
		mineGrid.getChildren().clear();// clear previous grid
		mineGrid.setPadding(new Insets(10));// set padding for grid
		for (int i = 0; i < mine.width; i++) {// rows
			for (int j = 0; j <mine.height ; j++) {// columns
				Button b = new Button(".");//default button text
				b.setFont(new Font("david", 20));//button font
				b.setMinHeight(40);//button size
				b.setMinWidth(40); //button size
				b.setMaxHeight(40);//button size
				b.setMaxWidth(40); //button size
				b.setStyle("-fx-border-color: #00A0AA; -fx-border-width: 1px;-fx-background-color: #dddddd;");//define button style
				b.setOnMouseClicked(new EventHandler<MouseEvent>() {//add action for when button is pressed
					@Override
					public void handle(MouseEvent mouseEvent) {
						if (!gameflag) {
							return;
						} // if game is already over do not handle any more key functions
						int bx = GridPane.getColumnIndex(b), by = GridPane.getRowIndex(b);//get the button location
						if (mouseEvent.getButton() == MouseButton.PRIMARY) {// if left click
							if (!mine.minefield[bx][by].isFlag() && mine.open(bx, by)) {// if not flag and game isn't
																						// over open desired location
								for (int i = 0; i < mine.width; i++) {// rows
									for (int j = 0; j < mine.height; j++) {// columns
										if (mine.minefield[i][j].isFlag() && mine.minefield[i][j].isOpen())
											bArr[i][j].setGraphic(null);// if flag and open reset the flag image
										bArr[i][j].setText(mine.get(i, j));
									}
								}
							} // if bomb and there is no flag
							else if (mine.minefield[bx][by].isBomb() && !mine.minefield[bx][by].isFlag()) {
								mine.setShowAll(true);//open all the bombs
								for (int i = 0; i < mine.width; i++) {// rows
									for (int j = 0; j < mine.height; j++) {// columns
										if (mine.minefield[i][j].isBomb() && !mine.minefield[i][j].isFlag()) {
											//if a bomb and there is no flag
											ImageView bombimg = new ImageView("mines/Bomb.png");//get a view for the bomb image
											bombimg.setFitHeight(20);//set height
											bombimg.setFitWidth(20);//set width
											bombimg.setPreserveRatio(true);//preserve the ratio of the picture
											bArr[i][j].setGraphic(bombimg);//set the graphic for the button
											bArr[i][j].setStyle("-fx-background-color: #FF4444");//paint the bomb cell backgrounds to red-ish color
										}
									}
								}
								gameflag = false;//game is over
							}
						} // end of left click
						else if (mouseEvent.getButton() == MouseButton.SECONDARY) {// right click toggles the flag
							if (!mine.minefield[bx][by].isOpen()) {//if not already open
								mine.toggleFlag(bx, by);//set flag logic
								if (!mine.minefield[bx][by].isFlag()) {//if already a flag
									b.setGraphic(null);//remove the image
									b.setText(".");//set text back to be "."
								} else {//if was "."
									ImageView flagimg = new ImageView("mines/flag.png");//set a view for the flag image
									flagimg.setFitHeight(20);//set height
									flagimg.setFitWidth(20);//set width
									flagimg.setPreserveRatio(true);//preserve the ratio of the picture
									b.setGraphic(flagimg);//set flag image
								}
							}
						}
						if (mine.isDone()) {//whenever the user opens a box we check if he won the game
							Alert alert = new Alert(AlertType.INFORMATION);//create anew alert for when winning the game 
							alert.setTitle("You are amazing");//title for the alert
							alert.setHeaderText("You won the game of the amazing mines sweeper");//main message for the alert
							alert.setContentText("press ok to close window");//button message
							ImageView win =new ImageView("mines/win.png");//image for winning window
							win.preserveRatioProperty();
							win.setFitHeight(100);
							win.setFitWidth(100);
							alert.getDialogPane().setGraphic(win);//set graphic instead of the original i icon
							alert.showAndWait().ifPresent(rs -> {//wait for button press
								if (rs == ButtonType.OK) {
								}
							});
							gameflag = false;
						}
					}
				});
				bArr[i][j] = b;//add new button for the array
				mineGrid.add(b, i, j);//add the button to the grid
			}
		}
		hbox.autosize();//autoSize the the HBox 
		primaryStage.sizeToScene();//resize the scene window
		gameflag = true;//set the game to be on
		return mineGrid;
	}
}