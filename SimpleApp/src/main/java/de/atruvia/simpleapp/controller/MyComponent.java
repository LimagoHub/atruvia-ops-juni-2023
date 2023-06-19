package de.atruvia.simpleapp.controller;

import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;
import org.springframework.stereotype.Component;

@Component

public class MyComponent {

    private final Counter counter = Metrics.counter("bar.counter");


    @Timed(value = "foo", description = "Eine Beschreibung") // Timed zaehlt die Aufrufe und misst die Ausfuehrungsdauer
    public void foo() {
        // do nothing
    }

    //@Counted()
    public void bar() {

        counter.increment();
    }



}
