package com.uygulama.model;

import javax.persistence.Embeddable;

@Embeddable
public class UserAddress {

    private String district;

    private String street;

    private Integer buildingNumber;

    private Integer postCode;

    public UserAddress() {
    }

    public UserAddress(String district, String street, Integer buildingNumber, Integer postCode) {
        this.district = district;
        this.street = street;
        this.buildingNumber = buildingNumber;
        this.postCode = postCode;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getBuildingNumber() {
        return buildingNumber;
    }

    public void setBuildingNumber(Integer buildingNumber) {
        this.buildingNumber = buildingNumber;
    }

    public Integer getPostCode() {
        return postCode;
    }

    public void setPostCode(Integer postCode) {
        this.postCode = postCode;
    }
}
