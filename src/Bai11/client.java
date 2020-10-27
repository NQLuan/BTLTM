package Bai11;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class client {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        Socket socket = new Socket("localhost", 5000);
        DataInputStream dis = new DataInputStream(socket.getInputStream());
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        while (true) {
            String s;
            System.out.print("Nhap chuoi:");
            s = scanner.nextLine();
            dos.writeUTF(s);
            System.out.println("Chuoi hoa: " + dis.readUTF());
            System.out.println("Chuoi thuong: " + dis.readUTF());
            System.out.println("So tu: " + dis.readUTF());
        }
    }
}
