package com.nhnacademy.illuwa.config;

import feign.Client;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import feign.form.spring.SpringFormEncoder;
import org.apache.http.impl.client.HttpClients;
import feign.codec.Encoder;

@Configuration
public class FeignClientConfig {
    @Bean
    public Encoder feignFormEncoder(ObjectFactory<HttpMessageConverters> messageConverters) {
        return new SpringFormEncoder(new SpringEncoder(messageConverters));
    }

    @Bean
    public Client feignClient() {
        return new feign.httpclient.ApacheHttpClient(HttpClients.createDefault());
    }
}
