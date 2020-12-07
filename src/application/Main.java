package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

public class Main extends Application {
	private Label logContent;
	private Label ucarrierHP, ubattleshipHP, udestroyerHP, usubmarineHP, upatrolboatHP;
	private Label ccarrierHP, cbattleshipHP, cdestroyerHP, csubmarineHP, cpatrolboatHP;
	private ToggleGroup ship;
	private GridPane uBoard;
	private static GridPane cBoard;
	private ToggleGroup direction;
	private Button place, start;
	private static HumanPlayer humanPlayer = new HumanPlayer();
	private static ComputerPlayer computerPlayer = new ComputerPlayer();
	private String selectedShip;
	private String selectedDir;
	private int selectedCol = -1;
	private int selectedRow = -1;
	private static int placed = 0;
	
	@Override
	public void start(Stage primaryStage) {
		//title for window
		primaryStage.setTitle("Battleship");
		
		
		
		logContent = new Label("Ship your ships");
		
		/*----------Information Area----------*/
		Label yourShips = new Label("Your Ships");
		
		ship = new ToggleGroup();
		RadioButton carrier = setShipButton("Carrier", ship);
		RadioButton battleship = setShipButton("Battleship", ship);
		RadioButton destroyer = setShipButton("Destroyer", ship);
		RadioButton submarine = setShipButton("Submarine", ship);
		RadioButton patrolboat = setShipButton("PatrolBoat", ship);		
		
		Label placement = new Label("Placement");
		
		direction = new ToggleGroup();
		RadioButton horizontal = setDirButton("Horizontal", direction);
		RadioButton vertical = setDirButton("Vertical", direction);
		
		place = new Button("Place Ship");
		place.setOnAction(new placeClickHandler());
		start = new Button("Start Game");
		start.setDisable(true);
		start.setOnAction(new startClickHandler());
		
		Label log = new Label("Log");
		
		//VBox for info
		VBox vInfo = new VBox(yourShips, carrier, battleship, destroyer, submarine, patrolboat, placement, horizontal, vertical, place, start, log, logContent);
		vInfo.setStyle("-fx-background-color: #A9A9A9; -fx-min-width: 120px;");

		
		
		
		/*----------Board Area----------*/
		//user board
		Label userBoard = new Label("User Board");
		
		ToggleGroup uGrid = new ToggleGroup();
		
		uBoard = new GridPane();
		for (int col = 0; col < 10; col++) {
			for (int row = 0; row < 10; row++) {
				uBoard.add(setuGridButton(uGrid, false), col, row);
			}
		}
		
		ucarrierHP = new Label("Carrier: " + humanPlayer.getShipByName("Carrier").getHP());
		ubattleshipHP = new Label("Battleship: " + humanPlayer.getShipByName("Battleship").getHP());
		udestroyerHP = new Label("Destroyer: " + humanPlayer.getShipByName("Destroyer").getHP());
		usubmarineHP = new Label("Submarine: " + humanPlayer.getShipByName("Submarine").getHP());
		upatrolboatHP = new Label("PatrolBoat: " + humanPlayer.getShipByName("PatrolBoat").getHP());
		
		//VBox for user board
		VBox vUser = new VBox(userBoard, uBoard, ucarrierHP, ubattleshipHP, udestroyerHP, usubmarineHP, upatrolboatHP);
		
		
		
		//computer board
		Label computerBoard = new Label("Computer Board");
		
		ToggleGroup cGrid = new ToggleGroup();
		
		cBoard = new GridPane();
		for (int col = 0; col < 10; col++) {
			for (int row = 0; row < 10; row++) {
				cBoard.add(setcGridButton(cGrid, true), col, row);
			}
		}
		
		ccarrierHP = new Label("Carrier: " + computerPlayer.getShipByName("Carrier").getHP());
		cbattleshipHP = new Label("Battleship: " + computerPlayer.getShipByName("Battleship").getHP());
		cdestroyerHP = new Label("Destroyer: " + computerPlayer.getShipByName("Destroyer").getHP());
		csubmarineHP = new Label("Submarine: " + computerPlayer.getShipByName("Submarine").getHP());
		cpatrolboatHP = new Label("PatrolBoat: " + computerPlayer.getShipByName("PatrolBoat").getHP());
		
		//VBox for computer board
		VBox vComputer = new VBox(computerBoard, cBoard, ccarrierHP, cbattleshipHP, cdestroyerHP, csubmarineHP, cpatrolboatHP);			
		
		//HBox for board area
		HBox hBoard = new HBox(20, vUser, vComputer);
		hBoard.setId("hBoard");
		
		
		
		//HBox that contains everything
		HBox hbox = new HBox(10, vInfo, hBoard);
		
		
		//create a scene and display it
		Scene scene = new Scene(hbox, 700, 400);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	/**
	 * Method for generating radio button for ship selection
	 * @param n - name of ship
	 * @param tg - toggle group that button belongs to
	 * @return a radio button for ship selection
	 */
	public RadioButton setShipButton(String n, ToggleGroup tg) {
		RadioButton btn = new RadioButton(n);
		
		btn.setToggleGroup(tg);
		
		btn.setOnAction(e->{
			selectedShip = btn.getText();
			if (selectedDir != null) {
				int size = humanPlayer.getShipByName(selectedShip).getSize();
				for (Node node : uBoard.getChildren()) {
					ToggleButton tbtn = (ToggleButton)node;
					tbtn.setDisable(false);
					if (selectedDir == "Horizontal") {
						if (GridPane.getColumnIndex(tbtn) > 10 - size) {
							tbtn.setDisable(true);
							if (tbtn.isSelected()) {
								tbtn.setSelected(false);
								selectedCol = -1;
								selectedRow = -1;
							}
						}
					} else {
						if (GridPane.getRowIndex(tbtn) > 10 - size) {
							tbtn.setDisable(true);
							if (tbtn.isSelected()) {
								tbtn.setSelected(false);
								selectedCol = -1;
								selectedRow = -1;
							}
						}
					}
				}
			}
			logContent.setText("Ship: " + selectedShip + "\n");
		});
		
		return btn;
	}
	
	/**
	 * Method for generating radio button for placement direction
	 * @param n - direction
	 * @param tg - toggle group that button belongs to
	 * @return a radio button for placement direction
	 */
	public RadioButton setDirButton(String n, ToggleGroup tg) {
		RadioButton btn = new RadioButton(n);
		
		btn.setToggleGroup(tg);
		
		btn.setOnAction(e->{
			selectedDir = btn.getText();
			if (selectedShip != null) {
				int size = humanPlayer.getShipByName(selectedShip).getSize();
				for (Node node : uBoard.getChildren()) {
					ToggleButton tbtn = (ToggleButton)node;
					tbtn.setDisable(false);
					if (selectedDir == "Horizontal") {	
						if (GridPane.getColumnIndex(tbtn) > 10 - size) {
							tbtn.setDisable(true);
							if (tbtn.isSelected()) {
								tbtn.setSelected(false);
								selectedCol = -1;
								selectedRow = -1;
							}
						}
					} else {
						if (GridPane.getRowIndex(tbtn) > 10 - size) {
							tbtn.setDisable(true);
							if (tbtn.isSelected()) {
								tbtn.setSelected(false);
								selectedCol = -1;
								selectedRow = -1;
							}
						}
					}
				}
			}
			logContent.setText("Direction: " + selectedDir + "\n");
		});
		
		return btn;
	}
	
	/**
	 * Method for generating toggle button used in user game board
	 * button return its column and row indexes when clicked
	 * @param tg - toggle group that button belongs to
	 * @param l - label for printing out information
	 * @return - a toggle button used in user game board
	 */
	public ToggleButton setuGridButton(ToggleGroup tg, boolean dis) {
		ToggleButton btn = new ToggleButton("");
		
		btn.setToggleGroup(tg);
		
		btn.setPrefSize(25, 25);
		
		btn.setDisable(dis);
		
		btn.setOnAction(e->{
			selectedCol = GridPane.getColumnIndex(btn);
			selectedRow = GridPane.getRowIndex(btn);
			logContent.setText("Column: " + selectedCol + "\nRow: " + selectedRow);
		});
		
		return btn;
	}
	
	/**
	 * Method for generating toggle button used in computer game board
	 * button attack 
	 * @param tg - toggle group that button belongs to
	 * @param l - label for printing out information
	 * @return - a toggle button used in computer game board
	 */
	public ToggleButton setcGridButton(ToggleGroup tg, boolean dis) {
		ToggleButton btn = new ToggleButton("");
		
		btn.setToggleGroup(tg);
		
		btn.setPrefSize(25, 25);
		
		btn.setDisable(dis);
		
		btn.setOnAction(e->{
			selectedCol = GridPane.getColumnIndex(btn);
			selectedRow = GridPane.getRowIndex(btn);
			logContent.setText("Column: " + selectedCol + "\nRow: " + selectedRow);
			if (selectedCol >= 0 && selectedRow >= 0) {
				humanPlayer.attack(computerPlayer, cBoard, selectedCol, selectedRow);
				ucarrierHP.setText("Carrier: " + humanPlayer.getShipByName("Carrier").getHP());
				ubattleshipHP.setText("Battleship: " + humanPlayer.getShipByName("Battleship").getHP());
				udestroyerHP.setText("Destroyer: " + humanPlayer.getShipByName("Destroyer").getHP());
				usubmarineHP.setText("Submarine: " + humanPlayer.getShipByName("Submarine").getHP());
				upatrolboatHP.setText("PatrolBoat: " + humanPlayer.getShipByName("PatrolBoat").getHP());
				computerPlayer.attack(humanPlayer, uBoard, 0, 0);
				ccarrierHP.setText("Carrier: " + computerPlayer.getShipByName("Carrier").getHP());
				cbattleshipHP.setText("Battleship: " + computerPlayer.getShipByName("Battleship").getHP());
				cdestroyerHP.setText("Destroyer: " + computerPlayer.getShipByName("Destroyer").getHP());
				csubmarineHP.setText("Submarine: " + computerPlayer.getShipByName("Submarine").getHP());
				cpatrolboatHP.setText("PatrolBoat: " + computerPlayer.getShipByName("PatrolBoat").getHP());
			} else {
				logContent.setText("Select Grid");
			}
			if (humanPlayer.allDestroyed() || computerPlayer.allDestroyed()) {
				for (Node node : cBoard.getChildren()) {
					ToggleButton tbtn = (ToggleButton)node;
					tbtn.setDisable(true);
				}
				String result;
				if (humanPlayer.allDestroyed()) {
					result = "You Lose";
				} else {
					result = "You Won";
				}
				Alert a = new Alert(AlertType.INFORMATION, result, ButtonType.OK);
				a.show();
			}
		});
		
		return btn;
	}
	
	/**
	 * Event handler for placement button
	 * @author Wye
	 *
	 */
	class placeClickHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			if (selectedShip != null) {
				if (selectedDir != null) {
					if (selectedCol >= 0 && selectedRow >= 0) {
						if (humanPlayer.placeShip(uBoard, selectedShip, selectedDir, selectedCol, selectedRow)) {
							Toggle t = ship.getSelectedToggle();
							RadioButton btn = (RadioButton)t;
							btn.setDisable(true);
							ship.selectToggle(null);
							selectedShip = null;
							selectedCol = -1;
							selectedRow = -1;
							placed++;
							logContent.setText("Placement Succeed");
						} else {
							logContent.setText("Placement Failed");
						}
					} else {
						logContent.setText("Select Grid");
					}
				} else {
					logContent.setText("Select Direction");
				}
			} else {
				logContent.setText("Select Ship");
			}
			if (placed == humanPlayer.numShip) {
				Object e = event.getSource();
				Button btn = (Button)e;
				btn.setDisable(true);
				start.setDisable(false);
				for (Toggle t : direction.getToggles()) {
					RadioButton rbtn = (RadioButton)t;
					rbtn.setDisable(true);
				}
				for (Node node : uBoard.getChildren()) {
					ToggleButton tbtn = (ToggleButton)node;
					tbtn.setDisable(true);
				}
				logContent.setText("Start Game");
			}
		}
	}
	
	/**
	 * Event handler for game start button
	 * @author Wye
	 *
	 */
	class startClickHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			Object e = event.getSource();
			Button btn = (Button)e;
			btn.setDisable(true);
			computerPlayer.placeShip(cBoard, "name", "direction", 0, 0);
			for (Node node : cBoard.getChildren()) {
				ToggleButton tbtn = (ToggleButton)node;
				tbtn.setDisable(false);
			}
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
