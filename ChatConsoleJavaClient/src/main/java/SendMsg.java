import api.ChatServerApiClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SendMsg implements Runnable {

    private ChatServerApiClient apiClient;
    private String argument;

    public SendMsg(ChatServerApiClient apiClient, String argument) {
        this.apiClient = apiClient;
        this.argument = argument;
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String message = null;
                if (argument == null) {
                    System.out.println("Enter message:");
                    message = reader.readLine();
                } else {
                    message = argument;
                }
                apiClient.sendMsg(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
