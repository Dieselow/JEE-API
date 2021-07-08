package fr.esgi.jee.api.geolocaliztion.models;

import lombok.Data;

@Data
public class Address {
    public double latitude;
    public double longitude;
    public String type;
    public String name;
    public String number;
    public String postal_code;
    public String street;
    public double confidence;
    public String region;
    public String region_code;
    public String county;
    public String locality;
    public String administrative_area;
    public String neighbourhood;
    public String country;
    public String country_code;
    public String continent;
    public String label;
}
