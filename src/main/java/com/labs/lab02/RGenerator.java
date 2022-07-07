package com.labs.lab02;

import com.labs.lab02.packet.Message;
import com.labs.lab02.packet.Packet;

import java.util.Random;

public class RGenerator {

    public Message generateMessage() {
        Random randomizer = new Random();
        int cType = randomizer.nextInt(Message.cType.values().length);
        String commandName = (Message.cType.values()[cType]).toString();
        Message message = new Message(commandName.getBytes(), cType, 1);
        return message;
    }

    public Packet generatePacket() {
        Message message = generateMessage();
        Packet packet = new Packet((byte) 0,1, message);
        return packet;
    }




}
