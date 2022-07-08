package com.labs.lab02.TCP;

import com.labs.lab02.RGenerator;
import com.labs.lab02.TCP.StoreClientTCP;
import com.labs.lab02.TCP.StoreServerTCP;
import com.labs.lab02.packet.Message;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class Main  {
    public static void main( String[] args ) throws NoSuchPaddingException, NoSuchAlgorithmException, IOException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        StoreServerTCP server = new StoreServerTCP(6666);
        server.start();
        StoreClientTCP client = new StoreClientTCP((byte) 1, 6666);

        Message m = new RGenerator().generateMessage();
        client.send(m);
    }
}