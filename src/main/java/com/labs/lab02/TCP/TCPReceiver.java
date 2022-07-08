package com.labs.lab02.TCP;

import com.labs.lab02.interfaces.Receiver;
import com.labs.lab02.packet.Packet;
import javax.crypto.NoSuchPaddingException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;

public class TCPReceiver extends Thread implements Receiver {

    private final TCPDecryptor decoder = new TCPDecryptor();

    public TCPReceiver() throws NoSuchPaddingException, NoSuchAlgorithmException {
        super("TCPReceiver");
        start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket socket = StoreServerTCP.serverSocket.accept();
                System.out.println("Connected to TCP server");
                ObjectInputStream instream = new ObjectInputStream(socket.getInputStream());
                byte[] bytes = (byte[]) instream.readObject();
                Packet packet = decoder.decode(bytes);
                StoreServerTCP.clientsInets.put(packet.getClientId(), socket);
                TCPDecryptor.queue_accept(bytes);
                System.out.println("Server received packet " + packet);
            }
            catch( Exception e )  { e.printStackTrace(); }
        }
    }

    @Override
    public byte[] receiveMessage() { return new byte[0]; }

    @Override
    public Packet receiveMessagePacket() { return null; }
}
