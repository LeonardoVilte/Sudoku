package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mercadopago")
public class ControladorMercadoPago {

    @Autowired
    private ServicioUsuario servicioUsuario;

    @PostMapping("/notifications")
    public ResponseEntity<Void> receiveNotification(@RequestBody MercadoPagoNotification notification) {
        try {
            // Aquí manejas la notificación y actualizas al usuario
            servicioUsuario.actualizarMonedas(notification);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            // Maneja la excepción adecuadamente
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
