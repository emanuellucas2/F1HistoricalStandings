package com.develrm.f1historicalstandings.xml;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.util.Date;

@Root(name = "Race", strict = false)
public class Race {

    @Attribute
    private int season;
    @Attribute
    private int round;
    @Attribute
    private String url;
    @Element(name = "RaceName")
    private String raceName;
    @Element(name = "Date")
    private Date date;

    public String getRaceName() {
        return raceName;
    }

    public Date getDate() {
        return date;
    }
}

