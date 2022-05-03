package things;

import ch.unisg.ics.interactions.wot.td.ThingDescription;
import ch.unisg.ics.interactions.wot.td.affordances.ActionAffordance;
import ch.unisg.ics.interactions.wot.td.affordances.Form;
import ch.unisg.ics.interactions.wot.td.schemas.BooleanSchema;
import ch.unisg.ics.interactions.wot.td.schemas.DataSchema;
import ch.unisg.ics.interactions.wot.td.schemas.ObjectSchema;
import ch.unisg.ics.interactions.wot.td.schemas.StringSchema;
import org.eclipse.rdf4j.model.vocabulary.DCTERMS;

import static org.eclipse.rdf4j.model.util.Values.iri;
import static org.eclipse.rdf4j.model.util.Values.literal;

public class EngraverActuators extends Thing{
    public EngraverActuators(String baseURI, String relativeURI, String title) {
        super(baseURI, relativeURI, title);

        Form openForm = new Form.Builder(baseURI + "/lid/open{?machineId}").build();

        DataSchema actionResponseSchema = new ObjectSchema.Builder()
                .addProperty("status", new StringSchema.Builder().build())
                .addProperty("demo", new BooleanSchema.Builder().build())
                .addRequiredProperties("status", "demo")
                .build();

        ActionAffordance open = new ActionAffordance.Builder("open", openForm)
                .addUriVariable("machineId", new StringSchema.Builder().build())
                .addOutputSchema(actionResponseSchema)
                .build();

        actions.add(open);

        Form closeForm = new Form.Builder(baseURI + "/lid/close{?machineId}").build();


        ActionAffordance close= new ActionAffordance.Builder("close", closeForm)
                .addUriVariable("machineId", new StringSchema.Builder().build())
                .addOutputSchema(actionResponseSchema)
                .build();

        actions.add(close);

        Form liftupForm = new Form.Builder(baseURI + "/table/liftup{?machineId}").build();


        ActionAffordance liftup= new ActionAffordance.Builder("liftup", liftupForm)
                .addUriVariable("machineId", new StringSchema.Builder().build())
                .addOutputSchema(actionResponseSchema)
                .build();

        actions.add(liftup);

        Form lowerdownForm = new Form.Builder(baseURI + "/table/lowerdown{?machineId}").build();


        ActionAffordance lowerdown= new ActionAffordance.Builder("lowerdown", lowerdownForm)
                .addUriVariable("machineId", new StringSchema.Builder().build())
                .addOutputSchema(actionResponseSchema)
                .build();

        actions.add(lowerdown);

        Form pushstartForm = new Form.Builder(baseURI + "/push-start-button{?machineId}").build();


        ActionAffordance pushstart =  new ActionAffordance.Builder("pushstart", pushstartForm)
                .addUriVariable("machineId", new StringSchema.Builder().build())
                .addOutputSchema(actionResponseSchema)
                .build();

        actions.add(pushstart);
    }

    @Override
    public ThingDescription exposeTD() {
        return new ThingDescription.Builder(title)
                .addSemanticType("http://w3id.org/eve#Artifact")
                //.addSemanticType("http://example.org/intellIoT#EngraverMachine")
                .addProperties(properties)
                .addActions(actions)
                .addThingURI(relativeURI)
                .addTriple(iri(relativeURI), DCTERMS.DESCRIPTION,
                        literal("Actuators for the engraver"))
                .build();
    }
}
