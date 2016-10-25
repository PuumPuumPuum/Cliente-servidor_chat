package chat;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ServidorChat implements Runnable{//The Runnable interface should be implemented by any class whose instances are intended to be executed by a thread. 
	Socket socket;
	private Scanner input;
	String mensaje = "";
	
	public ServidorChat(Socket socket){
		this.socket=socket;
	}
	
	public boolean estaConectado() throws IOException{
		if(!this.socket.isConnected()){//SI EL SOCKET ESTA DESCONECTADO LO ELIMINA DE MI LISTA DE CONEXIONES.
			for(int x=0; x<Servidor.listaDeConexiones.size(); x++){
				if(Servidor.listaDeConexiones.get(x)==this.socket){
					Servidor.listaDeConexiones.remove(x);
				}
			}
			return false;
		}
		return true;
	}
	
	
	@Override
	public void run() {
		
		
			try{
				input = new Scanner(socket.getInputStream());
				
				while(true){
					if(this.estaConectado()){
						if(!input.hasNext()){
							return;
						}
						
						mensaje = input.nextLine();
						System.out.println("El cliente dice: "+mensaje);
						
						for(int x=0; x<Servidor.listaDeConexiones.size(); x++){ //RECORRE TODA LA LISTA DE CONEXIONES PARA ENVIAR EL MENSAJE RECIBIDO A TODOS.
							Socket tempSocket = Servidor.listaDeConexiones.get(x);
							PrintWriter tempOut = new PrintWriter(tempSocket.getOutputStream());
							tempOut.println(socket.getLocalAddress().getHostName()+": "+mensaje);
							tempOut.flush();
							System.out.println("mensaje enviado a: "+tempSocket.getLocalAddress().getHostName());
						}
					}else{
						socket.close();
					}
					
					
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		
	}
	
}
