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

});


