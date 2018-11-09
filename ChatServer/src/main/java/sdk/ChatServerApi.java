package sdk;

import java.io.IOException;

public interface ChatServerApi {

    void sendMessage(String message) throws IOException;

}
