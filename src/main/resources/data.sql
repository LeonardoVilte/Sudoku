INSERT INTO Usuario(id, email, password, rol, activo, monedas) VALUES (null, 'test@unlam.edu.ar', 'test', 'ADMIN', true, 0);
INSERT INTO Usuario(id, email, password, rol, activo, monedas) VALUES (null, '123@123', '123', 'ADMIN', true, 0);

INSERT INTO Usuario (id, email, password, rol, activo, monedas) VALUES
                                                            (null, 'usuario1@example.com', 'password123', 'USER', true, 0),
                                                            (null, 'usuario2@example.com', 'password123', 'USER', true, 0),
                                                            (null, 'usuario3@example.com', 'password123', 'USER', true, 0),
                                                            (null, 'usuario4@example.com', 'password123', 'USER', true, 0),
                                                            (null, 'usuario5@example.com', 'password123', 'USER', true, 0);


UPDATE Usuario SET nombre = 'UsuarioTest', monedas = 0 WHERE id = 1;


UPDATE Usuario
SET nombre = 'usuarioPrueba1', horasJugadas = 100, cantidadPartidasJugadas = 50, tiempoPromedioResolucion = 12.5
where id = 3;

UPDATE Usuario
SET nombre = 'usuarioPrueba2', horasJugadas = 151, cantidadPartidasJugadas = 69, tiempoPromedioResolucion = 5.0
where id = 4;

UPDATE Usuario
SET nombre = 'usuarioPrueba3', horasJugadas = 10, cantidadPartidasJugadas = 23, tiempoPromedioResolucion = 15.2
where id = 5;

UPDATE Usuario
SET nombre = 'usuarioPrueba4', horasJugadas = 32, cantidadPartidasJugadas = 73, tiempoPromedioResolucion = 7.3
where id = 6;

UPDATE Usuario
SET nombre = 'usuarioPrueba5', horasJugadas = 15, cantidadPartidasJugadas = 41, tiempoPromedioResolucion = 16.5
where id = 7;

UPDATE Usuario SET horasJugadas = 0, cantidadPartidasJugadas = 0, tiempoPromedioResolucion = 0
WHERE id = 1;

INSERT INTO Partida(id,usuario_id,tiempo, resuelto,Dificultad) VALUES
                                                                (null, 3, '00:10:42',true,1),
                                                                (null, 4, '00:15:15',true,1),
                                                                (null, 5, '00:05:52',true,1),
                                                                (null, 3, '00:20:32',true,2),
                                                                (null, 4, '00:21:49',true,2),
                                                                (null, 5, '00:22:03',true,2),
                                                                (null, 3, '00:30:15',true,3),
                                                                (null, 4, '00:32:58',true,3),
                                                                (null, 5, '00:41:13',true,3);

UPDATE Usuario
SET ayudasDisponibles = 1, pistasDisponibles = 10 WHERE id = 1;

