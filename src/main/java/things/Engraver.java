package things;

import ch.unisg.ics.interactions.wot.td.ThingDescription;
import ch.unisg.ics.interactions.wot.td.affordances.ActionAffordance;
import ch.unisg.ics.interactions.wot.td.affordances.Form;
import ch.unisg.ics.interactions.wot.td.affordances.PropertyAffordance;
import ch.unisg.ics.interactions.wot.td.schemas.NumberSchema;
import ch.unisg.ics.interactions.wot.td.schemas.ObjectSchema;
import ch.unisg.ics.interactions.wot.td.schemas.StringSchema;
import ch.unisg.ics.interactions.wot.td.vocabularies.HTV;
import ch.unisg.ics.interactions.wot.td.vocabularies.TD;
import org.eclipse.rdf4j.model.vocabulary.DCTERMS;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.eclipse.rdf4j.model.util.Values.iri;
import static org.eclipse.rdf4j.model.util.Values.literal;
public class Engraver extends Thing{


    public Engraver(String baseURI, String relativeURI, String title) {
        super(baseURI, relativeURI, title);
        this.namespaces.put("htv", HTV.PREFIX);
        this.namespaces.put("intelliot", "http://example.org/intellIoT#");

        //Forms
        Form readConfForm = new Form.Builder(baseURI + "/configuration")
                .addOperationType(TD.readProperty)
                .setContentType("application/json")
                .addOperationType("GET")
                .build();

        Form invokeConfForm = new Form.Builder(baseURI + "/configuration")
                .addOperationType(TD.invokeAction)
                .setContentType("application/json")
                .addOperationType("POST")
                .build();

        Form readJobForm = new Form.Builder(baseURI + "/job")
                .addOperationType(TD.readProperty)
                .setContentType("application/json")
                .addOperationType("GET")
                .build();

        Form invokeJobForm = new Form.Builder(baseURI + "/job")
                .addOperationType(TD.invokeAction)
                .setContentType("multipart/form-data")
                .addOperationType("POST")
                .build();

        Form deleteJobForm = new Form.Builder(baseURI + "/job")
                .addOperationType(TD.invokeAction)
                .setContentType("application/json")
                .addOperationType("DELETE")
                .build();

        Form readSpecForm = new Form.Builder(baseURI + "/spec")
                .addOperationType(TD.readProperty)
                .setContentType("application/json")
                .addOperationType("GET")
                .build();

        //DataSchemas
        Set<String> stateEnum = new HashSet<>();
        Collections.addAll(stateEnum, "offline", "available", "working",
                "finished", "paused", "waiting", "unavailable", "failed", "error");
        StringSchema engraverState = new StringSchema.Builder()
                .addSemanticType("http://example.org/intellIoT#EngraverState")
                .addEnum(stateEnum)
                .build();

        Set<String> materialEnum = new HashSet<>();
        Collections.addAll(materialEnum, "populus", "paper", "kraftplex",
                "acrylic", "cherry", "pine", "beech", "birch", "oak, wallboard, slate");
        StringSchema material = new StringSchema.Builder()
                .addSemanticType("http://example.org/intellIoT#Material")
                .addEnum(materialEnum)
                .build();

        Set<String> machineTypeEnum = new HashSet<>();
        Collections.addAll(machineTypeEnum, "LaserCutter", "MillingMachine");
        StringSchema machineType = new StringSchema.Builder()
                .addSemanticType("http://example.org/intellIoT#MachineType")
                .addEnum(machineTypeEnum)
                .build();

        Set<String> authenticationModeEnum = new HashSet<>();
        Collections.addAll(authenticationModeEnum, "unauthenticated", "BasicAuth");
        StringSchema authenticationMode = new StringSchema.Builder()
                .addSemanticType("http://example.org/intellIoT#AuthenticationMode")
                .addEnum(authenticationModeEnum)
                .build();


        ObjectSchema engravingJob = new ObjectSchema.Builder()
                .addSemanticType("http://example.org/intellIoT#EngravingJob")
                .addProperty("x", new NumberSchema.Builder()
                        .addSemanticType("http://example.org/intellIoT#XCoordinate")
                        .build())
                .addProperty("y", new NumberSchema.Builder()
                        .addSemanticType("http://example.org/intellIoT#YCoordinate")
                        .build())
                .addProperty("width", new NumberSchema.Builder()
                        .addSemanticType("http://example.org/intellIoT#Width")
                        .build())
                .addProperty("height", new NumberSchema.Builder()
                        .addSemanticType("http://example.org/intellIoT#Height")
                        .build())
                .addProperty("angle", new NumberSchema.Builder()
                        .addSemanticType("http://example.org/intellIoT#Angle")
                        .build())
                .addProperty("lineColorCut", new NumberSchema.Builder()
                        .addSemanticType("http://example.org/intellIoT#LineColorCut")
                        .build())
                .addProperty("lineColorEngrave", new NumberSchema.Builder()
                        .addSemanticType("http://example.org/intellIoT#LineColorEngrave")
                        .build())
                .addProperty("lineColorEngrave", new NumberSchema.Builder()
                        .addSemanticType("http://example.org/intellIoT#LineColorEngrave")
                        .build())
                .addProperty("material", machineType)
                .addProperty("thickness", new NumberSchema.Builder()
                        .addSemanticType("http://example.org/intellIoT#Thickness")
                        .build())
                .addRequiredProperties("x", "y", "material", "thickness")
                .build();

        ObjectSchema engraverStatus = new ObjectSchema.Builder()
                .addSemanticType("http://example.org/intellIoT#EngraverStatus")
                .addProperty("state", engraverState)
                .addProperty("millisToFinished", new NumberSchema.Builder()
                        .addSemanticType("http://example.org/intellIoT#MillisToFinished")
                        .build())
                .build();

        ObjectSchema engraverSpec = new ObjectSchema.Builder()
                .addSemanticType("http://example.org/intellIoT#EngraverMachineSpecification")
                .addProperty("model", new StringSchema.Builder()
                        .addSemanticType("http://example.org/intellIoT#Model")
                        .build())
                .addProperty("type", machineType)
                .addProperty("workingAreaWidth", new NumberSchema.Builder()
                        .addSemanticType("http://example.org/intellIoT#WorkingAreaWidth")
                        .build())
                .addProperty("workingAreaHeight", new NumberSchema.Builder()
                        .addSemanticType("http://example.org/intellIoT#WorkingAreaHeight")
                        .build())
                .build();

        ObjectSchema engraverConfiguration = new ObjectSchema.Builder()
                .addSemanticType("http://example.org/intellIoT#EngraverServiceConfiguration")
                .addProperty("host", new StringSchema.Builder()
                        .addSemanticType("http://example.org/intellIoT#Host")
                        .build())
                .addProperty("authenticationMode", authenticationMode)
                .build();


        //Properties
        properties.add(new PropertyAffordance.Builder("spec", readSpecForm)
                .addSemanticType("http://example.org/intellIoT#ReadEngraverMachineSpecification")
                .addDataSchema(engraverSpec)
                .build());

        properties.add(new PropertyAffordance.Builder("configuration", readConfForm)
                .addSemanticType("http://example.org/intellIoT#ReadEngraverServiceConfiguration")
                .addDataSchema(engraverConfiguration)
                .build());

        properties.add(new PropertyAffordance.Builder("job", readJobForm)
                .addSemanticType("http://example.org/intellIoT#ReadEngravingJobStatus")
                .addDataSchema(engraverStatus)
                .build());

        //Actions
        actions.add(new ActionAffordance.Builder("configuration", invokeConfForm)
                .addSemanticType("http://example.org/intellIoT#SetEngraverServiceConfiguration")
                .addInputSchema(engraverConfiguration)
                .addOutputSchema(engraverConfiguration)
                .build());

        actions.add(new ActionAffordance.Builder("invokeJob", invokeJobForm)
                .addSemanticType("http://example.org/intellIoT#CreateEngravingJob")
                .addInputSchema(new ObjectSchema.Builder()
                        .addSemanticType("http://example.org/intellIoT#EngravingJobWithImage")
                        .addProperty("image", new StringSchema.Builder()
                                .addSemanticType("http://example.org/intellIoT#Image")
                                .build())
                        .addProperty("jobDescription", engravingJob)
                        .build())
                .addOutputSchema(engraverStatus)
                .build());

        actions.add(new ActionAffordance.Builder("deleteJob", deleteJobForm)
                .addSemanticType("http://example.org/intellIoT#DeleteEngravingJob")
                .addOutputSchema(engraverStatus)
                .build());


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
