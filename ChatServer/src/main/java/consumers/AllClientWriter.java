package consumers;

import java.io.BufferedWriter;

public class AllClientWriter extends Thread {

    private ClientsManager clientsManager;
    private BufferedWriter senderWriter;
    private String msg;

    public AllClientWriter(ClientsManager clientsManager, BufferedWriter senderWriter, String msg) {
        this.clientsManager = clientsManager;
        this.senderWriter = senderWriter;
        this.msg = msg;
        start();
    }

    @Override
    public void run() {
        clientsManager.retransmitToAllClients(senderWriter, msg);
    }
}
