package control;

import model.Board;
import model.Ship;
import model.Board.Hit;
import model.Ship.Direction;
import model.Ship.ShipType;

public class ShipController {
	
	private ClientController clientController;

	public ShipController(ClientController clientController) {
		this.clientController = clientController;
	}

	/*
	 * adds ships to the game, the three values get the amount of the ship
	 * There are three different shipsizes -> Enum in model.Ship
	 */
	public void initShips(int amountBigShips, int amountMediumShips, int amountSmallShips) {
		for(int b=0;b<amountBigShips;b++) {
			Ship bigShip = new Ship(5, ShipType.BIG);
			clientController.getClient().getAllShips().add(bigShip);
		}
		for(int m=0;m<amountMediumShips;m++) {
			Ship mediumShip = new Ship(4, ShipType.MEDIUM);
			clientController.getClient().getAllShips().add(mediumShip);
		}
		for(int s=0;s<amountSmallShips;s++) {
			Ship smallShip = new Ship(2, ShipType.SMALL);
			clientController.getClient().getAllShips().add(smallShip);
		}
		clientController.getClient().setShipsOnField(0);
	}
	
	/*
	 * gets the coordinates for the attack from the enemy
	 * returns the enum Hit (model.Ship)
	 */
	public Hit enemyAttack(int height, int width) {
		Board myBoard = clientController.getClient().getMyBoard();
		if(myBoard.getValue(height, width) == 0) {
			myBoard.setValue(height, width, 2);
			return Hit.NOHIT;
		}
		else if (myBoard.getValue(height, width) == 1) { 
			myBoard.setValue(height, width, 3);
			int hitted = clientController.getClient().getHitPoints();
			clientController.getClient().setHitPoints(hitted + 1);
			return Hit.HIT;
		}
		else {
			System.out.println("Feld wurde schonmal beschossen.");
			return Hit.NOHIT;
		}
	}
	
	/*
	 * sends coordiantes of my Attack to the enemy
	 */
	public void myAttack(int height, int width) {
		clientController.getConnectionController().sendMessage("attack " + height + " " + width);
	}
	
	/*
	 * outputs the board as a string to the console
	 */
	public String boardToString(Board board) {
		String output = board.getBoardName() + "  ";
		for(int i=0; i<board.getWidth(); i++) {
			output = output + i + " ";
		}
		output = output + "\n  ";
		for(int i=0; i<board.getWidth(); i++) {
			output = output + "--";
		}
		output = output + "\n";
		for(int h=0; h<board.getHeight(); h++) {
			output = output + h + " |";
			for(int w=0; w<board.getWidth(); w++) {
				output = output + board.getValue(h, w) + " ";
			}
			output = output + "\n";
		}
		return output;
	}
	
	/*
	 * puts the Ship ship on the field starting with the coordinate (height, width) and showing in the direction
	 */
	public void putShips(Ship ship, int height, int width, Direction direction) {
		switch (direction) {
		case LEFT: 
			if(width-ship.getSize() < 0) {
				System.out.println("Schiff hat da keinen Platz.");
			}
			else {
				for(int i=width;i>width-ship.getSize(); i--) {
					clientController.getClient().getMyBoard().setValue(height, i, 1);
				}
				ship.setOnField(true);
				clientController.getClient().setShipsOnField(clientController.getClient().getShipsOnField()+1);
			}
			break;
		case RIGHT: 
			if(width+ship.getSize() > 9) {
				System.out.println("Schiff hat da keinen Platz.");
			}
			else {
				for(int i=width;i<width+ship.getSize(); i++) {
					clientController.getClient().getMyBoard().setValue(height, i, 1);
				}
				ship.setOnField(true);
				clientController.getClient().setShipsOnField(clientController.getClient().getShipsOnField()+1);
			}
			break;
		case UP: 
			if(height-ship.getSize() < 0) {
				System.out.println("Schiff hat da keinen Platz.");
			}
			else {
				for(int i=height;i>height-ship.getSize(); i--) {
					clientController.getClient().getMyBoard().setValue(i, width, 1);
				}
				ship.setOnField(true);
				clientController.getClient().setShipsOnField(clientController.getClient().getShipsOnField()+1);
			}
			break;
		case DOWN:
			if(height+ship.getSize() > 9) {
				System.out.println("Schiff hat da keinen Platz.");
			}
			else {
				for(int i=height;i<height+ship.getSize(); i++) {
					clientController.getClient().getMyBoard().setValue(i, width, 1);
				}
				ship.setOnField(true);
				clientController.getClient().setShipsOnField(clientController.getClient().getShipsOnField()+1);
			}
			break;
		}
	}
}
