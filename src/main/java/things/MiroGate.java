package things;

import ch.unisg.ics.interactions.wot.td.ThingDescription;
import ch.unisg.ics.interactions.wot.td.affordances.Form;
import ch.unisg.ics.interactions.wot.td.affordances.PropertyAffordance;
import ch.unisg.ics.interactions.wot.td.schemas.IntegerSchema;
import ch.unisg.ics.interactions.wot.td.schemas.NumberSchema;
import ch.unisg.ics.interactions.wot.td.schemas.ObjectSchema;
import ch.unisg.ics.interactions.wot.td.vocabularies.COV;
import ch.unisg.ics.interactions.wot.td.vocabularies.TD;
import org.eclipse.rdf4j.model.vocabulary.DCTERMS;

import static org.eclipse.rdf4j.model.util.Values.iri;
import static org.eclipse.rdf4j.model.util.Values.literal;

// import ch.unisg.ics.interactions.wot.td.vocabularies.COV;

public class MiroGate extends Thing {

    public MiroGate(String baseURI, String relativeURI, String title) {
        super(baseURI, relativeURI, title);
        this.namespaces.put("cov", COV.PREFIX);
        this.namespaces.put("miro", "http://example.org/mirogate#");


        Form poseForm = new Form.Builder(baseURI + "/pose")
                .setMethodName("GET")
                .addOperationType(TD.observeProperty)
                .addSubProtocol(COV.observe)
                .build();
        Form humForm = new Form.Builder(baseURI + "/humidity")
                .setMethodName("GET")
                .addOperationType(TD.observeProperty)
                .addSubProtocol(COV.observe)
                .build();
        Form tempForm = new Form.Builder(baseURI + "/temperature")
                .setMethodName("GET")
                .addOperationType(TD.observeProperty)
                .addSubProtocol(COV.observe)
                .build();

        PropertyAffordance poseEvent = new PropertyAffordance.Builder(new ObjectSchema.Builder()
                .addProperty("value", new IntegerSchema.Builder()
                        .addSemanticType("http://example.org/mirogate#PoseValue")
                        .addMinimum(0)
                        .addMaximum(4)
                        .build())
                .build(), poseForm)
                .addTitle("Observe pose")
                .addSemanticType("http://example.org/mirogate#ObservePose")
                .addObserve()
                .build();

        PropertyAffordance humEvent = new PropertyAffordance.Builder(new ObjectSchema.Builder()
                .addProperty("value", new NumberSchema.Builder()
                        .addSemanticType("http://example.org/mirogate#HumidityValue")
                        .addMinimum(15.00)
                        .addMaximum(40.00)
                        .build())
                .build(), humForm)
                .addTitle("Observe humidity")
                .addSemanticType("http://example.org/mirogate#ObserveHumidity")
                .addObserve()
                .build();

        PropertyAffordance tempEvent = new PropertyAffordance.Builder(new ObjectSchema.Builder()
                .addProperty("value", new NumberSchema.Builder()
                        .addSemanticType("http://example.org/mirogate#TemperatureValue")
                        .addMinimum(10.00)
                        .addMaximum(26.00)
                        .build())
                .build(), tempForm)
                .addTitle("Observe temperature")
                .addSemanticType("http://example.org/mirogate#ObserveTemperature")
                .addObserve()
                .build();

        properties.add(poseEvent);
        properties.add(humEvent);
        properties.add(tempEvent);
    }

    @Override
    public ThingDescription exposeTD() {
        return new ThingDescription.Builder(title)
                .addThingURI(relativeURI)
                .addBaseURI(baseURI)
                .addTriple(iri(relativeURI), DCTERMS.DESCRIPTION,
                        literal("A Thing that provides affordances to consumers by using MiroCards."))
                .addProperties(properties)
                .build();
    }

}
