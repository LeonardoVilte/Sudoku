document.addEventListener('DOMContentLoaded', function(){
    const tablaSudoku=  document.getElementById("tablero-sudoku");
    const medidaCuadricula = 9;

    for(let fila = 0; fila < medidaCuadricula;fila++){
        const nuevaFila = document.createElement("tr");
        for(let columna= 0; columna < medidaCuadricula; columna++){
            const celda = document.createElement("td")
            const input = document.createElement("input");
            input.type = "number";
            input.className = "celda";
            input.id = `celda-${fila}-${columna}`;

            celda.appendChild(input);
            nuevaFila.appendChild(celda);
        }
        tablaSudoku.appendChild(nuevaFila);

    }
})