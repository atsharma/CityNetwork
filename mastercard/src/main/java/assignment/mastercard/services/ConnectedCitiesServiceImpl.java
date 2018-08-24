package assignment.mastercard.services;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import assignment.mastercard.domain.CityNetwork;

@Component
public class ConnectedCitiesServiceImpl implements ConnectedCitiesService {

	private List<CityNetwork> cityNetwork = null;
	@Override
	public boolean isPathPresent(String first, String second) {
		boolean iies=cityNetwork.stream().anyMatch(obj -> findCitiesIfConnected(first,second,obj));
		return iies;
	}
	private boolean findCitiesIfConnected(String first, String second,
			CityNetwork cityNetwork2) {
			boolean isfirst=false;
			boolean isSecond=false;
		    if(!(isfirst && isSecond))
		    {
		    	if(cityNetwork2.getCityName().equals(first) || cityNetwork2.getLikedCities().indexOf(first)>-1)
		    		isfirst=true;
		    	if(cityNetwork2.getCityName().equals(second) || cityNetwork2.getLikedCities().indexOf(second)>-1)
		    		isSecond=true;
		    }
		return isfirst && isSecond;
	}
	@PostConstruct
	public void init() {

		String fileName = "static/cities.txt";
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(fileName).getFile());
		cityNetwork = new ArrayList<CityNetwork>();

		try (Scanner scanner = new Scanner(file)) {

			while (scanner.hasNextLine()) {
				String[] split = scanner.nextLine().split(",");
				validateExistingCityNetwork(createCityNetworkObject(split));
			}
			scanner.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		cityNetwork=reArrangePath(cityNetwork);
	}

	private List<CityNetwork> reArrangePath(List<CityNetwork> cityNetwork2) {
		
		boolean isTrue=false;
		List<CityNetwork> cityNetwork1=new ArrayList<CityNetwork>();
		cityNetwork1.addAll(cityNetwork2);
		CityNetwork city1=cityNetwork2.remove(0);
		for(int i=0;i<cityNetwork2.size();i++)
		{
			if(checkIfCityPresent(cityNetwork2,city1))
			{
				isTrue=true;
				break;
			}
		}
		if(isTrue)
		{
			cityNetwork1=reArrangePath(cityNetwork2);
		}
		else if(cityNetwork2.size()>1)
		{
			reArrangePath(cityNetwork1);
		}
		return cityNetwork1;
	}

	private CityNetwork createCityNetworkObject(String ...split) {
		CityNetwork cn = new CityNetwork();
		if(split.length==2)
		{
			StringBuffer linkedCities = new StringBuffer();
			linkedCities.append(split[1]);
			cn.setLinkedCities(linkedCities);
		}
		cn.setCityName(split[0]);
		return cn;
	}

	private void validateExistingCityNetwork(CityNetwork city) {

		if (cityNetwork.size() == 0)
		{
			cityNetwork.add(city);
			
		}
		else if(!checkIfCityPresent(cityNetwork,city))
		{
			cityNetwork.add(city);
		}
		
	}
	private boolean checkIfCityPresent(List<CityNetwork> cityNetwork2, CityNetwork city)
	{
		boolean isTrue=false;
		
		for(CityNetwork city1: cityNetwork2)
		{
			if (!isTrue && city1.getCityName().equals(city.getCityName())) {
				
				if(city1.getLikedCities()==null)
				{
					city1.setLinkedCities(new StringBuffer());
					city1.getLikedCities().append(city.getLikedCities());	
				}
				else
				{
					city1.getLikedCities().append("->").append(city.getLikedCities());	
				}	
				return true;
			}
			else if(city1.getLikedCities()!=null && city1.getLikedCities().indexOf(city.getCityName())>-1)
			{
				city1.getLikedCities().append("->").append(city.getLikedCities());
				return true;
			}
		}	
		return isTrue;
	}
}
