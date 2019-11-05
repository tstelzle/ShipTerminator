package model;

import java.io.IOException;
import java.net.*;

public class Connection {
	
	DatagramSocket socket = null ;
	InetAddress IPAdress = null;
	int port;
	
	public Connection(String ip, int port) {
		try {
			IPAdress = InetAddress.getByName( ip ) ;
			this.port = port;
			
			// Construct the socket
			socket = new DatagramSocket(9876) ;
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 
	
	public void sentMessage(String message) {
		byte[] sendData = message.getBytes();
		DatagramPacket packet = new DatagramPacket( sendData, sendData.length, IPAdress, port) ;
		try {
			socket.send(packet);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String receive() {
		byte[] receiveData = new byte[1024];
		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
	    try {
			socket.receive(receivePacket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    String modifiedSentence = new String(receivePacket.getData());
	    return modifiedSentence;
	}
	
	public void stop() {
		socket.close();
	}

	public DatagramSocket getSocket() {
		return socket;
	}

	public void setIPAdress(InetAddress iPAdress) {
		IPAdress = iPAdress;
	}
	
}
