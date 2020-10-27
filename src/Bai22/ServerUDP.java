package Bai22;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Stack;

public class ServerUDP {
	public ServerUDP(){
		try{
			DatagramSocket ds= new DatagramSocket(5000);
			DatagramPacket dp;
			System.out.println("Server is started");
			while(true){
				byte[] rece= new byte[1024];
				dp= new DatagramPacket(rece, 1024);
				
				String str;
				ds.receive(dp);
				str= new String(dp.getData()).substring(0, dp.getLength());
				System.out.println("Da nhan chuoi: "+str);
				
				String str2= execute(str);
				dp= new DatagramPacket(str2.getBytes(), str2.length(), dp.getAddress(), dp.getPort());
				ds.send(dp);
				System.out.println("Da gui gia tri bieu thuc! ");
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private String getValue(String a, char o, String b){
		float af= Float.valueOf(a), bf= Float.valueOf(b);
		switch(o){
		case '+': return String.valueOf(af+bf);
		case '-': return String.valueOf(af-bf);
		case '*': return String.valueOf(af*bf);
		case '/':
			if(bf==0) return "error";
			return String.valueOf((float)af/bf);
		default: return "error";
		}
	}
	
	private int prio(char c){
		if(c=='+'||c=='-') return 0;
		if(c=='(') return 1;
		if(c=='*'||c=='/') return 2;
		return -1;
	}
	
	private String execute(String str){
		String v="";
		Stack<String> numst= new Stack<String>();
		Stack<Character> opest= new Stack<Character>();
		String num="";
		
		for(int i= 0; i< str.length(); i++){
			char c= str.charAt(i);
			switch(c){
			default:
				return "Error!";
			case '0':case'1':case'2':case '3':case'4':case'5':case'6':case'7':case '8':case'9':case'.':
				num+=c;
				if(i==(str.length()-1)){
					if(!opest.isEmpty()){
						numst.push(getValue(numst.pop(), opest.pop(), num));
						while(!opest.isEmpty()){
							String n2= numst.pop(), n1= numst.pop();
							numst.push(getValue(n1, opest.pop(), n2));
						}
					}else return num;
				}
				break;
			case '+':case'-':
				if(i==0){num+=c; break;}
			case '*':case '/':case '(':
				if(!num.equals("")){
					numst.push(num);
				num="";}
				if(opest.isEmpty()){
					opest.push(c);
				}else{
					if(c=='('){
						opest.push(c);
						break;
					}
					while(!opest.isEmpty()&&(opest.lastElement()!='(')&&prio(c)<=prio(opest.lastElement())){
						String n2= numst.pop(), n1= numst.pop();
						numst.push(getValue(n1, opest.pop(), n2));
					}
					opest.push(c);
				}
				break;
			case ')':
				if(!num.equals("")){
					numst.push(num);
				num="";}
				while((!opest.isEmpty())&&opest.lastElement()!='('){
					String n2= numst.pop(), n1= numst.pop();
					numst.push(getValue(n1, opest.pop(), n2));
				}
				opest.pop();
			}
		}
		while(!opest.isEmpty()){
			String n2= numst.pop(), n1= numst.pop();
			numst.push(getValue(n1, opest.pop(), n2));
		}
		v= numst.pop();
		return v;
	}
	
	public static void main(String[]args){
		new ServerUDP();
	}
}
