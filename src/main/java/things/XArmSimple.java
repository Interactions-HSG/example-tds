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
import ch.unisg.ics.interactions.wot.td.security.TokenBasedSecurityScheme;
import ch.unisg.ics.interactions.wot.td.vocabularies.HTV;
import ch.unisg.ics.interactions.wot.td.vocabularies.TD;
import vocabularies.CHERRY;
import vocabularies.FOAF;

public class XArmSimple extends Thing {

    public XArmSimple(String baseURI, String relativeURI, String title) {
        super(baseURI, relativeURI, title);
        this.namespaces.put("htv", HTV.PREFIX);
        this.namespaces.put("cherrybot", CHERRY.PREFIX);
        this.namespaces.put("foaf", FOAF.PREFIX);
        this.namespaces.put("hmas", "https://purl.org/hmas/");
        this.namespaces.put("rdfs", "http://www.w3.org/2000/01/rdf-schema#");

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

        Form putGripperForm = new Form.Builder(baseURI + "gripper")
                .setMethodName("PUT")
                .build();
        Form deleteOperatorForm = new Form.Builder(baseURI + "operator/{token}")
                .setMethodName("DELETE")
                .build();

        //Schemas
        ObjectSchema operatorSchema = new ObjectSchema.Builder()
                .addSemanticType(CHERRY.operatorSchema)
                .addProperty("name", new StringSchema.Builder()
                        .addSemanticType(FOAF.name)
                        .build())
                .addProperty("email", new StringSchema.Builder()
                        .addSemanticType(FOAF.mbox)
                        .build())
                .addRequiredProperties("name", "email")
                .build();

        ObjectSchema operatorWithTokenSchema = new ObjectSchema.Builder()
                .addSemanticType(CHERRY.operatorWithTokenSchema)
                .addProperty("name", new StringSchema.Builder()
                        .addSemanticType(FOAF.name)
                        .build())
                .addProperty("email", new StringSchema.Builder()
                        .addSemanticType(FOAF.mbox)
                        .build())
                .addProperty("token", new StringSchema.Builder()
                        .addSemanticType(CHERRY.operatorToken)
                        .build())
                .addRequiredProperties("name", "email", "token")
                .build();

        ObjectSchema coordinatesSchema = new ObjectSchema.Builder()
                .addSemanticType(CHERRY.coordinatesSchema)
                .addProperty("x", new NumberSchema.Builder()
                        .addSemanticType(CHERRY.xCoordinate)
                        .addMinimum((double) -720)
                        .addMaximum((double) 720)
                        .build())
                .addProperty("y", new NumberSchema.Builder()
                        .addSemanticType(CHERRY.yCoordinate)
                        .addMinimum((double) -720)
                        .addMaximum((double) 720)
                        .build())
                .addProperty("z", new NumberSchema.Builder()
                        .addSemanticType(CHERRY.zCoordinate)
                        .addMinimum((double) -178.3)
                        .addMaximum((double) 1010)
                        .build())
                .addRequiredProperties("x", "y", "z")
                .build();

        ObjectSchema rotationSchema = new ObjectSchema.Builder()
                .addSemanticType(CHERRY.rotationSchema)
                .addProperty("roll", new NumberSchema.Builder()
                        .addSemanticType(CHERRY.roll)
                        .addMinimum((double) -180)
                        .addMaximum((double) 180)
                        .build())
                .addProperty("pitch", new NumberSchema.Builder()
                        .addSemanticType(CHERRY.pitch)
                        .addMinimum((double) -180)
                        .addMaximum((double) 180)
                        .build())
                .addProperty("yaw", new NumberSchema.Builder()
                        .addSemanticType(CHERRY.yaw)
                        .addMinimum((double) -180)
                        .addMaximum((double) 180)
                        .build())
                .addRequiredProperties("roll", "pitch", "yaw")
                .build();

        ObjectSchema targetSchema = new ObjectSchema.Builder()
                .addSemanticType(CHERRY.tcpTargetSchema)
                .addProperty("coordinate", coordinatesSchema)
                .addProperty("rotation", rotationSchema)
                .addRequiredProperties("coordinate", "rotation")
                .build();

        ObjectSchema tcpMovementSchema = new ObjectSchema.Builder()
                .addSemanticType(CHERRY.tcpMovementSchema)
                .addProperty("target", targetSchema)
                .addProperty("speed", new IntegerSchema.Builder()
                        .addSemanticType(CHERRY.speed)
                        .addMinimum(10)
                        .addMaximum(400)
                        .build())
                .addRequiredProperties("target", "speed")
                .build();

        NumberSchema gripperSchema = new IntegerSchema.Builder()
                .addSemanticType(CHERRY.gripperIntegerSchema)
                .addMinimum(0)
                .addMaximum(800)
                .build();


        //Properties
        properties.add(new PropertyAffordance.Builder("currentUser",
                new Form.Builder(baseURI + "operator")
                        .addOperationType(TD.readProperty)
                        .build())
                .addSemanticType(CHERRY.operator)
                .addComment("Shows who is currently logged in and allowed to use the robot (name, email, and access token).")
                .addDataSchema(operatorWithTokenSchema)
                .build());

        properties.add(new PropertyAffordance.Builder("currentPosition",
                new Form.Builder(baseURI + "tcp")
                        .addOperationType(TD.readProperty)
                        .build())
                .addSemanticType(CHERRY.tcp)
                .addComment("Shows the robot’s current position and orientation in space.")
                .addDataSchema(targetSchema)
                .build());

        properties.add(new PropertyAffordance.Builder("goalPosition",
                new Form.Builder(baseURI + "tcp/target")
                        .addOperationType(TD.readProperty)
                        .build())
                .addSemanticType(CHERRY.tcpTarget)
                .addComment("Shows the position and orientation the robot is moving to. If this is the same as the current position, the robot is not moving.")
                .addDataSchema(targetSchema)
                .build());

        properties.add(new PropertyAffordance.Builder("handState",
                new Form.Builder(baseURI + "gripper")
                        .addOperationType(TD.readProperty)
                        .build())
                .addSemanticType(CHERRY.gripper)
                .addComment("Shows how open the robot’s hand is.")
                .addDataSchema(gripperSchema)
                .build());

        //Actions
        actions.add(new ActionAffordance.Builder("logIn", postOperatorForm)
                .addSemanticType(CHERRY.registerOperator)
                .addComment("Log in so you can use the robot (enter your name and email). After a successful login, the response will include a token in the Location header. This token is required to perform the robot’s movement and hand actions.")
                .addInputSchema(operatorSchema)
                .build());

        actions.add(new ActionAffordance.Builder("reset", initializeForm)
                .addSemanticType(CHERRY.initialize)
                .addComment("Moves the robot back to its starting position (reset).")
                .build());

        actions.add(new ActionAffordance.Builder("setGoalPosition", putTargetForm)
                .addSemanticType(CHERRY.setTarget)
                .addComment("Move the robot to a new position (X, Y, Z) and orientation (roll, pitch, yaw).")
                .addInputSchema(tcpMovementSchema)
                .build());

        actions.add(new ActionAffordance.Builder("setHandState", putGripperForm)
                .addSemanticType(CHERRY.setGripper)
                .addComment("Change how open the robot's hand is.")
                .addInputSchema(gripperSchema)
                .build());

        actions.add(new ActionAffordance.Builder("logOut", deleteOperatorForm)
                .addSemanticType(CHERRY.removeOperator)
                .addComment("Log out so someone else can use the robot.")
                .addUriVariable("token", new StringSchema.Builder()
                        .addSemanticType(CHERRY.operatorToken)
                        .build())
                .build());
    }

    @Override
    public ThingDescription exposeTD() {
        return new ThingDescription.Builder(title)
                .addThingURI(relativeURI)
                .addBaseURI(baseURI)
                .addSemanticType("https://purl.org/hmas/Artifact")
                .addProperties(properties)
                .addActions(actions)
                .addSecurityScheme("apikey", new APIKeySecurityScheme.Builder()
                        .addTokenName("Authentication")
                        .addTokenLocation(TokenBasedSecurityScheme.TokenLocation.HEADER)
                        .build())
                .build();
    }
}
