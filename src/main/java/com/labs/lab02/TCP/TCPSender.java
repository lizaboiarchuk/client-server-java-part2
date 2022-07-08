package com.labs.lab02.TCP;

import com.labs.lab02.interfaces.Sender;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

public class TCPSender extends Thread implements Sender {

    public static Queue<byte[]> queue;

    public TCPSender() {
        super("TCPSender");
        queue = new ConcurrentLinkedDeque<>();
        start();
    }

    public static void sender_accept(byte[] m) { queue.add(m); }

    @Override
    public void run() {
        while (true) {
            try {
                byte[] bytes = queue.poll();
                if (bytes != null) {
                    ObjectOutputStream outstream = new ObjectOutputStream(StoreServerTCP.clientsInets.get((byte) 1).getOutputStream());
                    outstream.writeObject(bytes);
                    System.out.println("Server send " + bytes);
                }
            } catch (Exception e) { e.printStackTrace(); }
        }
    }


    @Override
    public void sendMessage(byte[] message) {}

    @Override
    public void sendMessage(byte[] message, InetAddress target) {}
}
