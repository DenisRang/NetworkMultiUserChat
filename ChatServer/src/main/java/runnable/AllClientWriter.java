package runnable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import server.ClientsManager;

import java.io.BufferedWriter;

public class AllClientWriter implements Runnable {
    private static final Log logger = LogFactory.getLog(AllClientWriter.class);

    private ClientsManager clientsManager;
    private BufferedWriter senderWriter;
    private String msg;

    public AllClientWriter(ClientsManager clientsManager, BufferedWriter senderWriter, String msg) {
        this.clientsManager = clientsManager;
        this.senderWriter = senderWriter;
        this.msg = msg;
    }

    @Override
    public void run() {
        clientsManager.retransmitToAllClients(senderWriter, msg);
    }
}
