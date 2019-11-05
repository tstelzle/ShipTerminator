//package control;
//
//import java.io.IOException;
//import java.net.InetAddress;
//import java.util.Scanner;
//import java.util.StringTokenizer;
//import java.util.concurrent.TimeUnit;
//
//import model.Board;
//import model.Board.Hit;
//import model.Ship;
//import model.Ship.Direction;
//import model.Ship.ShipType;
//
//public class ConsoleController {
//	
//	public static void main(String[] args) throws IOException {
//		ClientController clientController = new ClientController();
//		
//		clientController.getShipController().initShips(1,0,1);
//		clientController.getPlayerController().setMaxHitPoints();
//				
//		@SuppressWarnings("resource")
//		Scanner scanner = new Scanner(System.in);
//		
//		System.out.println("Schiffe versenken");
//		
//		System.out.println("Mit welchem Server willst du dich verbinden?");
//		String ip = scanner.next();
//		System.out.println("Welchen Port willst du verwenden?");
//		int port = scanner.nextInt();
//		
//		System.out.println("OK! verbinde mit " + ip + " auf Port " + port + ".");
//		
//		try {
//			clientController.getConnectionController().establishConnection(ip, port);
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		
//		String message = "";
//		int answer;
//		/*
//		 * Block for getting the initial server, should be hardcoded but server changes right now
//		 */
//		boolean accepted = false;
//		while(!accepted) {
//			
//			System.out.println("Wie soll dein Spielername sein?");
//			String name = scanner.next();
//			clientController.getClient().setName(name);
//			
//			clientController.getPlayerController().sendAvailable();
//			
//			message = clientController.getConnectionController().receiveMessage();
//			message = message.trim();
//			
//			if(message.equals("200")) {
//				System.out.println("Deine Verbindung wurde angenommen.");
//				accepted = true;
//			}
//			else {
//				System.out.println("Leider ist was schief gelaufen. Probiere es doch erneut!");
//			}
//		}
//		
//		/*
//		 * Block for getting the connection to the enemy
//		 */
//		boolean partner = false;
//		while(!partner) {
//			boolean liste = false;
//			while(!liste) {
//				System.out.println("Willst du die Spielerliste wissen?\n1-ja\n2-nein");
//				answer = scanner.nextInt();
//				if(answer == 1) {
//					clientController.getPlayerController().requestPlayerlist();
//					message = clientController.getConnectionController().receiveMessage();
//					if(message.substring(0, 3).equals("201")) {
//						int amount = Integer.parseInt(message.substring(4).trim());
//						for(int a=0;a<amount; a++) { 
//							message = clientController.getConnectionController().receiveMessage().trim();
//							if(!message.equals(clientController.getClient().getName())) {
//								System.out.println(message);
//							}
//						}
//						liste = true;
//					}
//					else {
//						System.out.println("Die Spielerliste konnnte nicht empfangen werden.");				}
//				}
//				else if(answer == 2) {
//					System.out.println("Okay.");
//					liste = true;
//				}
//				else {
//					System.out.println("Das ist keine mögliche Antwort.");
//				}
//			}
//			
//			System.out.println("Möchtest du eine Einladung senden oder auf eine warten?\n1-senden\n2-warten");
//			answer = scanner.nextInt();
//			if(answer == 1) {
//				clientController.getClient().setSend(true);
//				System.out.println("Wie heisst dein Gegner?");
//				String enemy = scanner.next();
//				clientController.getPlayerController().sendRequest(enemy);
//				message = clientController.getConnectionController().receiveMessage();
//				if(message.substring(0, 3).equals("202")) {
//					String clientIp = message.substring(4);
//					//System.out.println("IP:"+clientIp+"t");
//					clientController.getConnectionController().stopConnection();
//					clientController.getConnectionController().establishConnection(clientIp, 9876);
//					clientController.getConnectionController().sendMessage("200 " + 
//					clientController.getClient().getName() + " " + clientController.getClient().getMyIp());
//					partner = true;
//				}
//				else if (message.substring(0, 3).equals("404")) {
//					System.out.println("Der Spieler ist nicht vorhanden.");
//				}
//				else {
//					System.out.println("Serverantwort nicht bekannt.");
//				}
//			}
//			else if(answer == 2) {
//				System.out.println("Okay.Warten.");
//				clientController.getClient().setSend(false);
//				clientController.getConnectionController().stopConnection();
//				clientController.getConnectionController().establishConnection("", 9876);
//				message = clientController.getConnectionController().receiveMessage();
//				System.out.println(message);
//				if(message.substring(0,3).equals("200")) {
//					StringTokenizer token = new StringTokenizer(message, " ");
//					token.nextToken();
//					token.nextToken();
//					String friendIp = token.nextToken();
//					friendIp = friendIp.substring(friendIp.indexOf('/') + 1);
//					System.out.println("start"+friendIp+"end");
//					clientController.getClient().getConnection().setIPAdress(InetAddress.getByName(friendIp));
//					System.out.println("Verbunden mit " + friendIp);
//					partner = true;
//				}
//
//			}
//			else {
//				System.out.println("Das ist keine mögliche Antwort.");
//			}
//		}
//		
//		/*
//		 * Block for putting ship on field, repeately goes throught the list and checks if all ship have been put
//		 */
//		while(clientController.getClient().getShipsOnField() != clientController.getClient().getAllShips().size()) {
//			Board board = clientController.getClient().getMyBoard();
//			System.out.println(clientController.getShipController().boardToString(board));
//			boolean shipAvailable = false;
//			ShipType type = null;
//			while(!shipAvailable) {
//				System.out.println("Welches Schiff möchtest du setzen?");
//				for(Ship ship : clientController.getClient().getAllShips()) {
//					if(!ship.isOnField()) {
//						System.out.println(ship.getType());
//					}
//				}
//				String answerShip = scanner.next();
//				switch(answerShip) {
//				case "BIG": type = ShipType.BIG; shipAvailable = true; break;
//				case "MEDIUM": type = ShipType.MEDIUM; shipAvailable = true; break;
//				case "SMALL": type = ShipType.SMALL; shipAvailable = true; break;
//				}
//				if(!shipAvailable) {
//					System.out.println("Keine gültige Eingabe.");
//				}
//			}
//			/*
//			 * looks for the next current available fitting ship and gets the coordinates to put it on field
//			 */
//			boolean found = false;
//			for(Ship ship : clientController.getClient().getAllShips()) {
//				if(!found) {
//					if(ship.getType() == type && !ship.isOnField()) {
//						found = true;
//						int height = 0;
//						int width = 0;
//						Direction direction = null;
//						boolean heightOkay = false;
//						while(!heightOkay) {
//							System.out.println("Auf Welchem y-Wert soll das Schiff sein?");
//							height = scanner.nextInt();
//							if(height < 0 || height >= clientController.getClient().getMyBoard().getHeight()) {
//								System.out.println("Keine gültige Eingabe.");
//							}
//							else {
//								heightOkay = true;
//							}
//						}
//						boolean widthOkay = false;
//						while(!widthOkay) {
//							System.out.println("Auf Welchem x-Wert soll das Schiff sein?");
//							width = scanner.nextInt();
//							if(width < 0 || width >= clientController.getClient().getMyBoard().getWidth()) {
//								System.out.println("Keine gültige Eingabe.");
//							}
//							else {
//								widthOkay = true;
//							}
//						}
//						boolean directionOkay = false;
//						while(!directionOkay) {
//							System.out.println("In welche Richtung soll dein Schiff zeigen?\n1-Hoch\n2-Runter\n3-Links\n4-Rechts");
//							answer = scanner.nextInt();
//							if(answer > 0 && answer < 5) {
//								directionOkay = true;
//								direction = null;
//								switch(answer) {
//								// TODO Fehler 
//								case 1: direction = Direction.UP; break;
//								case 2: direction = Direction.DOWN; break;
//								case 3: direction = Direction.LEFT; break;
//								case 4: direction = Direction.RIGHT; break;
//								}
//							}
//							else {
//								System.out.println("Keine gültige Eingabe.");
//							}
//						}
//						clientController.getShipController().putShips(ship, height, width, direction);
//					}
//				}
//			}
//		}
//		
//		/*
//		 * attacking block
//		 */
//		if(clientController.getClient().isSend()) {
//			boolean ready = false;
//			while(!ready) {
//				System.out.println("Bist du bereit?\n1-ja\n2-nein");
//				answer = scanner.nextInt();
//				if(answer == 1) {
//					ready = true;
//					System.out.println("Spiel wird gestartet");
//					clientController.getConnectionController().sendMessage("203");
//				}
//				else if(answer == 2) {
//					System.out.println("Okay. 5 Sekunden warten.");
//					try {
//						TimeUnit.SECONDS.sleep(5);
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//				}
//				else {
//					System.out.println("Keine gültige Antwort.");
//				}
//			}
//			
//			System.out.println("test");
//			message = clientController.getConnectionController().receiveMessage();
//			// TODO fix
//			System.out.println(message);
//			message = message.trim();
//			if(message.equals("203")) {
//				System.out.println("Gegner ist auch bereit.");
//				boolean attack = false;
//				while(!attack) {
//					
//
//					if(clientController.getPlayerController().checkLoss()) {
//						clientController.getConnectionController().sendMessage("WON");
//						System.out.println("Deine Schiffe sind alle zerstoert.");
//						System.out.println("Du hast verloren.");
//						attack = true;
//					}
//					
//					Board mboard = clientController.getClient().getMyBoard();
//					Board eboard = clientController.getClient().getEnemyBoard();
//					System.out.println(clientController.getShipController().boardToString(mboard));
//					System.out.println(clientController.getShipController().boardToString(eboard));	
//					/*
//					 * send attack
//					 */
//					boolean heightOkay = false;
//					int height = 0;
//					while(!heightOkay) {
//						System.out.println("Auf welchem y-Wert willst du schießen?");
//						height = scanner.nextInt();
//						if(height < 0 || height >= clientController.getClient().getMyBoard().getHeight()) {
//							System.out.println("Keine gültige Eingabe.");
//						}
//						else {
//							heightOkay = true;
//						}
//					}
//					boolean widthOkay = false;
//					int width = 0;
//					while(!widthOkay) {
//						System.out.println("Auf welchen x-Wert willst du schießen?");
//						width = scanner.nextInt();
//						if(width < 0 || width >= clientController.getClient().getMyBoard().getWidth()) {
//							System.out.println("Keine gültige Eingabe.");
//						}
//						else {
//							widthOkay = true;
//						}
//					}
//					clientController.getShipController().myAttack(height, width);
//					
//					/*
//					 * receive hit
//					 */
//					message = clientController.getConnectionController().receiveMessage();
//					message = message.trim();
//					if(message.equals("HIT")) {
//						System.out.println("Treffer! y:" + height + ", x:" + width );
//						clientController.getClient().getEnemyBoard().setValue(height, width, 2);
//						clientController.getClient().setEnemyHitPoints(clientController.getClient().getEnemyHitPoints()+1);
//					}
//					else if(message.equals("WON")) {
//						System.out.println("Du Hast gewonnen!");
//						attack = true;
//					}
//					else {
//						System.out.println("Daneben! y:" + height + ", x:" + width);
//						clientController.getClient().getEnemyBoard().setValue(height, width, 2);
//					}
//					
//					/*
//					 * receive attack
//					 */
//					message = clientController.getConnectionController().receiveMessage();
//					StringTokenizer tokener = new StringTokenizer(message);
//					if(tokener.nextToken().equals("attack")) {
//						height = Integer.parseInt(tokener.nextToken());
//						width = Integer.parseInt(tokener.nextToken().trim());
//						Hit hit = clientController.getShipController().enemyAttack(height, width);
//						if(hit == Hit.HIT) {
//							System.out.println("Treffer! y:" + height + ", x:" + width );
//							clientController.getClient().getMyBoard().setValue(height, width, 2);
//							clientController.getClient().setHitPoints(clientController.getClient().getHitPoints()+1);
//						}
//						else {
//							System.out.println("Daneben! y:" + height + ", x:" + width);
//							clientController.getClient().getMyBoard().setValue(height, width, 2);
//						}
//						clientController.getConnectionController().sendMessage("" + hit);
//					}
//					
//					if(clientController.getPlayerController().checkLoss()) {
//						clientController.getConnectionController().sendMessage("WON");
//						System.out.println("Deine Schiffe sind alle zerstoert.");
//						System.out.println("Du hast verloren.");
//						attack = true;
//					}
//				}
//				System.out.println("Spiel ist vorbei.");
//				// TODO disconnect connection ?; close programm
//			}
//			
//			
//		}
//		else {
//			message = clientController.getConnectionController().receiveMessage();
//			message = message.trim();
//			if(message.equals("203")) {
//				System.out.println("Gegner ist bereit. Er macht den ersten Angriff");
//				clientController.getConnectionController().sendMessage("203");
//				
//				boolean attack = false;
//				while(!attack) {
//					
//					if(clientController.getPlayerController().checkLoss()) {
//						clientController.getConnectionController().sendMessage("WON");
//						System.out.println("Deine Schiffe sind alle zerstoert.");
//						System.out.println("Du hast verloren.");
//						attack = true;
//					}
//					/*
//					 * receive attack
//					 */
//					Board mboard = clientController.getClient().getMyBoard();
//					Board eboard = clientController.getClient().getEnemyBoard();
//					System.out.println(clientController.getShipController().boardToString(mboard));
//					System.out.println(clientController.getShipController().boardToString(eboard));	
//				
//					message = clientController.getConnectionController().receiveMessage();
//					StringTokenizer tokener = new StringTokenizer(message);
//					if(tokener.nextToken().equals("attack")) {
//						int height = Integer.parseInt(tokener.nextToken());
//						int width = Integer.parseInt(tokener.nextToken().trim());
//						Hit hit = clientController.getShipController().enemyAttack(height, width);
//						if(hit == Hit.HIT) {
//							System.out.println("Treffer!");
//							clientController.getClient().getMyBoard().setValue(height, width, 2);
//							clientController.getClient().setHitPoints(clientController.getClient().getHitPoints()+1);
//						}
//						else {
//							System.out.println("Daneben!");
//							clientController.getClient().getMyBoard().setValue(height, width, 2);
//						}
//						clientController.getConnectionController().sendMessage("" + hit);
//					}
//					else {
//						System.out.println("Du hast gewonnen!");
//						attack = true;
//					}
//					
//					/*
//					 * send attack
//					 */
//					boolean heightOkay = false;
//					int height = 0;
//					while(!heightOkay) {
//						System.out.println("Auf welchem y-Wert willst du schießen?");
//						height = scanner.nextInt();
//						if(height < 0 || height >= clientController.getClient().getMyBoard().getHeight()) {
//							System.out.println("Keine gültige Eingabe.");
//						}
//						else {
//							heightOkay = true;
//						}
//					}
//					boolean widthOkay = false;
//					int width = 0;
//					while(!widthOkay) {
//						System.out.println("Auf welchen x-Wert willst du schießen?");
//						width = scanner.nextInt();
//						if(width < 0 || width >= clientController.getClient().getMyBoard().getWidth()) {
//							System.out.println("Keine gültige Eingabe.");
//						}
//						else {
//							widthOkay = true;
//						}
//					}
//					clientController.getShipController().myAttack(height, width);
//					
//					/*
//					 * receive hit
//					 */
//					message = clientController.getConnectionController().receiveMessage();
//					message = message.trim();
//					if(message.equals("HIT")) {
//						System.out.println("Treffer! y:" + height + ", x:" + width);
//						clientController.getClient().getEnemyBoard().setValue(height, width, 2);
//						clientController.getClient().setEnemyHitPoints(clientController.getClient().getEnemyHitPoints()+1);
//					}
//					else {
//						System.out.println("Daneben! y:" + height + ", x:" + width);
//						clientController.getClient().getEnemyBoard().setValue(height, width, 2);
//					}
//					/*
//					 * check victory or loss
//					 */
//					if(clientController.getPlayerController().checkLoss()) {
//						clientController.getConnectionController().sendMessage("WON");
//						System.out.println("Deine Schiffe sind alle zerstoert.");
//						System.out.println("Du hast verloren.");
//						attack = true;
//					}
//				}
//				System.out.println("Spiel ist vorbei.");
//				// TODO disconnect connection ?; close programm
//			}
//		}
//	}
//}
