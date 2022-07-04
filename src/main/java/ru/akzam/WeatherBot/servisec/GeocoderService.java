package ru.akzam.WeatherBot.servisec;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.akzam.WeatherBot.util.UrlUtil;

import java.io.IOException;

@Service
public class GeocoderService {
    private final UrlUtil urlUtil;

    @Autowired
    public GeocoderService(UrlUtil urlUtil) {
        this.urlUtil = urlUtil;
    }

    public Double[] getCoordinates(String location) throws IOException {
        String output = urlUtil.getUrlContent(urlUtil.getUrlToGeocoding(location.replace(" ","")));
        JSONArray results = new JSONObject(output).getJSONArray("results");
        if(results.isEmpty()) throw new IOException();
        JSONObject coordinates = results.getJSONObject(0).getJSONObject("geometry").getJSONObject("location");
        return new Double[] {coordinates.getDouble("lat"), coordinates.getDouble("lng")};
    }

    public boolean isExist(String location) {
        try {
            urlUtil.getUrlContent(urlUtil.getUrlToNowForecast(location));
            getCoordinates(location);
            return true;
        }catch (IOException e){
            return false;
        }
    }
}
