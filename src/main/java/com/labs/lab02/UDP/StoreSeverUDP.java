package com.labs.lab02.UDP;

import javax.crypto.NoSuchPaddingException;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class StoreSeverUDP {
    int THREADS = 2;
    public static final ConcurrentMap<Byte, SocketAddress> clientsInets = new ConcurrentHashMap<>();
    public static DatagramSocket socket;

    public StoreSeverUDP(int port) throws SocketException {
        socket = new DatagramSocket(port);
        System.out.println("Server started");
    }

    public void start() throws NoSuchPaddingException, NoSuchAlgorithmException {
        for (int i = 0; i < THREADS; i++) {
            new Thread(new UDPReceiver()).start();
            new Thread(new UDPDecryptor()).start();
            new Thread(new UDPProcessor()).start();
            new Thread(new UDPEncryptor()).start();
            new Thread(new UDPSender()).start();
        }
    }
}
