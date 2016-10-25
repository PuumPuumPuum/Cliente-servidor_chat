package chat;

import java.net.Socket;
import java.util.Scanner;

public class Cliente {
	public static void main(String[] args) throws Exception{
		String server="192.168.0.108";
		try{
			final int PORT = 444;
			Socket socket = new Socket(server, PORT);
			System.out.println("Te conectaste a: "+server);
						
			ClienteChat nuevoCliente = new ClienteChat(socket);
			Thread thread = new Thread(nuevoCliente);
			thread.start();
			
			String textoTeclado = "";
			while(!textoTeclado.equals("fin")){
				Scanner bufferDeTeclado = new Scanner(System.in);
				textoTeclado = bufferDeTeclado.nextLine();
				
				if(textoTeclado.equals("fin")){
					nuevoCliente.desconectar();
					bufferDeTeclado.close();
				}else{
					nuevoCliente.enviarDatos(textoTeclado);
				}
			}
			
		}catch(Exception e){
			
		}
	}
}
