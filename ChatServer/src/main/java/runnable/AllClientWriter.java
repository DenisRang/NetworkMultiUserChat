package runnable;

import server.ClientsManager;

import java.io.BufferedWriter;

public class AllClientWriter implements Runnable {

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
