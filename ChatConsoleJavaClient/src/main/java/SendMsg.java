import api.ChatServerApi;
import api.ChatServerApiClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SendMsg implements Runnable {
    private static final Log logger = LogFactory.getLog(SendMsg.class);


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
                    logger.info(String.format("Client enter new message: %s", message));
                    apiClient.sendMsg(message);
                } catch (IOException e) {
                    logger.warn("Process of reading and sending messages from a client was stopped");
                    return;
                }
            }
        } finally {
            try {
                apiClient.close();
            } catch (IOException e) {
                logger.error(e.getMessage());
            } finally {
                try {
                    readerConsole.close();
                } catch (IOException e) {
                    logger.error(e.getMessage());
                }
            }
        }
    }
}
