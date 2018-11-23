package consumers;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class RetransmitterService {

    private List<Socket> clients = new ArrayList<>();

    public void addClient(Socket socket) {
        clients.add(socket);
    }

    public void removeClient(Socket socket) {
        clients.remove(socket);
    }

    public synchronized void retransmitToAllClients(Socket senderClient, String msg) {
        int senderClientNumber = clients.indexOf(senderClient);
        for (Socket client : clients) {
            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()))) {
                String msgWithSender = String.format("Client %d send message:  %s", senderClientNumber, msg);
                writer.write(msgWithSender); // отправляем сообщение на сервер
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
