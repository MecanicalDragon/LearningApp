package net.medrag.something.ionio;

import com.github.tomakehurst.wiremock.WireMockServer;
import lombok.SneakyThrows;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.StandardCharsets;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

/**
 * Example from here: https://www.baeldung.com/java-io-vs-nio
 *
 * @author Stanislav Tretyakov
 * 03.11.2021
 */
public class IoNioExample {

    public static WireMockServer WIRE_MOCK_SERVER;
    private static final String RESPONSE = """
            Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et
             dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip
             ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu
             fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt
             mollit anim id est laborum.""";

    public static void main(String[] args) {

        WIRE_MOCK_SERVER = new WireMockServer(7676);
        WIRE_MOCK_SERVER.start();
        WIRE_MOCK_SERVER.stubFor(get(urlEqualTo("/stream")).willReturn(aResponse().withBody(RESPONSE)));

        instantiateIO();
        instantiateNIO();

        WIRE_MOCK_SERVER.stop();
    }

    @SneakyThrows
    private static void instantiateNIO() {

        InetSocketAddress address = new InetSocketAddress("localhost", WIRE_MOCK_SERVER.port());
        try (SocketChannel socketChannel = SocketChannel.open(address)) {

            Charset charset = StandardCharsets.UTF_8;
            socketChannel.write(charset.encode(CharBuffer.wrap("GET /stream HTTP/1.0\r\n\r\n")));

            ByteBuffer byteBuffer = ByteBuffer.allocate(8192); // allocateDirect() for native memory
            CharsetDecoder charsetDecoder = charset.newDecoder();
            CharBuffer charBuffer = CharBuffer.allocate(8192);

            socketChannel.read(byteBuffer);
            StringBuilder stringBuilder = new StringBuilder();

            while (socketChannel.read(byteBuffer) != -1 || byteBuffer.position() > 0) {
                byteBuffer.flip();
                charsetDecoder.decode(byteBuffer, charBuffer, true);
                charBuffer.flip();
                stringBuilder.append(charBuffer);
                charBuffer.clear();
                byteBuffer.compact();
            }

            System.out.println(stringBuilder);
        }
    }

    @SneakyThrows
    private static void instantiateIO() {
        try (Socket socket = new Socket("localhost", WIRE_MOCK_SERVER.port());
             OutputStream clientOutput = socket.getOutputStream();
             PrintWriter writer = new PrintWriter(new OutputStreamWriter(clientOutput));
             InputStream serverInput = socket.getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(serverInput))) {

            writer.print("GET /stream HTTP/1.0\r\n\r\n");
            writer.flush();

            StringBuilder sb = new StringBuilder();

            for (String line; (line = reader.readLine()) != null; ) {
                sb.append(line);
                sb.append(System.lineSeparator());
            }

            System.out.println(sb);
        }
    }
}
