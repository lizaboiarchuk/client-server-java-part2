package com.labs.lab02.interfaces;

import com.labs.lab02.packet.Packet;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.security.InvalidKeyException;

public interface Encryptor {

    public byte[] encode(Packet packet) throws BadPaddingException, IllegalBlockSizeException, InvalidKeyException;
}
