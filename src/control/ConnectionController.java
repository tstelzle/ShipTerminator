package control;

import java.io.IOException;
import java.net.DatagramSocket;

public class ConnectionController {
	
	private ClientController clientController;

	public ConnectionController(ClientController clientController) {
		this.clientController = clientController;
	}
	
	public void establishConnection(String ip, int port) throws IOException {
		clientController.getClient().setConnection(ip, port);
	}
		
	public void sendMessage(String message) {
		clientController.getClient().getConnection().sentMessage(message);
		
	}
	
	public String receiveMessage() {
		String message = clientController.getClient().getConnection().receive();
		return message;
	}
	
	public void stopConnection() {
		clientController.getClient().getConnection().stop();
	}
	
	public void startListening() throws IOException {
		Thread listener = new Thread(new Handler(clientController.getClient().getConnection().getSocket()));
		listener.start();
	}
	
	public class Handler implements Runnable {
	
		String message;
		public Handler(DatagramSocket mySocket) throws IOException {}
		
		@Override
		public void run() {
			try {
				while((message = receiveMessage()) != null) {
					System.out.println("From Connection: " + message);
				}
			}
			catch(Exception e) {}
			
		}
	}
}
