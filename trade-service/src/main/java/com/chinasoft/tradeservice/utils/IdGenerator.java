package com.chinasoft.tradeservice.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class IdGenerator {
    private final AtomicLong sequence = new AtomicLong(0);
    
    public String generateTradeId() {
        return "T" + LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE) +
               String.format("%07d", sequence.incrementAndGet() % 9999999);
    }
}