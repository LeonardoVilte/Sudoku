let posicionX = 0;
let posicionY = 0;
document.addEventListener('click', function(event) {
    if (event.target.classList.contains('celda')) {
        const celda = event.target;
        const id = celda.id;
        const [_, fila, columna] = id.split('-'); // Desestructuración de la cadena de ID para obtener fila y columna
        const filaNum = parseInt(fila);
        const columnaNum = parseInt(columna);

        console.log(`El usuario hizo clic en la celda en la fila ${filaNum + 1} y columna ${columnaNum + 1}`);
        posicionX = filaNum;
        posicionY = columnaNum;
        terminado();
    }
});

function terminado(){
    let matrizSudokuResuelta = stringAMatriz(document.getElementById("tablero-sudoku-rta").dataset.sudokuResuelto);
    let sudokuMatriz = stringAMatriz(document.getElementById("tablero-sudoku").dataset.sudoku);
    let completado = true;
    for (let i = 0; i < sudokuMatriz.length; i++) {
        for (let j = 0; j < sudokuMatriz[i].length; j++) {
            if(sudokuMatriz[i][j] !== matrizSudokuResuelta[i][j]){
                completado = false;
                break;
            }
        }
        if(!completado) break;
    }
    if(completado){
        alert('FELICIDADES LOGRASTE COMPLETAR EL SUDOKU!');
    }
}

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
    let matrizSudokuResuelta = stringAMatriz(document.getElementById("tablero-sudoku-rta").dataset.sudokuResuelto);
    let matrizSudoku = stringAMatriz(document.getElementById("tablero-sudoku").dataset.sudoku);

    if(posicionX !== null &&  posicionY!== null){
        if (matrizSudoku[posicionX][posicionY] === 0) {
            matrizSudoku[posicionX][posicionY] = matrizSudokuResuelta[posicionX][posicionY];
            imprimirSudoku(matrizSudoku);
        }else{
            alert("El casillero seleccionado debe estar vacio");
        }
    }else{
        alert("Debe seleccionar un casillero para recibir una pista")
    }
}

function ayuda() {
    let matrizSudokuResuelta = stringAMatriz(document.getElementById("tablero-sudoku-rta").dataset.sudokuResuelto);
    let matrizSudoku = stringAMatriz(document.getElementById("tablero-sudoku").dataset.sudoku);

    if(posicionX !== null &&  posicionY!== null){
        if (matrizSudoku[posicionX][posicionY] !== 0) {
            if(matrizSudoku[posicionX][posicionY] === matrizSudokuResuelta[posicionX][posicionY]){
                alert(`El valor ${matrizSudoku[posicionX][posicionY]} es correcto para esa casilla`);
            }else{
                alert(`El valor ${matrizSudoku[posicionX][posicionY]} es incorrecto para esa casilla`);
            }
            matrizSudoku[posicionX][posicionY] = 0;
            imprimirSudoku(matrizSudoku);
        }else{
            alert("El casillero seleccionado no debe estar vacio para recibir una ayuda");
        }
    }else{
        alert("Debe seleccionar un casillero para recibir una ayuda")
    }
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
