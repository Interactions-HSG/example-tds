package things;

import ch.unisg.ics.interactions.wot.td.ThingDescription;
import ch.unisg.ics.interactions.wot.td.affordances.Form;
import ch.unisg.ics.interactions.wot.td.affordances.PropertyAffordance;
import ch.unisg.ics.interactions.wot.td.schemas.IntegerSchema;
import ch.unisg.ics.interactions.wot.td.schemas.NumberSchema;
import ch.unisg.ics.interactions.wot.td.schemas.ObjectSchema;
import ch.unisg.ics.interactions.wot.td.vocabularies.COV;
import ch.unisg.ics.interactions.wot.td.vocabularies.HTV;
import ch.unisg.ics.interactions.wot.td.vocabularies.TD;
import org.eclipse.rdf4j.model.vocabulary.DCTERMS;
import vocabularies.MIRO;

import java.util.Arrays;

import static org.eclipse.rdf4j.model.util.Values.iri;
import static org.eclipse.rdf4j.model.util.Values.literal;

public class MiroGate extends Thing {

    public MiroGate(String baseURI, String relativeURI, String title) {
        super(baseURI, relativeURI, title);
        this.namespaces.put("cov", COV.PREFIX);
        this.namespaces.put("htv", HTV.PREFIX);
        this.namespaces.put("miro", MIRO.PREFIX);

        Form poseForm = new Form.Builder(baseURI + "/pose")
                .setMethodName("GET")
                .addOperationType(TD.observeProperty)
                .addSubProtocol(COV.observe)
                .build();
        Form humFormObserve = new Form.Builder(baseURI + "/humidity")
                .setMethodName("GET")
                .addOperationType(TD.observeProperty)
                .addSubProtocol(COV.observe)
                .build();
        Form tempFormObserve = new Form.Builder(baseURI + "/temperature")
                .setMethodName("GET")
                .addOperationType(TD.observeProperty)
                .addSubProtocol(COV.observe)
                .build();
        Form humForm = new Form.Builder(baseURI + "/humidity")
                .setMethodName("GET")
                .addOperationType(TD.readProperty)
                .build();
        Form tempForm = new Form.Builder(baseURI + "/temperature")
                .setMethodName("GET")
                .addOperationType(TD.readProperty)
                .build();

        PropertyAffordance poseEvent = new PropertyAffordance.Builder("pose", poseForm)
                .addTitle("Pose")
                .addSemanticType(MIRO.pose)
                .addDataSchema(new ObjectSchema.Builder()
                        .addProperty("value", new IntegerSchema.Builder()
                                .addSemanticType(MIRO.poseValue)
                                .addMinimum(0)
                                .addMaximum(4)
                                .build())
                        .build())
                .addObserve()
                .build();

        PropertyAffordance humEvent = new PropertyAffordance.Builder("humidity", Arrays.asList(humFormObserve, humForm))
                .addTitle("Humidity")
                .addSemanticType(MIRO.humidity)
                .addDataSchema(new ObjectSchema.Builder()
                        .addProperty("value", new NumberSchema.Builder()
                                .addSemanticType(MIRO.humidityValue)
                                .addMinimum(15.00)
                                .addMaximum(40.00)
                                .build())
                        .build())
                .addObserve()
                .build();

        PropertyAffordance tempEvent = new PropertyAffordance.Builder("temperature", Arrays.asList(tempFormObserve, tempForm))
                .addTitle("Temperature")
                .addSemanticType(MIRO.temperature)
                .addDataSchema(new ObjectSchema.Builder()
                        .addProperty("value", new NumberSchema.Builder()
                                .addSemanticType(MIRO.temperatureValue)
                                .addMinimum(10.00)
                                .addMaximum(26.00)
                                .build())
                        .build())
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
                .addTriple(iri(relativeURI), DCTERMS.DESCRIPTION,
                        literal("A Thing that provides affordances to consumers by using MiroCards."))
                .addProperties(properties)
                .build();
    }

}
