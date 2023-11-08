package things;

import ch.unisg.ics.interactions.wot.td.ThingDescription;
import ch.unisg.ics.interactions.wot.td.affordances.Form;
import ch.unisg.ics.interactions.wot.td.affordances.PropertyAffordance;
import ch.unisg.ics.interactions.wot.td.schemas.*;
import org.eclipse.rdf4j.model.vocabulary.DCTERMS;

import static org.eclipse.rdf4j.model.util.Values.iri;
import static org.eclipse.rdf4j.model.util.Values.literal;

public class IOBox extends Thing{
    public IOBox(String baseURI, String relativeURI, String title) {
        super(baseURI, relativeURI, title);

        DataSchema temperatureSchema = new ObjectSchema.Builder()
                .addProperty("temperature", new NumberSchema.Builder().build())
                .addProperty("timestamp", new IntegerSchema.Builder().build())
                .addRequiredProperties("temperature", "timestamp")
                .build();

        DataSchema deviceSchema = new ObjectSchema.Builder()
                .addProperty("mac", new StringSchema.Builder().build())
                .addProperty("module", new StringSchema.Builder().build())
                .addProperty("name", new StringSchema.Builder().build())
                .addProperty("type", new StringSchema.Builder().build())
                .addRequiredProperties("mac", "module", "name", "type")
                .build();

        DataSchema bleSchema = new ObjectSchema.Builder()
                //.addItem(deviceSchema)
                .build();

        Form getBLEDevicesForm = new Form.Builder(baseURI +"/devices/ble")
                .setMethodName("GET")
                .build();

        PropertyAffordance getBLEDevices = new PropertyAffordance.Builder("getBLEDevices", getBLEDevicesForm)
                .addDataSchema(bleSchema)
                .build();

        properties.add(getBLEDevices);

        Form getTemperatureForm = new Form.Builder(baseURI + "/devices/ble/{deviceId}/temperature")
                .setMethodName("GET")
                .build();

        PropertyAffordance getTemperature = new PropertyAffordance.Builder("getTemperature", getTemperatureForm)
                .addUriVariable("deviceId", new StringSchema.Builder().build())
                .addDataSchema(temperatureSchema)
                .build();

        properties.add(getTemperature);
    }

    @Override
    public ThingDescription exposeTD() {
        return new ThingDescription.Builder(title)
                .addSemanticType("http://w3id.org/eve#Artifact")
                //.addSemanticType("http://example.org/intellIoT#EngraverMachine")
                .addProperties(properties)
                .addActions(actions)
                .addThingURI(relativeURI)
                .addTriple(iri(relativeURI), DCTERMS.DESCRIPTION,
                        literal("Actuators for the engraver"))
                .build();
    }
}
