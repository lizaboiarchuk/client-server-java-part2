package com.labs.lab02.UDP;

import com.labs.lab02.FakeProcessor;
import com.labs.lab02.MessageEncryptor;
import com.labs.lab02.interfaces.Processor;
import com.labs.lab02.packet.Message;
import com.labs.lab02.packet.Packet;

import java.nio.charset.StandardCharsets;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

public class UDPProcessor extends Thread implements Processor {

    public static Queue<Message> messagesQueue;

    enum Responce { OK, NOT_OK }

    public UDPProcessor() {
        super("FakeProcessor");
        messagesQueue = new ConcurrentLinkedDeque<>();
        start();
    }

    public static void queue_accept(Message m){
        messagesQueue.add(m);
    }

    private FakeProcessor.Responce get_product_total_number(Message message) {
        System.out.println("GETTING PRODUCT TOTAL NUMBER...");
        return FakeProcessor.Responce.OK;
    }

    private FakeProcessor.Responce subtract_product(Message message) {
        System.out.println("SUBTRACTING PRODUCT...");
        return FakeProcessor.Responce.OK;
    }
    private FakeProcessor.Responce add_product(Message message) {
        System.out.println("ADDING PRODUCT...");
        return FakeProcessor.Responce.OK;
    }
    private FakeProcessor.Responce add_product_group(Message message) {
        System.out.println("ADDING PRODUCT GROUP...");
        return FakeProcessor.Responce.OK;
    }
    private FakeProcessor.Responce add_title_to_product_group(Message message) {
        System.out.println("ADDING TITLE TO PRODUCT GROUP...");
        return FakeProcessor.Responce.OK;
    }
    private FakeProcessor.Responce set_product_price(Message message) {
        System.out.println("SETTING PRODUCT PRICE...");
        return FakeProcessor.Responce.OK;
    }

    @Override
    public Packet process(Message message) {

        int cType = message.getcType();
        Message.cType commandName = Message.cType.values()[cType];
        FakeProcessor.Responce responce = FakeProcessor.Responce.NOT_OK;

        switch (commandName) {
            case GET_PRODUCT_TOTAL_NUMBER: responce = get_product_total_number(message); break;
            case SUBTRACT_PRODUCT: responce = subtract_product(message); break;
            case ADD_PRODUCT: responce = add_product(message); break;
            case ADD_PRODUCT_GROUP: responce = add_product_group(message); break;
            case ADD_TITLE_TO_PRODUCT_GROUP: responce = add_title_to_product_group(message); break;
            case SET_PRODUCT_PRICE: responce = set_product_price(message); break;
            default: System.out.println("Undefined command");
        }
        return new Packet((byte) 0, (byte) 1, new Message( responce.toString().getBytes(StandardCharsets.UTF_8), 1, 1));
    }



    @Override
    public void run() {
        while (true) {
            try {
                Message message = messagesQueue.poll();
                if (message != null) {
                    Packet res = process(message);
                    System.out.println("Processed message " + message);
                    UDPEncryptor.queue_accept(res);
                }
            } catch (Exception e) { e.printStackTrace(); }
        }
    }
}
