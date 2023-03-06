package things;

import ch.unisg.ics.interactions.wot.td.ThingDescription;
import ch.unisg.ics.interactions.wot.td.affordances.ActionAffordance;
import ch.unisg.ics.interactions.wot.td.affordances.Form;
import ch.unisg.ics.interactions.wot.td.affordances.PropertyAffordance;
import ch.unisg.ics.interactions.wot.td.schemas.ArraySchema;
import ch.unisg.ics.interactions.wot.td.schemas.IntegerSchema;
import ch.unisg.ics.interactions.wot.td.schemas.ObjectSchema;
import ch.unisg.ics.interactions.wot.td.schemas.StringSchema;
import ch.unisg.ics.interactions.wot.td.vocabularies.TD;

public class SimpleIrrigatorTractor extends Thing {

  public SimpleIrrigatorTractor(String baseURI, String relativeURI, String title) {
    super(baseURI, relativeURI, title);
    this.namespaces.put("htv", "http://www.w3.org/2011/http#");
    this.namespaces.put("farm", "http://www.semanticweb.org/farm-ontology#");

    Form form = new Form.Builder(baseURI + "moisture")
            .setMethodName("GET")
            .build();

    actions.add(new ActionAffordance.Builder("Read Moisture Level", form)
            .addSemanticType("http://www.semanticweb.org/farm-ontology#ReadMoisture")
            .addInputSchema(new ArraySchema.Builder()
                    .addMaxItems(4)
                    .addMinItems(4)
                    .addItem(new IntegerSchema.Builder()
                            .addMinimum(0)
                            .addMaximum(2)
                            .build())
                    .build())
                    .addOutputSchema(new IntegerSchema.Builder()
                            .addSemanticType("http://www.semanticweb.org/farm-ontology#Moisture")
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
