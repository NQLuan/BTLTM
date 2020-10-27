package Bai22;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class ClientUDP {
	public ClientUDP(){
		try{
			Scanner sc= new Scanner(System.in);
			String str;
			DatagramSocket ds= new DatagramSocket();
			DatagramPacket dp;
			while(true){
				System.out.print("Nhap bieu thuc: ");
				str= sc.nextLine();
				if(str.equals("exit")) break;
				dp= new DatagramPacket(str.getBytes(), str.length(), InetAddress.getByName("localhost"), 5000);
				ds.send(dp);

				String str2;
				ds.receive(dp);
				str2= new String(dp.getData()).substring(0, dp.getLength());
				System.out.println("Ket qua: "+str2);
			}
		}catch(Exception ex){
			
		}
	}
	
	public static void main(String []args){
		new ClientUDP();
	}
}