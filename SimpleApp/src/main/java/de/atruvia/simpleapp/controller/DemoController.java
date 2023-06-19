package de.atruvia.simpleapp.controller;

import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedList;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("demo")
@Timed("demo")
@Log4j2
public class DemoController {
    private final MyComponent myComponent;
    private LinkedList<Long> list = new LinkedList<>();
    private final MeterRegistry registry;

    private final RestTemplate restTemplate;

    // Update the constructor to create the gauge
    DemoController(MeterRegistry registry, MyComponent myComponent ,final RestTemplate restTemplate) {
        this.registry = registry;
        this.myComponent = myComponent;
        this.restTemplate = restTemplate;
        registry.gaugeCollectionSize("example.list.size", Tags.empty(), list);
    }
    @GetMapping(path = "path-1", produces = MediaType.TEXT_PLAIN_VALUE)
    @Counted("getone.counted")
    public String getOne() {
        log.info("get_one");
        myComponent.foo();
        myComponent.foo();
        myComponent.foo();
        myComponent.bar();
        myComponent.bar();
        return "path-1";
    }
    @GetMapping(path = "path-2", produces = MediaType.TEXT_PLAIN_VALUE)
    // @Timed(value = "example.metric.name", Perzentile = { 0,95, 0,75 })
    // https://medium.com/clarityai-engineering/effectively-measuring-execution-times-with-micrometer-datadog-5ad15fb8abee
    @Timed(value = "demo.getsecond", description = "eine doofe Beschreibung")
    public String getSecond() throws Exception{
        log.info("get_second");
        Thread.sleep(20);
        return "path-2";
    }
    @GetMapping(path = "path-3", produces = MediaType.TEXT_PLAIN_VALUE)
    // @Timed(value = "example.metric.name", Perzentile = { 0,95, 0,75 })
    // https://medium.com/clarityai-engineering/effectively-measuring-execution-times-with-micrometer-datadog-5ad15fb8abee
    @Timed(value = "demo.getthird", description = "eine doofe Beschreibung")
    public String getThird() throws Exception{
        log.info("get_third");
        String result = restTemplate.getForObject("http://localhost:8130/demo/path-2", String.class);
        return "path-3 enriched by "  + result;
    }




    @GetMapping(path = "gauge/{number}")
    // https://www.baeldung.com/java-netflix-spectator
    public Long checkListSize(@PathVariable("number") long number) {
        log.info("checkListSize");
        if (number == 2 || number % 2 == 0) {
            // add even numbers to the list
            list.add(number);
        } else {
            // remove items from the list for odd numbers
            try {
                number = list.removeFirst();
            } catch (NoSuchElementException nse) {
                number = 0;
            }
        }
        return number;
    }
}
