package Bai21;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Date;

public class ServerUDPChuoi {
	public String uppercase(String s) {
		String st=" ";
		int a=0;
		for(int i=0; i<s.length(); i++) {
			if(s.charAt(i)<='z' && s.charAt(i)>='a')
			{
				st+=(char)(s.charAt(i)-32);
			}
			else
				st+=s.charAt(i);
		}
		return st;
	}
	public String lowercase(String s) {
		String st=" ";
		int a=0;
		for(int i=0; i<s.length(); i++) {
			if(s.charAt(i)<='Z' && s.charAt(i)>='A')
			{
				a=(int)(s.charAt(i))+32;
				st+=(char)a;
			}
			else
				st+=(s.charAt(i));
		}
		return st;
	}
	public int count(String s) {
		int k=0;
		if(s.charAt(0)!=' ') k=1;
		else k=0;
		for(int i=0; i<s.length(); i++)
		{
			if(s.charAt(i)==' ' && s.charAt(i+1)!=' ')
				k+=1;
		}
		return k;
	}
	public static void main(String[] args) throws Exception {
		DatagramSocket serverSocket=new DatagramSocket(5000);
		System.out.println("Server is started");
		byte[] receiveData=new byte[1024];
		byte[] sendData1;
		byte[] sendData2;
		byte[] sendData5;
		ServerUDPChuoi sv=new ServerUDPChuoi();
		while(true) {
			DatagramPacket receivePacket=new DatagramPacket(receiveData, receiveData.length);
			serverSocket.receive(receivePacket);
			InetAddress IPAddress=receivePacket.getAddress();
			int port =receivePacket.getPort();
			String request=new String(receivePacket.getData()).trim();
			String hoa=sv.uppercase(request);
			String thuong=sv.lowercase(request);
			String count=Integer.toString((sv.count(request)));
			sendData1=hoa.getBytes();
			sendData2=thuong.getBytes();
			sendData5=count.getBytes();
			DatagramPacket sendPacket1=new DatagramPacket(sendData1, sendData1.length, IPAddress, port);
			DatagramPacket sendPacket2=new DatagramPacket(sendData2, sendData2.length, IPAddress, port);
			DatagramPacket sendPacket5=new DatagramPacket(sendData5, sendData5.length, IPAddress, port);
			serverSocket.send(sendPacket1);
			serverSocket.send(sendPacket2);
			serverSocket.send(sendPacket5);
		}
	}	
}
