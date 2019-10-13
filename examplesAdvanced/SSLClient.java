package com.SocketAPI.SSL;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import com.SocketAPI.MySocket;

public class SSLClient extends MySocket {

	public static final boolean DEBUG = true;
	public static int port = 8282;
	public static String host = "localhost";
	public static final String TRUSTTORE_LOCATION = "C:/Keys/DebKeyStore.jks";

	public SSLClient(int port, String host) {
		super(port, host);
		this.port = port;
		this.host = host;
	}

	public static void main(String[] args) {

		System.setProperty("javax.net.ssl.trustStore", TRUSTTORE_LOCATION);
		System.setProperty("javax.net.ssl.trustStorePassword", "zxcvbnm1");

		if (DEBUG)
			System.setProperty("javax.net.debug", "ssl:record");

		SSLSocketFactory f = (SSLSocketFactory) SSLSocketFactory.getDefault();
		try {
			SSLSocket c = (SSLSocket) f.createSocket(host, port);

			c.startHandshake();

			Texting(c, new Scanner(System.in));

		} catch (IOException e) {
			System.err.println(e.toString());
		}

	}
}
