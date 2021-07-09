package things;

import ch.unisg.ics.interactions.wot.td.ThingDescription;
import ch.unisg.ics.interactions.wot.td.affordances.ActionAffordance;
import ch.unisg.ics.interactions.wot.td.affordances.Form;
import ch.unisg.ics.interactions.wot.td.schemas.IntegerSchema;

public class CupProvider extends Thing {

    public CupProvider(String baseURI, String relativeURI, String title) {
        super(baseURI, relativeURI, title);
        this.namespaces.put("htv", "http://www.w3.org/2011/http#");
        this.namespaces.put("ex", "http://example.org#");

        Form orderForm = new Form.Builder(baseURI + "order")
                .build();

        ActionAffordance orderAction = new ActionAffordance.Builder(orderForm)
                .addTitle("order")
                .addSemanticType("http://example.org#Order")
                .addInputSchema(new IntegerSchema.Builder()
                        .build())
                .build();

        actions.add(orderAction);
    }

    public ThingDescription exposeTD() {
        return new ThingDescription.Builder(title)
                .addThingURI(relativeURI)
                .addBaseURI(baseURI)
                .addActions(actions)
                .build();
    }

}
