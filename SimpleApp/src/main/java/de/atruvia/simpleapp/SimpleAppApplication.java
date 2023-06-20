package de.atruvia.simpleapp;

import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.util.HierarchicalNameMapper;
import io.micrometer.graphite.GraphiteConfig;
import io.micrometer.graphite.GraphiteMeterRegistry;
import io.micrometer.graphite.GraphiteProtocol;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@SpringBootApplication
public class SimpleAppApplication {

    static {
        GraphiteConfig graphiteConfig = new GraphiteConfig() {
            @Override
            public String host() {
                return "192.168.2.100";
            }// Graphite Server

            @Override
            public String prefix() {
                return "simple.app";
            }

            @Override
            public int port() {
                return 2004;
            }

            @Override
            public boolean enabled() {
                return true;
            }

            @Override
            public GraphiteProtocol protocol() {
                return GraphiteProtocol.PICKLED;
            }

            @Override
            public String get(String k) {
                return null; // accept the rest of the defaults
            }




            @Override
            public Duration step() {
                return Duration.ofSeconds(1);
            }
        };

        MeterRegistry registry = new GraphiteMeterRegistry(graphiteConfig, Clock.SYSTEM, HierarchicalNameMapper.DEFAULT);
    }

    public static void main(String[] args) {
        SpringApplication.run(SimpleAppApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
}
