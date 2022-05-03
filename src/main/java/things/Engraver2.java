package things;

import ch.unisg.ics.interactions.wot.td.ThingDescription;
import ch.unisg.ics.interactions.wot.td.affordances.ActionAffordance;
import ch.unisg.ics.interactions.wot.td.affordances.Form;
import ch.unisg.ics.interactions.wot.td.schemas.*;
import org.eclipse.rdf4j.model.vocabulary.DCTERMS;

import static org.eclipse.rdf4j.model.util.Values.iri;
import static org.eclipse.rdf4j.model.util.Values.literal;

public class Engraver2 extends Thing{
    public Engraver2(String baseURI, String relativeURI, String title) {
        super(baseURI, relativeURI, title);
        Form createEngraveTextJobForm = new Form.Builder(baseURI + "/job/text").build();
        DataSchema engraveTextSchema = new ObjectSchema.Builder()
                .addProperty("font", new StringSchema.Builder().build())
                .addProperty("fontsize", new NumberSchema.Builder().build())
                .addProperty("positionReference", new StringSchema.Builder().build())
                .addProperty("text", new ArraySchema.Builder().addItem(new StringSchema.Builder().build()).build())
                .addProperty("variant", new StringSchema.Builder().build())
                .addProperty("x", new NumberSchema.Builder().build())
                .addProperty("y", new NumberSchema.Builder().build())
                .addProperty("laserOn", new BooleanSchema.Builder().build())
                .build();
        ActionAffordance createEngraveText = new ActionAffordance.Builder("createEngraveText", createEngraveTextJobForm)
                .addInputSchema(engraveTextSchema)
                .build();
        actions.add(createEngraveText);

        Form lowerTableForm = new Form.Builder(baseURI + "/actuators/table/lower/{id}")
                .build();

        ActionAffordance lowerTable = new ActionAffordance.Builder("lowerTable", lowerTableForm)
                .addUriVariable("id", new StringSchema.Builder().build())
                .build();

        actions.add(lowerTable);

        Form liftTableForm = new Form.Builder(baseURI + "/actuators/table/lift/{id}")
                .build();

        ActionAffordance liftTable = new ActionAffordance.Builder("liftTable", liftTableForm)
                .addUriVariable("id", new StringSchema.Builder().build())
                .build();

        actions.add(liftTable);

        Form closeCoverForm = new Form.Builder(baseURI + "/actuators/cover/close/{id}")
                .build();

        ActionAffordance closeCover = new ActionAffordance.Builder("closeCover", closeCoverForm)
                .addUriVariable("id", new StringSchema.Builder().build())
                .build();

        actions.add(closeCover);

        Form openCoverForm = new Form.Builder(baseURI + "/actuators/cover/open/{id}")
                .build();

        ActionAffordance openCover = new ActionAffordance.Builder("openCover", openCoverForm)
                .addUriVariable("id", new StringSchema.Builder().build())
                .build();

        actions.add(openCover);

        Form jobStatusForm = new Form.Builder(baseURI + "/job/status")
                .setMethodName("GET")
                .build();

        ActionAffordance jobStatus = new ActionAffordance.Builder("jobStatus", closeCoverForm)
                .build();

        actions.add(jobStatus);

        Form pushstartForm = new Form.Builder(baseURI + "/actuators/pushstart")
                .setMethodName("GET")
                .build();

        ActionAffordance pushstart = new ActionAffordance.Builder("pushstart", closeCoverForm)
                .build();

        actions.add(pushstart);
    }

    @Override
    public ThingDescription exposeTD() {
        return new ThingDescription.Builder(title)
                .addSemanticType("http://w3id.org/eve#Artifact")
                .addSemanticType("http://example.org/intellIoT#EngraverMachine")
                .addProperties(properties)
                .addActions(actions)
                .addThingURI(relativeURI)
                .addTriple(iri(relativeURI), DCTERMS.DESCRIPTION,
                        literal("An engraver machine used on the IntellIoT project"))
                .build();
    }
}
