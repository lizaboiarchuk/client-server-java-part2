package com.labs.lab02;

import javax.crypto.NoSuchPaddingException;
import java.security.NoSuchAlgorithmException;

public class Main
{
    public static void main( String[] args ) throws NoSuchPaddingException, NoSuchAlgorithmException {
        MessageEncryptor encoder = new MessageEncryptor();
        FakeReceiver receiver = new FakeReceiver();
        MessageDecryptor decoder = new MessageDecryptor();
        FakeProcessor processor = new FakeProcessor();
        FakeSender sender = new FakeSender();
    }
}
