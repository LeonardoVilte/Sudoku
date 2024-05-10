function pista() {
    // Código para mostrar una pista en el sudoku y sacarle monedas al user
}
function ayuda() {
    // Código para mostrar ayuda en el sudoku y sacarle monedas al user
}
function iniciarJuego() {
    //let sudokuData = document.getElementById("tablero-sudoku").dataset.sudoku;
    //console.log("Valor de sudokuData:", sudokuData);
    let sudokuData = document.getElementById("tablero-sudoku").dataset.sudoku;
    let filas = sudokuData.split(";");

    for (let i = 0; i < 9; i++) {
        let numeros = filas[i].split(",");
        for (let j = 0; j < 9; j++) {
            const celdaId = `celda-${i}-${j}`;
            const celda = document.getElementById(celdaId);
            celda.value = numeros[j] || "";
        }
    }
}


function resolverSudoku() {
}
