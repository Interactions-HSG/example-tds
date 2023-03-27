package things;

import ch.unisg.ics.interactions.wot.td.ThingDescription;
import ch.unisg.ics.interactions.wot.td.affordances.ActionAffordance;
import ch.unisg.ics.interactions.wot.td.affordances.Form;
import ch.unisg.ics.interactions.wot.td.schemas.StringSchema;

import java.util.Arrays;
import java.util.HashSet;

public class Lights extends Thing {
  public Lights(String baseURI, String relativeURI, String title) {
    super(baseURI, relativeURI, title);
    this.namespaces.put("htv", "http://www.w3.org/2011/http#");
    this.namespaces.put("was", "https://was-course.interactions.ics.unisg.ch/wake-up-ontology#");

    Form form = new Form.Builder(baseURI + "state")
            .setMethodName("PUT")
            .build();

    actions.add(new ActionAffordance.Builder("Set the lights state", form)
            .addSemanticType("https://was-course.interactions.ics.unisg.ch/wake-up-ontology#SetState")
            .addInputSchema(new StringSchema.Builder()
                    .addSemanticType("https://was-course.interactions.ics.unisg.ch/wake-up-ontology#LightsState")
                    .addEnum(new HashSet<>(Arrays.asList("on", "off")))
                    .build())
            .build());
  }

  @Override
  public ThingDescription exposeTD() {
    return new ThingDescription.Builder(title)
            .addThingURI(relativeURI)
            .addBaseURI(baseURI)
            .addSemanticType("https://was-course.interactions.ics.unisg.ch/wake-up-ontology#Lights")
            .addActions(actions)
            .build();
  }
}
