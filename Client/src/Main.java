import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("127.0.0.1", 14200);
            System.out.println("Connected with server!!!");

            DataOutputStream send = new DataOutputStream(socket.getOutputStream());
            DataInputStream receive = new DataInputStream(new BufferedInputStream(socket.getInputStream()));

            Scanner scanner = new Scanner(System.in);
            int fromBase = 10;
            int toBase = 10;
            String number;
            while (true) {
                System.out.print("From base:  ");
                fromBase = Integer.parseInt(scanner.nextLine());
//                fromBase = scanner.nextInt();
                System.out.print("To base:  ");
                toBase = Integer.parseInt(scanner.nextLine());
//                toBase = scanner.nextInt();
                System.out.print("Number =  ");
                number = scanner.nextLine();


                send.writeInt(fromBase);
                send.writeInt(toBase);
                send.writeUTF(number);
                System.out.println("Successfully send!!");

                if(fromBase == -1 || toBase == -1) {
                    break;
                }

                System.out.println( "Number After conversion = " + receive.readUTF() + "\n\n");
            }
            System.out.println("\tServer disconnected.");

        } catch (UnknownHostException u) {
            System.out.println(u);
        } catch (IOException i) {
            System.out.println(i);
        }
    }
}
