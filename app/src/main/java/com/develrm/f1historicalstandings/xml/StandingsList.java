package com.develrm.f1historicalstandings.xml;

import com.develrm.f1historicalstandings.xml.DriverStanding;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "StandingsList")
public class StandingsList {
    @Attribute
    private int season;
    @Attribute
    private int round;
    @ElementList(inline = true)
    private List<DriverStanding> driverStandings;

    public List<DriverStanding> getDriverStandings() {
        return driverStandings;
    }

    public void setDriverStandings(List<DriverStanding> driverStandings) {
        this.driverStandings = driverStandings;
    }
}
