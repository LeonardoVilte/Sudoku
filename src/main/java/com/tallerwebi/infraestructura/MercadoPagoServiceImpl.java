package com.tallerwebi.infraestructura;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.resources.preference.Preference;
import com.tallerwebi.dominio.MercadoPagoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service("mercadoPagoService")
public class MercadoPagoServiceImpl implements MercadoPagoService {

    @Value("${mercadoPago.accessToken}")
    private String mercadoPagoAccessToken;

    @Override
    public Preference crearPreferencia(int cantidad) {
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

            // Crear las backUrls
            PreferenceBackUrlsRequest backUrlsRequest = PreferenceBackUrlsRequest.builder()
                    .success("https://www.tusitio.com/success")
                    .failure("https://www.tusitio.com/failure")
                    .pending("https://www.tusitio.com/pending")
                    .build();

            PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                    .items(items)
                    .backUrls(backUrlsRequest) // Usar el objeto backUrlsRequest aquí
                    .build();

            PreferenceClient client = new PreferenceClient();
            return client.create(preferenceRequest);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
