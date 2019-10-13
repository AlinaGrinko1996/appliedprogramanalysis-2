package com.SocketAPI.UDP;

import java.net.DatagramSocket;
import java.util.Scanner;

import com.SocketAPI.MySocket;

public class UDPClient extends MySocket {

	public UDPClient(int port, String host) {
		super(port, host);
	}

	public static void main(String args[]) {
		try {
			UDPClient u = new UDPClient(9875, "localhost");
			DatagramSocket clientSocket = new DatagramSocket(9875);
			while (true) {
				Scanner scan = new Scanner(System.in);
				u.SendDatagram(clientSocket, scan, 9876, "localhost");
				System.out.println(u.ReceiveDatagram(clientSocket));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
