package application;

import javafx.scene.layout.GridPane;

public class ComputerPlayer extends Player {
	
	/**
	 * Default constructor for ComputerPlayer
	 */
	public ComputerPlayer() {
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
		int randDir;
		int placed = 0;
		for (Ship ship : getShips()) {
			randDir = (int)(Math.random() * 10);
			if (randDir < 6) {
				dir = "Horizontal";
				col = (int)(Math.random() * (10 - ship.getSize()));
				row = (int)(Math.random() * 10);
			} else {
				dir = "Vertical";
				col = (int)(Math.random() * 10);
				row = (int)(Math.random() * (10 - ship.getSize()));
			}
			
			
			if (dir == "Horizontal") {
				while (ship.getIsPlaced() == false) {
					if (checkHorizontal(col, row, ship.getSize())) {
						placeGridHorizontal(gp, col, row, ship.getSize(), ship.getID(), false, "#87CEFA");
						ship.setIsPlaced();
						placed++;
					}
				}
			} else {
				while (ship.getIsPlaced() == false) {
					if (checkVertical(col, row, ship.getSize())) {
						placeGridVertical(gp, col, row, ship.getSize(), ship.getID(), false, "#87CEFA");
						ship.setIsPlaced();
						placed++;
					}
				}
			}
		}
		if (placed == 5) {
			return true;
		} else {
			return false;
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
		do {
			col = (int)(Math.random() * 10);
			row = (int)(Math.random() * 10);
		} while (p.getGridByColRow(col, row) == 'X' || p.getGridByColRow(col,row) == 'O');
		
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
