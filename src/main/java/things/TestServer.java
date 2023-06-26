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


    public TestServer(String baseURI, String relativeURI, String title) {
        super(baseURI, relativeURI, title);

        DataSchema propertySchema = new ObjectSchema.Builder()
                .addProperty("name", new StringSchema.Builder().build())
                .addRequiredProperties("name")
                .build();

        Form propertyForm = new Form.Builder(baseURI + "/property").setMethodName("GET").build();

        PropertyAffordance property = new PropertyAffordance.Builder("property", propertyForm)
                .addDataSchema(propertySchema)
                .build();

        properties.add(property);

        Form actionForm = new Form.Builder(baseURI + "/action").build();

        DataSchema actionSchema = new ObjectSchema.Builder()
                .addProperty("name", new StringSchema.Builder().build())
                .addRequiredProperties("name")
                .build();

        ActionAffordance action = new ActionAffordance.Builder("action", actionForm)
                .addUriVariable("name", new StringSchema.Builder().build())
                .addOutputSchema(actionSchema)
                .build();

        actions.add(action);
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
