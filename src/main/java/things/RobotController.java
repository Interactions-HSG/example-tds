package things;

import ch.unisg.ics.interactions.wot.td.ThingDescription;
import ch.unisg.ics.interactions.wot.td.affordances.ActionAffordance;
import ch.unisg.ics.interactions.wot.td.affordances.Form;
import ch.unisg.ics.interactions.wot.td.schemas.BooleanSchema;
import ch.unisg.ics.interactions.wot.td.schemas.DataSchema;
import ch.unisg.ics.interactions.wot.td.schemas.ObjectSchema;
import ch.unisg.ics.interactions.wot.td.schemas.StringSchema;

public class RobotController extends Thing {

    RobotController(String baseURI, String relativeURI, String title) {
        super(baseURI, relativeURI, title);
        Form getOperatorForm = new Form.Builder(baseURI+"operator")
                .setMethodName("GET")
                .build();
        DataSchema operatorSchema = new ObjectSchema.Builder()
                .addProperty("name", new StringSchema.Builder().build())
                .addProperty("email", new StringSchema.Builder().build())
                .addRequiredProperties("name", "email")
                .build();
        ActionAffordance getOperator = new ActionAffordance.Builder("getOperator", getOperatorForm)
                .addOutputSchema(operatorSchema)
                .build();
        actions.add(getOperator);

        Form postOperatorForm = new Form.Builder(baseURI+"operator")
                .build();
        ActionAffordance postOperator = new ActionAffordance.Builder("postOperator", postOperatorForm)
                .addInputSchema(operatorSchema)
                .build();
        actions.add(postOperator);

        Form deleteOperatorForm = new Form.Builder(baseURI+"operator")
                .setMethodName("DELETE")
                .build();
        ActionAffordance deleteOperator = new ActionAffordance.Builder("deleteOperator", deleteOperatorForm)
                .build();
        actions.add(deleteOperator);

        Form getHandleForm = new Form.Builder(baseURI+"handle")
                .setMethodName("GET")
                .build();
        DataSchema handleSchema = new ObjectSchema.Builder()
                .addProperty("robot", new StringSchema.Builder().build())
                .addProperty("camera", new StringSchema.Builder().build())
                .addRequiredProperties("robot", "camera")
                .build();
        ActionAffordance getHandle = new ActionAffordance.Builder("getHandle", getHandleForm)
                .addOutputSchema(handleSchema)
                .build();
        actions.add(getHandle);

        Form getStatusForm = new Form.Builder(baseURI+"status")
                .setMethodName("GET")
                .build();
        DataSchema statusSchema = new ObjectSchema.Builder()
                .addProperty("inMovement", new BooleanSchema.Builder().build())
                .addRequiredProperties("inMovement")
                .build();
        ActionAffordance getStatus = new ActionAffordance.Builder("getStatus", getStatusForm)
                .addOutputSchema(statusSchema)
                .build();
        actions.add(getStatus);

        Form getPoseForm = new Form.Builder(baseURI+"pose")
                .setMethodName("GET")
                .build();
        DataSchema poseSchema = new ObjectSchema.Builder()
                .build();
        ActionAffordance getPose = new ActionAffordance.Builder("getPose", getPoseForm)
                .addOutputSchema(poseSchema)
                .build();
        actions.add(getPose);

        Form putPoseForm = new Form.Builder(baseURI + "pose")
                .setMethodName("PUT")
                .build();
        ActionAffordance putPose = new ActionAffordance.Builder("putPose", putPoseForm)
                .addInputSchema(poseSchema)
                .build();
        actions.add(putPose);

        Form getGripperForm = new Form.Builder(baseURI+"gripper")
                .setMethodName("GET")
                .build();

        DataSchema gripperSchema = new ObjectSchema.Builder()
                .addProperty("status", new StringSchema.Builder().build())
                .build();
        ActionAffordance getGripper = new ActionAffordance.Builder("getGripper", getGripperForm)
                .addOutputSchema(gripperSchema)
                .build();

        actions.add(getGripper);

        Form putGripperForm = new Form.Builder(baseURI+"gripper")
                .setMethodName("PUT")
                .build();

        ActionAffordance putGripper = new ActionAffordance.Builder("putGripper", putGripperForm)
                .addInputSchema(gripperSchema)
                .build();
        actions.add(putGripper);




    }

    @Override
    public ThingDescription exposeTD() {
        return new ThingDescription.Builder(title)
                .addThingURI(relativeURI)
                .addBaseURI(baseURI)
                .addActions(actions)
                .build();
    }
}
