package com.SocketAPI;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.Scanner;

public class MySocket {
	public int port;
	public String host;
	
	public MySocket(int port, String host){
		this.port=port;
		this.host=host;
	}
	
	public void FindInNetwork(){}
	
	public void SendDatagram(DatagramSocket Socket, Scanner scan, int port, String host){
	    new Thread(new Runnable() {
	        @Override
	        public void run() {
	        	byte[] sendData = new byte[1024];
	    		try {
	    			InetAddress IPAddress = InetAddress.getByName(host);
	    			String sentence = scan.nextLine();
	    			sendData = sentence.getBytes();
	    			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
	    			Socket.send(sendPacket);
	    		} catch (Exception e) {
	    			e.printStackTrace();
	    		}
	        }
	    }).start();
	}
	
	public String ReceiveDatagram(DatagramSocket Socket){
		byte[] receiveData = new byte[1024];
		String modifiedSentence="";
		try {
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			Socket.receive(receivePacket);
			modifiedSentence = new String(receivePacket.getData());	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return modifiedSentence;
	}
	
	public static void Texting(Socket socket, Scanner scan){
		try {
			while (true) {
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				String output = "";
				new ConsoleWriter(in).start();
				while (!output.equals("_stop")) {
					output = scan.nextLine();
					out.println(output);
					out.flush();
				}
			}
		} catch (Exception e) {
			System.out.println("Exception:" + e.getMessage());
		}
	}
	
	public void SendFile(){}
}

