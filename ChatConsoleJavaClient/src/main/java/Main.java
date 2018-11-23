import api.ChatServerApiClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Main {
    public static void main(String[] args) throws InterruptedException, IOException {
        ChatServerApiClient apiClient = new ChatServerApiClient();
        new SendMsg(apiClient, args[0]).run();
        new GetMsg(apiClient).run();
    }
}
