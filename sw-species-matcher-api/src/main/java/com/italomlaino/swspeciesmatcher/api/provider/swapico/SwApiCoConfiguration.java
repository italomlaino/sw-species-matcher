package com.italomlaino.swspeciesmatcher.api.provider.swapico;

import feign.codec.ErrorDecoder;
import feign.error.AnnotationErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwApiCoConfiguration {

    @Bean
    public ErrorDecoder errorDecoder() {
        return AnnotationErrorDecoder.builderFor(SwApiCoFeignClient.class).build();
    }
}
