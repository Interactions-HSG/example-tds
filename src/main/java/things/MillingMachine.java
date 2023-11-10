package things;

import ch.unisg.ics.interactions.wot.td.ThingDescription;
import ch.unisg.ics.interactions.wot.td.affordances.ActionAffordance;
import ch.unisg.ics.interactions.wot.td.affordances.Form;
import ch.unisg.ics.interactions.wot.td.affordances.PropertyAffordance;
import ch.unisg.ics.interactions.wot.td.schemas.*;
import org.eclipse.rdf4j.model.vocabulary.DCTERMS;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.eclipse.rdf4j.model.util.Values.iri;
import static org.eclipse.rdf4j.model.util.Values.literal;

public class MillingMachine extends Thing{
    public MillingMachine(String baseURI, String relativeURI, String title) {
        super(baseURI, relativeURI, title);

        Set<String> machineTypes = new HashSet<>(Arrays.asList("LaserCutter", "MillingMachine"));

        StringSchema machineTypeSchema = new StringSchema.Builder()
                .addEnum(machineTypes)
                .build();

        ObjectSchema specSchema = new ObjectSchema.Builder()
                .addProperty("model", new StringSchema.Builder().build())
                .addProperty("type", machineTypeSchema)
                .addProperty("workingAreaWidthMillimeter", new NumberSchema.Builder().build())
                .addProperty("workingAreaLengthMillimeter", new NumberSchema.Builder().build())
                .addProperty("workingAreaHeightMillimeter", new NumberSchema.Builder().build())
                .addProperty("powerWatt", new NumberSchema.Builder().build())
                .addProperty("laserClass", new StringSchema.Builder().build())
                .build();

        Form getSpecForm = new Form.Builder(baseURI + "/spec")
                .setMethodName("GET")
                .build();

        PropertyAffordance getSpec = new PropertyAffordance.Builder("getSpec", getSpecForm)
                .addDataSchema(specSchema)
                .build();

        properties.add(getSpec);

        Set<String> authenticationModes = new HashSet<>();
        machineTypes.add("unauthenticated");
        machineTypes.add("BasicAuth");

        StringSchema authenticationModesSchema = new StringSchema.Builder()
                .addEnum(authenticationModes)
                .build();

        DataSchema configurationSchema = new ObjectSchema.Builder()
                .addProperty("host", new StringSchema.Builder().build())
                .addProperty("authenticationMode", authenticationModesSchema)
                .addRequiredProperties("host","authenticationMode" )
                .build();

        Form getConfigurationForm = new Form.Builder(baseURI + "/configuration")
                .setMethodName("GET")
                .build();

        PropertyAffordance getConfiguration = new PropertyAffordance.Builder("getConfiguration", getConfigurationForm)
                .addDataSchema(configurationSchema)
                .build();

        properties.add(getConfiguration);

        Form setConfigurationForm = new Form.Builder(baseURI + "/configuration")
                .setMethodName("POST")
                .build();

        ActionAffordance setConfiguration = new ActionAffordance.Builder("setConfiguration", setConfigurationForm)
                .addInputSchema(configurationSchema)
                .addOutputSchema(configurationSchema)
                .build();

        actions.add(setConfiguration);


        Form createEngraveTextJobForm = new Form.Builder(baseURI + "/api/job/text").build();
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
                .addProperty("noDrilling", new BooleanSchema.Builder().build())
                .build();
        ActionAffordance createEngraveText = new ActionAffordance.Builder("createEngraveText", createEngraveTextJobForm)
                .addInputSchema(engraveTextSchema)
                .build();
        actions.add(createEngraveText);

        Form getJobForm = new Form.Builder(baseURI + "/api/job").setMethodName("GET").build();



        Set<String> states = new HashSet<>(Arrays.asList("unconnected", "available", "working", "finished", "paused", "waiting", "error"));

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

        Form deleteJobForm = new Form.Builder(baseURI + "/api/job").setMethodName("DELETE").build();

        ActionAffordance deleteJob = new ActionAffordance.Builder("deleteJob", deleteJobForm)
                .addOutputSchema(engraverStatus)
                .build();

        actions.add(deleteJob);



        properties.add(getSpec);





        DataSchema actionResponseSchema = new ObjectSchema.Builder()
                .addProperty("status", new StringSchema.Builder().build())
                .addProperty("demo", new BooleanSchema.Builder().build())
                .addRequiredProperties("status", "demo")
                .build();

        Form getClampForm = new Form.Builder(baseURI + "/actuator-api/clamp")
                .setMethodName("GET")
                .build();



        PropertyAffordance getClamp = new PropertyAffordance.Builder("getClamp", getClampForm)
                .addUriVariable("machineId", new StringSchema.Builder().build())
                .addDataSchema(actionResponseSchema)
                .build();

        properties.add(getClamp);

        Form openClampForm = new Form.Builder(baseURI + "/actuator-api/clamp/open")
                .setMethodName("POST")
                .build();

        ActionAffordance openClamp = new ActionAffordance.Builder("openClamp", openClampForm)
                .addUriVariable("machineId", new StringSchema.Builder().build())
                .addOutputSchema(actionResponseSchema)
                .build();

        actions.add(openClamp);

        Form closeClampForm = new Form.Builder(baseURI + "/actuator-api/clamp/close")
                .setMethodName("POST")
                .build();

        ActionAffordance closeClamp = new ActionAffordance.Builder("closeClamp", closeClampForm)
                .addUriVariable("machineId", new StringSchema.Builder().build())
                .addOutputSchema(actionResponseSchema)
                .build();

        actions.add(closeClamp);

        Form getSpindleForm = new Form.Builder(baseURI + "/actuator-api/spindle")
                .setMethodName("GET")
                .build();



        PropertyAffordance getSpindle = new PropertyAffordance.Builder("getSpindle", getSpindleForm)
                .addUriVariable("machineId", new StringSchema.Builder().build())
                .addDataSchema(actionResponseSchema)
                .build();

        properties.add(getSpindle);

        Form startSpindleForm = new Form.Builder(baseURI + "/actuator-api/spindle/start")
                .setMethodName("POST")
                .build();

        ActionAffordance startSpindle = new ActionAffordance.Builder("startSpindle", startSpindleForm)
                .addUriVariable("machineId", new StringSchema.Builder().build())
                .addOutputSchema(actionResponseSchema)
                .build();

        actions.add(startSpindle);

        Form stopSpindleForm = new Form.Builder(baseURI + "/actuator-api/spindle/stop")
                .setMethodName("POST")
                .build();

        ActionAffordance stopSpindle = new ActionAffordance.Builder("stopSpindle", stopSpindleForm)
                .addUriVariable("machineId", new StringSchema.Builder().build())
                .addOutputSchema(actionResponseSchema)
                .build();

        actions.add(stopSpindle);
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
