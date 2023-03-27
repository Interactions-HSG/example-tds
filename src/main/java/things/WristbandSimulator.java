package things;

import ch.unisg.ics.interactions.wot.td.ThingDescription;
import ch.unisg.ics.interactions.wot.td.affordances.ActionAffordance;
import ch.unisg.ics.interactions.wot.td.affordances.Form;
import ch.unisg.ics.interactions.wot.td.schemas.ArraySchema;
import ch.unisg.ics.interactions.wot.td.schemas.IntegerSchema;
import ch.unisg.ics.interactions.wot.td.schemas.StringSchema;

public class WristbandSimulator extends Thing {
  public WristbandSimulator(String baseURI, String relativeURI, String title) {
    super(baseURI, relativeURI, title);
    this.namespaces.put("htv", "http://www.w3.org/2011/http#");
    this.namespaces.put("was", "https://was-course.interactions.ics.unisg.ch/wake-up-ontology#");

    Form form = new Form.Builder(baseURI + "owner-state")
            .setMethodName("GET")
            .build();

    actions.add(new ActionAffordance.Builder("Read owner state", form)
            .addSemanticType("https://was-course.interactions.ics.unisg.ch/wake-up-ontology#ReadOwnerState")
            .addInputSchema(new StringSchema.Builder()
                    .addSemanticType("https://was-course.interactions.ics.unisg.ch/wake-up-ontology#Intuition")
                    .build())
            .addOutputSchema(new StringSchema.Builder()
                    .addSemanticType("https://was-course.interactions.ics.unisg.ch/wake-up-ontology#OwnerState")
                    .build())
            .build());
  }

  @Override
  public ThingDescription exposeTD() {
    return new ThingDescription.Builder(title)
            .addThingURI(relativeURI)
            .addBaseURI(baseURI)
            .addSemanticType("https://was-course.interactions.ics.unisg.ch/wake-up-ontology#Wristband")
            .addActions(actions)
            .build();
  }
}
