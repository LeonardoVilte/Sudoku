document.addEventListener('DOMContentLoaded', function(){
    const tablaSudoku=  document.getElementById("tablero-sudoku");
    let sudokuMatriz = stringAMatriz(document.getElementById("tablero-sudoku").dataset.sudoku);
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
                validarEntradaYActualizar(event, fila, columna);
            })

            celda.appendChild(input);
            nuevaFila.appendChild(celda);
        }
        tablaSudoku.appendChild(nuevaFila);
    }
})

function validarEntradaYActualizar(event, fila, colum) {
    let sudokuMatriz = stringAMatriz(document.getElementById("tablero-sudoku").dataset.sudoku);
    let sudokuActualizado = sudokuMatriz;
    const celdaId = `celda-${fila}-${colum}`;
    const celda = document.getElementById(celdaId);
    const valor = celda.value;

     if(sudokuMatriz[fila][colum] === 0){
         if (!/^[1-9]$/.test(valor) ) {
             alert("El numero no es valido, ingrese un valor entre 1 y 9")
             celda.value = "";
         }else{
             return sudokuActualizado[fila][colum]=celda.value;
         }

    }else{
         alert("Esta celda ya se encuentra ocupada");
         celda.value = sudokuMatriz[fila][colum];
     }
}

