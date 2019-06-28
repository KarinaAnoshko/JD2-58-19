package by.pvt;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 */
public class Server {

    private static Logger log = Logger.getLogger("by.pvt.Server");

    public static void main(String[] args) {
        try(ServerSocket serverSocket = new ServerSocket(3036)) {
            ExecutorService service = Executors.newCachedThreadPool();
            do {
                Socket socket = serverSocket.accept();
                DataInputStream dataInputStream =
                        new DataInputStream(socket.getInputStream());
                service.submit(() -> {
                    try {
                        String input = "";
                        while (!"END".equals(input)) {
                            input = dataInputStream.readUTF();
                            log.info("Server input: " + input);
                        }
                    } catch (IOException e) {
                        log.log(Level.SEVERE, e.getMessage(), e);
                    }
                });
            } while (true);

        } catch (IOException e) {
            log.log(Level.SEVERE, e.getMessage(), e);
        }

    }

}
