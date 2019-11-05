package model;

public class Board {
	
	private int height;
	private int width;
	/*
	 * The boardName value is either 'M' if it is your own board or 'E' if its the enemy board.
	 */
	private char boardName;
	/*
	 * matrix which displays the board, 0 represents water, 
	 * 1 represents a part of a ship, 2 if there is a missile and 3 if the missile hit a ship
	 */
	private int[][] board;
	
	public Board(char boardName) {
		board = new int[10][10];
		this.height = 10;
		this.width = 10;
		this.boardName = boardName;
	}
	
	public Board(int height, int width, char boardName) {
		this.height = height;
		this.width = width;
		board = new int[height][width];
		fillBoardWithWater();
		this.boardName = boardName;
	}
	
	private void fillBoardWithWater() {
		for(int h=0;h<this.getHeight(); h++) {
			for(int w=0;w<this.getWidth(); w++) {
				this.setValue(h, w, 0);
			}
		}
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public int[][] getBoard() {
		return board;
	}
	
	public int getValue(int height, int width) {
		return board[height][width];
	}
	
	public void setValue(int height, int width, int value) {
		board[height][width] = value;
	}
	
	public char getBoardName() {
		return boardName;
	}


	public enum Hit {HIT, NOHIT};
	
}
