package openapi;

import ch.unisg.ics.interactions.wot.td.ThingDescription;
import ch.unisg.ics.interactions.wot.td.affordances.Form;
import ch.unisg.ics.interactions.wot.td.schemas.DataSchema;
import things.Thing;

import java.util.Map;

public class OpenThing extends Thing {

    public Map<String, DataSchema> schemas;

    public OpenThing(String baseUrl, String relativeUrl, String desc){
        super(baseUrl, relativeUrl, desc);
    }

    @Override
    public ThingDescription exposeTD() {
        return new ThingDescription.Builder(title)
                .addThingURI(relativeURI)
                .addBaseURI(baseURI)
                .addActions(getActions())
                .addProperties(getProperties())
                .addEvents(getEvents())
                .build();
    }
}
