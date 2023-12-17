//calling the function in window.onload to make sure the HTML is loaded
let currentDay;

async function update() {
    currentDay = 1;
    let bar = document.getElementById("searchBar");
    let cityName = bar.value.length > 0 ? bar.value : "Minsk";
    let units = "metric";
    if (document.getElementById("unitsToggle")) {
        units = document.getElementById("unitsToggle").checked ? "imperial" : "metric";
        console.log(document.getElementById("unitsToggle").checked);
    }
    let result = await fetch("http://localhost:8080/WeatherRestApi-1.0-SNAPSHOT/api/realWeather/currentWeather?" + new URLSearchParams({
        units: units,
        cityName: cityName
    }))
        .then((result) => result.json())
        .then((result) => loadCurrInfo(result, units));
    let forecastResult = await fetch("http://localhost:8080/WeatherRestApi-1.0-SNAPSHOT/api/realWeather/forecast?" + new URLSearchParams({
        units: units,
        cityName: cityName
    }))
        .then((forecastResult) => forecastResult.json())
        .then((forecastResult) => loadTodayHourlyForecast(forecastResult, units));
}

async function updateOnUnitsChange() {
    let bar = document.getElementById("searchBar");
    let cityName = bar.value.length > 0 ? bar.value : "Minsk";
    let units = "metric";
    if (document.getElementById("unitsToggle")) {
        units = document.getElementById("unitsToggle").checked ? "imperial" : "metric";
        console.log(document.getElementById("unitsToggle").checked);
    }
    let result = await fetch("http://localhost:8080/WeatherRestApi-1.0-SNAPSHOT/api/realWeather/currentWeather?" + new URLSearchParams({
        units: units,
        cityName: cityName
    }))
        .then((result) => result.json())
        .then((result) => loadCurrInfo(result, units));
    let forecastResult = await fetch("http://localhost:8080/WeatherRestApi-1.0-SNAPSHOT/api/realWeather/forecast?" + new URLSearchParams({
        units: units,
        cityName: cityName
    }))
        .then((forecastResult) => forecastResult.json())
        .then((forecastResult) => loadDayForecast(forecastResult, units));
    await getForecast(currentDay);
}

async function cityUpdate(event) {
    if (event.keyCode == 13) {
        await update();
    }
}

async function getForecast(count) {
    currentDay = count;
    let bar = document.getElementById("searchBar");
    let cityName = bar ? bar.placeholder : "Minsk";
    let units = "metric";
    if (document.getElementById("unitsToggle")) {
        units = document.getElementById("unitsToggle").checked ? "imperial" : "metric";
    }
    let forecastResult = await fetch("http://localhost:8080/WeatherRestApi-1.0-SNAPSHOT/api/realWeather/forecast?" + new URLSearchParams({
        units: units,
        cityName: cityName
    }))
        .then((forecastResult) => forecastResult.json())
        .then((forecastResult) => loadHourlyForecast(count, forecastResult, units));
}

function loadCurrInfo(result, units) {
    daysSetUp();
    let windUnits = (units == "metric") ? "m/s" : "mph";
    let tempUnits = (units == "metric") ? "C" : "F";

    //setting img
    let iconId = result.weather[0].icon.toString();
    let iconPartOfTheDay = "iconDay";
    if (iconId.slice(-1) == "n") {
        iconPartOfTheDay = "iconNight";
    }
    document.getElementById("todayImg").src = `./img/${iconPartOfTheDay}/${iconId}.png`;
    document.getElementById("todayTemp").textContent = result.main.temp.toString() + '\xB0' + tempUnits;
    document.getElementById("todayHum").textContent = "Humidity: " + result.main.humidity.toString() + "%";
    document.getElementById("todayClouds").textContent = result.weather[0].description.toString();
    document.getElementById("todayWindSpeed").textContent = "Wind: " + result.wind.speed.toString() + windUnits;
    document.getElementById("todayWindDir").textContent = "  " + getCardinalDirection(result.wind.deg);
}

