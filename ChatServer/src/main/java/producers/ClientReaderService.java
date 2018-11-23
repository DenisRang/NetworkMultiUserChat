package producers;

import consumers.RetransmitterService;

import java.io.*;
import java.net.Socket;

public class ClientReaderService implements Runnable {
    private RetransmitterService retransmitterService;
    private Socket socket;

    public ClientReaderService(RetransmitterService retransmitterService, Socket socket) {
        this.retransmitterService = retransmitterService;
        this.socket = socket;
    }

    @Override
    public void run() {
        while (true) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                StringBuilder msg = new StringBuilder();
                String line = reader.readLine();
                while (line != null) {
                    msg.append(line);
                    line = reader.readLine();
                }
                retransmitterService.retransmitToAllClients(socket, msg.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
