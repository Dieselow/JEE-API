package fr.esgi.jee.api.geolocaliztion;

import fr.esgi.jee.api.geolocaliztion.models.GeoCoordsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class GeolocaliztionService {

    @Value("${geoloc.accessKey}")
    private String accessKey;

    @Value("${geoloc.apiUrl}")
    private String apiUrl;

    public GeoCoordsResponse addressToGeoloc(String address){
        String encodedAddress = URLEncoder.encode(address, StandardCharsets.UTF_8);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(apiUrl + "forward?access_key=" + accessKey + "&query=" + encodedAddress + "&limit=1", GeoCoordsResponse.class);
    }

}
