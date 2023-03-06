package things;

import ch.unisg.ics.interactions.wot.td.ThingDescription;
import ch.unisg.ics.interactions.wot.td.affordances.ActionAffordance;
import ch.unisg.ics.interactions.wot.td.affordances.Form;
import ch.unisg.ics.interactions.wot.td.schemas.ArraySchema;
import ch.unisg.ics.interactions.wot.td.schemas.IntegerSchema;

public class IrrigatorTractor extends Thing {
  public IrrigatorTractor(String baseURI, String relativeURI, String title) {
    super(baseURI, relativeURI, title);
    this.namespaces.put("htv", "http://www.w3.org/2011/http#");
    this.namespaces.put("farm", "http://www.semanticweb.org/farm-ontology#");

    Form form = new Form.Builder(baseURI + "sprayer")
            .setMethodName("PUT")
            .build();

    actions.add(new ActionAffordance.Builder("Irrigate", form)
            .addSemanticType("http://www.semanticweb.org/farm-ontology#Irrigate")
            .addInputSchema(new ArraySchema.Builder()
                    .addMaxItems(4)
                    .addMinItems(4)
                    .addItem(new IntegerSchema.Builder()
                            .addMinimum(0)
                            .addMaximum(2)
                            .build())
                    .build())
            .build());
  }

  @Override
  public ThingDescription exposeTD() {
    return new ThingDescription.Builder(title)
            .addThingURI(relativeURI)
            .addBaseURI(baseURI)
            .addSemanticType("http://www.semanticweb.org/farm-ontology#Tractor")
            .addActions(actions)
            .build();
  }
}
