package com.labs.lab02;

import com.labs.lab02.packet.Message;
import com.labs.lab02.packet.Packet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit test for simple App.
 */

class TestLab2 {
//    @org.junit.jupiter.api.Test
//    void PacketTest() throws NoSuchPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException, NoSuchPaddingException, NoSuchAlgorithmException {
//        MessageDecryptor coder = new MessageDecryptor();
//        MessageEncryptor encoder = new MessageEncryptor();
//        Packet packet = new Packet((byte) 1, 42, new Message("Testing this message", 0, 1));
//        byte[] encoded = encoder.encode(packet);
//        Message decoded = coder.decode(encoded);
//        assertEquals("Testing this message", new String(decoded.getMessageString()));
//    }
//
//    @ParameterizedTest
//    @CsvSource({
//            "2, 234, How are you?",
//            "1, 41, 12132323",
//            "0, 1323, One more message",
//            "4, 1000, And one more message..."
//    })
//    void parametrizedPacketTest(byte clientId, long packetId, String message) throws NoSuchPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
//        MessageDecryptor coder = new MessageDecryptor();
//        MessageEncryptor encoder = new MessageEncryptor();
//        Packet packet = new Packet(clientId, packetId, new Message(message, 0, 1));
//        byte[] encoded = encoder.encode(packet);
//        Message decoded = coder.decode(encoded);
//        assertEquals(message, decoded.getMessageString());
//
//    }
//
//    @Test
//    void brokenHeaderCRC16Test() throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException {
//        MessageDecryptor coder = new MessageDecryptor();
//        MessageEncryptor encoder = new MessageEncryptor();
//        Packet packet = new Packet((byte) 0, 1, new Message("Hey there", 0, 1));
//        byte[] encoded = encoder.encode(packet);
//        encoded[9] = (byte) (encoded[9] - 1);
//        assertThrows(IllegalArgumentException.class, () -> coder.decode(encoded));
//    }
//
//    @Test
//    void brokenBodyCRC16Test() throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
//        MessageDecryptor coder = new MessageDecryptor();
//        MessageEncryptor encoder = new MessageEncryptor();
//        Packet packet = new Packet((byte) 0, 1, new Message("Hey there", 0, 1));
//        byte[] encoded = encoder.encode(packet);
//        encoded[encoded.length - 3] = (byte) (encoded[encoded.length - 3] - 2);
//        assertThrows(IllegalArgumentException.class, () -> coder.decode(encoded));
//    }
}
