package things;

import ch.unisg.ics.interactions.wot.td.ThingDescription;
import ch.unisg.ics.interactions.wot.td.affordances.ActionAffordance;
import ch.unisg.ics.interactions.wot.td.affordances.Form;
import ch.unisg.ics.interactions.wot.td.affordances.PropertyAffordance;
import ch.unisg.ics.interactions.wot.td.schemas.ArraySchema;
import ch.unisg.ics.interactions.wot.td.schemas.IntegerSchema;
import ch.unisg.ics.interactions.wot.td.schemas.NumberSchema;
import ch.unisg.ics.interactions.wot.td.schemas.ObjectSchema;
import ch.unisg.ics.interactions.wot.td.vocabularies.TD;

import java.util.Arrays;

public class Tractorbot extends Thing {

    public Tractorbot(String baseURI, String relativeURI, String title) {
        super(baseURI, relativeURI, title);
        namespaces.put("htv", "http://www.w3.org/2011/http#");
        namespaces.put("hsg", "http://semantics.interactions.ics.unisg.ch/hackathon21#");

        Form batteryLevelForm = new Form.Builder(baseURI + "things/tractorbot/properties/batteryvoltage")
                .addOperationType(TD.readProperty)
                .build();

        Form lidarForm = new Form.Builder(baseURI + "things/tractorbot/properties/lidar")
                .addOperationType(TD.readProperty)
                .build();

        Form wheelControlForm = new Form.Builder(baseURI + "things/tractorbot/actions/wheelControl")
                .addOperationType(TD.invokeAction)
                .build();

        Form highTemperatureForm = new Form.Builder(baseURI + "things/tractorbot/events/highTemperature")
                .setMethodName("GET")
                .build();

        Form lowBatteryForm = new Form.Builder(baseURI + "things/tractorbot/events/lowBattery")
                .setMethodName("GET")
                .build();

        PropertyAffordance batteryLevel = new PropertyAffordance.Builder("batteryLevel", batteryLevelForm)
                .addSemanticType("http://semantics.interactions.ics.unisg.ch/hackathon21#BatteryVoltage")
                .addDataSchema(new ArraySchema.Builder().build())
                .build();

        PropertyAffordance lidar = new PropertyAffordance.Builder("lidar", lidarForm)
                .addSemanticType("http://semantics.interactions.ics.unisg.ch/hackathon21#RadarData")
                .addDataSchema(new ArraySchema.Builder().build())
                .build();

        ActionAffordance wheelControl = new ActionAffordance.Builder("setWheelControl", wheelControlForm)
                .addSemanticType("http://semantics.interactions.ics.unisg.ch/hackathon21#MecannumWheelAction")
                .addInputSchema(new ObjectSchema.Builder()
                        .addProperty("axis", new IntegerSchema.Builder()
                                .addMinimum(0)
                                .addMaximum(2)
                                .build())
                        .addProperty("speed", new IntegerSchema.Builder()
                                .addMinimum(-7)
                                .addMaximum(7)
                                .build())
                        .addProperty("duration", new IntegerSchema.Builder()
                                .addMinimum(0)
                                .addMaximum(20000)
                                .build())
                        .build())
                .build();

        PropertyAffordance highTemperature = new PropertyAffordance.Builder("highTemperature", highTemperatureForm)
                .addObserve()
                .addSemanticType("http://semantics.interactions.ics.unisg.ch/hackathon21#HighTemperatureEvent")
                .addDataSchema(new NumberSchema.Builder().build())
                .build();

        PropertyAffordance lowBattery = new PropertyAffordance.Builder("lowBattery", lowBatteryForm)
                .addObserve()
                .addSemanticType("http://semantics.interactions.ics.unisg.ch/hackathon21#LowBattteryEvent")
                .addDataSchema(new NumberSchema.Builder().build())
                .build();

        properties.addAll(Arrays.asList(batteryLevel, lidar, highTemperature, lowBattery));
        actions.add(wheelControl);
    }

    @Override
    public ThingDescription exposeTD() {
        return new ThingDescription.Builder(title)
                .addThingURI(relativeURI)
                .addBaseURI(baseURI)
                .addSemanticType("http://semantics.interactions.ics.unisg.ch/hackathon21#Robot")
                .addProperties(properties)
                .addActions(actions)
                .build();
    }
}
