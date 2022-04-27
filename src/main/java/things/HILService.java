package things;

import ch.unisg.ics.interactions.wot.td.ThingDescription;
import ch.unisg.ics.interactions.wot.td.affordances.ActionAffordance;
import ch.unisg.ics.interactions.wot.td.affordances.Form;
import ch.unisg.ics.interactions.wot.td.affordances.PropertyAffordance;
import ch.unisg.ics.interactions.wot.td.schemas.*;
import ch.unisg.ics.interactions.wot.td.security.APIKeySecurityScheme;

import java.sql.Array;
import java.util.HashSet;
import java.util.Set;

public class HILService extends Thing{
    public HILService(String baseURI, String relativeURI, String title) {
        super(baseURI, relativeURI, title);


        DataSchema operatorDescriptionSchema = new ObjectSchema.Builder()
                .addProperty("operatorIpAddress", new StringSchema.Builder().build())
                .addProperty("operatorMacAddress", new StringSchema.Builder().build())
                .addRequiredProperties("operatorIpAddress", "operatorMacAddress")
                .build();

        DataSchema operatorSchema = new ObjectSchema.Builder()
                .addProperty("description", operatorDescriptionSchema)
                .addProperty("operatorId", new StringSchema.Builder().build())
                .addRequiredProperties("description", "operatorId")
                .build();

        Set<String> sessions = new HashSet<>();
        sessions.add("wood_piece_picking");
        DataSchema sessionTypeSchema = new StringSchema.Builder().addEnum(sessions).build();



        DataSchema hilSessionDescriptionSchema = new ObjectSchema.Builder()
                .addProperty("aiSessionId", new StringSchema.Builder().build())
                .addProperty("robotId", new StringSchema.Builder().build())
                .addProperty("cameraId", new StringSchema.Builder().build())
                .addProperty("sessionType", sessionTypeSchema)
                .addRequiredProperties("aiSessionId", "robotId", "cameraId", "sessionType")
                .build();


        Set<String> decisionTypes = new HashSet<>();
        decisionTypes.add("accepted");
        decisionTypes.add("standby");
        decisionTypes.add("rejected");
        DataSchema decisionTypeSchema = new StringSchema.Builder().addEnum(decisionTypes).build();

        DataSchema hilSessionHelpOfferingResponseSchema = new ObjectSchema.Builder()
                .addProperty("decision", decisionTypeSchema)
                .addProperty("subscribeTo", new StringSchema.Builder().build())
                .addRequiredProperties("decision", "subscribeTo")
                .build();

        Set<String> workResults = new HashSet<>();
        workResults.add("completed");
        workResults.add("unfinished");
        workResults.add("failed");
        DataSchema hilWorkResultSchema = new StringSchema.Builder().addEnum(workResults).build();

        Set<String> requestStatus = new HashSet<>();
        requestStatus.add("unstarted");
        requestStatus.add("preparing");
        requestStatus.add("started");
        requestStatus.add("finished");
        requestStatus.add("failed");
        requestStatus.add("expired");

        DataSchema hilRequestStatusSchema = new StringSchema.Builder().addEnum(requestStatus).build();

        DataSchema hilSessionSchema = new ObjectSchema.Builder()
                .addProperty("creationTime", new StringSchema.Builder().build())
                .addProperty("willExpireAt", new StringSchema.Builder().build())
                .addProperty("takenOverAt", new StringSchema.Builder().build())
                .addProperty("finishedAt", new StringSchema.Builder().build())
                .addProperty("status", hilRequestStatusSchema)
                .addProperty("workResult", hilWorkResultSchema)
                .addProperty("operatorId", new StringSchema.Builder().build())
                .addProperty("cameraId", new StringSchema.Builder().build())
                .addProperty("robotId", new StringSchema.Builder().build())
                .addProperty("hilAppIpAddress", new StringSchema.Builder().build())
                .addProperty("hyperMasCallbackUrl", new StringSchema.Builder().build())
                .addProperty("numberReassignments", new IntegerSchema.Builder().build())
                .addProperty("description", hilSessionDescriptionSchema)
                .addRequiredProperties("creationTime", "willExpireAt", "takenOverAt", "finishedAt", "status", "workResult", "operatorId", "cameraId", "robotId",
                        "hilAppIpAddress", "hyperMasCallbackUrl", "numberReassignments","description" )
                .build();


        Form getOperatorsForm = new Form.Builder(baseURI + "/operators").setMethodName("GET").build();

        PropertyAffordance getOperators = new PropertyAffordance.Builder("getOperators", getOperatorsForm)
                .addDataSchema(new ArraySchema.Builder().addItem(operatorSchema).build())
                .build();

        this.properties.add(getOperators);

        Form createOperatorForm = new Form.Builder(baseURI+"/operators").build();

        ActionAffordance createOperator = new ActionAffordance.Builder("createOperator", createOperatorForm)
                .addInputSchema(operatorDescriptionSchema)
                .addOutputSchema(operatorSchema)
                .build();

        this.actions.add(createOperator);

        Form getOperatorFromIdForm = new Form.Builder(baseURI + "/operators/{operatorId}")
                .setMethodName("GET")
                .build();

        PropertyAffordance getOperatorFromId = new PropertyAffordance.Builder("getOperatorFromId", getOperatorFromIdForm)
                .addUriVariable("operatorId", new StringSchema.Builder().build())
                .addDataSchema(operatorSchema)
                .build();

        this.properties.add(getOperatorFromId);

        Form deleteOperatorForm = new Form.Builder(baseURI + "/operators/{operatorId}")
                .setMethodName("DELETE")
                .build();

        ActionAffordance deleteOperator = new ActionAffordance.Builder("deleteOperator", deleteOperatorForm)
                .addUriVariable("operatorId", new StringSchema.Builder().build())
                .build();


        this.actions.add(deleteOperator);

        Form createSessionForm = new Form.Builder(baseURI + "/sessions")
                .build();

        ActionAffordance createSession = new ActionAffordance.Builder("createSession", createSessionForm)
                .addInputSchema(hilSessionDescriptionSchema)
                .addOutputSchema(hilSessionSchema)
                .build();

        this.actions.add(createSession);

        Form createSessionWithCallbackForm = new Form.Builder(baseURI + "/sessions{?callbackUrl}")
                .build();

        ActionAffordance createSessionWithCallback = new ActionAffordance.Builder("createSessionWithCallback", createSessionWithCallbackForm)
                .addInputSchema(hilSessionDescriptionSchema)
                .addOutputSchema(hilSessionSchema)
                .addUriVariable("callbackUrl", new StringSchema.Builder().build())
                .build();

        this.actions.add(createSessionWithCallback);

        Form getAllSessionsForm = new Form.Builder(baseURI + "/sessions")
                .setMethodName("GET")
                .build();

        PropertyAffordance getAllSessions = new PropertyAffordance.Builder("getAllSessions", getAllSessionsForm)
                .addDataSchema(new ArraySchema.Builder().addItem(hilSessionSchema).build())
                .build();

        this.properties.add(getAllSessions);

        Form deleteAllSessionsForm = new Form.Builder(baseURI + "/sessions")
                .setMethodName("DELETE")
                .build();

        ActionAffordance deleteAllSessions = new ActionAffordance.Builder("deleteAllSessions", deleteAllSessionsForm)
                .build();

        this.actions.add(deleteAllSessions);

        Form getSessionForm = new Form.Builder(baseURI + "/sessions/{sessionId}")
                .setMethodName("GET")
                .build();

        PropertyAffordance getSession = new PropertyAffordance.Builder("getSessions", getSessionForm)
                .addDataSchema(hilSessionSchema)
                .addUriVariable("sessionId", new StringSchema.Builder().build())
                .build();

        this.properties.add(getSession);

        Form deleteSessionForm = new Form.Builder(baseURI + "/sessions/{sessionId}")
                .setMethodName("DELETE")
                .build();

        ActionAffordance deleteSession = new ActionAffordance.Builder("deleteSession", deleteSessionForm)
                .addUriVariable("sessionId", new StringSchema.Builder().build())
                .build();

        this.actions.add(deleteSession);

        Form takeoverForm = new Form.Builder(baseURI + "/sessions/{aiSessionId}/takeover")
                .build();

        ActionAffordance takeover = new ActionAffordance.Builder("takeover", takeoverForm)
                .addInputSchema(operatorDescriptionSchema)
                .addOutputSchema(hilSessionHelpOfferingResponseSchema)
                .addUriVariable("aiSessionId", new StringSchema.Builder().build())
                .build();

        this.actions.add(takeover);

        Form rejectForm = new Form.Builder(baseURI + "/sessions/{aiSessionId}/reject")
                .build();

        ActionAffordance reject = new ActionAffordance.Builder("reject", rejectForm)
                .addUriVariable("aiSessionId", new StringSchema.Builder().build())
                .build();

        this.actions.add(reject);

        Form doneForm = new Form.Builder(baseURI + "/sessions/{aiSessionId}/done")
                .build();

        ActionAffordance done = new ActionAffordance.Builder("done", doneForm)
                .addInputSchema(hilWorkResultSchema)
                .addUriVariable("aiSessionId", new StringSchema.Builder().build())
                .build();

        this.actions.add(done);

        Form reassignForm = new Form.Builder(baseURI + "/sessions/{aiSessionId}/reassign")
                .build();

        ActionAffordance reassign = new ActionAffordance.Builder("reassign", reassignForm)
                .addUriVariable("aiSessionId", new StringSchema.Builder().build())
                .build();

        this.actions.add(reassign);



    }

    @Override
    public ThingDescription exposeTD() {
        return new ThingDescription.Builder(title)
                .addSecurityScheme(new APIKeySecurityScheme(APIKeySecurityScheme
                        .TokenLocation.HEADER, "X-API-Key"))
                .addThingURI(relativeURI)
                .addBaseURI(baseURI)
                .addActions(actions)
                .addProperties(properties)
                .addEvents(events)
                .build();
    }
}
