package com.labs.lab02.UDP;

import com.labs.lab02.RGenerator;
import com.labs.lab02.packet.Message;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class Main  {
    public static void main( String[] args ) throws NoSuchPaddingException, NoSuchAlgorithmException, IOException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        StoreSeverUDP server = new StoreSeverUDP(3000);
        server.start();

        StoreClientUDP client = new StoreClientUDP((byte) 1, 3000);
        Message m = new RGenerator().generateMessage();
        client.send(m);
    }
}