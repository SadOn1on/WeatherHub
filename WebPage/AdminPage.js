document.getElementById("postForm").addEventListener("submit", function (event) {
    event.preventDefault(); // Prevent the default form submission behavior

    // Get the form data
    let formData = new FormData(event.target);
    formData.append("units", currUnits);
    formData.append("username", sessionStorage.getItem("username"));

    // Send the request using fetch
    fetch("http://localhost:8080/WeatherRestApi-1.0-SNAPSHOT/api/dbWeather?units=lj", {
        method: 'POST',
        body: formData,
        headers: {
            Authentication: "Bearer " + sessionStorage.getItem("token")
        }
    }).then(response => {
        if (response.ok) {
            return response.text().then(response => {
                console.log(response);
            });
        } else {
            throw new Error("login error");
        }
    }).catch(error => {
        console.error('Error:', error);
    });
});

document.getElementById("getForm").addEventListener("submit", function(event) {
    // Prevent the form from submitting normally
    event.preventDefault();
});

async function getDbInfo(event) {
    let formData = new FormData(document.getElementById("getForm"));
    let result  = await fetch("http://localhost:8080/WeatherRestApi-1.0-SNAPSHOT/api/dbWeather?" +
        new URLSearchParams({
        username: formData.get("username"),
        date: formData.get("date"),
        city: formData.get("city"),
        units: currUnits
    }),{
        headers: {
            Authentication: "Bearer " + sessionStorage.getItem("token")
        }
    })
        .then((result) => result.json())
        .then((result) => {addInfoToTable(result); console.log(result);});
}

currUnits = "Metric";

window.onload = showForm();

function showTable() {
    document.getElementById("postFromDiv").style.display = "none";
    document.getElementById("getFormDiv").style.display = "block"
}
function showForm() {
    document.getElementById("postFromDiv").style.display = "block";
    document.getElementById("getFormDiv").style.display = "none";
}

function addInfoToTable(weatherInfo) {
    let table = document.getElementById("infoTable");
    while (table.hasChildNodes()) {
        if (table.childElementCount == 1) {
            break;
        }
        table.removeChild(table.lastChild);
    }
    for (let i = 0, n = weatherInfo.length; i < n; ++i) {
        let parentNode = document.createElement("tr");
        let tdName = document.createElement("td");
        let tdCity = document.createElement("td");
        let tdDate = document.createElement("td");
        let tdTemp = document.createElement("td");
        let tdHumidity = document.createElement("td");
        let tdClouds= document.createElement("td");
        let tdWindSpeed = document.createElement("td");
        let tdWindDirection = document.createElement("td");

        tdName.textContent = weatherInfo[i].username;
        tdCity.textContent = weatherInfo[i].city;
        tdDate.textContent = weatherInfo[i].date;
        tdTemp.textContent = weatherInfo[i].temperature;
        tdHumidity.textContent = weatherInfo[i].humidity;
        tdClouds.textContent = weatherInfo[i].clouds;
        tdWindSpeed.textContent = weatherInfo[i].windSpeed;
        tdWindDirection.textContent = weatherInfo[i].windDIr;

        parentNode.appendChild(tdName);
        parentNode.appendChild(tdCity);
        parentNode.appendChild(tdDate);
        parentNode.appendChild(tdTemp);
        parentNode.appendChild(tdHumidity);
        parentNode.appendChild(tdClouds);
        parentNode.appendChild(tdWindSpeed);
        parentNode.appendChild(tdWindDirection);

        table.appendChild(parentNode);
    }
}

function changeUnits() {
    if (currUnits === "Metric") {
        currUnits = "Imperial";
        document.getElementById("postTemp").textContent = "Temperature " + '\xB0' + "F";
        document.getElementById("postWindSpeed").textContent = "Wind speed mph";
        document.getElementById("getTemp").textContent = "Temperature " + '\xB0' + "F";
        document.getElementById("getWindSpeed").textContent = "Wind speed mph";
    } else {
        currUnits = "Metric";
        document.getElementById("postTemp").textContent = "Temperature " + '\xB0' + "C";
        document.getElementById("postWindSpeed").textContent = "Wind speed m/s";
        document.getElementById("getTemp").textContent = "Temperature " + '\xB0' + "C";
        document.getElementById("getWindSpeed").textContent = "Wind speed m/s";
    }
}