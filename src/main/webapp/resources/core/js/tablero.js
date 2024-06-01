document.addEventListener('DOMContentLoaded', function(){
    const tablaSudoku=  document.getElementById("tablero-sudoku");
    let sudokuMatriz = stringAMatriz(document.getElementById("tablero-sudoku").dataset.sudoku);
    let sudokuActualizado = sudokuMatriz;
    const medidaCuadricula = 9;

    for(let fila = 0; fila < medidaCuadricula;fila++){
        const nuevaFila = document.createElement("tr");
        for(let columna= 0; columna < medidaCuadricula; columna++){
            const celda = document.createElement("td")
            const input = document.createElement("input");
            input.type = "number";
            input.className = "celda";
            input.id = `celda-${fila}-${columna}`;
            if(sudokuMatriz[fila][columna] !== 0) {
                input.setAttribute("disabled", "true");
            }
            input.addEventListener('input', function(event){
                if (!/^[1-9]$/.test(input.value) ) {
                    alert("El numero no es valido, ingrese un valor entre 1 y 9")
                    celda.value = "";
                }
                sudokuActualizado[fila][columna]=input.value;
            })

            celda.appendChild(input);
            nuevaFila.appendChild(celda);
        }
        tablaSudoku.appendChild(nuevaFila);
    }imprimirSudoku(sudokuMatriz);
})


