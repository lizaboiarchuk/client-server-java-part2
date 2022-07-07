package com.labs.lab02.interfaces;

import com.labs.lab02.packet.Message;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.security.InvalidKeyException;

public interface Decryptor {

    public Message decode(byte[] message) throws BadPaddingException, IllegalBlockSizeException, InvalidKeyException;

}
