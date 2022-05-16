package things;

import ch.unisg.ics.interactions.wot.td.ThingDescription;
import ch.unisg.ics.interactions.wot.td.affordances.Form;
import ch.unisg.ics.interactions.wot.td.affordances.PropertyAffordance;
import ch.unisg.ics.interactions.wot.td.schemas.NumberSchema;
import ch.unisg.ics.interactions.wot.td.schemas.ObjectSchema;
import ch.unisg.ics.interactions.wot.td.security.NoSecurityScheme;
import ch.unisg.ics.interactions.wot.td.vocabularies.TD;

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

    properties.add(readStatus);
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
            .build();
  }
}
