package server;

import consumers.ClientsManager;

import java.net.Socket;

public class ClientVerifier extends Thread {
    private ClientsManager clientsManager;

    public ClientVerifier(ClientsManager clientsManager) {
        this.clientsManager = clientsManager;
        start();
    }

    @Override
    public void run() {
        super.run();
        while (true){
            for(Socket socket:clientsManager.getClients()){
                if(socket.isClosed()){
                    clientsManager.removeClient(socket);
                }
            }
        }
    }
}
