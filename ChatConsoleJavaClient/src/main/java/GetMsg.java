import api.ChatServerApiClient;

public class GetMsg implements Runnable {

    private ChatServerApiClient apiClient;

    public GetMsg(ChatServerApiClient apiClient) {
        this.apiClient = apiClient;
    }

    @Override
    public void run() {
        String msg;
        while (true) {
            msg = apiClient.getMsg();
            if (msg != null) {
                System.out.println(msg);
            }
        }

    }
}
