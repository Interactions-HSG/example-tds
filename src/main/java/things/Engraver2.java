package things;

import ch.unisg.ics.interactions.wot.td.ThingDescription;
import ch.unisg.ics.interactions.wot.td.affordances.ActionAffordance;
import ch.unisg.ics.interactions.wot.td.affordances.Form;
import ch.unisg.ics.interactions.wot.td.affordances.PropertyAffordance;
import ch.unisg.ics.interactions.wot.td.schemas.*;
import org.eclipse.rdf4j.model.vocabulary.DCTERMS;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.eclipse.rdf4j.model.util.Values.iri;
import static org.eclipse.rdf4j.model.util.Values.literal;

public class Engraver2 extends Thing{
    public Engraver2(String baseURI, String relativeURI, String title) {
        super(baseURI, relativeURI, title);
        Form createEngraveTextJobForm = new Form.Builder(baseURI + "/job/text").build();
        DataSchema engraveTextSchema = new ObjectSchema.Builder()
                .addProperty("font", new StringSchema.Builder().build())
                .addProperty("fontsize", new NumberSchema.Builder().build())
                .addProperty("textWidth", new NumberSchema.Builder().build())
                .addProperty("textHeight", new NumberSchema.Builder().build())
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

        Form getJobForm = new Form.Builder(baseURI + "/job").setMethodName("GET").build();



        Set<String> states = new HashSet<>(Arrays.asList(new String[] {"unconnected", "available", "working", "finished", "paused", "waiting", "error" }));

        DataSchema stateSchema = new StringSchema.Builder().addEnum(states).build();

        DataSchema engraverStatus = new ObjectSchema.Builder()
                .addProperty("state", stateSchema)
                .addProperty("secondsToFinish", new IntegerSchema.Builder().build())
                .addProperty("percentageCompleted", new IntegerSchema.Builder().build())
                .build();

        PropertyAffordance getJob = new PropertyAffordance.Builder("getJob", getJobForm)
                .addDataSchema(engraverStatus)
                .build();

        properties.add(getJob);

        Form deleteJobForm = new Form.Builder(baseURI + "/job").setMethodName("DELETE").build();

        ActionAffordance deleteJob = new ActionAffordance.Builder("deleteJob", deleteJobForm)
                .addOutputSchema(engraverStatus)
                .build();

        actions.add(deleteJob);

        Form getSpecForm = new Form.Builder(baseURI + "/spec").setMethodName("GET").build();

        Set<String> machineTypes = new HashSet<>(Arrays.asList(new String[]{"LaserCutter", "MillingMachine"}));

        DataSchema machineTypeSchema = new DataSchema.Builder().addEnum(machineTypes).build();

        DataSchema specSchema = new ObjectSchema.Builder()
                .addProperty("model", new StringSchema.Builder().build())
                .addProperty("type", machineTypeSchema)
                .addProperty("workingAreaWidthMillimeter", new NumberSchema.Builder().build())
                .addProperty("workingAreaLengthMillimeter", new NumberSchema.Builder().build())
                .addProperty("workingAreaHeightMillimeter", new NumberSchema.Builder().build())
                .addProperty("laserClass", new StringSchema.Builder().build())
                .build();

        PropertyAffordance getSpec = new PropertyAffordance.Builder("getSpec", getSpecForm)
                .addDataSchema(specSchema)
                .build();

        properties.add(getSpec);

        Form getConfigurationForm = new Form.Builder(baseURI + "/configuration").setMethodName("GET").build();

        Set<String> authenticationModes = new HashSet<>(Arrays.asList(new String[]{"unauthenticated", "BasicAuth"}));

        DataSchema authenticationModeSchema = new StringSchema.Builder().addEnum(machineTypes).build();

        DataSchema configurationSchema = new ObjectSchema.Builder()
                .addProperty("host", new StringSchema.Builder().build())
                .addProperty("authenticationMode", authenticationModeSchema)
                .addRequiredProperties("host","authenticationMode" )
                .build();


        PropertyAffordance getConfiguration = new PropertyAffordance.Builder("getConfiguration", getConfigurationForm)
                .addDataSchema(configurationSchema)
                .build();

        properties.add(getConfiguration);

        Form setConfigurationForm = new Form.Builder(baseURI + "/configuration").setMethodName("POST").build();

        ActionAffordance setConfiguration = new ActionAffordance.Builder("getConfiguration", getConfigurationForm)
                .addInputSchema(configurationSchema)
                .addOutputSchema(configurationSchema)
                .build();

        actions.add(setConfiguration);


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
