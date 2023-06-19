package things;

import ch.unisg.ics.interactions.wot.td.ThingDescription;
import ch.unisg.ics.interactions.wot.td.affordances.ActionAffordance;
import ch.unisg.ics.interactions.wot.td.affordances.EventAffordance;
import ch.unisg.ics.interactions.wot.td.affordances.Form;
import ch.unisg.ics.interactions.wot.td.affordances.PropertyAffordance;
import ch.unisg.ics.interactions.wot.td.schemas.*;
import ch.unisg.ics.interactions.wot.td.security.APIKeySecurityScheme;
import ch.unisg.ics.interactions.wot.td.vocabularies.TD;
import vocabularies.INTELLIOT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class RobotController2 extends Thing{
    public RobotController2(String baseURI, String relativeURI, String title) {
        super(baseURI, relativeURI, title);

        // Read handles form
        Form getHandleForm = new Form.Builder(baseURI + "/handle")
                .setMethodName("GET")
                .addOperationType(TD.readProperty)
                .build();

        // Read status form
        Form getStatusForm = new Form.Builder(baseURI + "/status")
                .setMethodName("GET")
                .addOperationType(TD.readProperty)
                .build();

        // Read pose forms
        Form getPoseJointForm = new Form.Builder(baseURI + "/pose")
                .setMethodName("GET")
                .addOperationType(TD.readProperty)
                .setContentType("application/joint+json")
                .build();
        Form getPoseTcpForm = new Form.Builder(baseURI + "/pose")
                .setMethodName("GET")
                .addOperationType(TD.readProperty)
                .setContentType("application/tcp+json")
                .build();
        Form getPoseNamedPoseForm = new Form.Builder(baseURI + "/pose")
                .setMethodName("GET")
                .addOperationType(TD.readProperty)
                .setContentType("application/namedpose+json")
                .build();
        Form getPoseAiForm = new Form.Builder(baseURI + "/pose")
                .setMethodName("GET")
                .addOperationType(TD.readProperty)
                .setContentType("application/ai+json")
                .build();

        // Read gripper form
        Form getGripperForm = new Form.Builder(baseURI + "/gripper")
                .setMethodName("GET")
                .addOperationType(TD.readProperty)
                .build();

        // Set gripper form
        Form putGripperForm = new Form.Builder(baseURI + "/gripper")
                .setMethodName("PUT")
                .build();

        // Event forms
        Form putPoseJointForm = new Form.Builder(baseURI + "/pose")
                .setMethodName("PUT")
                .setContentType("application/joint+json")
                .build();
        Form putPoseTcpForm = new Form.Builder(baseURI + "/pose")
                .setMethodName("PUT")
                .setContentType("application/tcp+json")
                .build();
        Form putPoseNamedPoseForm = new Form.Builder(baseURI + "/pose")
                .setMethodName("PUT")
                .setContentType("application/namedpose+json")
                .build();
        Form putPoseAiForm = new Form.Builder(baseURI + "/pose")
                .setMethodName("PUT")
                .setContentType("application/ai+json")
                .build();

        Form putManualResetForm = new Form.Builder(baseURI + "/manual_reset")
                .setMethodName("PUT")
                .build();


        // Handle schema
        DataSchema handleSchema = new ObjectSchema.Builder()
                .addSemanticType(INTELLIOT.handle)
                .addProperty("robot", new StringSchema.Builder()
                        .addSemanticType(INTELLIOT.robotHandle)
                        .build())
                .addProperty("camera", new StringSchema.Builder()
                        .addSemanticType(INTELLIOT.cameraHandle)
                        .build())
                .addRequiredProperties("robot", "camera")
                .build();

        // Status schema
        DataSchema statusSchema = new ObjectSchema.Builder()
                .addProperty("inMovement", new BooleanSchema.Builder()
                        .addSemanticType(INTELLIOT.inMovement)
                        .build())
                .addRequiredProperties("inMovement")
                .build();
        PropertyAffordance getStatus = new PropertyAffordance.Builder("status", getStatusForm)
                .addSemanticType(INTELLIOT.readStatus)
                .addDataSchema(statusSchema)
                .build();
        properties.add(getStatus);

        // Done schema
        DataSchema doneSchema = new ObjectSchema.Builder()
                .addSemanticType(INTELLIOT.poseNotification)
                .addProperty("message", new StringSchema.Builder()
                        .addSemanticType(INTELLIOT.poseUpdate)
                        .addEnum(new HashSet<>(Arrays.asList("completed", "aborted")))
                        .build())
                .addRequiredProperties("message")
                .build();

        // Joint coordinates schema
        DataSchema poseJointSchema = new ObjectSchema.Builder()
                .addSemanticType(INTELLIOT.jointCoordinates)
                .addSemanticType(INTELLIOT.poseValue)
                .addProperty("j1", new IntegerSchema.Builder()
                        .addSemanticType(INTELLIOT.joint1Coordinate).build())
                .addProperty("j2", new IntegerSchema.Builder()
                        .addSemanticType(INTELLIOT.joint2Coordinate).build())
                .addProperty("j3", new IntegerSchema.Builder()
                        .addSemanticType(INTELLIOT.joint3Coordinate).build())
                .addProperty("j4", new IntegerSchema.Builder()
                        .addSemanticType(INTELLIOT.joint4Coordinate).build())
                .addProperty("j5", new IntegerSchema.Builder()
                        .addSemanticType(INTELLIOT.joint5Coordinate).build())
                .addProperty("j6", new IntegerSchema.Builder()
                        .addSemanticType(INTELLIOT.joint6Coordinate).build())
                .addRequiredProperties("j1", "j2", "j3", "j4", "j5", "j6")
                .setContentMediaType("application/joint+json")
                .build();

        // TCP coordinates schema
        DataSchema poseTcpSchema = new ObjectSchema.Builder()
                .addSemanticType(INTELLIOT.tcpCoordinate)
                .addSemanticType(INTELLIOT.poseValue)
                .addProperty("x", new IntegerSchema.Builder()
                        .addSemanticType(INTELLIOT.xCoordinate).build())
                .addProperty("y", new IntegerSchema.Builder()
                        .addSemanticType(INTELLIOT.yCoordinate).build())
                .addProperty("z", new IntegerSchema.Builder()
                        .addSemanticType(INTELLIOT.zCoordinate).build())
                .addProperty("alpha", new IntegerSchema.Builder()
                        .addSemanticType(INTELLIOT.alphaCoordinate).build())
                .addProperty("beta", new IntegerSchema.Builder()
                        .addSemanticType(INTELLIOT.betaCoordinate).build())
                .addProperty("gamma", new IntegerSchema.Builder()
                        .addSemanticType(INTELLIOT.gammaCoordinate).build())
                .addRequiredProperties("x", "y", "z", "alpha", "beta", "gamma")
                .setContentMediaType("application/tcp+json")
                .build();

        // Named pose schema
        DataSchema poseNamedPoseSchema = new StringSchema.Builder()
                .addSemanticType(INTELLIOT.namedPose)
                .addSemanticType(INTELLIOT.poseValue)
                .addEnum(new HashSet<>(Arrays.asList("home", "milling_machine_pick", "milling_machine_place",
                        "engraver_load", "error")))
                .setContentMediaType("application/namedpose+json")
                .build();

        // AI coordinates schema
        DataSchema poseAiSchema = new ObjectSchema.Builder()
                .addSemanticType(INTELLIOT.aiCoordinate)
                .addSemanticType(INTELLIOT.poseValue)
                .addProperty("x", new IntegerSchema.Builder()
                        .addSemanticType(INTELLIOT.xCoordinate).build())
                .addProperty("y", new IntegerSchema.Builder()
                        .addSemanticType(INTELLIOT.yCoordinate).build())
                .addProperty("alpha", new IntegerSchema.Builder()
                        .addSemanticType(INTELLIOT.alphaCoordinate).build())
                .addRequiredProperties("x", "y", "alpha")
                .setContentMediaType("application/ai+json")
                .build();

        // Gripper schema
        DataSchema gripperSchema = new ObjectSchema.Builder()
                .addProperty("status", new StringSchema.Builder()
                        .addSemanticType(INTELLIOT.gripper)
                        .build())
                .build();


        // Read handle
        PropertyAffordance getHandle = new PropertyAffordance.Builder("readHandles", getHandleForm)
                .addSemanticType(INTELLIOT.readHandles)
                .addDataSchema(handleSchema)
                .build();
        properties.add(getHandle);

        // Read pose
        List<Form> readPoseForms = new ArrayList<>();
        readPoseForms.addAll(Arrays.asList(getPoseJointForm, getPoseTcpForm, getPoseNamedPoseForm, getPoseAiForm));
        PropertyAffordance getPose = new PropertyAffordance.Builder("readPose", readPoseForms)
                .addSemanticType(INTELLIOT.readPose)
                .addDataSchema(new ObjectSchema.Builder()
                        .addProperty("value",
                                new DataSchema.Builder().oneOf(poseJointSchema, poseTcpSchema,
                                        poseNamedPoseSchema, poseAiSchema).build())
                        .addRequiredProperties("value")
                        .build())
                .build();
        properties.add(getPose);

        // Read gripper
        PropertyAffordance getGripper = new PropertyAffordance.Builder("readGripper", getGripperForm)
                .addSemanticType(INTELLIOT.readGripper)
                .addDataSchema(gripperSchema)
                .build();
        properties.add(getGripper);

        // Set gripper
        ActionAffordance putGripper = new ActionAffordance.Builder("setGripper", putGripperForm)
                .addInputSchema(gripperSchema)
                .addSemanticType(INTELLIOT.setGripper)
                .build();
        actions.add(putGripper);

        ActionAffordance putManualReset = new ActionAffordance.Builder("putManualReset", putManualResetForm)
                .build();


        List<Form> setPoseForms = new ArrayList<>();
        setPoseForms.addAll(Arrays.asList(putPoseJointForm, putPoseTcpForm, putPoseNamedPoseForm, putPoseAiForm));
        /*EventAffordance putPose = new EventAffordance.Builder("setPose", setPoseForms)
                .addSemanticType(INTELLIOT.setPose)
                .addSemanticType(INTELLIOT.subscribePose)
                .addSubscriptionSchema(new ObjectSchema.Builder()
                        .addProperty("callback", new StringSchema.Builder()
                                .addSemanticType(INTELLIOT.callbackLocation)
                                .build())
                        .addProperty("value",
                                new DataSchema.Builder().oneOf(poseJointSchema, poseTcpSchema,
                                        poseNamedPoseSchema, poseAiSchema).build())
                        .addRequiredProperties("value")
                        .build())
                .addNotificationSchema(doneSchema)
                .build();
        events.add(putPose);*/

        ActionAffordance setAIPose = new ActionAffordance.Builder("setAIPose", putPoseAiForm)
                .addInputSchema(new ObjectSchema.Builder()
                        .addProperty("value", poseAiSchema)
                        .addProperty("callback", new StringSchema.Builder().build())
                        .addRequiredProperties("value","callback")
                        .build())

                .build();

        actions.add(setAIPose);

        ActionAffordance setNamedPose = new ActionAffordance.Builder("setNamedPose", putPoseNamedPoseForm)
                .addInputSchema(new ObjectSchema.Builder()
                        .addProperty("value", poseNamedPoseSchema)
                        .addProperty("callback", new StringSchema.Builder().build())
                        .addRequiredProperties("value","callback")
                        .build())
                .build();

        actions.add(setNamedPose);

        ActionAffordance setJointPose = new ActionAffordance.Builder("setJointPose", putPoseJointForm)
                .addInputSchema(new ObjectSchema.Builder()
                        .addProperty("value", poseJointSchema)
                        .addProperty("callback", new StringSchema.Builder().build())
                        .addRequiredProperties("value","callback")
                        .build())
                .build();

        actions.add(setJointPose);

        ActionAffordance setTcpPose = new ActionAffordance.Builder("setTcpPose", putPoseTcpForm)
                .addInputSchema(new ObjectSchema.Builder()
                        .addProperty("value", poseTcpSchema)
                        .addProperty("callback", new StringSchema.Builder().build())
                        .addRequiredProperties("value","callback")
                        .build())
                .build();

        actions.add(setTcpPose);

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