function loadDayForecast(forecastResult, units) {
    let now = new Date();
    let windUnits = (units == "metric") ? "m/s" : "mph";
    let tempUnits = (units == "metric") ? "C" : "F";
    let i = 0;
    for (i; i < 40; ++i) {
        let checkDate = new Date(forecastResult.list[i].dt_txt.toString());
        let dayDiff = Math.ceil((checkDate - now) / (1000 * 60 * 60 * 24));
        if (dayDiff == 1 && checkDate.getHours() == 15) {
            document.getElementById("tomorrowTemp").textContent = forecastResult.list[i].main.temp.toString() + '\xB0' + tempUnits;
            let iconId = forecastResult.list[i].weather[0].icon.toString();
            document.getElementById("tomorrowImg")
                .setAttribute("src", `./img/iconDay/${iconId}.png`);
            break;
        }
    }
    for (i; i < 40; ++i) {
        let checkDate = new Date(forecastResult.list[i].dt_txt.toString());
        let dayDiff = Math.ceil((checkDate - now) / (1000 * 60 * 60 * 24));
        if (dayDiff == 2 && checkDate.getHours() == 15) {
            document.getElementById("2dayTemp").textContent = forecastResult.list[i].main.temp.toString() + '\xB0' + tempUnits;
            let iconId = forecastResult.list[i].weather[0].icon.toString();
            document.getElementById("2dayImg")
                .setAttribute("src", `./img/iconDay/${iconId}.png`);
            break;
        }
    }
    for (i; i < 40; ++i) {
        let checkDate = new Date(forecastResult.list[i].dt_txt.toString());
        let dayDiff = Math.ceil((checkDate - now) / (1000 * 60 * 60 * 24));
        if (dayDiff == 3 && checkDate.getHours() == 15) {
            document.getElementById("3dayTemp").textContent = forecastResult.list[i].main.temp.toString() + '\xB0' + tempUnits;
            let iconId = forecastResult.list[i].weather[0].icon.toString();
            document.getElementById("3dayImg")
                .setAttribute("src", `./img/iconDay/${iconId}.png`);
            break;
        }
    }
    for (i; i < 40; ++i) {
        let checkDate = new Date(forecastResult.list[i].dt_txt.toString());
        let dayDiff = Math.ceil((checkDate - now) / (1000 * 60 * 60 * 24));
        if (dayDiff == 4 && checkDate.getHours() == 15) {
            document.getElementById("4dayTemp").textContent = forecastResult.list[i].main.temp.toString() + '\xB0' + tempUnits;
            let iconId = forecastResult.list[i].weather[0].icon.toString();
            document.getElementById("4dayImg")
                .setAttribute("src", `./img/iconDay/${iconId}.png`);
            break;
        }
    }
}

function loadTodayHourlyForecast(forecastResult, units) {
    loadDayForecast(forecastResult, units);
    let windUnits = (units == "metric") ? "m/s" : "mph";
    let tempUnits = (units == "metric") ? "C" : "F";

    let today = new Date();
    let numberOfForecasts = 0;
    for (let i = 0; i < 7; ++i) {
        let testDay = new Date(forecastResult.list[i].dt_txt.toString());
        if (testDay.getDay() - today.getDay() == 0) {
            ++numberOfForecasts;
        } else {
            break;
        }
    }
    let hourlyForecastDiv = document.getElementById("hourlyForecastContainer");
    hourlyForecastDiv.setAttribute("width", (numberOfForecasts * 12.5).toString() + "%");
    while (hourlyForecastDiv.hasChildNodes()) {
        hourlyForecastDiv.removeChild(hourlyForecastDiv.lastChild);
    }
    for (let i = 0; i < numberOfForecasts; ++i) {
        createHourlyForecastDiv(i + 1, numberOfForecasts);
    }
    for (let i = 0; i < numberOfForecasts; ++i) {
        document.getElementById(`temp${i + 1}`).textContent = forecastResult.list[i].main.temp.toString() + '\xB0' + tempUnits;
        document.getElementById(`clouds${i + 1}`).textContent = forecastResult.list[i].weather[0].description
            .toString();
        document.getElementById(`hum${i + 1}`).textContent = "Humidity: " + forecastResult.list[i].main.humidity.toString() + "%";
        document.getElementById(`wind${i + 1}`).textContent = "Wind: " + forecastResult.list[i].wind.speed.toString() + windUnits;
        document.getElementById(`windDir${i + 1}`).textContent = getCardinalDirection(forecastResult.list[i].wind.deg);
        let iconId = forecastResult.list[i].weather[0].icon.toString();
        let iconPartOfTheDay = "iconDay";
        if (iconId.slice(-1) == "n") {
            iconPartOfTheDay = "iconNight";
        }
        document.getElementById(`img${i + 1}`)
            .setAttribute("src",
                `./img/${iconPartOfTheDay}/${iconId}.png`);
        let forecastDate = new Date(forecastResult.list[i].dt_txt);
        document.getElementById(`time${i + 1}`).textContent = padTo2Digits(forecastDate.getHours()) +
            ":" + padTo2Digits(forecastDate.getMinutes());
    }
}

