package com.martinachov.resilience4j.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ResilienceService {
    public String getTimeDelayData(int delay) {
        try {
            Thread.sleep(delay*1000);
            return "Delay = " + delay;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getError() {
        throw  new RuntimeException("Se produjo un error inesperado !!");
    }
}
