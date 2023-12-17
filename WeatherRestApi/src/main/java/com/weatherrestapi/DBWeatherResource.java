package com.weatherrestapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.*;
import java.util.ArrayList;

@Path("/dbWeather")
public class DBWeatherResource {
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
        try (WeatherDataGateway weatherDataGateway = new WeatherDataGateway("jdbc:postgresql:Weather_app")) {
            if (units.equals("imperial")) {
                temperature = ConverterClass.fahrenheitToCelsius(temperature);
                windSpeed = ConverterClass.mphToMs(windSpeed);
            }
            WeatherRecord weatherRecord = new WeatherRecord(city, date, temperature, humidity, username, windDir,
                    clouds, windSpeed);
            weatherDataGateway.postData(weatherRecord, username);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return Response.ok(e.getMessage()).build();
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


        try (WeatherDataGateway weatherDataGateway = new WeatherDataGateway("jdbc:postgresql:Weather_app")) {
            ArrayList<WeatherRecord> records = weatherDataGateway.getData(username, cityName, date);
            ObjectMapper objectMapper = new ObjectMapper();
            ArrayNode result = objectMapper.createArrayNode();
            for (WeatherRecord i : records) {
                result.add(i.toJSONNode());
            }
            return result.toString();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return "error";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "error";
        }
    }
}
