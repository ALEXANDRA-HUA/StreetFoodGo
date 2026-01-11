// Απλό alert όταν πατάμε "Δείτε Μενού"
document.addEventListener("DOMContentLoaded", function() {
    const buttons = document.querySelectorAll("a");

    buttons.forEach(button => {
        button.addEventListener("click", function(event) {
            if (this.textContent.includes("Δείτε Μενού")) {
                alert("Θα μεταβείτε στο μενού του καταστήματος!");
            }
        });
    });
});
