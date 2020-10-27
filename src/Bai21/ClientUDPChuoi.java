package Bai21;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ClientUDPChuoi {
	public static void main(String[] args) throws Exception {
		DatagramSocket clientSocket=new DatagramSocket();//gan xong
		InetAddress IPAddress = InetAddress.getByName("localhost");
		InputStreamReader luongvao=new InputStreamReader(System.in);
		BufferedReader br=new BufferedReader(luongvao);
		System.out.println("Nhap chuoi:");
		String s=br.readLine();
		byte[] sendData=new byte[1024];
		byte[] receiveData1=new byte[1024];
		byte[] receiveData2=new byte[1024];
		byte[] receiveData5=new byte[1024];
		sendData=s.getBytes();
		DatagramPacket sendPacket=new DatagramPacket(sendData, sendData.length, IPAddress, 5000);
		clientSocket.send(sendPacket);
		DatagramPacket receivePacket1=new DatagramPacket(receiveData1, receiveData1.length);
		DatagramPacket receivePacket2=new DatagramPacket(receiveData2, receiveData2.length);
		DatagramPacket receivePacket5=new DatagramPacket(receiveData5, receiveData5.length);
		clientSocket.receive(receivePacket1);
		clientSocket.receive(receivePacket2);
		clientSocket.receive(receivePacket5);
		String hoa=new String(receivePacket1.getData());
		String thuong=new String(receivePacket2.getData());
		String sotu=new String(receivePacket5.getData());
		String sms="Chuoi hoa:"+hoa+
				"\nChuoi thuong:"+thuong+
				"\nSotu:"+sotu;
		System.out.println(sms);
		clientSocket.close();
	}
}
