package application;

import javafx.scene.layout.GridPane;
import javafx.scene.control.ToggleButton;

public abstract class Player implements Operations {
	final int numShip = 5;
	private Ship[] ships;
	private char[][] grid;
	
	/**
	 * Default constructor for Player
	 */
	public Player() {
		Carrier carrier = new Carrier();
		Battleship battleship = new Battleship();
		Destroyer destroyer = new Destroyer();
		Submarine submarine = new Submarine();
		PatrolBoat patrolboat = new PatrolBoat();
		ships = new Ship[] {carrier, battleship, destroyer, submarine, patrolboat};
		grid = new char[10][10];
	}
	
	/**
	 * Accessor method for "ships"
	 * @return all ships in an array of Ship objects
	 */
	public Ship[] getShips() {
		return ships;
	}
	
	/**
	 * Accessor method searching for a specific ship by name
	 * @param n - name of the ship to search
	 * @return the ship specified by name
	 */
	public Ship getShipByName(String n) {
		for (Ship ship : ships) {
			if (ship.getName() == n) {
				return ship;
			}
		}
		return null;
	}
	
	/**
	 * Accessor method searching for a specific ship by id
	 * @param id - id of the ship to search
	 * @return the ship specified by id
	 */
	public Ship getShipByID(char id) {
		for (Ship ship : ships) {
			if (ship.getID() == id) {
				return ship;
			}
		}
		return null;
	}
	
	/**
	 * Accessor method for "grid"
	 * @return a 2D array of char values
	 */
	public char[][] getGrid() {
		return grid;
	}
	
	/**
	 * Accessor method for "grid" at specific column and row number
	 * @param col - column number of the intented location
	 * @param row - row number of the intented location
	 * @return a char value from "grid" specified by column and row number
	 */
	public char getGridByColRow(int col, int row) {
		return grid[row][col];
	}
	
	/**
	 * Mutator method for "grid" at specific column and row number
	 * @param gp - game board where the change will be made
	 * @param col - column number of the intented location
	 * @param row - row number of the intented location
	 * @param c - a char value for setting the specified grid value to
	 * @param s - color that the grid on the game board would be changed to
	 */
	public void setGridByColRow(GridPane gp, int col, int row, char c, String s) {
		char id;
		if (grid[row][col] != '\u0000') {
			id = grid[row][col];
		} else {
			id = c;
		}
		grid[row][col] = c;
		setBoardByColRow(gp, col, row, id, s);
	}
	
	/**
	 * Method for checking the availability of grid in horizontal direction
	 * @param col - column number of the intented checking location
	 * @param row - row number of the intented checking location
	 * @param size - number of available grids
	 * @return a boolean value for whether or not the grids are available
	 */
	public boolean checkHorizontal(int col, int row, int size) {
		for (int i = col; i < col + size; i++) {
			if (grid[row][i] != '\u0000') {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Method for checking the availability of grid in vertical direction
	 * @param col - column number of the intented checking location
	 * @param row - row number of the intented checking location
	 * @param size - number of available grids
	 * @return a boolean value for whether or not the grids are available
	 */
	public boolean checkVertical(int col, int row, int size) {
		for (int i = row; i < row + size; i++) {
			if (grid[i][col] != '\u0000') {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Method for setting the grid according to the ship placement in horizontal direction
	 * @param gp - game board where the ship will be placed
	 * @param col - column number of the placement location
	 * @param row - row number of the placement location
	 * @param size - size of the ship to be placed
	 * @param id - id of the ship
	 * @param b - whether or not showing the changes on the game board
	 * @param s - color that the grid on the game board would be changed to
	 */
	public void placeGridHorizontal(GridPane gp, int col, int row, int size, char id, boolean b, String s) {
		for (int i = col; i < col + size; i++) {
			grid[row][i] = id;
			if (b) {
				setBoardByColRow(gp, i, row, id, s);
			}
		}
	}
	
	/**
	 * Method for setting the grid according to the ship placement in vertical direction
	 * @param gp - game board where the ship will be placed
	 * @param col - column number of the placement location
	 * @param row - row number of the placement location
	 * @param size - size of the ship to be placed
	 * @param id - id of the ship
	 * @param b - whether or not showing the changes on the game board
	 * @param s - color that the grid on the game board would be changed to
	 */
	public void placeGridVertical(GridPane gp, int col, int row, int size, char id, boolean b, String s) {
		for (int i = row; i < row + size; i++) {
			grid[i][col] = id;
			if (b) {
				setBoardByColRow(gp, col, i, id, s);
			}
		}
	}
	
	/**
	 * Method for changing the appearance of the grid on the game board
	 * @param gp - game board that will be changed
	 * @param col - column number of the changing grid
	 * @param row - row number of the changing grid
	 * @param c - character to be shown in the specified grid
	 * @param s - color that grid will be changed to
	 */
	public void setBoardByColRow(GridPane gp, int col, int row, char c, String s) {
		for (javafx.scene.Node node : gp.getChildren()) {
			if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {				
				ToggleButton btn = (ToggleButton)node;
				btn.setText(Character.toString(c));
				btn.setStyle("-fx-background-color: " + s);
				btn.setDisable(true);
			}
		}
	}
	
	/**
	 * Method for checking if all the ships are destroyed
	 * @return a boolean for whether or not all ships are destroyed
	 */
	public boolean allDestroyed() {
		int destroyed = 0;
		for (Ship ship : ships) {
			if (ship.getIsDestroyed() == true) {
				destroyed++;
			}
		}
		if(destroyed == numShip) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Method for ship placement
	 * @param gp - game board that ship will be placed on
	 * @param name - name of the ship to be placed
	 * @param dir - direction of the placement
	 * @param col - column number of the placement location
	 * @param row - row number of the placement location
	 * @return a boolean value for whether the ship is successfully placed
	 */
	@Override
	public abstract boolean placeShip(GridPane gp, String name, String dir, int col, int row);
	
	/**
	 * Method for attack
	 * @param p - player that will be attacked
	 * @param gp - game board where the attack will happen
	 * @param col - column number of the attacking location
	 * @param row - row number of the attacking location
	 * @return a boolean value for whether a ship is attacked
	 */
	@Override
	public abstract boolean attack(Player p, GridPane gp, int col, int row);
}
