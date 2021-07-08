package fr.esgi.jee.api.geolocaliztion.models;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class GeoCoordsResponse implements Serializable {
    private List<Address> data;
}
