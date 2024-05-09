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

            input.addEventListener('input', function(event){
                validarEntrada(event, fila, columna);
            })


            celda.appendChild(input);
            nuevaFila.appendChild(celda);
        }
        tablaSudoku.appendChild(nuevaFila);
    }
})


function validarEntrada(event, fila, colum) {

    const celdaId = `celda-${fila}-${colum}`;
    const celda = document.getElementById(celdaId);
    const valor = celda.value;

    if (!/^[1-9]$/.test(valor)) {
        alert("El numero no es valido, ingrese un valor entre 1 y 9")
        celda.value = "";
    }
}