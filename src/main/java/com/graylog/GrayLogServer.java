package com.graylog;


import org.graylog2.gelfclient.*;
import org.graylog2.gelfclient.transport.GelfTransport;
import org.slf4j.LoggerFactory;

public class GrayLogServer {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(GrayLogServer.class);


    public static void main(String[] args) throws InterruptedException {
        GelfConfiguration gelfConfiguration = new GelfConfiguration("127.0.0.1", 12201)
                .transport(GelfTransports.TCP).tcpKeepAlive(false)
                .queueSize(512).connectTimeout(5000).reconnectDelay(1000).tcpNoDelay(true).sendBufferSize(32768);

        GelfTransport gelfTransport = GelfTransports.create(gelfConfiguration);

        GelfMessage gelfMessage = new GelfMessageBuilder("Hi").fullMessage("Hello").
                additionalField("_facility", "test").level(GelfMessageLevel.ERROR).build();
        for (int i = 0; i <1000000; i++) {
            System.out.println(i);
            gelfTransport.send(gelfMessage);
            Thread.sleep(10);
        }
        System.out.println("done");

    }
}
