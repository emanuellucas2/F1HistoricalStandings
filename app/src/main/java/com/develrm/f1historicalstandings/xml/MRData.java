package com.develrm.f1historicalstandings.xml;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "MRData", strict = false)
public class MRData {
    @Element(name = "StandingsTable")
    private StandingsTable standingsTable;

    public StandingsTable getStandingsTable() {
        return standingsTable;
    }

}
