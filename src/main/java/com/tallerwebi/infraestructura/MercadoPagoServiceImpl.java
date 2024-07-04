package com.tallerwebi.infraestructura;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import com.mercadopago.net.MPResponse;
import com.tallerwebi.dominio.MercadoPagoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service("mercadoPagoService")
public class MercadoPagoServiceImpl implements MercadoPagoService {

    private static final Logger logger = LoggerFactory.getLogger(MercadoPagoServiceImpl.class);

    //@Value("${mercadopago.access.token}")
    private String mercadoPagoAccessToken = "APP_USR-7148490535853042-063000-edad81c3b1347085e79da1ca7b622b94-1874683882";

    @Override
    public Preference crearPreferencia(int cantidad) throws MPApiException, MPException {
        try {
            MercadoPagoConfig.setAccessToken(mercadoPagoAccessToken);

            PreferenceItemRequest itemRequest = PreferenceItemRequest.builder()
                    .id("1234")
                    .title("Monedas")
                    .description("Compra de monedas")
                    .quantity(cantidad)
                    .currencyId("ARS")
                    .unitPrice(new BigDecimal("10")) // Precio por unidad, ajustar según sea necesario
                    .build();
            List<PreferenceItemRequest> items = new ArrayList<>();
            items.add(itemRequest);

            PreferenceBackUrlsRequest backUrlsRequest = PreferenceBackUrlsRequest.builder()
                    .success("http://localhost:8080/spring/success")
                    .failure("http://localhost:8080/spring/failure")
                    .pending("http://localhost:8080/spring/pending")
                    .build();

            // Usar externalReference para pasar la cantidad de monedas
            PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                    .items(items)
                    .backUrls(backUrlsRequest)
                    .externalReference(String.valueOf(cantidad)) // Almacenar la cantidad de monedas compradas
                    .build();

            PreferenceClient client = new PreferenceClient();

            return client.create(preferenceRequest);
        } catch (MPApiException e) {
            logger.error("Error al crear la preferencia en Mercado Pago", e);

            MPResponse response = e.getApiResponse();
            if (response != null) {
                logger.error("Código de estado: {}", response.getStatusCode());
                logger.error("Contenido: {}", response.getContent());
            }
            throw e;
        } catch (MPException e) {
            logger.error("Error general de Mercado Pago", e);
            throw e;
        }
        }
}