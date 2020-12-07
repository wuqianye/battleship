package application;

import javafx.scene.layout.GridPane;

public interface Operations {
	
	/**
	 * Method for ship placement
	 * @param gp - game board that ship will be placed on
	 * @param name - name of the ship to be placed
	 * @param dir - direction of the placement
	 * @param col - column number of the placement location
	 * @param row - row number of the placement location
	 * @return a boolean value for whether the ship is successfully placed
	 */
	public boolean placeShip(GridPane gp, String name, String dir, int col, int row);
	
	/**
	 * Method for attack
	 * @param p - player that will be attacked
	 * @param gp - game board where the attack will happen
	 * @param col - column number of the attacking location
	 * @param row - row number of the attacking location
	 * @return a boolean value for whether a ship is attacked
	 */
	public boolean attack(Player p, GridPane gp, int col, int row);
}
