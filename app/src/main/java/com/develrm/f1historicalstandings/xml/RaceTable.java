package com.develrm.f1historicalstandings.xml;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;


@Root(name = "RaceTable")
public class RaceTable {

    @Attribute
    private int season;

    @ElementList(inline = true)
    private List<Race> races;

    public List<Race> getRaces() {
        return races;
    }
}