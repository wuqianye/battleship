package application;

import javafx.scene.layout.GridPane;

public class HumanPlayer extends Player {
	
	/**
	 * Default constructor for HumanPlayer
	 */
	public HumanPlayer() {
		super();
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
	public boolean placeShip(GridPane gp, String name, String dir, int col, int row) {
		if (dir == "Horizontal") {
			if (checkHorizontal(col, row, getShipByName(name).getSize())) {
				placeGridHorizontal(gp, col, row, getShipByName(name).getSize(), getShipByName(name).getID(), true, "#87CEFA");
				getShipByName(name).setIsPlaced();
				return true;
			} else {
				return false;
			}
		} else {
			if (checkVertical(col, row, getShipByName(name).getSize())) {
				placeGridVertical(gp, col, row, getShipByName(name).getSize(), getShipByName(name).getID(), true, "#87CEFA");
				getShipByName(name).setIsPlaced();
				return true;
			} else {
				return false;
			}
		}
	}

	/**
	 * Method for attack
	 * @param p - player that will be attacked
	 * @param gp - game board where the attack will happen
	 * @param col - column number of the attacking location
	 * @param row - row number of the attacking location
	 * @return a boolean value for whether a ship is attacked
	 */
	@Override
	public boolean attack(Player p, GridPane gp, int col, int row) {
		if (p.getGridByColRow(col, row) != '\u0000') {
			char id = p.getGridByColRow(col, row);
			p.setGridByColRow(gp, col, row, 'X', "#F08080");
			p.getShipByID(id).decrementHP();
			if (p.getShipByID(id).getHP() == 0) {
				p.getShipByID(id).setIsDestroyed();
			}
			return true;
		} else {
			p.setGridByColRow(gp, col, row, 'O', "#D3D3D3");
			return false;
		}	
	}
}
