package model;

public class Ship {
	
	private int size;
	private ShipType type;
	private boolean onField;
	
	public Ship(int size, ShipType type) {
		this.size = size;
		this.type = type;
	}
	
	public boolean isOnField() {
		return onField;
	}
	public void setOnField(boolean onField) {
		this.onField = onField;
	}
	public int getSize() {
		return size;
	}
	public ShipType getType() {
		return type;
	}

	public enum ShipType {BIG, MEDIUM, SMALL};
	public enum Direction {LEFT, RIGHT, UP, DOWN};
}
