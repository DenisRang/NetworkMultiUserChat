import api.ChatServerApi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SendMsg implements Runnable {

    private ChatServerApi apiClient;
    private BufferedReader readerConsole;

    public SendMsg(ChatServerApi apiClient) {
        this.apiClient = apiClient;
        readerConsole = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    String message = readerConsole.readLine();
                    apiClient.sendMsg(message);
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
            }
        } finally {
            try {
                apiClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    readerConsole.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
