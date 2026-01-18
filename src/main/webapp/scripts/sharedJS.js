window.onload = function() {
    const message = document.getElementById('temp-message');
    if (message) {
        setTimeout(function() {
            message.style.display = 'none';
        }, 3000);
    }
};