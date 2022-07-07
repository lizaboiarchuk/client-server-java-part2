package com.labs.lab02.interfaces;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.security.InvalidKeyException;

public interface Sender {

    public void sendMessage(byte[] message) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException;

}
