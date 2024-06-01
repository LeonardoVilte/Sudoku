document.addEventListener('DOMContentLoaded', function() {
    const tiempoTranscurridoElement = document.getElementById('tiempoTranscurrido');
    const tiempoTranscurrido = parseInt(document.getElementById('timer').textContent);
    const horas = Math.floor(tiempoTranscurrido / 3600);
    const minutos = Math.floor((tiempoTranscurrido % 3600) / 60);
    const segundosRestantes = tiempoTranscurrido % 60;
    const tiempoFormateado = `${horas.toString().padStart(2, '0')}:${minutos.toString().padStart(2, '0')}:${segundosRestantes.toString().padStart(2, '0')}`;
    tiempoTranscurridoElement.textContent = tiempoFormateado;
});