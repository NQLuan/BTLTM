package Bai13;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Server {
    private int port;
    public static ArrayList<Socket> listSocket;

    private static final int PORT = 5000;

    public Server(int port) {
        this.port = port;
    }

    private void execute() throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);

        ThreadWriteServer writeServer = new ThreadWriteServer();
        writeServer.start();
        System.out.println("Server is listening.........");

        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("Da ket noi: " + socket);
            listSocket.add(socket);
            ThreadReadServer readServer = new ThreadReadServer(socket);
            readServer.start();
        }

    }

    public static void main(String[] args) throws IOException {
        Server Server = new Server(PORT);
        Server.listSocket = new ArrayList<>();
        Server.execute();
    }
}

class ThreadReadServer extends Thread {
    private Socket client;

    public ThreadReadServer(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        DataInputStream dis = null;
        try {
            dis = new DataInputStream(client.getInputStream());
            while (true) {
                String sms = dis.readUTF();
                if (sms.contains("exit")) {
                    Server.listSocket.remove(client);
                    System.out.println("Da ngat ket noi voi " + client);
                    dis.close();
                    client.close();
                    continue;
                }

                for (Socket item : Server.listSocket) {
                    if (item.getPort() != client.getPort()) {
                        DataOutputStream dos = new DataOutputStream(item.getOutputStream());
                        dos.writeUTF(sms);
                    }
                }
                System.out.println(sms);
            }
        } catch (IOException e) {
            try {
                dis.close();
                client.close();
            } catch (IOException ex) {
                System.out.println("Ngat ket noi voi server");
            }
        }
    }
}

class ThreadWriteServer extends Thread {
    @Override
    public void run() {
        DataOutputStream dos;
        Scanner sc = new Scanner(System.in);
        while (true) {
            String sms = sc.nextLine();
            for (Socket item : Server.listSocket) {
                try {
                    dos = new DataOutputStream(item.getOutputStream());
                    dos.writeUTF("CalculateServer: " + sms);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
