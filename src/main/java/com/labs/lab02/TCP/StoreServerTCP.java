package com.labs.lab02.TCP;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class StoreServerTCP {
    private final int THREADS = 2;
    public static final ConcurrentMap<Byte, Socket> clientsInets = new ConcurrentHashMap<>();
    public static int port;
    public static ServerSocket serverSocket;


    public StoreServerTCP(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        this.port = port;
    }


    public void start() throws IOException, NoSuchPaddingException, NoSuchAlgorithmException {
        for (int i = 0; i< THREADS; i++) {
            new Thread(new TCPReceiver()).start();
            new Thread(new TCPDecryptor()).start();
            new Thread(new TCPProcessor()).start();
            new Thread(new TCPEncryptor()).start();
            new Thread(new TCPSender()).start();
        }
    }
}
