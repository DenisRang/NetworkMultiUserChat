package api;

import java.io.IOException;

public interface ChatServerApi {

    void sendMsg(String message) throws IOException;

    String getMsg();

}
