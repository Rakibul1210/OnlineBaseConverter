import java.io.*;
import java.net.Socket;

public class ServerThreads extends Thread{
    Socket socket;
    int clientID;
    int sleepingTime;
    DataInputStream inputStream;
    DataOutputStream outputStream;

    public ServerThreads(Socket socket, int clientID, int sleepingTime) {
        this.socket = socket;
        this.clientID = clientID;
        this.sleepingTime = sleepingTime;
    }

    @Override
    public void run() {
        try {
            inputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            outputStream = new DataOutputStream(socket.getOutputStream());
            // multiple message for same client
            while (true) {
                int fromBase = inputStream.readInt();
                int toBase = inputStream.readInt();
                String number = inputStream.readUTF();
                System.out.println(clientID + " is sent  number = " + number);

                if (fromBase == -1 || toBase == -1) {
                    try {
                        System.out.println("Connection closed with " + clientID);
                        socket.close();
                        inputStream.close();

                        Server.freeClients(clientID);
                        break;
                    } catch (IOException ioException) {
                        System.out.println(ioException);
                    }
                }

                BaseConversion baseConversion = new BaseConversion();
                String str = baseConversion.decimalToBase(baseConversion.anyBaseToDecimal(number,fromBase),toBase);
                System.out.println(str);
                outputStream.writeUTF(str);

                Thread.sleep(sleepingTime);
            }
        } catch (IOException | InterruptedException exception) {
            System.out.println(exception);
        }

        // close client socket if client connection is broken
        try {
            this.socket.close();
        } catch (IOException ioException) {
            System.out.println(ioException);
        }
    }
}
