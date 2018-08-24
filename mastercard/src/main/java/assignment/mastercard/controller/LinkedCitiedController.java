package assignment.mastercard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import assignment.mastercard.services.ConnectedCitiesService;

@RestController
public class LinkedCitiedController {

	@Autowired
	private ConnectedCitiesService citiesPathFinderService;

	@RequestMapping(value = "/connected")
	public String areCitiesConnected(@RequestParam("origin") String origin, @RequestParam("destination") String destination) {
		boolean pathPresent = citiesPathFinderService.isPathPresent(origin, destination);
		if(pathPresent) return "yes";
		return "no";
	}
}
