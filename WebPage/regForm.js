const registrationButton = document.getElementById("loginButton");
const registrationFormContainer = document.getElementById("registration-form-container");

registrationFormContainer.style.display = 'none';


registrationButton.addEventListener("click", () => {
    registrationFormContainer.style.display = "block";
});

const registrationFormOverlay = document.getElementById("registration-form-overlay");

registrationFormOverlay.addEventListener("click", (event) => {
    registrationFormContainer.style.display = "none";
});
