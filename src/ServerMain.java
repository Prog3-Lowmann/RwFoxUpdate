import java.util.Scanner;

public class ServerMain {
    public static void main(String[] args) {


        System.out.println("Enter tcp/udp server to start : ");
        Scanner sc = new Scanner(System.in);

        String type = sc.next();
        if(type.equals("tcp")) {
            TCPServer tcpServer = new TCPServer();
            tcpServer.start();
        }
        else {
            UDPServer udpServer = new UDPServer();
            udpServer.start();
        }
    }
}
