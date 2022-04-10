package com.silverspoon8.eventloopdemo;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@RestController
public class BlockingController {
    @GetMapping(value = "/block/health", produces = MediaType.TEXT_PLAIN_VALUE)
    public Mono<String> sleep() {
        return Mono.fromSupplier(() -> blockingFunction(10_000L));
                // .subscribeOn(Schedulers.boundedElastic());
    }

    private String blockingFunction(long sleepMs) {
        try {
            Thread.sleep(sleepMs);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "OK";
    }

    @GetMapping(value = "/health", produces = MediaType.TEXT_PLAIN_VALUE)
    public String health() {
        return "OK";
    }
}
