
function iniciarJuego() {
    let sudokuData = document.getElementById("tablero-sudoku").dataset.sudoku;
    let sudokuMatriz = stringAMatriz(sudokuData);
    imprimirSudoku(sudokuMatriz);
}

function resolverSudoku() {
    let sudokuDataRta = document.getElementById("tablero-sudoku-rta").dataset.sudokuResuelto;
    let sudokuMatriz = stringAMatriz(sudokuDataRta);
    imprimirSudoku(sudokuMatriz);
}

function pista() {
    let stringSudoku = document.getElementById("tablero-sudoku").dataset.sudoku;
    let matrizSudoku = stringAMatriz(stringSudoku);
    let vectorRta = matrizInverza(document.getElementById("tablero-sudoku-rta").dataset.sudokuResuelto, stringSudoku);

    for (let i = 0; i < matrizSudoku.length; i++) {
        for (let j = 0; j < matrizSudoku[i].length; j++) {
            if (matrizSudoku[i][j] === 0) {
                matrizSudoku[i][j] = vectorRta.shift();
                break;
            }
        }
    }
    imprimirSudoku(matrizSudoku);

}

function ayuda() {
    let sudokuDataRta = document.getElementById("tablero-sudoku-rta").dataset.sudokuResuelto;


}

function imprimirSudoku(sudokuMatriz){
    // Llenar las celdas del Sudoku con los números del Sudoku
    for (let i = 0; i < sudokuMatriz.length; i++) {
        for (let j = 0; j < sudokuMatriz[i].length; j++) {
            const celdaId = `celda-${i}-${j}`;
            const celda = document.getElementById(celdaId);
            celda.value = sudokuMatriz[i][j] || ""; // Mostrar el número si existe, de lo contrario, dejar el campo vacío
        }
    }
}

function stringAMatriz(sudoku){
    let sudokuMatriz;
    if (sudoku.includes(';')) {
        // Dividir la cadena en filas y luego en números
        sudokuMatriz = sudoku.split(';').map(row => row.split(',').map(Number));
    } else {
        // La matriz ya está en formato de matriz, solo necesita dividir las filas
        sudokuMatriz = sudoku.split(',').map(row => row.split(';').map(Number));
    }
    return sudokuMatriz;
}

function matrizInverza(stringCompleta, stringIncompleta){
    let matrizCompleta = stringAMatriz(stringCompleta);
    let matrizIncompleta = stringAMatriz(stringIncompleta)
    let faltan = [];

    for (let i = 0; i < matrizCompleta.length; i++) {
        for (let j = 0; j < matrizCompleta[i].length; j++) {
            if (matrizIncompleta[i][j] !== matrizCompleta[i][j]) {
                faltan = faltan.concat(matrizCompleta[i][j]);
            }
        }
    }
    return faltan;
}