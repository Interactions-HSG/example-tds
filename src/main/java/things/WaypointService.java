package things;

import ch.unisg.ics.interactions.wot.td.ThingDescription;
import ch.unisg.ics.interactions.wot.td.affordances.ActionAffordance;
import ch.unisg.ics.interactions.wot.td.affordances.Form;
import ch.unisg.ics.interactions.wot.td.affordances.PropertyAffordance;
import ch.unisg.ics.interactions.wot.td.schemas.ArraySchema;
import ch.unisg.ics.interactions.wot.td.schemas.ObjectSchema;
import ch.unisg.ics.interactions.wot.td.schemas.StringSchema;
import ch.unisg.ics.interactions.wot.td.security.NoSecurityScheme;

public class WaypointService extends Thing{
    public WaypointService(String baseURI, String relativeURI, String title) {
        super(baseURI, relativeURI, title);

        Form getWaypointForm = new Form.Builder(baseURI + "/waypoint")
                .setMethodName("GET")
                .build();

        PropertyAffordance getWaypoint = new PropertyAffordance.Builder("getWaypoint", getWaypointForm)
                .addUriVariable("?field", new StringSchema.Builder().build())
                .addDataSchema(new ArraySchema.Builder().build())
                .build();

        properties.add(getWaypoint);

        Form registerFieldForm = new Form.Builder(baseURI + "/register")
                .build();

        ObjectSchema registerFieldSchema = new ObjectSchema.Builder()
                .addProperty("field", new StringSchema.Builder().build())
                .addProperty("polygon", new ArraySchema.Builder().build())
                .addRequiredProperties("field", "polygon")
                .build();

        ActionAffordance registerField = new ActionAffordance.Builder("registerField", registerFieldForm)
                .addInputSchema(registerFieldSchema)
                .build();

        actions.add(registerField);

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
