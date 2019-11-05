package control;

import model.Client;
import model.Ship;

public class PlayerController {
	
	private ClientController clientController;

	public PlayerController(ClientController clientController) {
		this.clientController = clientController;
	}
	
	/*
	 * sets the maximal hitpoins of the player according to the shipamount and size
	 */
	public void setMaxHitPoints() {
		int maxHitPoints = 0;
		for(Ship ship : clientController.getClient().getAllShips()) {
			maxHitPoints += ship.getSize();
		}
		System.out.println("hitPoints: " + clientController.getClient().getHitPoints());
		System.out.println("MaxHitPoints: " + maxHitPoints);
		clientController.getClient().setMaxhitPoints(maxHitPoints);
	}
	
	/*
	 * checks if alle ships of the player have been destroyed
	 */
	public boolean checkLoss() {
		Client client = clientController.getClient();
		if(client.getHitPoints() == client.getMaxhitPoints()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean checkVictory() {
		Client client = clientController.getClient();
		if(client.getEnemyHitPoints() == client.getMaxhitPoints()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public void sendAvailable() {
		clientController.getConnectionController().sendMessage("available " + clientController.getClient().getName());
	}
	
	public void requestPlayerlist() {
		clientController.getConnectionController().sendMessage("request_list");
	}
	
	public void sendRequest(String enemyName) {
		clientController.getConnectionController().sendMessage("request_player " + enemyName);
	}
}
 