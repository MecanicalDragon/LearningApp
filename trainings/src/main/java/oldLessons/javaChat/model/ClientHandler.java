package oldLessons.javaChat.model;

import java.io.*;
import java.net.Socket;
import java.util.*;

public class ClientHandler extends Thread {

    private Socket socket;
    private DataOutputStream output;
    private DataInputStream input;
    private static List<ClientHandler> arbor = Collections.synchronizedList(new ArrayList<>());

    public ClientHandler(Socket s) throws IOException {
        socket = s;
        input = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        output = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
    }

    public void run(){
        try {
            arbor.add(this);
            while(true){
                String msg = input.readUTF();
//                System.out.println("сообщение принято сервером");
                broadcast(msg);
            }
        } catch (IOException e){e.printStackTrace();}


    }

   void broadcast(String msg) throws IOException {
        for (ClientHandler h : arbor) {
            h.output.writeUTF(msg);
            h.output.flush();
//            System.out.println("Сообщение переправлено");
        }
    }
}
