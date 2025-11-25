package com.manav.JotDown.service;

import com.manav.JotDown.api.response.WeatherResponse;
import com.manav.JotDown.cache.AppCache;
import com.manav.JotDown.constants.PlaceHolders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {
    @Value("${weather.api.key}")
    private String apiKey;
    private static final String baseUrl = " http://api.weatherapi.com/v1";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AppCache appCache;

    public WeatherResponse getWeather(String city) {
        String finalUrl = appCache.APP_CACHE.get(AppCache.keys.WEATHER_API.toString()).replace(PlaceHolders.API_KEY, apiKey).replace(PlaceHolders.CITY, city);
        RequestEntity requestEntity = RequestEntity.get(finalUrl).accept(MediaType.APPLICATION_JSON).build();
        ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalUrl, HttpMethod.GET, null, WeatherResponse.class);
//        String requestBody = "{\n" +
//                "\"userName\": \"Spider_Man\", \n" +
//                "\"password\": \"Peter_Parker\" \n" +
//                "}";
//        HttpEntity<String> httpEntity = new HttpEntity<>(requestBody);
//        ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalUrl, HttpMethod.POST, httpEntity, WeatherResponse.class);
        return response.getBody();
    }
}
