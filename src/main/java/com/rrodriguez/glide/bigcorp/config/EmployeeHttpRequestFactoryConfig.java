package com.rrodriguez.glide.bigcorp.config;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.security.GeneralSecurityException;

@Configuration
public class EmployeeHttpRequestFactoryConfig {


    private final Logger LOGGER = LoggerFactory.getLogger(EmployeeHttpRequestFactoryConfig.class);

    @Value("${employees.api.read.timeout}")
    private int readTimeout;

    @Value("${employees.api.connect.timeout}")
    private int connectTimeout;

    @Bean("restTemplateEmployees")
    public RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate(getClientHttpRequestFactory());
        return restTemplate;
    }

    private ClientHttpRequestFactory getClientHttpRequestFactory() {
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();

        requestFactory.setHttpClient(getHttpClient());
        requestFactory.setReadTimeout(readTimeout);
        requestFactory.setConnectTimeout(connectTimeout);

        return requestFactory;
    }

    private HttpClient getHttpClient() {
        SSLContext sslContext = null;
        try {
            sslContext = new SSLContextBuilder()
                    .loadTrustMaterial(null, (cert, auth) -> true)
                    .build();
        } catch (GeneralSecurityException e) {
            LOGGER.error("Could not load HttpClient. Exception is: {}", e);
        }

        return HttpClients.custom()
                .setSSLContext(sslContext)
                .setSSLHostnameVerifier(new NoopHostnameVerifier())
                .build();
    }
}
