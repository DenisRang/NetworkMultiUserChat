import api.ChatServerApiClient;

import java.io.IOException;
import java.util.concurrent.ThreadPoolExecutor;

class ClientMain {

    public static void main(String[] args) throws IOException {
        ChatServerApiClient apiClient = new ChatServerApiClient();
        new Thread(new SendMsg(apiClient)).start();
        new Thread(new GetMsg(apiClient)).start();
    }
}
