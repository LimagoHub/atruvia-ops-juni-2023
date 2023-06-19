package de.atruvia.simpleapp.config;

import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MicrometerConfig {



    @Bean
    public TimedAspect timedAspect(MeterRegistry registry) {

        var timeAspect =  new TimedAspect(registry);

        return timeAspect;
    }



}
