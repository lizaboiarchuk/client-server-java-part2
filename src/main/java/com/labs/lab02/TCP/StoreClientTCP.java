package com.labs.lab02.TCP;

import com.labs.lab02.packet.Message;
import com.labs.lab02.packet.Packet;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class StoreClientTCP {
    private final byte client;
    public static int numOfPackets;
    public static int port;
    Socket socket;
    ObjectOutputStream stream;
    private final TCPEncryptor encoder = new TCPEncryptor();

    public StoreClientTCP(byte client, int port) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException {
        this.port = port;
        this.numOfPackets = 0;
        this.socket = new Socket(InetAddress.getLocalHost().getHostAddress(), this.port);
        this.client = client;
        this.stream = null;
    }

    public void send(Message message) throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException, IOException {
        numOfPackets = numOfPackets + 1;
        Packet result = new Packet(client, numOfPackets, message);
        byte[] bytes = encoder.encode(result);
        stream = new ObjectOutputStream(socket.getOutputStream());
        stream.writeObject(bytes);

        System.out.println("Request sent to socket from client  " + client);
    }
}
