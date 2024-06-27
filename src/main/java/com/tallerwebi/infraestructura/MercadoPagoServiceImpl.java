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
    private String mercadoPagoAccessToken = "";

    @Override
    public Preference crearPreferencia(int cantidad) throws MPApiException, MPException {
        try {
            logger.info("El access token es esta:" + mercadoPagoAccessToken);
            // Configurar el token de acceso
            MercadoPagoConfig.setAccessToken(mercadoPagoAccessToken);

            // Crear el ítem de preferencia
            PreferenceItemRequest itemRequest = PreferenceItemRequest.builder()
                    .id("1234")
                    .title("Monedas")
                    .description("Compra de monedas")
                    .quantity(cantidad)
                    .currencyId("ARS")
                    .unitPrice(new BigDecimal("5")) // Precio por unidad
                    .build();
            List<PreferenceItemRequest> items = new ArrayList<>();
            items.add(itemRequest);

            // Crear las backUrls
            PreferenceBackUrlsRequest backUrlsRequest = PreferenceBackUrlsRequest.builder()
                    .success("http://localhost:8080/success")
                    .failure("http://localhost:8080/failure")
                    .pending("http://localhost:8080/pending")
                    .build();

            // Crear la solicitud de preferencia
            PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                    .items(items)
                    .backUrls(backUrlsRequest)
                    .build();

            // Crear el cliente de preferencia
            PreferenceClient client = new PreferenceClient();

            // Registrar los detalles antes de la solicitud
            logger.info("Enviando solicitud para crear preferencia con los siguientes detalles:");
            logger.info("Items: {}", items);
            logger.info("BackUrls: {}", backUrlsRequest);

            // Realizar la solicitud para crear la preferencia
            return client.create(preferenceRequest);
        } catch (MPApiException e) {
            // Loguear los detalles del error de API
            logger.error("Error al crear la preferencia en Mercado Pago", e);

            MPResponse response = e.getApiResponse();
            if (response != null) {
                logger.error("Código de estado: {}", response.getStatusCode());
                logger.error("Contenido: {}", response.getContent());
            }

            // Loguear la causa de la excepción si está disponible
            Throwable cause = e.getCause();
            if (cause != null) {
                logger.error("Causa del error: {}", cause.getMessage(), cause);
            }

            throw e; // Volver a lanzar la excepción para que sea manejada en el controlador
        } catch (MPException e) {
            // Loguear el error genérico de MPException
            logger.error("Error general de Mercado Pago", e);
            throw e;
        }
    }
}