package com.SocketAPI.SSL;

import java.net.Socket;
import java.util.Scanner;

import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;

import com.SocketAPI.*;

public class SSLServer extends MySocket {

	public static final boolean DEBUG = true;
	public static int port = 8282;
	public static final String KEYSTORE_LOCATION = "C:/Keys/ServerKeyStore.jks";
	public static final String KEYSTORE_PASSWORD = "zxcvbnm1";
	
	public SSLServer(int port, String host) {
		super(port, host);
	}

	public static void main(String argv[]) throws Exception {

		System.setProperty("javax.net.ssl.keyStore", KEYSTORE_LOCATION);
		System.setProperty("javax.net.ssl.keyStorePassword", KEYSTORE_PASSWORD);

		if (DEBUG)
			System.setProperty("javax.net.debug", "ssl:record");

		SSLServer server = new SSLServer(8282, "localhost");
		server.startServer();
	}

	public void startServer() {
		try {
			ServerSocketFactory ssf = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
			SSLServerSocket serversocket = (SSLServerSocket) ssf.createServerSocket(port);

			while (true) {
				Socket client = serversocket.accept();
				
				Texting(client, new Scanner(System.in));
			}
		} catch(Exception e){
			e.printStackTrace();
		}
	}
}


