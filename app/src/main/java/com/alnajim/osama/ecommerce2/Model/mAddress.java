package com.alnajim.osama.ecommerce2.Model;

public class mAddress
{
    private String City;
    private String Area;
    private String Discrit;
    private String Neighborhood;
    private String buildingNumber;
    private String houseNumber;
    private String fullAddress ;


    public mAddress(String city, String area, String discrit, String neighborhood, String buildingNumber, String houseNumber, String fullAddress) {
        City = city;
        Area = area;
        Discrit = discrit;
        Neighborhood = neighborhood;
        this.buildingNumber = buildingNumber;
        this.houseNumber = houseNumber;
        this.fullAddress = fullAddress;
    }
    public mAddress()
    {}



    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getArea() {
        return Area;
    }

    public void setArea(String area) {
        Area = area;
    }

    public String getDiscrit() {
        return Discrit;
    }

    public void setDiscrit(String discrit) {
        Discrit = discrit;
    }

    public String getNeighborhood() {
        return Neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        Neighborhood = neighborhood;
    }

    public String getBuildingNumber() {
        return buildingNumber;
    }

    public void setBuildingNumber(String buildingNumber) {
        this.buildingNumber = buildingNumber;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }






}
