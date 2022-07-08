package com.labs.lab02.TCP;

import com.labs.lab02.packet.CRC16;
import com.labs.lab02.packet.Message;
import com.labs.lab02.packet.Packet;
import com.labs.lab02.interfaces.Encryptor;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;


public class TCPEncryptor extends Thread implements Encryptor {
    public static Queue<Packet> packetQueue;
    private static final String ENCRYPTION_STRING_KEY = "encryptkeystring";
    private static final byte START_BYTE = 0x13;
    private static int minMessageLength = 8;
    private static SecretKey secret;
    private static Cipher cipher;


    public TCPEncryptor() throws NoSuchPaddingException, NoSuchAlgorithmException {
        super("MessageEncryptor");
        byte[] encryptionBytes = ENCRYPTION_STRING_KEY.getBytes();
        cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        secret = new SecretKeySpec(encryptionBytes, "AES");
        packetQueue =new ConcurrentLinkedDeque<>();
        start();
    }

    public static void queue_accept(Packet p){
        packetQueue.add(p);
    }

    public byte[] encode(Packet packet) throws BadPaddingException,IllegalBlockSizeException, InvalidKeyException {

        Message message = packet.getMessage();

        // encrypt message content
        cipher.init(Cipher.ENCRYPT_MODE, secret);
        byte[] contentEncrypted = cipher.doFinal(message.getMessageContent());

        // create packet
        byte[] head = ByteBuffer.allocate(14)
                .order(ByteOrder.BIG_ENDIAN)
                .put(START_BYTE)
                .put(packet.getClientId())
                .putLong(packet.getPacketId())
                .putInt(minMessageLength + contentEncrypted.length)
                .array();

        byte[] body = ByteBuffer.allocate(minMessageLength + contentEncrypted.length)
                .order(ByteOrder.BIG_ENDIAN)
                .putInt(message.getcType())
                .putInt(message.getbUserId())
                .put(contentEncrypted)
                .array();

        System.out.println(contentEncrypted);

        return ByteBuffer.allocate(18 + minMessageLength + contentEncrypted.length)
                .order(ByteOrder.BIG_ENDIAN)
                .put(head)
                .putShort(CRC16.crc16(head))
                .put(body)
                .putShort(CRC16.crc16(body))
                .array();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Packet packet = packetQueue.poll();
                if (packet != null) {
                    byte[] encoded = encode(packet);
                    System.out.println("Encoded: " + encoded);
                    TCPSender.sender_accept(encoded);
                }
            } catch (Exception e) { e.printStackTrace(); }
        }
    }

}
