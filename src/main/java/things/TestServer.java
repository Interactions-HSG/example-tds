package things;

import ch.unisg.ics.interactions.wot.td.ThingDescription;
import ch.unisg.ics.interactions.wot.td.affordances.ActionAffordance;
import ch.unisg.ics.interactions.wot.td.affordances.EventAffordance;
import ch.unisg.ics.interactions.wot.td.affordances.Form;
import ch.unisg.ics.interactions.wot.td.affordances.PropertyAffordance;
import ch.unisg.ics.interactions.wot.td.schemas.DataSchema;
import ch.unisg.ics.interactions.wot.td.schemas.ObjectSchema;
import ch.unisg.ics.interactions.wot.td.schemas.StringSchema;
import ch.unisg.ics.interactions.wot.td.security.APIKeySecurityScheme;

public class TestServer extends Thing {


    TestServer(String baseURI, String relativeURI, String title) {
        super(baseURI, relativeURI, title);

        DataSchema schema = new ObjectSchema.Builder()
                .addProperty("type", new StringSchema.Builder().build())
                .addRequiredProperties("type")
                .build();

        Form propertyForm = new Form.Builder(baseURI + "/get").setMethodName("GET").build();

        PropertyAffordance property = new PropertyAffordance.Builder("property", propertyForm)
                .addDataSchema(schema)
                .build();

        properties.add(property);

        Form actionForm = new Form.Builder(baseURI + "/post").build();

        DataSchema actionSchema = new ObjectSchema.Builder()
                .addProperty("type", new StringSchema.Builder().build())
                .addProperty("name", new StringSchema.Builder().build())
                .addRequiredProperties("type", "name")
                .build();

        ActionAffordance action = new ActionAffordance.Builder("action", actionForm)
                .addUriVariable("name", new StringSchema.Builder().build())
                .addOutputSchema(schema)
                .build();

        actions.add(action);

        Form eventForm = new Form.Builder(baseURI + "/event").build();

        EventAffordance event = new EventAffordance.Builder("event", eventForm).build();

        events.add(event);
    }

    @Override
    public ThingDescription exposeTD() {
        return new ThingDescription.Builder(title)
                .addThingURI(relativeURI)
                .addBaseURI(baseURI)
                .addActions(actions)
                .addProperties(properties)
                .addEvents(events)
                .build();
    }
}
