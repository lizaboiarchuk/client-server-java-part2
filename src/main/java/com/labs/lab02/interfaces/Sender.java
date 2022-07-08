package com.labs.lab02.interfaces;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.net.InetAddress;
import java.security.InvalidKeyException;

public interface Sender {
    public void sendMessage(byte[] message) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException;
    public void sendMessage(byte[] message, InetAddress target) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException;

}
