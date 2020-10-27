package Bai11;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class server {
    public String uppercase(String s) {
        StringBuilder st = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) <= 'z' && s.charAt(i) >= 'a') {
                st.append((char) (s.charAt(i) - 32));
            } else
                st.append(s.charAt(i));
        }
        return st.toString();
    }

    public String lowercase(String s) {
        StringBuilder st = new StringBuilder();
        int a;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) <= 'Z' && s.charAt(i) >= 'A') {
                a = (int) (s.charAt(i)) + 32;
                st.append((char) a);
            } else
                st.append(s.charAt(i));
        }
        return st.toString();
    }

    public int count(String s) {
        int k;
        if (s.charAt(0) != ' ') k = 1;
        else k = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ' ' && s.charAt(i + 1) != ' ')
                k += 1;
        }
        return k;
    }

    public static void main(String[] args) throws Exception {
        ServerSocket ss = new ServerSocket(5000);
        System.out.println("Server is started");
        server sv = new server();
        while (true) {
            Socket socket = ss.accept();
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            String st = dis.readUTF();
            System.out.println("client send:" + st);
            int count = sv.count(st);
            dos.writeUTF(sv.uppercase(st));
            dos.flush();
            dos.writeUTF(sv.lowercase(st));
            dos.flush();
            dos.writeUTF(Integer.toString(count));
            dos.flush();
            socket.close();
        }
    }
}
