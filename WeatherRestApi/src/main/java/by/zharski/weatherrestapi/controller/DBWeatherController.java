package by.zharski.weatherrestapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import by.zharski.weatherrestapi.utill.ConverterClass;
import by.zharski.weatherrestapi.security.Secured;
import by.zharski.weatherrestapi.repository.WeatherRepository;
import by.zharski.weatherrestapi.entity.WeatherRecord;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

@Path("/dbWeather")
public class DBWeatherController {
    @Secured
    @POST
    public Response postWeatherToDB(@FormParam("city") String city,
                                    @FormParam("date") String date,
                                    @FormParam("temperature") Double temperature,
                                    @FormParam("humidity") Double humidity,
                                    @FormParam("clouds") String clouds,
                                    @FormParam("windSpeed") Double windSpeed,
                                    @FormParam("windDir") Integer windDir,
                                    @FormParam("username") String username,
                                    @FormParam("units") String units) {
        try {
            WeatherRepository weatherRepository = new WeatherRepository();
            if (units.equals("imperial")) {
                temperature = ConverterClass.fahrenheitToCelsius(temperature);
                windSpeed = ConverterClass.mphToMs(windSpeed);
            }

            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            java.sql.Date sqlDate = new java.sql.Date(format.parse(date).getTime());

            WeatherRecord weatherRecord = new WeatherRecord(city, sqlDate, temperature, humidity, windDir,
                    clouds, windSpeed);
            weatherRepository.postData(weatherRecord, username);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Response.ok(e.getMessage()).build();
        }

        return Response.ok().build();
    }

    @Secured
    @GET
    @Produces("text/plain")
    public String getWeatherFromDB(@QueryParam("username") String username,
                                   @QueryParam("date") String date,
                                   @QueryParam("city") String cityName,
                                   @QueryParam("units") String units) {


        try {
            WeatherRepository weatherRepository = new WeatherRepository();

            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            java.sql.Date sqlDate = new java.sql.Date(format.parse(date).getTime());

            List<WeatherRecord> records = weatherRepository.getData(username, cityName, sqlDate);
            ObjectMapper objectMapper = new ObjectMapper();
            ArrayNode result = objectMapper.createArrayNode();
            for (WeatherRecord i : records) {
                if (units.equals("imperial")) {
                    i.setTemperature(ConverterClass.celsiusToFahrenheit(i.getTemperature()));
                    i.setWindSpeed(ConverterClass.msToMph(i.getWindSpeed()));
                }
                result.add(i.toJSONNode());
            }
            return result.toString();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "error";
        }
    }
}
