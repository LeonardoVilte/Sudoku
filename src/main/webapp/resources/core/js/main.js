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

let tiempoInicio = Date.now(); // Guardar el tiempo de inicio cuando se carga la página

function obtenerTiempoActual() {
    const tiempoActual = Date.now();
    const tiempoTranscurrido = tiempoActual - tiempoInicio; // Tiempo transcurrido en milisegundos

    const segundosTotales = Math.floor(tiempoTranscurrido / 1000);
    const horas = Math.floor(segundosTotales / 3600);
    const minutos = Math.floor((segundosTotales % 3600) / 60);
    const segundos = segundosTotales % 60;

    // Formatear el tiempo como HH:MM:SS
    const horasFormateadas = String(horas).padStart(2, '0');
    const minutosFormateados = String(minutos).padStart(2, '0');
    const segundosFormateados = String(segundos).padStart(2, '0');

    return `${horasFormateadas}:${minutosFormateados}:${segundosFormateados}`;
}

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
        window.location.href = "/spring/Resultad0";
       // Guardar el tiempo al momento de resolver el sudoku
        const tiempoActual = obtenerTiempoActual(); // Función para obtener el tiempo actual, reemplaza esto con tu lógica real

            // Redirigir al usuario a la vista de resultado y pasar el tiempo como parámetro en la URL
        window.location.href = "/spring/Resultad0?tiempo=" + tiempoActual;
    }
}

function resolverSudoku() {
    let sudokuDataRta = document.getElementById("tablero-sudoku-rta").dataset.sudokuResuelto;
    document.getElementById("tablero-sudoku").dataset.sudoku = sudokuDataRta;
    imprimirSudoku(stringAMatriz(sudokuDataRta));
}

function pista() {
    let matrizSudokuResuelta = stringAMatriz(document.getElementById("tablero-sudoku-rta").dataset.sudokuResuelto);
    let matrizSudoku = stringAMatriz(document.getElementById("tablero-sudoku").dataset.sudoku);

    if(posicionX !== null &&  posicionY!== null){
        if (matrizSudoku[posicionX][posicionY] === 0) {
            matrizSudoku[posicionX][posicionY] = matrizSudokuResuelta[posicionX][posicionY];
            document.getElementById("tablero-sudoku").dataset.sudoku = matrizAString(matrizSudoku);
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
                matrizSudoku[posicionX][posicionY] = 0;
                document.getElementById("tablero-sudoku").dataset.sudoku = matrizAString(matrizSudoku);
            }
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

function matrizAString(matriz) {
    let sudokuString = '';
    for (let i = 0; i < matriz.length; i++) {
        sudokuString += matriz[i].join(',') + ';';
    }
    // Eliminar el último ';' si existe
    sudokuString = sudokuString.slice(0, -1);
    return sudokuString;
}
