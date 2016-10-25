package chat;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Servidor {
	public static ArrayList<Socket> listaDeConexiones = new ArrayList<>();
	public static ArrayList<String> listaDeUsuarios = new ArrayList<>();
	
	public static void main(String[] args) throws Exception{
		try{
			final int PORT = 444;
			ServerSocket server = new ServerSocket(PORT); //CREO EL SERVER CON EL PUERTO A UTILIZAR. ESTE VA A SER IGUAL PARA TODOS
			
			
			while(true){
				System.out.println("Esperando un cliente");
				Socket socket = server.accept();//CREA UN NUEVO SOCKET Y QUEDA A LA ESPERA DE UNA CONEXION NUEVA
				listaDeConexiones.add(socket); //AGREGO ESTE NUEVO SOCKET A MI LISTA DE SOCKETS. EL SOCKET ES EL INTERMEDIARIO ENTRE LA COMUNICACION
				
				System.out.println("Cliente conectado desde: "+ socket.getLocalAddress().getHostName());
				
				
				ServidorChat chat = new ServidorChat(socket);
				Thread nuevoProcesoParalelo = new Thread(chat); // GENERO UN NUEVO THREAD PARA CORRER EL NUEVO PROCESO.
				nuevoProcesoParalelo.start(); // HAGO CORRER EL THREAD DEL NUEVO PROCESO.
			}
		}catch(Exception e){
			
		}
	}
	
	public static void addUserName(Socket x) throws Exception{
		Scanner input = new Scanner(x.getInputStream());
		String userName = input.nextLine();
		
		listaDeUsuarios.add(userName);
	}
}
