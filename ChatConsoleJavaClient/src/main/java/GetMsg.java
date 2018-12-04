import api.ChatServerApi;
import api.ChatServerApiClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.net.SocketException;
import java.util.Timer;

public class GetMsg implements Runnable {
    private static final Log logger = LogFactory.getLog(GetMsg.class);

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
                    logger.info(String.format("Client get a new message from server: %s", msg));
                    System.out.println(msg);
                } catch (IOException e) {
                    logger.warn("Process of getting messages from the server was stopped");
                    return;
                }
            }
        } finally {
            try {
                apiClient.close();
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
    }
}
