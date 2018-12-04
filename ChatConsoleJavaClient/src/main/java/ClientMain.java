import api.ChatServerApiClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;

class ClientMain {
    private static final Log logger = LogFactory.getLog(ClientMain.class);

    public static void main(String[] args) {
        ChatServerApiClient apiClient = null;
        try {
            apiClient = new ChatServerApiClient();
            logger.info("Client connected with server and started to work");
        } catch (IOException e) {
            logger.error("Client can't connects with server");
            return;
        }
        new Thread(new SendMsg(apiClient)).start();
        new Thread(new GetMsg(apiClient)).start();
    }
}
