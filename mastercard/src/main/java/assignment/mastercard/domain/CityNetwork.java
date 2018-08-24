package assignment.mastercard.domain;

import java.util.List;

public class CityNetwork {
	
	private String cityName;
	private StringBuffer linkedCities;
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public StringBuffer getLikedCities() {
		return linkedCities;
	}
	public void setLinkedCities(StringBuffer linkedCities) {
		this.linkedCities = linkedCities;
	}

	
}

