import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MainParallel {
    public static void main(String[] args) throws IOException {

        try (ServerSocket sSocket = new ServerSocket(2004)) {
            while (true) {
                {
                    Socket cSocket = sSocket.accept();
                    new RequestThread(cSocket);
                }
            }
        }
    }
}
