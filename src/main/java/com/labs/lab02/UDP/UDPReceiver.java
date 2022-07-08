package com.labs.lab02.UDP;

import com.labs.lab02.interfaces.Receiver;
import com.labs.lab02.packet.Packet;
import javax.crypto.NoSuchPaddingException;
import java.net.DatagramPacket;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import static com.labs.lab02.UDP.StoreSeverUDP.clientsInets;
import static com.labs.lab02.UDP.StoreSeverUDP.socket;

public class UDPReceiver extends Thread implements Receiver {

    private final UDPDecryptor decoder = new UDPDecryptor();

    public UDPReceiver() throws NoSuchPaddingException, NoSuchAlgorithmException {
        super("UDPReceiver");
        start();
    }


    @Override
    public void run() {
        while (true) {
            try {
                DatagramPacket datagramPacket = new DatagramPacket(new byte[1000], 1000);
                socket.receive(datagramPacket);
                byte[] request = Arrays.copyOfRange(datagramPacket.getData(), 0, datagramPacket.getLength());
                Packet packet = decoder.decode(request);
                clientsInets.put(packet.getClientId(), datagramPacket.getSocketAddress());
                UDPDecryptor.queue_accept(request);
                System.out.println("Server received packet " + packet);

            } catch (Exception e) { e.printStackTrace(); }
        }
    }


    @Override
    public byte[] receiveMessage() { return new byte[0]; }

    @Override
    public Packet receiveMessagePacket() { return null; }
}