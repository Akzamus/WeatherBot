package ru.akzam.WeatherBot.servisec;

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
        String output = urlUtil.getUrlContent(urlUtil.getUrlToGeocoding(location));
        JSONObject coordinates = new JSONObject(output).getJSONArray("results").
                getJSONObject(0).getJSONObject("geometry").getJSONObject("location");
        return new Double[] {coordinates.getDouble("lat"), coordinates.getDouble("lng")};
    }
}
