package things;

import ch.unisg.ics.interactions.wot.td.ThingDescription;
import ch.unisg.ics.interactions.wot.td.affordances.Form;
import ch.unisg.ics.interactions.wot.td.affordances.PropertyAffordance;
import ch.unisg.ics.interactions.wot.td.schemas.*;

public class AIService extends Thing{
    AIService(String baseURI, String relativeURI, String title) {
        super(baseURI, relativeURI, title);

        DataSchema grabspotSchema = new ObjectSchema.Builder()
                .addProperty("xcoordinate", new NumberSchema.Builder().build())
                .addProperty("ycoordinate", new NumberSchema.Builder().build())
                .addProperty("angle", new NumberSchema.Builder().build())
                .addProperty("confidence", new NumberSchema.Builder().build())
                .addRequiredProperties("xcoordinate", "ycoordinate", "angle", "confidence")
                .build();

        Form getGrabspotForm = new Form.Builder(baseURI + "/AI_Service/GET_grabspot")
                .setMethodName("GET")
                .build();

        PropertyAffordance getGrabspot = new PropertyAffordance.Builder("getGrabspot", getGrabspotForm)
                .addUriVariable("storageId", new StringSchema.Builder().build())
                .addUriVariable("cameraHostname", new StringSchema.Builder().build())
                .addUriVariable("cameraId", new StringSchema.Builder().build())
                .addDataSchema(new ArraySchema.Builder().addItem(grabspotSchema).build())
                .build();

        DataSchema engravingAreaSchema = new ObjectSchema.Builder()
                .addProperty("xcoordinate", new NumberSchema.Builder().build())
                .addProperty("ycoordinate", new NumberSchema.Builder().build())
                .addProperty("radius-mm", new NumberSchema.Builder().build())
                .addProperty("confidence", new NumberSchema.Builder().build())
                .addRequiredProperties("xcoordinate", "ycoordinate", "radius-mm", "confidence")
                .build();

        Form computeEngravingAreaForm = new Form.Builder(baseURI + "/AI_Service/compute_engravingArea")
                .setMethodName("GET")
                .build();

        PropertyAffordance computeEngravingArea = new PropertyAffordance.Builder("computeEngravingArea", computeEngravingAreaForm)
                .addUriVariable("storageId", new StringSchema.Builder().build())
                .addUriVariable("cameraHostname", new StringSchema.Builder().build())
                .addUriVariable("cameraId", new StringSchema.Builder().build())
                .addDataSchema(new ArraySchema.Builder().addItem(engravingAreaSchema).build())
                .build();

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
