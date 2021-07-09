package things;

import ch.unisg.ics.interactions.wot.td.ThingDescription;
import ch.unisg.ics.interactions.wot.td.affordances.ActionAffordance;
import ch.unisg.ics.interactions.wot.td.affordances.Form;
import ch.unisg.ics.interactions.wot.td.schemas.IntegerSchema;

public class CupProvider extends Thing {

    CupProvider(ThingDescription td) {
        super(td);
        this.namespaces.put("htv", "http://www.w3.org/2011/http#");
        this.namespaces.put("ex", "http://example.org#");
    }

    public static CupProvider instantiate(String baseURI, String relativeURI, String title) {

        Form orderForm = new Form.Builder(baseURI + "order")
                .build();

        ActionAffordance orderAction = new ActionAffordance.Builder(orderForm)
                .addTitle("order")
                .addSemanticType("http://example.org#Order")
                .addInputSchema(new IntegerSchema.Builder()
                        .build())
                .build();

        ThingDescription td = (new ThingDescription.Builder(title))
                .addThingURI(relativeURI)
                .addBaseURI(baseURI)
                .addAction(orderAction)
                .build();

        return new CupProvider(td);
    }

    public ThingDescription getTD() {
        return td;
    }

}
