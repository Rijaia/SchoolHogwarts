package ru.hogwarts.school.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.IntStream;
import java.util.stream.Stream;

@RestController
@RequestMapping("/getPort")
public class InfoController {
    @Value("${server.port}")
    private int port;

    @GetMapping
    public int getPort() {
        return port;
    }

    @GetMapping("/sum")
    public String getSum() {

        Logger log = LoggerFactory.getLogger(InfoController.class);
        long startTime = System.currentTimeMillis();
        int sum1 = Stream.iterate(1, a -> a +1)
                .limit(1_000_000) .reduce(0, (a, b) -> a + b );
        long endTime = System.currentTimeMillis();
        log.info("Experiment 1. Sum with reduce"+
                "Sum = "+sum1+
                "total calculate time "+ (endTime-startTime));

        startTime = System.currentTimeMillis();
        int sum2 = IntStream.iterate(1, a -> a +1)
                .limit(1_000_000).sum();
        endTime = System.currentTimeMillis();
        log.info("Experiment 1. Sum with sum"+
                "Sum = "+sum1+
                "total calculate time "+ (endTime-startTime));

        startTime = System.currentTimeMillis();
        int sum3 = Stream.iterate(1, a -> a +1) .limit(1_000_000)
                .parallel()
                .reduce(0, (a, b) -> a + b );
        endTime = System.currentTimeMillis();
        log.info("Experiment 1. Sum with reduce and parallel"+
                "Sum = "+sum1+
                "total calculate time "+ (endTime-startTime));

        startTime = System.currentTimeMillis();
        int sum4 = 0;
        for (int i = 0; i <= 1000000; i++) {
            sum4+=i;
        }
        endTime = System.currentTimeMillis();
        log.info("Experiment 1. Sum cycle for"+
                "Sum = "+sum1+
                "total calculate time "+ (endTime-startTime));
        return "sum 1 = "+sum1+
                "sum 2 = "+ sum2+
                "sum 3 = "+ sum3+
                "sum 4 = "+ sum4;
    }
}
