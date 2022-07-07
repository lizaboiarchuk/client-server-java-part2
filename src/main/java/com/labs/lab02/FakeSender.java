package com.labs.lab02;

import com.labs.lab02.interfaces.Sender;
import com.labs.lab02.packet.Message;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

public class FakeSender extends Thread implements Sender {

    MessageDecryptor decryptor = new MessageDecryptor();
    public static Queue<byte[]> resQueue;

    public FakeSender() throws NoSuchPaddingException, NoSuchAlgorithmException {
        super("FakeSender");
        resQueue = new ConcurrentLinkedDeque<>();
        start();
    }

    public static void queue_accept(byte[] m) { resQueue.add(m); }

    @Override
    public void sendMessage(byte[] message) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        Message m = decryptor.decode(message);
        System.out.println(m.getMessageString());
    }

    @Override
    public void run(){
        while (true) {
            try {
                byte[] message = resQueue.poll();
                if (message != null) { sendMessage(message); }
            } catch (Exception e) { e.printStackTrace(); }
        }
    }
}
