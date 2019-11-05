package control;

import model.Client;

public class ClientController {
	
	private Client client;
	private ConnectionController connectionController;
	private PlayerController playerController;
	private ShipController shipController;

	public Client getClient() {
		if(client == null) {
			client = new Client();
		}
		return client;
	}

	public ConnectionController getConnectionController() {
		if(connectionController == null) {
			connectionController = new ConnectionController(this);
		}
		return connectionController;
	}

	public PlayerController getPlayerController() {
		if(playerController == null) {
			playerController = new PlayerController(this);
		}
		return playerController;
	}
	
	public ShipController getShipController() {
		if(shipController == null) {
			shipController = new ShipController(this);
		}
		return shipController;
	}
}
