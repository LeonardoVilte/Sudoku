INSERT INTO Usuario(id, email, password, rol, activo) VALUES(null, 'test@unlam.edu.ar', 'test', 'ADMIN', true);

INSERT INTO Usuario (id, email, password, rol, activo) VALUES
                                                           (null, 'usuario1@example.com', 'password123', 'ADMIN', true),
                                                           (null, 'usuario2@example.com', 'password123', 'ADMIN', true),
                                                           (null, 'usuario3@example.com', 'password123', 'ADMIN', true),
                                                           (null, 'usuario4@example.com', 'password123', 'ADMIN', true),
                                                           (null, 'usuario5@example.com', 'password123', 'ADMIN', true);

UPDATE Usuario
SET nombre = 'usuarioPrueba1', horasJugadas = 100, cantidadPartidasJugadas = 50, tiempoPromedioResolucion = 12.5
where id = 2;

UPDATE Usuario
SET nombre = 'usuarioPrueba2', horasJugadas = 151, cantidadPartidasJugadas = 69, tiempoPromedioResolucion = 5.0
where id = 3;

UPDATE Usuario
SET nombre = 'usuarioPrueba3', horasJugadas = 10, cantidadPartidasJugadas = 23, tiempoPromedioResolucion = 15.2
where id = 4;

UPDATE Usuario
SET nombre = 'usuarioPrueba4', horasJugadas = 32, cantidadPartidasJugadas = 73, tiempoPromedioResolucion = 7.3
where id = 5;

UPDATE Usuario
SET nombre = 'usuarioPrueba5', horasJugadas = 15, cantidadPartidasJugadas = 41, tiempoPromedioResolucion = 16.5
where id = 6;

UPDATE Usuario SET horasJugadas = 0, cantidadPartidasJugadas = 0, tiempoPromedioResolucion = 0
WHERE id = 1;

