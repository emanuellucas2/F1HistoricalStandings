package com.develrm.f1historicalstandings.xml;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name = "DriverStanding", strict = false)
@Namespace(reference = "http://ergast.com/mrd/1.5")
public class DriverStanding {

    @Attribute
    private String points;
    @Element(name = "Driver")
    private Driver driver;

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public String getPoints() {
        return points;
    }
}
