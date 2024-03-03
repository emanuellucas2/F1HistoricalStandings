package com.develrm.f1historicalstandings.xml;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name = "Driver", strict = false)
@Namespace(reference = "http://ergast.com/mrd/1.5")
public class Driver {

    @Element(name = "GivenName")
    private String givenName;
    @Element(name = "FamilyName")
    private String familyName;

    public String getGivenName() {
        return givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

}
