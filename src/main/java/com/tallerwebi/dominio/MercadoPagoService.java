package com.tallerwebi.dominio;

import com.mercadopago.resources.preference.Preference;

public interface MercadoPagoService {
    Preference crearPreferencia(int cantidad) throws Exception;
}
