package oldLessons.javaChat.model;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread{
    private int port;

    public Server(int port){
        this.port = port;
    }

    public void run(){

        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Сервер создан. Порт № "+port);
            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    new ClientHandler(socket).start();
                    System.out.println("Клиент подключен");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Порт, судя по всему, занят. Попробуйте другой.");
        }
    }
}
