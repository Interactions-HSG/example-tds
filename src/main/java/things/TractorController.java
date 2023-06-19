package things;

import ch.unisg.ics.interactions.wot.td.ThingDescription;
import ch.unisg.ics.interactions.wot.td.affordances.ActionAffordance;
import ch.unisg.ics.interactions.wot.td.affordances.Form;
import ch.unisg.ics.interactions.wot.td.affordances.PropertyAffordance;
import ch.unisg.ics.interactions.wot.td.schemas.DataSchema;
import ch.unisg.ics.interactions.wot.td.schemas.NumberSchema;
import ch.unisg.ics.interactions.wot.td.schemas.ObjectSchema;
import ch.unisg.ics.interactions.wot.td.schemas.StringSchema;

public class TractorController extends Thing {

    TractorController(String baseURI, String relativeURI, String title) {
        super(baseURI, relativeURI, title);

        ObjectSchema driveCmdsSchema = new ObjectSchema.Builder()
                .addProperty("set_velocity", new NumberSchema.Builder().build())
                .addProperty("set_steerAngle", new NumberSchema.Builder().build())
                .addRequiredProperties("set_velocity", "set_steerAngle")
                .build();
        Form readRootForm = new Form.Builder(baseURI)
                .setMethodName("GET")
                .build();
        PropertyAffordance readRoot = new PropertyAffordance.Builder("readRoot", readRootForm).build();

        properties.add(readRoot);

        Form implementForm = new Form.Builder(baseURI+"implement").setMethodName("GET").build();

        PropertyAffordance implement = new PropertyAffordance.Builder("implement", implementForm).build();

        properties.add(implement);

        Form driveCmdsForm = new Form.Builder(baseURI+"driveCmds").setMethodName("PUT").build();



        ActionAffordance driveCmds = new ActionAffordance.Builder("driveCmds", driveCmdsForm)
                .addInputSchema(driveCmdsSchema)
                .addOutputSchema(new StringSchema.Builder().build())
                .build();

        actions.add(driveCmds);
    }




    @Override
    public ThingDescription exposeTD() {
        return null;
    }
}
