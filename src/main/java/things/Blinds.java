package things;

import ch.unisg.ics.interactions.wot.td.ThingDescription;
import ch.unisg.ics.interactions.wot.td.affordances.ActionAffordance;
import ch.unisg.ics.interactions.wot.td.affordances.Form;
import ch.unisg.ics.interactions.wot.td.schemas.StringSchema;

import java.util.Arrays;
import java.util.HashSet;

public class Blinds extends Thing {
  public Blinds(String baseURI, String relativeURI, String title) {
    super(baseURI, relativeURI, title);
    this.namespaces.put("htv", "http://www.w3.org/2011/http#");
    this.namespaces.put("was", "https://was-course.interactions.ics.unisg.ch/wake-up-ontology#");

    Form form = new Form.Builder(baseURI + "state")
            .setMethodName("PUT")
            .build();

    actions.add(new ActionAffordance.Builder("Set the blinds state", form)
            .addSemanticType("https://was-course.interactions.ics.unisg.ch/wake-up-ontology#SetState")
            .addInputSchema(new StringSchema.Builder()
                    .addSemanticType("https://was-course.interactions.ics.unisg.ch/wake-up-ontology#BlindsState")
                    .addEnum(new HashSet<>(Arrays.asList("raised", "lowered")))
                    .build())
            .build());
  }

  @Override
  public ThingDescription exposeTD() {
    return new ThingDescription.Builder(title)
            .addThingURI(relativeURI)
            .addBaseURI(baseURI)
            .addSemanticType("https://was-course.interactions.ics.unisg.ch/wake-up-ontology#Blinds")
            .addActions(actions)
            .build();
  }
}
