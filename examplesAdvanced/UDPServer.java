package com.SocketAPI.UDP;

import java.net.DatagramSocket;
import java.util.Scanner;

import com.SocketAPI.MySocket;

public class UDPServer extends MySocket {

	public UDPServer(int port, String host) {
		super(port, host);
	}

	public static void main(String args[]) {
		try {
			DatagramSocket serverSocket = new DatagramSocket(9876);
			UDPServer u = new UDPServer(9876, "localhost");
			while (true) {
				Scanner scan = new Scanner(System.in);
				u.SendDatagram(serverSocket, scan, 9875, "localhost");
				System.out.println(u.ReceiveDatagram(serverSocket));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
