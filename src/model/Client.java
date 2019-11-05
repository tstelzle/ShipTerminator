package model;

import java.util.ArrayList;
import java.util.List;

public class Client {

	private int hitPoints;
	private int enemyHitPoints;
	private int maxhitPoints;
	public Connection connection;
	private Board myBoard;
	private Board enemyBoard;
	private String name = "";
	private List<Ship> allShips = new ArrayList<Ship>();
	private int shipsOnField;
	private String myIp;
	private boolean send;
	
	public Client() {
		myBoard = new Board('M');
		enemyBoard = new Board('E');
		hitPoints = 0;
		enemyHitPoints = 0;
	}
	
	public void setConnection(String ip, int port) {
		connection = new Connection(ip, port);
	}
	
	public Connection getConnection() {
		return connection;
	}

	public int getHitPoints() {
		return hitPoints;
	}

	public void setHitPoints(int hitPoints) {
		this.hitPoints = hitPoints;
	}

	public int getMaxhitPoints() {
		return maxhitPoints;
	}

	public void setMaxhitPoints(int maxhitPoints) {
		this.maxhitPoints = maxhitPoints;
	}

	public Board getMyBoard() {
		return myBoard;
	}

	public Board getEnemyBoard() {
		return enemyBoard;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Ship> getAllShips() {
		return allShips;
	}

	public int getShipsOnField() {
		return shipsOnField;
	}

	public void setShipsOnField(int shipsOnField) {
		this.shipsOnField = shipsOnField;
	}

	public String getMyIp() {
		return myIp;
	}

	public void setMyIp(String myIp) {
		this.myIp = myIp;
	}

	public boolean isSend() {
		return send;
	}

	public void setSend(boolean send) {
		this.send = send;
	}

	public int getEnemyHitPoints() {
		return enemyHitPoints;
	}

	public void setEnemyHitPoints(int enemyHitPoints) {
		this.enemyHitPoints = enemyHitPoints;
	}
}
