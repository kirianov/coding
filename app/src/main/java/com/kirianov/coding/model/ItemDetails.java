package com.kirianov.coding.model;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

public class ItemDetails {

    @Expose @SerializedName("dbn") private String dbn;
    @Expose @SerializedName("school_name") private String schoolName;
    @Expose @SerializedName("sat_math_avg_score") private String satMathScore;
    @Expose @SerializedName("sat_critical_reading_avg_score") private String satReadingScore;
    @Expose @SerializedName("sat_writing_avg_score") private String satWritingScore;
    @Expose @SerializedName("num_of_sat_test_takers") private String numSatTakers;

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

    public String getSatReadingScore() {
        return satReadingScore;
    }

    public void setSatReadingScore(String satReadingScore) {
        this.satReadingScore = satReadingScore;
    }

    public String getSatMathScore() {
        return satMathScore;
    }

    public void setSatMathScore(String satMathScore) {
        this.satMathScore = satMathScore;
    }

    public String getSatWritingScore() {
        return satWritingScore;
    }

    public void setSatWritingScore(String satWritingScore) {
        this.satWritingScore = satWritingScore;
    }

    public String getNumSatTakers() {
        return numSatTakers;
    }

    public void setNumSatTakers(String numSatTakers) {
        this.numSatTakers = numSatTakers;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ItemDetails)) {
            return false;
        }
        ItemDetails item = (ItemDetails) object;
        return dbn != null && dbn.equals(item.dbn) &&
                ((schoolName == null && item.schoolName == null) || (schoolName != null && schoolName.equals(item.schoolName))) &&
                ((satMathScore == null && item.satMathScore == null) || (satMathScore != null && satMathScore.equals(item.satMathScore))) &&
                ((satReadingScore == null && item.satReadingScore == null) || (satReadingScore != null && satReadingScore.equals(item.satReadingScore))) &&
                ((satWritingScore == null && item.satWritingScore == null) || (satWritingScore != null && satWritingScore.equals(item.satWritingScore))) &&
                ((numSatTakers == null && item.numSatTakers == null) || (numSatTakers != null && numSatTakers.equals(item.numSatTakers)));
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
