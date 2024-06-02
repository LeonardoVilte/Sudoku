document.addEventListener('DOMContentLoaded', function(){
    const tablaSudoku=  document.getElementById("tablero-sudoku");
    let sudokuMatriz = stringAMatriz(document.getElementById("tablero-sudoku").dataset.sudoku);
    let sudokuInicial = sudokuMatriz;
    const medidaCuadricula = 9;

    const timerElement = document.getElementById('timer');
    let segundos = 0;

    for(let fila = 0; fila < medidaCuadricula;fila++){
        const nuevaFila = document.createElement("tr");
        for(let columna= 0; columna < medidaCuadricula; columna++){
            const celda = document.createElement("td")
            const input = document.createElement("input");
            input.type = "number";
            input.className = "celda";
            input.id = `celda-${fila}-${columna}`;
            if(sudokuInicial[fila][columna] !== 0) {
                input.setAttribute("disabled", "true");
            }
            input.addEventListener('input', function(event){
                if (!/^[1-9]$/.test(input.value)  && input.value !== null && input.value !== "") {
                    alert("El numero no es valido, ingrese un valor entre 1 y 9")
                    input.value = "";
                }else{
                    sudokuMatriz[fila][columna]=parseInt(input.value);
                    document.getElementById("tablero-sudoku").dataset.sudoku = matrizAString(sudokuMatriz);
                    terminado();
                }
            });

            celda.appendChild(input);
            nuevaFila.appendChild(celda);
        }
        tablaSudoku.appendChild(nuevaFila);
    } imprimirSudoku(sudokuMatriz);

    function actualizarTimer() {
                segundos++;
                const horas = Math.floor(segundos / 3600);
                const minutos = Math.floor((segundos % 3600) / 60);
                const segundosRestantes = segundos % 60;
                const tiempoFormateado = `${horas.toString().padStart(2, '0')}:${minutos.toString().padStart(2, '0')}:${segundosRestantes.toString().padStart(2, '0')}`;
                timerElement.textContent = tiempoFormateado;
            }

    setInterval(actualizarTimer, 1000);

    // Variable para almacenar la celda seleccionada
    let selectedCell = null;

    // Variables para almacenar las posiciones de la celda seleccionada
    let posicionX = null;
    let posicionY = null;

    // Función para manejar la selección de celdas del Sudoku
    function selectCell(event) {
        // Eliminar la clase de celda seleccionada de cualquier otra celda
        if (selectedCell) {
            selectedCell.classList.remove('selected');
        }
        // Marcar la celda actual como seleccionada
        selectedCell = event.target;
        selectedCell.classList.add('selected');

        // Actualizar las posiciones de la celda seleccionada
        let idParts = selectedCell.id.split('-');
        posicionX = parseInt(idParts[1]);
        posicionY = parseInt(idParts[2]);
    }

    // Añadir event listeners a las celdas del Sudoku
    document.querySelectorAll('.celda').forEach(cell => {
        cell.addEventListener('click', selectCell);
    });

    // Función para manejar la inserción de números en la celda seleccionada
    function insertNumber(event) {
        if (selectedCell) {
            // Insertar el número en la celda seleccionada
            selectedCell.value = event.target.textContent;

            // Actualizar la matriz del Sudoku
            let matrizSudoku = stringAMatriz(document.getElementById("tablero-sudoku").dataset.sudoku);
            matrizSudoku[posicionX][posicionY] = parseInt(event.target.textContent);
            document.getElementById("tablero-sudoku").dataset.sudoku = matrizAString(matrizSudoku);

            // Opcional: Desmarcar la celda después de insertar el número
            selectedCell.classList.remove('selected');
            selectedCell = null;
        }
    }

    // Añadir event listeners a los botones del teclado numérico
    document.querySelectorAll('#teclado-numeric .tecla').forEach(button => {
        button.addEventListener('click', insertNumber);
    });

});


