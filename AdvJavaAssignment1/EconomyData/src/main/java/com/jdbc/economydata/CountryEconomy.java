package com.jdbc.economydata;

public class CountryEconomy {
    private String country;
    private double gdp;
    private double inflationRate;
    private double unemploymentRate;

    public CountryEconomy(String country, double gdp, double inflationRate, double unemploymentRate) {
        this.country = country;
        this.gdp = gdp;
        this.inflationRate = inflationRate;
        this.unemploymentRate = unemploymentRate;
    }

    public String getCountry() {
        return country;
    }

    public double getGdp() {
        return gdp;
    }

    public double getInflationRate() {
        return inflationRate;
    }

    public double getUnemploymentRate() {
        return unemploymentRate;
    }
}
