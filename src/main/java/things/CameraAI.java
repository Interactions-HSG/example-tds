package things;

import ch.unisg.ics.interactions.wot.td.ThingDescription;
import ch.unisg.ics.interactions.wot.td.affordances.ActionAffordance;
import ch.unisg.ics.interactions.wot.td.affordances.Form;
import ch.unisg.ics.interactions.wot.td.schemas.*;
import ch.unisg.ics.interactions.wot.td.security.APIKeySecurityScheme;

public class CameraAI extends Thing{
    CameraAI(String baseURI, String relativeURI, String title) {
        super(baseURI, relativeURI, title);

        Form getGrabspotForm = new Form.Builder(baseURI+"/AI_Service/GET_grabspot")
                .setMethodName("GET")
                .build();

        DataSchema grabspotSchema = new ArraySchema.Builder()
                .addItem(new ObjectSchema.Builder()
                        .addProperty("xcoordinate", new NumberSchema.Builder().build())
                        .addProperty("ycoordinate", new NumberSchema.Builder().build())
                        .addProperty("angle", new NumberSchema.Builder().build())
                        .addProperty("confidence", new NumberSchema.Builder().build())
                        .addRequiredProperties("xcoordinate", "ycoordinate", "angle", "confidence")
                        .build())
                .build();

        ActionAffordance getGrabspot = new ActionAffordance.Builder("getGrabspot", getGrabspotForm)
                .addUriVariable("storageId", new DataSchema.Builder().build())
                .addUriVariable("cameraHostname", new DataSchema.Builder().build())
                .addUriVariable("cameraId", new DataSchema.Builder().build())
                .addOutputSchema(grabspotSchema)
                .build();

        Form computeEngravingAreaForm = new Form.Builder(baseURI+"/AI_Service/GET_grabspot")
                .setMethodName("GET")
                .build();

        DataSchema engravingAreaSchema = new ArraySchema.Builder()
                .addItem(new ObjectSchema.Builder()
                        .addProperty("xcoordinate", new NumberSchema.Builder().build())
                        .addProperty("ycoordinate", new NumberSchema.Builder().build())
                        .addProperty("radius-mm", new NumberSchema.Builder().build())
                        .addProperty("confidence", new NumberSchema.Builder().build())
                        .addRequiredProperties("xcoordinate", "ycoordinate", "radius-mm", "confidence")
                        .build())
                .build();

        ActionAffordance computeEngravingArea = new ActionAffordance.Builder("computeEngravingArea", computeEngravingAreaForm)
                .addUriVariable("storageId", new DataSchema.Builder().build())
                .addUriVariable("cameraHostname", new DataSchema.Builder().build())
                .addUriVariable("cameraId", new DataSchema.Builder().build())
                .addOutputSchema(engravingAreaSchema)
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
