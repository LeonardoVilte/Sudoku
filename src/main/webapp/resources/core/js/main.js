function pista() {
    // Código para mostrar una pista en el sudoku y sacarle monedas al user
}
function ayuda() {
    // Código para mostrar ayuda en el sudoku y sacarle monedas al user
}
// Obtener todas las celdas de la tabla
const cells = document.querySelectorAll('#sudoku-table td');

// Iterar sobre cada celda y agregar un listener de clic
cells.forEach(cell => {
    cell.addEventListener('click', () => {
        // Habilitar la edición directa del contenido de la celda
        cell.contentEditable = true;
        cell.focus(); // Poner el foco en la celda para que el usuario pueda escribir inmediatamente

        // Después de que el usuario ingrese un número, validar y deshabilitar la edición directa
        cell.addEventListener('blur', () => {
            let input = cell.textContent.trim();
            if (input !== "" && !isNaN(input) && input >= 1 && input <= 9) {
                cell.textContent = input;
            } else {
                cell.textContent = ""; // Limpiar la celda si el valor no es válido
                alert("Ingrese un número válido del 1 al 9.");
            }
            cell.contentEditable = false; // Deshabilitar la edición directa
        });
    });
});