package com.labs.lab02.UDP;

import com.labs.lab02.interfaces.Sender;
import com.labs.lab02.packet.Packet;
import javax.crypto.NoSuchPaddingException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.security.NoSuchAlgorithmException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

import static com.labs.lab02.UDP.StoreSeverUDP.clientsInets;
import static com.labs.lab02.UDP.StoreSeverUDP.socket;

public class UDPSender extends Thread implements Sender {
    public static Queue<Packet> queue;
    private final UDPEncryptor encoder = new UDPEncryptor();

    public UDPSender() throws NoSuchPaddingException, NoSuchAlgorithmException {
        super("UDPSender");
        queue = new ConcurrentLinkedDeque<>();
        start();
    }

    public static void queue_accept(Packet p) { queue.add(p); }

    @Override
    public void run() {
        while (true) {
            try {
                Packet packet = queue.poll();
                if (packet != null) {
                    byte[] bytes = encoder.encode(packet);
                    DatagramPacket datagramPacket = new DatagramPacket(bytes, bytes.length, clientsInets.get((byte) 1));
                    socket.send(datagramPacket);
                    System.out.println("Send " + packet);
                }
            } catch (Exception e) { e.printStackTrace(); }
        }
    }

    @Override
    public void sendMessage(byte[] message) {}

    @Override
    public void sendMessage(byte[] message, InetAddress target) {}
}

