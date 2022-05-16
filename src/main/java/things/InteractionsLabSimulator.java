package things;

import ch.unisg.ics.interactions.wot.td.ThingDescription;
import ch.unisg.ics.interactions.wot.td.affordances.ActionAffordance;
import ch.unisg.ics.interactions.wot.td.affordances.Form;
import ch.unisg.ics.interactions.wot.td.affordances.PropertyAffordance;
import ch.unisg.ics.interactions.wot.td.schemas.IntegerSchema;
import ch.unisg.ics.interactions.wot.td.schemas.NumberSchema;
import ch.unisg.ics.interactions.wot.td.schemas.ObjectSchema;
import ch.unisg.ics.interactions.wot.td.schemas.StringSchema;
import ch.unisg.ics.interactions.wot.td.security.NoSecurityScheme;
import ch.unisg.ics.interactions.wot.td.vocabularies.TD;

import java.util.Arrays;
import java.util.HashSet;

public class InteractionsLabSimulator extends Thing {

  public InteractionsLabSimulator(String baseURI, String relativeURI, String title) {
    super(baseURI, relativeURI, title);
    this.namespaces.put("htv", "http://www.w3.org/2011/http#");
    this.namespaces.put("eve", "http://w3id.org/eve#");

    Form readStatusForm = new Form.Builder(baseURI + "status")
            .setMethodName("GET")
            .addOperationType(TD.readProperty)
            .build();

    Form setStatusForm = new Form.Builder(baseURI + "action")
            .setMethodName("POST")
            .addOperationType(TD.invokeAction)
            .build();

    PropertyAffordance readStatus = new PropertyAffordance.Builder("Status", readStatusForm)
            .addTitle("Read the lab status")
            .addSemanticType("https://example.org/was#Status")
            .addDataSchema(new ObjectSchema.Builder()
                    .addProperty("Z1Level", new NumberSchema.Builder()
                            .addSemanticType("http://qudt.org/vocab/unit/LUX")
                            .build())
                    .build())
            .build();

    ActionAffordance controlLightsZ1 = new ActionAffordance.Builder("Control Lights Z1", setStatusForm)
            .addTitle("Turn on and off the lights in Z1")
            .addSemanticType("http://example.org/was#SetZ1Light")
            .addInputSchema(new ObjectSchema.Builder()
                    .addProperty("Z1Light", new StringSchema.Builder()
                            .addEnum(new HashSet<>(Arrays.asList("on", "off")))
                            .build())
                    .build())
            .build();

    ActionAffordance controlLightsZ2 = new ActionAffordance.Builder("Control Lights Z2", setStatusForm)
            .addTitle("Turn on and off the lights in Z2")
            .addSemanticType("http://example.org/was#SetZ2Light")
            .addInputSchema(new ObjectSchema.Builder()
                    .addProperty("Z2Light", new StringSchema.Builder()
                            .addEnum(new HashSet<>(Arrays.asList("on", "off")))
                            .build())
                    .build())
            .build();

    ActionAffordance controlBlindsZ1 = new ActionAffordance.Builder("Control Blinds Z1", setStatusForm)
            .addTitle("Raise and lower the blinds in Z1")
            .addSemanticType("http://example.org/was#SetZ1Blinds")
            .addInputSchema(new ObjectSchema.Builder()
                    .addProperty("Z1Blinds", new StringSchema.Builder()
                            .addEnum(new HashSet<>(Arrays.asList("up", "down")))
                            .build())
                    .build())
            .build();

    ActionAffordance controlBlindsZ2 = new ActionAffordance.Builder("Control Blinds Z2", setStatusForm)
            .addTitle("Raise and lower the blinds in Z2")
            .addSemanticType("http://example.org/was#SetZ1Blinds")
            .addInputSchema(new ObjectSchema.Builder()
                    .addProperty("Z2Blinds", new StringSchema.Builder()
                            .addEnum(new HashSet<>(Arrays.asList("up", "down")))
                            .build())
                    .build())
            .build();

    properties.add(readStatus);
    actions.addAll(Arrays.asList(controlLightsZ1, controlLightsZ2, controlBlindsZ1, controlBlindsZ2));
  }

  @Override
  public ThingDescription exposeTD() {
    return new ThingDescription.Builder(title)
            .addThingURI(relativeURI)
            .addBaseURI(baseURI)
            .addSemanticType("https://example.org/was#Lab")
            .addSemanticType("http://w3id.org/eve#Artifact")
            .addSecurityScheme(new NoSecurityScheme())
            .addProperties(properties)
            .addActions(actions)
            .build();
  }
}
