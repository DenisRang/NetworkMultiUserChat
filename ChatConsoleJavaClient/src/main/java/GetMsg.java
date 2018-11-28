import api.ChatServerApi;
import api.ChatServerApiClient;

import java.io.IOException;
import java.net.SocketException;
import java.util.Timer;

public class GetMsg implements Runnable {

    private ChatServerApi apiClient;

    public GetMsg(ChatServerApi apiClient) {
        this.apiClient = apiClient;
    }

    @Override
    public void run() {
        String msg;
        try {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    msg = apiClient.getMsg();
                    System.out.println(msg);
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
            }
        }
    }
}
