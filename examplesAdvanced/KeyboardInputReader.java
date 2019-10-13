package sockets.api.general;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.function.Consumer;

public class KeyboardInputReader {

    private Thread inputThread;
    private Consumer<String> inputListener;

    public void start() {
        inputThread = new Thread(this::readInput);
        inputThread.start();
    }

    private void readInput() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            try {
                String input = bufferedReader.readLine();
                if (inputListener != null) {
                    inputListener.accept(changeSymbols(input));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String changeSymbols(String input){
        StringBuilder stringBuilder=new StringBuilder();
        if(input!=null) {
            for (int i = 0; i < input.length(); i++) {
                if (input.charAt(i) == '$')
                    stringBuilder.append('&');
                else {
                    if (input.charAt(i) == 'A')
                        stringBuilder.append('B');
                    else
                        stringBuilder.append(input.charAt(i));
                }
            }
        }
        return stringBuilder.toString();
    }

    public void setInputListener(Consumer<String> inputListener) {
        this.inputListener = inputListener;
    }
}

