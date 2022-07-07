package com.labs.lab02.interfaces;

import com.labs.lab02.packet.Message;
import com.labs.lab02.packet.Packet;

public interface Processor {

    public Packet process(Message message);

}
