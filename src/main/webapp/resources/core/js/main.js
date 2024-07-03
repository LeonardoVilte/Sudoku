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
let tiempoPausaTotal = 0;
let tiempoPausaInicio;

function obtenerTiempoActual() {
    const tiempoActual = Date.now();
    const tiempoTranscurrido = tiempoActual - tiempoInicio - tiempoPausaTotal;
    const segundosTotales = Math.floor(tiempoTranscurrido / 1000);
    const horas = Math.floor(segundosTotales / 3600);
    const minutos = Math.floor((segundosTotales % 3600) / 60);
    const segundos = segundosTotales % 60;

    return `${horas.toString().padStart(2, '0')}:${minutos.toString().padStart(2, '0')}:${segundos.toString().padStart(2, '0')}`;
}

function terminado() {
    let matrizSudokuResuelta = stringAMatriz(document.getElementById("tablero-sudoku-rta").dataset.sudokuResuelto);
    let sudokuMatriz = stringAMatriz(document.getElementById("tablero-sudoku").dataset.sudoku);
    let completado = true;

    for (let i = 0; i < sudokuMatriz.length; i++) {
        for (let j = 0; j < sudokuMatriz[i].length; j++) {
            if (sudokuMatriz[i][j] !== matrizSudokuResuelta[i][j]) {
                completado = false;
                break;
            }
        }
        if (!completado) break;
    }
    if (completado) {
        alert('FELICIDADES LOGRASTE COMPLETAR EL SUDOKU!');
        const tiempoActual = obtenerTiempoActual();
        let resuelto = true;
        window.location.href = "/spring/Resultad0?tiempo=" + tiempoActual + "&resuelto=" + resuelto;
    }
}

function resolverSudoku() {
    let confirmAction = confirm("¿Esta seguro de que desea rendirse?");
    if (confirmAction) {
        let sudokuDataRta = document.getElementById("tablero-sudoku-rta").dataset.sudokuResuelto;
        document.getElementById("tablero-sudoku").dataset.sudoku = sudokuDataRta;
        imprimirSudoku(stringAMatriz(sudokuDataRta));
        let tiempo = obtenerTiempoActual();
        let resuelto = false;

        window.location.href = `/spring/Resultad0?tiempo=${tiempo}&resuelto=${resuelto}`;
    }
}

function pista() {
    let matrizSudokuResuelta = stringAMatriz(document.getElementById("tablero-sudoku-rta").dataset.sudokuResuelto);
    let matrizSudoku = stringAMatriz(document.getElementById("tablero-sudoku").dataset.sudoku);

    if (posicionX !== null && posicionY !== null) {
        if (matrizSudoku[posicionX][posicionY] === 0) {
            matrizSudoku[posicionX][posicionY] = matrizSudokuResuelta[posicionX][posicionY];
            document.getElementById("tablero-sudoku").dataset.sudoku = matrizAString(matrizSudoku);
            imprimirSudoku(matrizSudoku);
        } else {
            alert("El casillero seleccionado debe estar vacio");
        }
    } else {
        alert("Debe seleccionar un casillero para recibir una pista");
    }
}

function ayuda() {
    let matrizSudokuResuelta = stringAMatriz(document.getElementById("tablero-sudoku-rta").dataset.sudokuResuelto);
    let matrizSudoku = stringAMatriz(document.getElementById("tablero-sudoku").dataset.sudoku);

    if (posicionX !== null && posicionY !== null) {
        if (matrizSudoku[posicionX][posicionY] !== 0) {
            if (matrizSudoku[posicionX][posicionY] === matrizSudokuResuelta[posicionX][posicionY]) {
                alert(`El valor ${matrizSudoku[posicionX][posicionY]} es correcto para esa casilla`);
            } else {
                alert(`El valor ${matrizSudoku[posicionX][posicionY]} es incorrecto para esa casilla`);
                matrizSudoku[posicionX][posicionY] = 0;
                document.getElementById("tablero-sudoku").dataset.sudoku = matrizAString(matrizSudoku);
            }
            imprimirSudoku(matrizSudoku);
        } else {
            alert("El casillero seleccionado no debe estar vacío para recibir una ayuda");
        }
    } else {
        alert("Debe seleccionar un casillero para recibir una ayuda");
    }
}

function imprimirSudoku(sudokuMatriz) {
    for (let i = 0; i < sudokuMatriz.length; i++) {
        for (let j = 0; j < sudokuMatriz[i].length; j++) {
            const celdaId = `celda-${i}-${j}`;
            const celda = document.getElementById(celdaId);
            celda.value = sudokuMatriz[i][j] || ""; // Mostrar el número si existe, de lo contrario, dejar el campo vacío
        }
    }
}

function stringAMatriz(sudoku) {
    let sudokuMatriz;
    if (sudoku.includes(';')) {
        sudokuMatriz = sudoku.split(';').map(row => row.split(',').map(Number));
    } else {
        sudokuMatriz = sudoku.split(',').map(row => row.split(';').map(Number));
    }
    return sudokuMatriz;
}

function matrizAString(matriz) {
    let sudokuString = '';
    for (let i = 0; i < matriz.length; i++) {
        sudokuString += matriz[i].join(',') + ';';
    }
    sudokuString = sudokuString.slice(0, -1);
    return sudokuString;
}

let isPaused = false;
let timerInterval;
let segundos = 0;

function togglePause() {
    const sudokuContainer = document.getElementById("contenedor-sudoku");
    const togglePauseButton = document.getElementById("togglePauseButton");
    const cells = document.querySelectorAll('.celda');

    isPaused = !isPaused;

    if (isPaused) {
        clearInterval(timerInterval);
        sudokuContainer.classList.add("paused");
        togglePauseButton.textContent = "Reanudar";
        cells.forEach(cell => cell.setAttribute('disabled', true));

        tiempoPausaInicio = Date.now(); // Guardar el tiempo de inicio de la pausa
    } else {
        timerInterval = setInterval(actualizarTimer, 1000);
        sudokuContainer.classList.remove("paused");
        togglePauseButton.textContent = "Pausa";
        cells.forEach(cell => {
            if (!cell.classList.contains('pre-filled')) {
                cell.removeAttribute('disabled');
            }
        });

        tiempoPausaTotal += Date.now() - tiempoPausaInicio; // Acumular el tiempo de pausa
    }

    $.ajax({
        type: "POST",
        url: "/spring/jugar?dificultad=1", // Asegúrate de ajustar aquí la URL correcta
        data: {
            esPausa: isPaused
        },
        success: function(response) {
            console.log("Operación completada en el backend:", response);
        },
        error: function(error) {
            console.error('Error al comunicarse con el backend:', error);
        }
    });
}

function actualizarTimer() {
    const timerElement = document.getElementById("timer");

    if (timerElement && !isPaused) {
        const tiempoActual = Date.now();
        const tiempoTranscurrido = tiempoActual - tiempoInicio - tiempoPausaTotal;
        const segundosTotales = Math.floor(tiempoTranscurrido / 1000);
        const horas = Math.floor(segundosTotales / 3600);
        const minutos = Math.floor((segundosTotales % 3600) / 60);
        const segundos = segundosTotales % 60;

        timerElement.textContent =
            `${horas.toString().padStart(2, '0')}:${minutos.toString().padStart(2, '0')}:${segundos.toString().padStart(2, '0')}`;
    }
}

document.addEventListener('DOMContentLoaded', function() {
    if (document.getElementById("timer")) { // Verificar si estamos en la vista del juego
        timerInterval = setInterval(actualizarTimer, 1000);
    }
});

