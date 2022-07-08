package com.labs.lab02;

import com.labs.lab02.packet.Packet;
import com.labs.lab02.interfaces.Receiver;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class FakeReceiver extends Thread implements Receiver {

    private RGenerator generator = new RGenerator();
    MessageEncryptor coder = new MessageEncryptor();

    private int numOfFakeMessages = 1;


    public FakeReceiver() throws NoSuchPaddingException, NoSuchAlgorithmException {
        super("FakeReceiver");
        start();
    }


    @Override
    public byte[] receiveMessage() throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        System.out.println("--- RECEIVED NEW MESSAGE ---");
        Packet packet = generator.generatePacket();
        return coder.encode(packet);
    }

    @Override
    public Packet receiveMessagePacket() throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, IOException {
        return null;
    }

    @Override
    public void run() {
        for (int i = 0; i < numOfFakeMessages; i++) {
            try {
                byte[] message = receiveMessage();
                MessageDecryptor.queue_accept(message);
            } catch (Exception e) { e.printStackTrace(); }
        }
    }
}
