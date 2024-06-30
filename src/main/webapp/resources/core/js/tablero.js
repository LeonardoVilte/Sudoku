let isPaused = false;
let timerInterval;
let segundos = 0;

function togglePause() {
    const sudokuContainer = document.getElementById("contenedor-sudoku");
    const timerElement = document.getElementById("timer");
    const togglePauseButton = document.getElementById("togglePauseButton");

    if (!isPaused) {
        timerInterval = null;
        sudokuContainer.classList.add("paused");
        togglePauseButton.textContent = "Reanudar";
    } else {
        timerInterval = setInterval(actualizarTimer, 1000);
        sudokuContainer.classList.remove("paused");
        togglePauseButton.textContent = "Pausa";
        clearInterval(timerElement);
    }
    isPaused = !isPaused;
}

function actualizarTimer() {
    if (!isPaused) {
        segundos++;
        const horas = Math.floor(segundos / 3600);
        const minutos = Math.floor((segundos % 3600) / 60);
        const segundosRestantes = segundos % 60;
        const tiempoFormateado = `${horas.toString().padStart(2, '0')}:${minutos.toString().padStart(2, '0')}:${segundosRestantes.toString().padStart(2, '0')}`;
        timerElement.textContent = tiempoFormateado;
    }
}

document.addEventListener('DOMContentLoaded', function(){
    const tablaSudoku = document.getElementById("tablero-sudoku");
    let sudokuMatriz = stringAMatriz(document.getElementById("tablero-sudoku").dataset.sudoku);
    let sudokuInicial = sudokuMatriz;
    const medidaCuadricula = 9;

    const timerElement = document.getElementById('timer');
    let segundos = 0;

    const toggleAnotacionesButton = document.getElementById('toggleAnotacionesButton');
    let modoAnotaciones = false;

    toggleAnotacionesButton.addEventListener('click', () => {
        modoAnotaciones = !modoAnotaciones;
        toggleAnotacionesButton.textContent = modoAnotaciones ? 'Desactivar Anotaciones' : 'Activar Anotaciones';
        document.querySelectorAll('.annotations').forEach(annotationDiv => {
            annotationDiv.style.display = modoAnotaciones ? 'grid' : 'none';
        });
    });

    for(let fila = 0; fila < medidaCuadricula; fila++){
        const nuevaFila = document.createElement("tr");
        for(let columna = 0; columna < medidaCuadricula; columna++){
            const celda = document.createElement("td");

            const annotationsDiv = document.createElement('div');
            annotationsDiv.classList.add('annotations');
            annotationsDiv.style.display = 'none';

            for (let k = 1; k <= 9; k++) {
                const annotation = document.createElement('div');
                annotation.classList.add('annotation');
                annotation.textContent = k;
                annotationsDiv.appendChild(annotation);
            }

            const input = document.createElement("input");
            input.type = "number";
            input.className = "celda";
            input.id = `celda-${fila}-${columna}`;
            if(sudokuInicial[fila][columna] !== 0) {
                input.setAttribute("disabled", "true");
            }
            input.addEventListener('input', function(event){
                if (!/^[1-9]$/.test(input.value) && input.value !== null && input.value !== "") {
                    alert("El numero no es valido, ingrese un valor entre 1 y 9");
                    input.value = "";
                } else {
                    if (modoAnotaciones && /^[1-9]$/.test(input.value)) {
                        const annotation = annotationsDiv.querySelector(`.annotation:nth-child(${input.value})`);
                        annotation.style.display = annotation.style.display === 'flex' ? 'none' : 'flex';
                        input.value = '';
                    } else {
                        sudokuMatriz[fila][columna] = parseInt(input.value);
                        document.getElementById("tablero-sudoku").dataset.sudoku = matrizAString(sudokuMatriz);
                        terminado();
                    }
                }
            });

            celda.appendChild(annotationsDiv);
            celda.appendChild(input);
            nuevaFila.appendChild(celda);
        }
        tablaSudoku.appendChild(nuevaFila);
    }

    imprimirSudoku(sudokuMatriz);

    function actualizarTimer() {
        segundos++;
        const horas = Math.floor(segundos / 3600);
        const minutos = Math.floor((segundos % 3600) / 60);
        const segundosRestantes = segundos % 60;
        const tiempoFormateado = `${horas.toString().padStart(2, '0')}:${minutos.toString().padStart(2, '0')}:${segundosRestantes.toString().padStart(2, '0')}`;
        timerElement.textContent = tiempoFormateado;
    }

    setInterval(actualizarTimer, 1000);

    let selectedCell = null;


    let posicionX = null;
    let posicionY = null;


    function selectCell(event) {

        if (selectedCell) {
            selectedCell.classList.remove('selected');
        }

        selectedCell = event.target;
        selectedCell.classList.add('selected');

        let idParts = selectedCell.id.split('-');
        posicionX = parseInt(idParts[1]);
        posicionY = parseInt(idParts[2]);
    }

    document.querySelectorAll('.celda').forEach(cell => {
        cell.addEventListener('click', selectCell);
    });

    function insertNumber(event) {
        if (selectedCell) {
            if (modoAnotaciones) {
                const annotationsDiv = selectedCell.closest('td').querySelector('.annotations');
                const annotation = annotationsDiv.querySelector(`.annotation:nth-child(${event.target.textContent})`);
                annotation.style.display = annotation.style.display === 'flex' ? 'none' : 'flex';
            } else {
                selectedCell.value = event.target.textContent;

                let matrizSudoku = stringAMatriz(document.getElementById("tablero-sudoku").dataset.sudoku);
                matrizSudoku[posicionX][posicionY] = parseInt(event.target.textContent);
                document.getElementById("tablero-sudoku").dataset.sudoku = matrizAString(matrizSudoku);


                selectedCell.classList.remove('selected');
                selectedCell = null;
            }
        }
    }

    function deleteNumber() {
        if (selectedCell) {
            if (modoAnotaciones) {
                const annotationsDiv = selectedCell.closest('td').querySelector('.annotations');
                annotationsDiv.querySelectorAll('.annotation').forEach(annotation => {
                    annotation.style.display = 'none';
                });
            } else {
                selectedCell.value = '';

                let matrizSudoku = stringAMatriz(document.getElementById("tablero-sudoku").dataset.sudoku);
                matrizSudoku[posicionX][posicionY] = 0;
                document.getElementById("tablero-sudoku").dataset.sudoku = matrizAString(matrizSudoku);

                selectedCell.classList.remove('selected');
                selectedCell = null;
            }
        }
    }

    document.querySelectorAll('#teclado-numeric .tecla').forEach(button => {
        button.addEventListener('click', insertNumber);
    });

    document.addEventListener('keydown', function(event) {
        if (event.key === 'Delete') {
            deleteNumber();
        }
    });
});



