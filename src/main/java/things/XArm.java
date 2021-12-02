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
import vocabularies.FOAF;

public class XArm extends Thing {

    public XArm(String baseURI, String relativeURI, String title) {
        super(baseURI, relativeURI, title);
        this.namespaces.put("htv", HTV.PREFIX);
        this.namespaces.put("cherrybot", CHERRY.PREFIX);
        this.namespaces.put("foaf", FOAF.PREFIX);
        this.namespaces.put("eve", "http://w3id.org/eve#");

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
        properties.add(new PropertyAffordance.Builder("operator",
                new Form.Builder(baseURI + "operator")
                        .addOperationType(TD.readProperty)
                        .build())
                .addSemanticType(CHERRY.operator)
                .addDataSchema(operatorWithTokenSchema)
                .build());

        properties.add(new PropertyAffordance.Builder("tcp",
                new Form.Builder(baseURI + "tcp")
                        .addOperationType(TD.readProperty)
                        .build())
                .addSemanticType(CHERRY.tcp)
                .addDataSchema(targetSchema)
                .build());

        properties.add(new PropertyAffordance.Builder("tcpTarget",
                new Form.Builder(baseURI + "tcp/target")
                        .addOperationType(TD.readProperty)
                        .build())
                .addSemanticType(CHERRY.tcpTarget)
                .addDataSchema(targetSchema)
                .build());

        properties.add(new PropertyAffordance.Builder("gripper",
                new Form.Builder(baseURI + "gripper")
                        .addOperationType(TD.readProperty)
                        .build())
                .addSemanticType(CHERRY.gripper)
                .addDataSchema(gripperSchema)
                .build());

        //Actions
        actions.add(new ActionAffordance.Builder("initialize", initializeForm)
                .addSemanticType(CHERRY.initialize)
                .build());

        actions.add(new ActionAffordance.Builder("registerOperator", postOperatorForm)
                .addSemanticType(CHERRY.registerOperator)
                .addInputSchema(operatorSchema)
                .build());

        actions.add(new ActionAffordance.Builder("setTarget", putTargetForm)
                .addSemanticType(CHERRY.setTarget)
                .addInputSchema(tcpMovementSchema)
                .build());

        actions.add(new ActionAffordance.Builder("setGripper", putGripperForm)
                .addSemanticType(CHERRY.setGripper)
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
