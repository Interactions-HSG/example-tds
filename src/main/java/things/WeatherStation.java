package things;

import ch.unisg.ics.interactions.wot.td.ThingDescription;
import ch.unisg.ics.interactions.wot.td.affordances.Form;
import ch.unisg.ics.interactions.wot.td.affordances.PropertyAffordance;
import ch.unisg.ics.interactions.wot.td.schemas.NumberSchema;
import ch.unisg.ics.interactions.wot.td.schemas.ObjectSchema;
import ch.unisg.ics.interactions.wot.td.schemas.StringSchema;
import ch.unisg.ics.interactions.wot.td.security.NoSecurityScheme;
import ch.unisg.ics.interactions.wot.td.vocabularies.TD;

public class WeatherStation extends Thing {
  public WeatherStation(String baseURI, String relativeURI, String title) {
    super(baseURI, relativeURI, title);
    this.namespaces.put("htv", "http://www.w3.org/2011/http#");
    this.namespaces.put("brick", "https://brickschema.org/schema/Brick#");
    this.namespaces.put("qudt", "http://qudt.org/vocab/unit/");
    this.namespaces.put("eve", "http://w3id.org/eve#");

    Form readAirTempForm = new Form.Builder(baseURI + "&field=temperature")
            .setMethodName("GET")
            .addOperationType(TD.readProperty)
            .build();

    Form readLightLevelForm = new Form.Builder(baseURI + "&field=light_level")
            .setMethodName("GET")
            .addOperationType(TD.readProperty)
            .build();

    PropertyAffordance readAirTemp = new PropertyAffordance.Builder("Temperature", readAirTempForm)
            .addTitle("Read Outside Air Temperature")
            .addSemanticType("https://brickschema.org/schema/Brick#Temperature")
            .addDataSchema(new ObjectSchema.Builder()
                    .addProperty("value", new NumberSchema.Builder()
                            .addSemanticType("http://qudt.org/vocab/unit/DEG_C")
                            .addMinimum(-50.00)
                            .addMaximum(80.00)
                            .build())
                    .addProperty("time", new StringSchema.Builder()
                            .addSemanticType("http://qudt.org/vocab/unit/DateTimeStringEncodingType")
                            .build())
                    .build())
            .build();

    PropertyAffordance readLightLevel = new PropertyAffordance.Builder("LightLevel", readLightLevelForm)
            .addTitle("Read Light level in Lux")
            .addSemanticType("https://brickschema.org/schema/Brick#Outside_Illuminance_Sensor")
            .addDataSchema(new ObjectSchema.Builder()
                    .addProperty("value", new NumberSchema.Builder()
                            .addSemanticType("http://qudt.org/vocab/unit/LUX")
                            .addMinimum(0.00)
                            .addMaximum(10000.00)
                            .build())
                    .addProperty("time", new StringSchema.Builder()
                            .addSemanticType("http://qudt.org/vocab/unit/DateTimeStringEncodingType")
                            .build())
                    .build())
            .build();

    properties.add(readAirTemp);
    properties.add(readLightLevel);
  }

  @Override
  public ThingDescription exposeTD() {
    return new ThingDescription.Builder(title)
            .addThingURI(relativeURI)
            .addBaseURI(baseURI)
            .addSemanticType("https://brickschema.org/schema/Brick#WeatherStation")
            .addSemanticType("http://w3id.org/eve#Artifact")
            .addSecurityScheme(new NoSecurityScheme())
            .addProperties(properties)
            .build();
  }
}