function loadHourlyForecast(count, forecastResult, units) {
    let windUnits = (units == "metric") ? "m/s" : "mph";
    let tempUnits = (units == "metric") ? "C" : "F";
    let hourlyForecastDiv = document.getElementById("hourlyForecastContainer");
    hourlyForecastDiv.setAttribute("width", (8 * 12.5).toString() + "%");
    while (hourlyForecastDiv.hasChildNodes()) {
        hourlyForecastDiv.removeChild(hourlyForecastDiv.lastChild);
    }

    for (let i = 1; i <= 8; ++i) {
        createHourlyForecastDiv(i, 8);
    }
    let k = 1;
    for (let i = 0; i < 40; ++i) {
        let now = new Date();
        let checkDate = new Date(forecastResult.list[i].dt_txt.toString());
        now.setDate(now.getDate() + count);
        if (checkDate.toDateString() == now.toDateString()) {
            document.getElementById(`temp${k}`).textContent = forecastResult.list[i].main.temp.toString() + '\xB0' + tempUnits;
            document.getElementById(`clouds${k}`).textContent = forecastResult.list[i].weather[0].description
                .toString();
            document.getElementById(`hum${k}`).textContent = "Humidity: " + forecastResult.list[i].main.humidity.toString() + "%";
            document.getElementById(`wind${k}`).textContent = "Wind: " + forecastResult.list[i].wind.speed.toString() + windUnits;
            document.getElementById(`windDir${k}`).textContent = getCardinalDirection(forecastResult.list[i].wind.deg);
            let iconId = forecastResult.list[i].weather[0].icon.toString();
            let iconPartOfTheDay = "iconDay";
            if (iconId.slice(-1) == "n") {
                iconPartOfTheDay = "iconNight";
            }
            document.getElementById(`img${k}`)
                .setAttribute("src",
                    `./img/${iconPartOfTheDay}/${iconId}.png`);
            let forecastDate = new Date(forecastResult.list[i].dt_txt);
            document.getElementById(`time${k}`).textContent = padTo2Digits(forecastDate.getHours()) +
                ":" + padTo2Digits(forecastDate.getMinutes());
            ++k;
        }
    }
}

function padTo2Digits(num) {
    return String(num).padStart(2, '0');
}

function createHourlyForecastDiv(count, overallCount) {
    let div = document.createElement("div");
    let subDiv1 = document.createElement("div");
    let subDiv2 = document.createElement("div");
    let img = document.createElement("img");
    let span1 = document.createElement("span");
    let span2 = document.createElement("span");
    let span3 = document.createElement("span");
    let span4 = document.createElement("span");
    let span5 = document.createElement("span");
    let span6 = document.createElement("span");
    let span7 = document.createElement("span");

    let occPercent = 100 / overallCount;

    div.setAttribute("width", `${occPercent}%`);
    span1.setAttribute("id", `temp${count}`);
    span2.setAttribute("id", `clouds${count}`);
    span3.setAttribute("id", `hum${count}`);
    span4.setAttribute("id", `wind${count}`);
    span5.setAttribute("id", `windDir${count}`);
    span6.setAttribute("id", `time${count}`)
    span7.setAttribute("id", `windDir${count}`)
    if (count == overallCount) {
        div.classList.add("home-container26-no-border");
    } else {
        div.classList.add("home-container26");
    }
    subDiv1.setAttribute("class", "home-container27");
    subDiv2.setAttribute("class", "home-container28");
    img.setAttribute("class", "home-image01");
    img.setAttribute("id", `img${count}`);
    img.setAttribute("alt", "image");


    subDiv1.appendChild(span1);
    subDiv1.appendChild(img);
    subDiv2.appendChild(span2);
    subDiv2.appendChild(span3);
    subDiv2.appendChild(span4);
    subDiv2.appendChild(span5);
    subDiv2.appendChild(span7)
    div.appendChild(subDiv1);
    div.appendChild(subDiv2);
    div.appendChild(span6)

    document.getElementById("hourlyForecastContainer").appendChild(div);
}

function getCardinalDirection(angle) {
    const directions = ['↑ N', '↗ NE', '→ E', '↘ SE', '↓ S', '↙ SW', '← W', '↖ NW'];
    return directions[Math.round(angle / 45) % 8];
}

function daysSetUp() {
    let day = (new Date()).getDay();
    const WEEKDAYS = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday",
        "Monday", "Tuesday", "Wednesday"];
    document.getElementById("2day").textContent = WEEKDAYS[day + 2];
    document.getElementById("3day").textContent = WEEKDAYS[day + 3];
    document.getElementById("4day").textContent = WEEKDAYS[day + 4];
}

window.onload = update();

document.getElementById("loginForm").addEventListener("submit",
    async (event) => {
        event.preventDefault(); // Prevent default form submission behavior
        let formData = new FormData(event.target);

        try {
            const response = await fetch("http://localhost:8080/WeatherRestApi-1.0-SNAPSHOT/api/auth/login", {
                method: 'POST',
                body: formData
            })
                .then(response => {
                    if (response.ok) {
                        return response.text().then(token => {
                            // Here, 'token' is the token string returned by the server
                            sessionStorage.setItem("token", token);
                            sessionStorage.setItem("username", formData.get("username").toString())
                            window.location.href = "admin-page.html";
                        });
                    } else {
                        throw new Error('Failed to login');
                    }
                });
        } catch (error) {
            // Handle login error
            console.error(error);
            alert(error);
        }
    });

const homeContainers = document.querySelectorAll('.home-container-forecast');

// Add a click event listener to each .home-container-forecast element
homeContainers.forEach(homeContainer => {
    homeContainer.addEventListener('click', () => {
        // Remove the active class from all the .home-container-forecast elements
        homeContainers.forEach(container => container.classList.remove('active'));
        // Add the active class to the clicked .home-container-forecast element
        homeContainer.classList.add('active');
    });
});