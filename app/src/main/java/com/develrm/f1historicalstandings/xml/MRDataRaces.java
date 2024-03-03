package com.develrm.f1historicalstandings.xml;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "MRData", strict = false)
public class MRDataRaces {
    @Element(name = "RaceTable")
    private RaceTable raceTable;

    public RaceTable getRaceTable() {
        return raceTable;
    }
}
