package oldLessons.javaChat.examples.ChatClient;

import java.io.*;
import java.net.Socket;

public class MainClass {
    static DataInputStream input;
    static DataOutputStream output;

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost",8008);
        input = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        output = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        new ChatFrame();
    }
}
