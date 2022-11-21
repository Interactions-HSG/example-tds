package things;

import ch.unisg.ics.interactions.wot.td.ThingDescription;
import ch.unisg.ics.interactions.wot.td.affordances.Form;
import ch.unisg.ics.interactions.wot.td.affordances.PropertyAffordance;
import ch.unisg.ics.interactions.wot.td.schemas.DataSchema;
import ch.unisg.ics.interactions.wot.td.schemas.NumberSchema;
import ch.unisg.ics.interactions.wot.td.schemas.ObjectSchema;
import ch.unisg.ics.interactions.wot.td.schemas.StringSchema;
import ch.unisg.ics.interactions.wot.td.security.APIKeySecurityScheme;
import ch.unisg.ics.interactions.wot.td.security.SecurityScheme;

public class HotWater extends Thing{
    public HotWater(String baseURI, String relativeURI, String title) {
        super(baseURI, relativeURI, title);

        ObjectSchema volumeDataSchema = new ObjectSchema.Builder()
                .addProperty("value", new NumberSchema.Builder()
                        .build())
                .addProperty("timestamp", new StringSchema.Builder().build())
                .addRequiredProperties("value", "timestamp")
                .build();


        Form readVolumeForm = new Form.Builder(baseURI + "/public/api/wot/v1/property/33527")
                .setMethodName("GET")
                .setContentType("application/json")
                .addOperationType("readproperty")
                .build();

        PropertyAffordance volume = new PropertyAffordance.Builder("volume", readVolumeForm)
                .addDataSchema(volumeDataSchema)
                .build();

        properties.add(volume);

        Form supplyTemperatureForm = new Form.Builder(baseURI + "/public/api/wot/v1/property/33526")
                .setMethodName("GET")
                .setContentType("application/json")
                .addOperationType("readproperty")
                .build();

        PropertyAffordance supplyTemperature = new PropertyAffordance.Builder("supplyTemperature", supplyTemperatureForm)
                .addDataSchema(volumeDataSchema)
                .build();

        properties.add(supplyTemperature);

        Form returnTemperatureForm = new Form.Builder(baseURI + "/public/api/wot/v1/property/33529")
                .setMethodName("GET")
                .setContentType("application/json")
                .addOperationType("readproperty")
                .build();

        PropertyAffordance returnTemperature = new PropertyAffordance.Builder("returnTemperature", returnTemperatureForm)
                .addDataSchema(volumeDataSchema)
                .build();

        properties.add(returnTemperature);

        Form powerForm = new Form.Builder(baseURI + "/public/api/wot/v1/property/33528")
                .setMethodName("GET")
                .setContentType("application/json")
                .addOperationType("readproperty")
                .build();

        PropertyAffordance power = new PropertyAffordance.Builder("power", powerForm)
                .addDataSchema(volumeDataSchema)
                .build();

        properties.add(power);

        Form flowForm = new Form.Builder(baseURI + "/public/api/wot/v1/property/33530")
                .setMethodName("GET")
                .setContentType("application/json")
                .addOperationType("readproperty")
                .build();

        PropertyAffordance flow = new PropertyAffordance.Builder("flow", flowForm)
                .addDataSchema(volumeDataSchema)
                .build();

        properties.add(flow);

        Form energyForm = new Form.Builder(baseURI + "/public/api/wot/v1/property/33525")
                .setMethodName("GET")
                .setContentType("application/json")
                .addOperationType("readproperty")
                .build();

        PropertyAffordance energy = new PropertyAffordance.Builder("energy", energyForm)
                .addDataSchema(volumeDataSchema)
                .build();

        properties.add(energy);


    }

    @Override
    public ThingDescription exposeTD() {
        return new ThingDescription.Builder(title)
                .addSecurityScheme(new APIKeySecurityScheme(APIKeySecurityScheme
                        .TokenLocation.HEADER, "Authorization"))
                .addThingURI(relativeURI)
                .addBaseURI(baseURI)
                .addActions(actions)
                .addProperties(properties)
                .addEvents(events)
                .build();
    }
}
