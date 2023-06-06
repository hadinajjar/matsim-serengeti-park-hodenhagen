package org.matsim.analysis;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.events.PersonArrivalEvent;
import org.matsim.api.core.v01.events.PersonDepartureEvent;
import org.matsim.api.core.v01.events.handler.PersonArrivalEventHandler;
import org.matsim.api.core.v01.events.handler.PersonDepartureEventHandler;
import org.matsim.api.core.v01.population.Person;

import java.util.HashMap;
import java.util.Map;

public class SimplePersonEventHandler implements PersonDepartureEventHandler, PersonArrivalEventHandler {

    private final static Map<Id<Person>, Double> personToDepartureTime = new HashMap<>();


    @Override
    public void handleEvent(PersonArrivalEvent personArrivalEvent) {

        var personDepartureTime = personToDepartureTime.get(personArrivalEvent.getPersonId());
        var personArrivalTime = personArrivalEvent.getTime();
        var travelTime = personArrivalTime - personDepartureTime;
        System.out.println("Person: " + personArrivalEvent.getPersonId() + "has travelled: " + travelTime + " s");

        //System.out.println("Arrival: " + personArrivalEvent.getTime() + ": of person " + personArrivalEvent.getPersonId());
    }

    @Override
    public void handleEvent(PersonDepartureEvent personDepartureEvent) {

        var personId = personDepartureEvent.getPersonId();
        var departureTime = personDepartureEvent.getTime();
        personToDepartureTime.put(personId, departureTime);
        //System.out.println("Departure: " + personDepartureEvent.getTime() + ": of person " + personDepartureEvent.getPersonId());

    }
}
