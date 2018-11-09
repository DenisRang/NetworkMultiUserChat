import sdk.ChatServerApiClient;
import server.ServerTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Main {
    private static BufferedReader reader;

    public static void main(String[] args) throws InterruptedException, IOException {
        ChatServerApiClient apiClient = new ChatServerApiClient();
        reader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            Thread.sleep(2000);
            String message = null;
            if (args[0] == null) {
                System.out.println("Enter message:");
                message = reader.readLine();
            } else {
                message = args[0];
            }
            apiClient.sendMessage(message);
        }
    }
}
