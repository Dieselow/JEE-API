package fr.esgi.jee.api.utils;

import fr.esgi.jee.api.geolocaliztion.GeolocaliztionService;
import fr.esgi.jee.api.geolocaliztion.models.GeoCoordsResponse;
import fr.esgi.jee.api.partner.domain.timeslot.TimeSlotServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("utils")
public class UtilsController {

    private final GeolocaliztionService geolocaliztionService;

    public UtilsController(GeolocaliztionService geolocaliztionService) {
        this.geolocaliztionService = geolocaliztionService;
    }
    @GetMapping("address/{addr}")
    public GeoCoordsResponse test(@PathVariable String addr){
        return geolocaliztionService.addressToGeoloc(addr);
    }

}
