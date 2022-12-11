package com.kirianov.coding.model;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

public class Item {

    @Expose @SerializedName("dbn") private String dbn;
    @Expose @SerializedName("school_name") private String schoolName;
    @Expose @SerializedName("overview_paragraph") private String overviewParagraph;
    @Expose @SerializedName("location") private String location;
    @Expose @SerializedName("phone_number") private String phoneNumber;
    @Expose @SerializedName("Latitude") private String latitude;
    @Expose @SerializedName("Longitude") private String longitude;

    public String getDbn() {
        return dbn;
    }

    public void setDbn(String dbn) {
        this.dbn = dbn;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getOverviewParagraph() {
        return overviewParagraph;
    }

    public void setOverviewParagraph(String overviewParagraph) {
        this.overviewParagraph = overviewParagraph;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Item)) {
            return false;
        }
        Item item = (Item)object;
        return dbn != null && dbn.equals(item.dbn) &&
                ((schoolName == null && item.schoolName == null) || (schoolName != null && schoolName.equals(item.schoolName))) &&
                ((overviewParagraph == null && item.overviewParagraph == null) || (overviewParagraph != null && overviewParagraph.equals(item.overviewParagraph))) &&
                ((location == null && item.location == null) || (location != null && location.equals(item.location))) &&
                ((phoneNumber == null && item.phoneNumber == null) || (phoneNumber != null && phoneNumber.equals(item.phoneNumber))) &&
                ((latitude == null && item.latitude == null) || (latitude != null && latitude.equals(item.latitude))) &&
                ((longitude == null && item.longitude == null) || (longitude != null && longitude.equals(item.longitude)));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = dbn != null ? prime * result + dbn.hashCode() : result;
        return result;
    }

    @NotNull
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}