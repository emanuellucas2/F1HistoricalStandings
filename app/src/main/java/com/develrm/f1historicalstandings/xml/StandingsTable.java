package com.develrm.f1historicalstandings.xml;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "StandingsTable")
public class StandingsTable {
    @Attribute
    private int season;
    @Attribute
    private int round;
    @Element(name = "StandingsList")
    private StandingsList standingsList;

    public StandingsList getStandingsList() {
        return standingsList;
    }

    public void setStandingsList(StandingsList standingsList) {
        this.standingsList = standingsList;
    }
}
