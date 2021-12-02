package things;

import ch.unisg.ics.interactions.wot.td.ThingDescription;
import ch.unisg.ics.interactions.wot.td.affordances.ActionAffordance;
import ch.unisg.ics.interactions.wot.td.affordances.Form;
import ch.unisg.ics.interactions.wot.td.affordances.PropertyAffordance;
import ch.unisg.ics.interactions.wot.td.schemas.IntegerSchema;
import ch.unisg.ics.interactions.wot.td.schemas.ObjectSchema;
import ch.unisg.ics.interactions.wot.td.schemas.StringSchema;
import ch.unisg.ics.interactions.wot.td.security.APIKeySecurityScheme;
import ch.unisg.ics.interactions.wot.td.vocabularies.HTV;
import ch.unisg.ics.interactions.wot.td.vocabularies.TD;
import vocabularies.FOAF;
import vocabularies.MINES;

public class PhantomXReactor extends Thing {

    public PhantomXReactor(String baseURI, String relativeURI, String title) {
        super(baseURI, relativeURI, title);
        this.namespaces.put("htv", HTV.PREFIX);
        this.namespaces.put("onto", MINES.PREFIX);
        this.namespaces.put("foaf", FOAF.PREFIX);
        this.namespaces.put("eve", "http://w3id.org/eve#");

        //Action forms
        Form resetForm = new Form.Builder(baseURI + "reset")
                .setMethodName("PUT")
                .build();
        Form baseForm = new Form.Builder(baseURI + "base")
                .setMethodName("PUT")
                .build();
        Form shoulderForm = new Form.Builder(baseURI + "shoulder")
                .setMethodName("PUT")
                .build();
        Form elbowForm = new Form.Builder(baseURI + "elbow")
                .setMethodName("PUT")
                .build();
        Form wristAngleForm = new Form.Builder(baseURI + "wrist/angle")
                .setMethodName("PUT")
                .build();
        Form wristRotationForm = new Form.Builder(baseURI + "wrist/rotation")
                .setMethodName("PUT")
                .build();
        Form gripperForm = new Form.Builder(baseURI + "gripper")
                .setMethodName("PUT")
                .build();
        Form userForm = new Form.Builder(baseURI + "user")
                .setMethodName("POST")
                .build();
        Form userDeleteForm = new Form.Builder(baseURI + "user")
                .setMethodName("DELETE")
                .build();

        //Properties
        ObjectSchema propObjectSchema = new ObjectSchema.Builder()
                .addProperty("name", new StringSchema.Builder()
                        .addSemanticType(MINES.jointName)
                        .build())
                .addProperty("value", new IntegerSchema.Builder()
                        .addSemanticType(MINES.jointValue)
                        .build())
                .build();

        ObjectSchema userObjectSchema = new ObjectSchema.Builder()
                .addProperty("name", new StringSchema.Builder()
                        .addSemanticType(FOAF.name)
                        .build())
                .addProperty("email", new IntegerSchema.Builder()
                        .addSemanticType(FOAF.mbox)
                        .build())
                .build();

        ObjectSchema postureObjectSchema = new ObjectSchema.Builder()
                .addProperty("base", new StringSchema.Builder()
                        .addSemanticType(MINES.baseJointValue)
                        .build())
                .addProperty("shoulder", new IntegerSchema.Builder()
                        .addSemanticType(MINES.shoulderJointValue)
                        .build())
                .addProperty("elbow", new IntegerSchema.Builder()
                        .addSemanticType(MINES.elbowJointValue)
                        .build())
                .addProperty("wristAngle", new IntegerSchema.Builder()
                        .addSemanticType(MINES.wristAngleJointValue)
                        .build())
                .addProperty("wristRotation", new IntegerSchema.Builder()
                        .addSemanticType(MINES.wristRotationJointValue)
                        .build())
                .addProperty("gripper", new IntegerSchema.Builder()
                        .addSemanticType(MINES.gripperJointValue)
                        .build())
                .build();

        properties.add(new PropertyAffordance.Builder("base",
                new Form.Builder(baseURI + "base")
                        .addOperationType(TD.readProperty).build())
                .addSemanticType(MINES.base)
                .addTitle("Read Base State")
                .addDataSchema(propObjectSchema)
                .build());

        properties.add(new PropertyAffordance.Builder("shoulder",
                new Form.Builder(baseURI + "shoulder")
                        .addOperationType(TD.readProperty).build())
                .addSemanticType(MINES.shoulder)
                .addTitle("Read Shoulder Angle")
                .addDataSchema(propObjectSchema)
                .build());

        properties.add(new PropertyAffordance.Builder("elbow",
                new Form.Builder(baseURI + "elbow")
                        .addOperationType(TD.readProperty).build())
                .addSemanticType(MINES.elbow)
                .addTitle("Read Elbow Angle")
                .addDataSchema(propObjectSchema)
                .build());

        properties.add(new PropertyAffordance.Builder("wristAngle",
                new Form.Builder(baseURI + "wrist/angle")
                        .addOperationType(TD.readProperty).build())
                .addSemanticType(MINES.wristAngle)
                .addTitle("Read Wrist Angle")
                .addDataSchema(propObjectSchema)
                .build());

        properties.add(new PropertyAffordance.Builder("wristRotation",
                new Form.Builder(baseURI + "wrist/rotation")
                        .addOperationType(TD.readProperty).build())
                .addSemanticType(MINES.wristRotation)
                .addTitle("Read Wrist Rotation")
                .addDataSchema(propObjectSchema)
                .build());

        properties.add(new PropertyAffordance.Builder("gripper",
                new Form.Builder(baseURI + "gripper").build())
                .addSemanticType(MINES.gripper)
                .addTitle("Read Gripper")
                .addDataSchema(propObjectSchema)
                .build());

        properties.add(new PropertyAffordance.Builder("posture",
                new Form.Builder(baseURI + "posture")
                        .addOperationType(TD.readProperty).build())
                .addSemanticType(MINES.posture)
                .addTitle("Read Posture Information")
                .addDataSchema(postureObjectSchema)
                .build());

        properties.add(new PropertyAffordance.Builder("user",
                new Form.Builder(baseURI + "user")
                        .addOperationType(TD.readProperty).build())
                .addSemanticType(FOAF.agent)
                .addTitle("Read User Information")
                .addDataSchema(userObjectSchema)
                .build());


        //Actions
        actions.add(new ActionAffordance.Builder("reset", resetForm)
                .addTitle("Reset")
                .addSemanticType(MINES.reset)
                .build());

        actions.add(new ActionAffordance.Builder("setBase", baseForm)
                .addTitle("Set Base")
                .addSemanticType(MINES.setBase)
                .addInputSchema(new ObjectSchema.Builder()
                        .addSemanticType(MINES.baseJoint)
                        .addProperty("value", new IntegerSchema.Builder()
                                .addMinimum(512)
                                .addMaximum(1023)
                                .build())
                        .addRequiredProperties("value")
                        .build())
                .build());

        actions.add(new ActionAffordance.Builder("setShoulder", shoulderForm)
                .addTitle("Set Shoulder")
                .addSemanticType(MINES.setShoulder)
                .addInputSchema(new ObjectSchema.Builder()
                        .addSemanticType(MINES.shoulderJoint)
                        .addProperty("value", new IntegerSchema.Builder()
                                .addMinimum(205)
                                .addMaximum(810)
                                .build())
                        .addRequiredProperties("value")
                        .build())
                .build());

        actions.add(new ActionAffordance.Builder("setElbow", elbowForm)
                .addTitle("Set Elbow")
                .addSemanticType(MINES.setElbow)
                .addInputSchema(new ObjectSchema.Builder()
                        .addSemanticType(MINES.elbowJoint)
                        .addProperty("value", new IntegerSchema.Builder()
                                .addMinimum(210)
                                .addMaximum(900)
                                .build())
                        .addRequiredProperties("value")
                        .build())
                .build());

        actions.add(new ActionAffordance.Builder("setWristAngle", wristAngleForm)
                .addTitle("Set Wrist Angle")
                .addSemanticType(MINES.setWristAngle)
                .addInputSchema(new ObjectSchema.Builder()
                        .addSemanticType(MINES.wristAngleJoint)
                        .addProperty("value", new IntegerSchema.Builder()
                                .addMinimum(200)
                                .addMaximum(830)
                                .build())
                        .addRequiredProperties("value")
                        .build())
                .build());

        actions.add(new ActionAffordance.Builder("setWristRotation", wristRotationForm)
                .addTitle("Set Wrist Rotation")
                .addSemanticType(MINES.setWristAngle)
                .addInputSchema(new ObjectSchema.Builder()
                        .addSemanticType(MINES.wristRotateJoint)
                        .addProperty("value", new IntegerSchema.Builder()
                                .addMinimum(0)
                                .addMaximum(1023)
                                .build())
                        .addRequiredProperties("value")
                        .build())
                .build());

        actions.add(new ActionAffordance.Builder("setGripper", gripperForm)
                .addTitle("Set Gripper")
                .addSemanticType(MINES.setGripper)
                .addInputSchema(new ObjectSchema.Builder()
                        .addSemanticType(MINES.gripperJoint)
                        .addProperty("value", new IntegerSchema.Builder()
                                .addMinimum(0)
                                .addMaximum(512)
                                .build())
                        .addRequiredProperties("value")
                        .build())
                .build());

        actions.add(new ActionAffordance.Builder("logIn", userForm)
                .addTitle("Log In")
                .addSemanticType(MINES.logIn)
                .addInputSchema(new ObjectSchema.Builder()
                        .addSemanticType(FOAF.agent)
                        .addProperty("name", new StringSchema.Builder()
                                .build())
                        .addProperty("email", new StringSchema.Builder()
                                .build())
                        .addRequiredProperties("name", "email")
                        .build())
                .build());

        // [Andrei] this form cannot be properly implemented with the current version of wot-td-java,
        // we need support for URI variables. I'm commenting this out to avoid confusion
        // TODO: implement after we add URI variables to wot-td-java
//        actions.add(new ActionAffordance.Builder("logOut", userDeleteForm)
//                .addTitle("Log Out")
//                .addSemanticType(MINES.logOut)
//                .build());
    }

    @Override
    public ThingDescription exposeTD() {
        return new ThingDescription.Builder(title)
                .addThingURI(relativeURI)
                .addBaseURI(baseURI)
                .addSemanticType(MINES.phantomX)
                .addSemanticType("http://w3id.org/eve#Artifact")
                .addSecurityScheme(new APIKeySecurityScheme(APIKeySecurityScheme.TokenLocation.HEADER, "X-API-Key"))
                .addProperties(properties)
                .addActions(actions)
                .build();
    }
}
