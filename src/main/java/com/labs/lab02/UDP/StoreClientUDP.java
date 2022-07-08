package com.labs.lab02.UDP;

import com.labs.lab02.packet.Message;
import com.labs.lab02.packet.Packet;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.net.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


public class StoreClientUDP {

    private final DatagramSocket socket;
    private final UDPEncryptor encoder = new UDPEncryptor();
    private final byte client;
    public static int numOfPackets = 0;
    private int port;

    public StoreClientUDP(byte client, int port) throws SocketException, NoSuchPaddingException, NoSuchAlgorithmException {
        this.socket = new DatagramSocket();
        this.client = client;
        this.port = port;
    }

    public void send(Message mes) throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException, IOException {
        Packet response = new Packet(client, ++numOfPackets, mes);
        byte[] responseBytes = encoder.encode(response);
        DatagramPacket responseDatagram = new DatagramPacket(responseBytes, responseBytes.length, InetAddress.getByName(null), this.port);
        socket.send(responseDatagram);
        System.out.println("Message sent");
    }
}
