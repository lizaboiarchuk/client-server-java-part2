package com.labs.lab02;

import com.labs.lab02.packet.CRC16;
import com.labs.lab02.packet.Message;
import com.labs.lab02.interfaces.Decryptor;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;


public class MessageDecryptor extends Thread implements Decryptor {

    public static Queue<byte[]> messageQueue;
    private static final String ENCRYPTION_STRING_KEY = "encryptkeystring";
    private static final byte START_BYTE = 0x13;
    private static int minMessageLength = 8;
    private static SecretKey secret;
    private static Cipher cipher;


    public MessageDecryptor() throws NoSuchPaddingException, NoSuchAlgorithmException {
        super("MessageDecryptor");
        messageQueue = new ConcurrentLinkedDeque<>();
        start();
        byte[] encryptionBytes = ENCRYPTION_STRING_KEY.getBytes();
        cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        secret = new SecretKeySpec(encryptionBytes, "AES");
    }

    public static void queue_accept(byte[] m){ messageQueue.add(m); }

    @Override
    public Message decode(byte[] bytes) throws  BadPaddingException,  IllegalBlockSizeException, InvalidKeyException {
        ByteBuffer buffer = ByteBuffer.wrap(bytes).order(ByteOrder.BIG_ENDIAN);
        if (buffer.get() != START_BYTE) { throw new IllegalArgumentException("Invalid start byte :( "); }

        byte clientId = buffer.get();
        long packetId = buffer.getLong();
        int contentLen = buffer.getInt();
        short cHead = buffer.getShort();

        // check packet head for validity
        byte [] head = ByteBuffer.allocate(14)
                .order(ByteOrder.BIG_ENDIAN)
                .put(START_BYTE)
                .put(clientId)
                .putLong(packetId)
                .putInt(contentLen)
                .array();

        if (CRC16.crc16(head)!= cHead) { throw new IllegalArgumentException("CRC16 head broken :( "); }

        // check packet body for validity
        byte [] messageBytes = Arrays.copyOfRange(bytes, 16, 16 + contentLen);

        int cType = buffer.getInt();
        int bUserId = buffer.getInt();
        byte[] content = Arrays.copyOfRange(messageBytes,8, contentLen);

        short cContent = buffer.getShort(16 + contentLen);

        if (CRC16.crc16(messageBytes) != cContent) { throw new IllegalArgumentException("CRC16 body broken :( "); }

        // decrypt message content
        cipher.init(Cipher.DECRYPT_MODE, secret);
        byte[] decodedContent = cipher.doFinal(content);

        return new Message(decodedContent, cType, bUserId);
    }

    @Override
    public void run() {
        while (true) {
            try {
                byte[] message = messageQueue.poll();
                if (message != null) {
                    Message result = decode(message);
                    System.out.println("Decoded " + result);
                    FakeProcessor.queue_accept(result);
                }
            } catch (Exception e) { e.printStackTrace(); }
        }
    }
}
