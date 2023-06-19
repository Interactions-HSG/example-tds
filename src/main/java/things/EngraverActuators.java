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

        Form openForm = new Form.Builder(baseURI + "/lid/open").build();

        DataSchema actionResponseSchema = new ObjectSchema.Builder()
                .addProperty("status", new StringSchema.Builder().build())
                .addProperty("demo", new BooleanSchema.Builder().build())
                .addRequiredProperties("status", "demo")
                .build();

        ActionAffordance open = new ActionAffordance.Builder("open", openForm)
                .addOutputSchema(actionResponseSchema)
                .build();

        actions.add(open);

        Form openWithIdForm = new Form.Builder(baseURI + "/lid/open{?machineId}").build();


        ActionAffordance openWithId = new ActionAffordance.Builder("openWithId", openWithIdForm)
                .addUriVariable("machineId", new StringSchema.Builder().build())
                .addOutputSchema(actionResponseSchema)
                .build();

        actions.add(openWithId);

        Form closeForm = new Form.Builder(baseURI + "/lid/close").build();


        ActionAffordance close= new ActionAffordance.Builder("close", closeForm)
                .addOutputSchema(actionResponseSchema)
                .build();

        actions.add(close);

        Form closeWithIdForm = new Form.Builder(baseURI + "/lid/close{?machineId}").build();


        ActionAffordance closeWithId= new ActionAffordance.Builder("closeWithId", closeWithIdForm)
                .addUriVariable("machineId", new StringSchema.Builder().build())
                .addOutputSchema(actionResponseSchema)
                .build();

        actions.add(closeWithId);

        Form liftupForm = new Form.Builder(baseURI + "/table/liftup").build();


        ActionAffordance liftup= new ActionAffordance.Builder("liftup", liftupForm)
                .addOutputSchema(actionResponseSchema)
                .build();

        actions.add(liftup);

        Form liftupWithIdForm = new Form.Builder(baseURI + "/table/liftup{?machineId}").build();


        ActionAffordance liftupWithId= new ActionAffordance.Builder("liftupWithId", liftupWithIdForm)
                .addUriVariable("machineId", new StringSchema.Builder().build())
                .addOutputSchema(actionResponseSchema)
                .build();

        actions.add(liftupWithId);

        Form lowerdownForm = new Form.Builder(baseURI + "/table/lowerdown").build();


        ActionAffordance lowerdown= new ActionAffordance.Builder("lowerdown", lowerdownForm)
                .addOutputSchema(actionResponseSchema)
                .build();

        actions.add(lowerdown);

        Form lowerdownWithIdForm = new Form.Builder(baseURI + "/table/lowerdown{?machineId}").build();


        ActionAffordance lowerdownWithId= new ActionAffordance.Builder("lowerdownWithId", lowerdownWithIdForm)
                .addUriVariable("machineId", new StringSchema.Builder().build())
                .addOutputSchema(actionResponseSchema)
                .build();

        actions.add(lowerdownWithId);

        Form pushstartForm = new Form.Builder(baseURI + "/push-start-button").build();


        ActionAffordance pushstart =  new ActionAffordance.Builder("pushstart", pushstartForm)
                .addOutputSchema(actionResponseSchema)
                .build();

        actions.add(pushstart);

        Form pushstartWithIdForm = new Form.Builder(baseURI + "/push-start-button{?machineId}").build();


        ActionAffordance pushstartWithId =  new ActionAffordance.Builder("pushstartWithId", pushstartWithIdForm)
                .addUriVariable("machineId", new StringSchema.Builder().build())
                .addOutputSchema(actionResponseSchema)
                .build();

        actions.add(pushstartWithId);
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
