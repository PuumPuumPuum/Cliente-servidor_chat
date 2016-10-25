package chat;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


public class ClienteChat implements Runnable{
	Socket socket;
	Scanner entradaDatos;
	
	PrintWriter salidaDatos;
	
	public ClienteChat(Socket socket){
		this.socket=socket;
	}

	@Override
	public void run() {
		try{
			try{
				entradaDatos = new Scanner(socket.getInputStream());
				salidaDatos = new PrintWriter(socket.getOutputStream());
				salidaDatos.flush();

				while(true){
					recibirDatos();
				}			
			}finally{
				this.socket.close();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	
	public void recibirDatos(){
		if(entradaDatos.hasNext()){
			String message = entradaDatos.nextLine();
			System.out.println(message);
		}
	}
	
	public void enviarDatos(String string){
		salidaDatos.println(string);
		salidaDatos.flush();
	}
	
	public void desconectar()throws Exception{
		salidaDatos.println(socket.getLocalAddress().getHostName()+" se ha retirado de la sala");
		salidaDatos.flush();
		socket.close();
		System.exit(0);
	}
}
