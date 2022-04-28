import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Server {
    static List<Integer> clientsList;

    public static void freeClients(int clientNumber) {
        clientsList.remove(clientsList.indexOf(clientNumber));
        System.out.println("Client-" + clientNumber + " is remove from clientIndexList,,,,,");
    }

    public Server(int port, int sleepingTime, int maximumClients) {
        try {
            clientsList = new Vector<>();

            ServerSocket server = new ServerSocket(port);

            System.out.println("Waiting for a client....");

            while (true) {
                Socket socket = server.accept();

                if(clientsList.isEmpty()) {
                    clientsList.add(1);
                    System.out.println("Client-1"+ " is connected...(server)");
                    // create a new thread for current client
                    ServerThreads clientsHandle = new ServerThreads(socket, 1, sleepingTime);
                    clientsHandle.start();
                }
                else if(clientsList.size() == maximumClients) {
                    System.out.println("more than enough clients!!!!!");
                    socket.close();
                }
                else{
                    for (int i = 0; i < maximumClients; i++) {
                        if(!clientsList.contains(i+1)) {
                            clientsList.add(i+1);
                            System.out.println("Client-" + i + " is connected...(server)");
                            // create a new thread for current client
                            ServerThreads clientsHandle = new ServerThreads(socket, i+1, sleepingTime);
                            clientsHandle.start();
                            break;
                        }
                    }
                }
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
