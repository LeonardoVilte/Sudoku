function pista() {
    // Código para mostrar una pista en el sudoku y sacarle monedas al user
}
function ayuda() {
    // Código para mostrar ayuda en el sudoku y sacarle monedas al user
}
function iniciarJuego() {
    let sudokuData = document.getElementById("tablero-sudoku").dataset.sudoku;
    let sudokuMatriz;

    if (sudokuData.includes(';')) {
        // Dividir la cadena en filas y luego en números
        sudokuMatriz = sudokuData.split(';').map(row => row.split(',').map(Number));
    } else {
        // La matriz ya está en formato de matriz, solo necesita dividir las filas
        sudokuMatriz = sudokuData.split(',').map(row => row.split(';').map(Number));
    }

    // Llenar las celdas del Sudoku con los números del Sudoku
    for (let i = 0; i < sudokuMatriz.length; i++) {
        for (let j = 0; j < sudokuMatriz[i].length; j++) {
            const celdaId = `celda-${i}-${j}`;
            const celda = document.getElementById(celdaId);
            celda.value = sudokuMatriz[i][j] || ""; // Mostrar el número si existe, de lo contrario, dejar el campo vacío
        }
    }
}


function resolverSudoku() {

}
