function pista() {
    // Código para mostrar una pista en el sudoku y sacarle monedas al user
}
function ayuda() {
    // Código para mostrar ayuda en el sudoku y sacarle monedas al user
}
function iniciarJuego() {
    // Realiza una solicitud GET al backend para obtener el tablero inicial del Sudoku
    fetch('/sudoku-inicial')
        .then(response => {
            // Verifica si la respuesta fue exitosa
            if (!response.ok) {
                throw new Error('No se pudo obtener el tablero inicial del Sudoku');
            }
            // Si la respuesta es exitosa, devuelve el cuerpo de la respuesta como texto
            return response.text();
        })
        .then(tableroInicial => {
            // Convierte la cadena de texto en un array de filas
            let filas = tableroInicial.split('\n');
            // Recorre cada fila y crea las celdas en el tablero HTML
            filas.forEach((fila, indiceFila) => {
                let celdas = fila.split(',');
                celdas.forEach((valor, indiceColumna) => {
                    // Actualiza el contenido de la celda correspondiente en el tablero HTML
                    let celdaId = 'cell' + indiceFila + indiceColumna;
                    document.getElementById(celdaId).innerText = valor.trim();
                });
            });
        })
        .catch(error => {
            // Maneja cualquier error que ocurra durante la solicitud
            console.error('Error al iniciar el juego:', error);
        });
}
function resolverSudoku() {
    fetch('/resolver-sudoku', {
        method: 'POST'
    })
        .then(response => response.json())
        .then(tableroResuelto => {
            // Actualiza el tablero en la página HTML con el tablero resuelto
            mostrarTablero(tableroResuelto);
        })
        .catch(error => {
            console.error('Error al resolver el Sudoku:', error);
        });
}
