package com.redhat.onetapsoccer;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

import org.apache.camel.Message;

public class RangeBean {

    public void defineRange(Message message) {
        ZoneId zoneId = ZoneId.of("America/Sao_Paulo");
        LocalDateTime now = LocalDateTime.now(zoneId);
        LocalDateTime nowMinus1h = now.minus(1, ChronoUnit.HOURS);
        System.out.println("Now   : "+now+"="+now.toEpochSecond(ZoneOffset.ofHours(-3)));
        System.out.println("Now-1h: "+nowMinus1h+"="+nowMinus1h.toEpochSecond(ZoneOffset.ofHours(-3)));
        message.setHeader("start", nowMinus1h.toEpochSecond(ZoneOffset.ofHours(-3)));
        message.setHeader("end", now.toEpochSecond(ZoneOffset.ofHours(-3)));
        
    }

}
