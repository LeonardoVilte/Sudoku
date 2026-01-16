package com.tallerwebi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MercadoPagoConfig {

    @Value("${mercadopago.access.token}")
    private String accessToken;

    public String getAccessToken(){
        return accessToken;
    }
}
