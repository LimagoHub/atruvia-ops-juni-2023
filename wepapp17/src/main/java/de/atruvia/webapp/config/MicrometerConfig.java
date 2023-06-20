package de.atruvia.webapp.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.micrometer.core.aop.CountedAspect;
import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import io.opentelemetry.exporter.otlp.http.trace.OtlpHttpSpanExporter;

@Configuration
public class MicrometerConfig
{
    @Bean
    public TimedAspect timedAspect(MeterRegistry registry)
    {
        var timeAspect = new TimedAspect(registry);

        return timeAspect;
    }

    @Bean
    public CountedAspect countedAspect(MeterRegistry registry)
    {

        var countAspect = new CountedAspect(registry);

        return countAspect;
    }

    @Bean
    OtlpHttpSpanExporter otlpHttpSpanExporter(@Value("${tracing.url}") String url)
    {
        return OtlpHttpSpanExporter.builder().setEndpoint(url)

            .build();
    }
}
