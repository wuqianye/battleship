package application;

public class Ship {
	private String name;
	private char id;
	private int size;
	private int hp;
	private boolean isPlaced;
	private boolean isDestroyed;
	
	/**
	 * Default constructor for Ship
	 */
	public Ship() {
		name = "";
		id = '\u0000';
		size = 0;
		hp = 0;
		isPlaced = false;
		isDestroyed = false;
	}
	
	/**
	 * Constructor for Ship
	 * @param n - value for "name"
	 * @param i - value for "id"
	 * @param s - value for "size"
	 */
	public Ship(String n, char i, int s) {
		name = n;
		id = i;
		size = s;
		hp = s;
		isPlaced = false;
		isDestroyed = false;
	}
	
	/**
	 * Accessor method for "name"
	 * @return value of "name"
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Accessor method for "id"
	 * @return value of "id"
	 */
	public char getID() {
		return id;
	}
	
	/**
	 * Accessor method for "size"
	 * @return value of "size"
	 */
	public int getSize() {
		return size;
	}
	
	/**
	 * Accessor method for "hp"
	 * @return value of "hp"
	 */
	public int getHP() {
		return hp;
	}
	
	/**
	 * Accessor method for "isPlaced"
	 * @return value of "isPlaced"
	 */
	public boolean getIsPlaced() {
		return isPlaced;
	}
	
	/**
	 * Accessor method for "isDestroyed"
	 * @return value of "isDestroyed"
	 */
	public boolean getIsDestroyed() {
		return isDestroyed;
	}
	
	/**
	 * Decrement the value of "hp" by 1
	 * if "hp" reaches 0, "isDestroyed" is set to true
	 */
	public void decrementHP() {
		hp--;
		if (hp == 0) {
			setIsDestroyed();
		}
	}
	
	/**
	 * Mutator method for "isPlaced"
	 * set "isPlaced" to true
	 */
	public void setIsPlaced() {
		isPlaced = true;
	}
	
	/**
	 * Mutator method for "isDestroyed"
	 * set "isDestroyed" to true
	 */
	public void setIsDestroyed() {
		isDestroyed = true;
	}
}
