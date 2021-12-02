package things;

import ch.unisg.ics.interactions.wot.td.ThingDescription;
import ch.unisg.ics.interactions.wot.td.affordances.ActionAffordance;
import ch.unisg.ics.interactions.wot.td.affordances.Form;
import ch.unisg.ics.interactions.wot.td.affordances.PropertyAffordance;
import ch.unisg.ics.interactions.wot.td.schemas.IntegerSchema;
import ch.unisg.ics.interactions.wot.td.schemas.NumberSchema;
import ch.unisg.ics.interactions.wot.td.schemas.ObjectSchema;
import ch.unisg.ics.interactions.wot.td.schemas.StringSchema;
import ch.unisg.ics.interactions.wot.td.security.APIKeySecurityScheme;
import ch.unisg.ics.interactions.wot.td.vocabularies.HTV;
import ch.unisg.ics.interactions.wot.td.vocabularies.TD;
import vocabularies.CHERRY;

public class XArm extends Thing {

    public XArm(String baseURI, String relativeURI, String title) {
        super(baseURI, relativeURI, title);
        this.namespaces.put("htv", HTV.PREFIX);
        this.namespaces.put("cherrybot", CHERRY.PREFIX);

        //Action forms
        Form postOperatorForm = new Form.Builder(baseURI + "operator")
                .setMethodName("POST")
                .build();

        Form initializeForm = new Form.Builder(baseURI + "initialize")
                .setMethodName("PUT")
                .build();

        Form putTargetForm = new Form.Builder(baseURI + "tcp/target")
                .setMethodName("PUT")
                .build();

        Form putGripperForm = new Form.Builder(baseURI + "tcp/gripper")
                .setMethodName("PUT")
                .build();

        //Schemas
        ObjectSchema operatorSchema = new ObjectSchema.Builder()
                .addSemanticType(CHERRY.operator)
                .addProperty("name", new StringSchema.Builder()
                        .build())
                .addProperty("email", new StringSchema.Builder()
                        .build())
                .addRequiredProperties("name", "email")
                .build();

        ObjectSchema operatorWithTokenSchema = new ObjectSchema.Builder()
                .addSemanticType(CHERRY.operatorWithToken)
                .addProperty("name", new StringSchema.Builder()
                        .build())
                .addProperty("email", new StringSchema.Builder()
                        .build())
                .addProperty("token", new StringSchema.Builder()
                        .build())
                .addRequiredProperties("name", "email", "token")
                .build();

        ObjectSchema coordinatesSchema = new ObjectSchema.Builder()
                .addSemanticType(CHERRY.coordinates)
                .addProperty("x", new NumberSchema.Builder()
                        .addMinimum((double) -720)
                        .addMaximum((double) 720)
                        .build())
                .addProperty("y", new NumberSchema.Builder()
                        .addMinimum((double) -720)
                        .addMaximum((double) 720)
                        .build())
                .addProperty("z", new NumberSchema.Builder()
                        .addMinimum((double) -178.3)
                        .addMaximum((double) 1010)
                        .build())
                .addRequiredProperties("x", "y", "z")
                .build();

        ObjectSchema rotationSchema = new ObjectSchema.Builder()
                .addSemanticType(CHERRY.rotation)
                .addProperty("roll", new NumberSchema.Builder()
                        .addMinimum((double) -180)
                        .addMaximum((double) 180)
                        .build())
                .addProperty("pitch", new NumberSchema.Builder()
                        .addMinimum((double) -180)
                        .addMaximum((double) 180)
                        .build())
                .addProperty("yaw", new NumberSchema.Builder()
                        .addMinimum((double) -180)
                        .addMaximum((double) 180)
                        .build())
                .addRequiredProperties("roll", "pitch", "yaw")
                .build();

        ObjectSchema targetSchema = new ObjectSchema.Builder()
                .addSemanticType(CHERRY.target)
                .addProperty("coordinate", coordinatesSchema)
                .addProperty("rotation", rotationSchema)
                .addRequiredProperties("coordinate", "rotation")
                .build();

        ObjectSchema tcpMovementSchema = new ObjectSchema.Builder()
                .addSemanticType(CHERRY.tcpMovement)
                .addProperty("target", targetSchema)
                .addProperty("speed", new IntegerSchema.Builder()
                        .addMinimum(10)
                        .addMaximum(400)
                        .build())
                .addRequiredProperties("target", "speed")
                .build();

        NumberSchema gripperSchema = new IntegerSchema.Builder()
                .addMinimum(0)
                .addMaximum(800)
                .build();


        //Properties
        properties.add(new PropertyAffordance.Builder("oeprator",
                new Form.Builder(baseURI + "operator")
                        .addOperationType(TD.readProperty)
                        .build())
                .addSemanticType(CHERRY.getOperator)
                .addDataSchema(operatorWithTokenSchema)
                .build());

        properties.add(new PropertyAffordance.Builder("tcp",
                new Form.Builder(baseURI + "tcp")
                        .addOperationType(TD.readProperty)
                        .build())
                .addSemanticType(CHERRY.getTCP)
                .addDataSchema(targetSchema)
                .build());

        properties.add(new PropertyAffordance.Builder("tcpTarget",
                new Form.Builder(baseURI + "tcp/target")
                        .addOperationType(TD.readProperty)
                        .build())
                .addSemanticType(CHERRY.getTarget)
                .addDataSchema(targetSchema)
                .build());

        properties.add(new PropertyAffordance.Builder("gripper",
                new Form.Builder(baseURI + "gripper")
                        .addOperationType(TD.readProperty)
                        .build())
                .addSemanticType(CHERRY.getGripper)
                .addDataSchema(gripperSchema)
                .build());

        //Actions
        actions.add(new ActionAffordance.Builder("initialize", initializeForm)
                .addSemanticType(CHERRY.initialize)
                .build());

        actions.add(new ActionAffordance.Builder("postOperator", postOperatorForm)
                .addSemanticType(CHERRY.postOperator)
                .addInputSchema(operatorSchema)
                .build());

        actions.add(new ActionAffordance.Builder("putTarget", putTargetForm)
                .addSemanticType(CHERRY.putTarget)
                .addInputSchema(tcpMovementSchema)
                .build());

        actions.add(new ActionAffordance.Builder("putGripper", putGripperForm)
                .addSemanticType(CHERRY.putGripper)
                .addInputSchema(gripperSchema)
                .build());
    }

    @Override
    public ThingDescription exposeTD() {
        return new ThingDescription.Builder(title)
                .addThingURI(relativeURI)
                .addBaseURI(baseURI)
                .addSemanticType("http://w3id.org/eve#Artifact")
                .addProperties(properties)
                .addActions(actions)
                .addSecurityScheme(new APIKeySecurityScheme(APIKeySecurityScheme.TokenLocation.HEADER, "Authentication"))
                .build();
    }
}
