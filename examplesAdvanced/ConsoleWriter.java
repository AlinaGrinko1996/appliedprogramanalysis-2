package com.SocketAPI;

import java.io.BufferedReader;
import java.io.IOException;

public class ConsoleWriter  extends Thread {
	BufferedReader input;

	public ConsoleWriter(BufferedReader in) {
		input = in;
	}

	public void run() {
		while (true) {
			try {
				System.out.println(input.readLine());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}