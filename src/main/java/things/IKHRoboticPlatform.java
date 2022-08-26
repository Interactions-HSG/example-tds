package things;

import ch.unisg.ics.interactions.wot.td.ThingDescription;
import ch.unisg.ics.interactions.wot.td.affordances.ActionAffordance;
import ch.unisg.ics.interactions.wot.td.affordances.Form;
import ch.unisg.ics.interactions.wot.td.affordances.PropertyAffordance;
import ch.unisg.ics.interactions.wot.td.schemas.ObjectSchema;
import ch.unisg.ics.interactions.wot.td.schemas.StringSchema;
import ch.unisg.ics.interactions.wot.td.security.APIKeySecurityScheme;

public class IKHRoboticPlatform extends Thing{
    public IKHRoboticPlatform(String baseURI, String relativeURI, String title) {
        super(baseURI, relativeURI, title);

        //DataSchemas

        ObjectSchema operatorSchema = new ObjectSchema.Builder()
                .addProperty("name", new StringSchema.Builder().build())
                .addProperty("email", new StringSchema.Builder().build())
                .addRequiredProperties("name", "email")
                .build();

        ObjectSchema operatorDelSchema = new ObjectSchema.Builder()
                .addProperty("token", new StringSchema.Builder().build())
                .addRequiredProperties("token")
                .build();

        ObjectSchema tcpSchema = new ObjectSchema.Builder()
                .addProperty("pos_x", new StringSchema.Builder().build())
                .addProperty("pos_y", new StringSchema.Builder().build())
                .addProperty("pos_z", new StringSchema.Builder().build())
                .addProperty("ori_x", new StringSchema.Builder().build())
                .addProperty("ori_y", new StringSchema.Builder().build())
                .addProperty("ori_z", new StringSchema.Builder().build())
                .addProperty("ori_w", new StringSchema.Builder().build())
                .addRequiredProperties("pos_x", "pos_y", "pos_z", "ori_x", "ori_y","ori_z","ori_w")
                .build();

        ObjectSchema velocitySchema = new ObjectSchema.Builder()
                .addProperty("velocity", new StringSchema.Builder().build())
                .addProperty("acceleration", new StringSchema.Builder().build())
                .addRequiredProperties("velocity", "acceleration")
                .build();

        ObjectSchema teleopSchema = new ObjectSchema.Builder()
                .addProperty("axis_linear", new StringSchema.Builder().build())
                .addProperty("axis_angular", new StringSchema.Builder().build())
                .addProperty("speed_up", new StringSchema.Builder().build())
                .addProperty("speed_down", new StringSchema.Builder().build())
                .addProperty("deadman", new StringSchema.Builder().build())
                .addRequiredProperties("axis_linear", "axis_angular", "speed_up", "speed_down", "deadman")
                .build();

        //Operator

        Form getOperatorForm = new Form.Builder(baseURI + "/Operator")
                .setMethodName("GET")
                .build();

        PropertyAffordance getOperator = new PropertyAffordance.Builder("getOperator", getOperatorForm)
                .build();

        properties.add(getOperator);

        Form postOperatorForm = new Form.Builder(baseURI + "/Operator")
                .build();

        ActionAffordance postOperator = new ActionAffordance.Builder("getOperator", postOperatorForm)
                .addInputSchema(operatorSchema)
                .build();

        actions.add(postOperator);

        Form deleteOperatorForm = new Form.Builder(baseURI + "/Operator")
                .setMethodName("delete")
                .build();

        ActionAffordance deleteOperator = new ActionAffordance.Builder("getOperator", deleteOperatorForm)
                .addInputSchema(operatorDelSchema)
                .build();

        actions.add(deleteOperator);

        //TCP

        Form getTCPForm = new Form.Builder(baseURI + "/tcp")
                .setMethodName("GET")
                .build();

        PropertyAffordance getTCP = new PropertyAffordance.Builder("getTCP", getTCPForm)
                .build();

        properties.add(getTCP);

        Form getTCPTargetForm = new Form.Builder(baseURI + "/tcp/target")
                .setMethodName("GET")
                .build();

        PropertyAffordance getTCPTarget = new PropertyAffordance.Builder("getTCPTarget", getTCPTargetForm)
                .build();

        properties.add(getTCPTarget);

        Form putTCPTargetForm = new Form.Builder(baseURI + "/tcp/target")
                .setMethodName("PUT")
                .build();

        ActionAffordance putTCPTarget = new ActionAffordance.Builder("putTCPTarget", putTCPTargetForm)
                .addInputSchema(tcpSchema)
                .build();

        actions.add(putTCPTarget);

        Form putTCPVelocityForm = new Form.Builder(baseURI + "/tcp/velocity")
                .setMethodName("PUT")
                .build();

        ActionAffordance putTCPVelocity = new ActionAffordance.Builder("putTCPVelocity", putTCPVelocityForm)
                .addInputSchema(velocitySchema)
                .build();

        actions.add(putTCPVelocity);

        //Joints

        Form initializeForm = new Form.Builder(baseURI + "initialize")
                .setMethodName("PUT")
                .build();

        ActionAffordance initialize = new ActionAffordance.Builder("initialize", initializeForm)
                .build();

        actions.add(initialize);

        Form lookbackForm = new Form.Builder(baseURI + "lookback")
                .setMethodName("PUT")
                .build();

        ActionAffordance lookback = new ActionAffordance.Builder("lookback", lookbackForm)
                .build();

        actions.add(lookback);

        Form getJointsForm = new Form.Builder(baseURI + "/joints")
                .setMethodName("GET")
                .build();

        PropertyAffordance getJoints = new PropertyAffordance.Builder("getJoints", getJointsForm)
                .build();

        properties.add(getJoints);

        Form getJointsTargetForm = new Form.Builder(baseURI + "/joints_target")
                .setMethodName("GET")
                .build();

        PropertyAffordance getJointsTarget = new PropertyAffordance.Builder("getJointsTarget", getJointsTargetForm)
                .build();

        properties.add(getJointsTarget);

        Form putJointsTargetForm = new Form.Builder(baseURI + "/joints_target")
                .setMethodName("PUT")
                .build();

        ActionAffordance putJointsTarget = new ActionAffordance.Builder("putJointsTarget", putJointsTargetForm)
                .build();

        actions.add(putJointsTarget);

        Form getJointsVelocityForm = new Form.Builder(baseURI + "/joints_velocity")
                .setMethodName("GET")
                .build();

        PropertyAffordance getJointsVelocity = new PropertyAffordance.Builder("getJointsVelocity", getJointsVelocityForm)
                .build();

        properties.add(getJointsVelocity);



        //Teleop

        Form putTeleopForm = new Form.Builder(baseURI + "/teleop")
                .setMethodName("PUT")
                .build();

        ActionAffordance putTeleop = new ActionAffordance.Builder("putTeleop", putTeleopForm)
                .addInputSchema(teleopSchema)
                .build();

        actions.add(putTeleop);

        //Spray

        Form putSprayForm = new Form.Builder(baseURI + "/spray")
                .setMethodName("PUT")
                .build();

        ActionAffordance putSpray = new ActionAffordance.Builder("putSpray", putSprayForm)
                .build();

        actions.add(putSpray);

        Form getSprayForm = new Form.Builder(baseURI + "/spray")
                .setMethodName("GET")
                .build();

        PropertyAffordance getSpray = new PropertyAffordance.Builder("getSpray", getSprayForm)
                .build();

        properties.add(getSpray);







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
