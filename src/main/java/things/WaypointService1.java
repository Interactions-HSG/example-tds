package things;

import ch.unisg.ics.interactions.wot.td.ThingDescription;
import ch.unisg.ics.interactions.wot.td.affordances.Form;
import ch.unisg.ics.interactions.wot.td.affordances.PropertyAffordance;
import ch.unisg.ics.interactions.wot.td.schemas.*;
import ch.unisg.ics.interactions.wot.td.security.NoSecurityScheme;

public class WaypointService1 extends Thing{
    public WaypointService1(String baseURI, String relativeURI, String title) {
        super(baseURI, relativeURI, title);

        DataSchema coordinateSchema = new ArraySchema.Builder()
                .addItem(new NumberSchema.Builder().build())
                .addItem(new NumberSchema.Builder().build())
                .build();

        DataSchema geometrySchema = new ObjectSchema.Builder()
                .addProperty("type", new StringSchema.Builder().build())
                .addProperty("coordinates", coordinateSchema)
                .addRequiredProperties("type", "coordinates")
                .build();

        DataSchema propertiesSchema = new ObjectSchema.Builder()
                .addProperty("name", new StringSchema.Builder().build())
                .addRequiredProperties("name")
                .build();

        DataSchema waypointSchema = new ObjectSchema.Builder()
                .addProperty("type", new StringSchema.Builder().build())
                .addProperty("geometry", geometrySchema)
                .addProperty("properties", propertiesSchema)
                .addRequiredProperties("type", "geometry", "properties")
                .build();

        Form getWaypointForm = new Form.Builder(baseURI + "/waypoint").setMethodName("GET").build();

        PropertyAffordance getNextWaypoint = new PropertyAffordance.Builder("getNextWaypoint", getWaypointForm)
                .addDataSchema(waypointSchema)
                .build();

        this.properties.add(getNextWaypoint);
    }

    @Override
    public ThingDescription exposeTD() {
        return new ThingDescription.Builder(title)
                .addSemanticType("http://w3id.org/eve#Artifact")
                .addSecurityScheme(new NoSecurityScheme())
                .addThingURI(relativeURI)
                .addBaseURI(baseURI)
                .addActions(actions)
                .addProperties(properties)
                .build();
    }
}
