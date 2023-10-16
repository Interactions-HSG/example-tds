package things;

import ch.unisg.ics.interactions.wot.td.ThingDescription;
import ch.unisg.ics.interactions.wot.td.affordances.ActionAffordance;
import ch.unisg.ics.interactions.wot.td.affordances.Form;
import ch.unisg.ics.interactions.wot.td.affordances.PropertyAffordance;
import ch.unisg.ics.interactions.wot.td.schemas.DataSchema;
import ch.unisg.ics.interactions.wot.td.schemas.NumberSchema;
import ch.unisg.ics.interactions.wot.td.schemas.ObjectSchema;
import ch.unisg.ics.interactions.wot.td.schemas.StringSchema;

import ch.unisg.ics.interactions.wot.td.affordances.EventAffordance;
import ch.unisg.ics.interactions.wot.td.affordances.Form;
import ch.unisg.ics.interactions.wot.td.affordances.PropertyAffordance;
import ch.unisg.ics.interactions.wot.td.schemas.*;
import ch.unisg.ics.interactions.wot.td.security.APIKeySecurityScheme;

import java.util.HashSet;
import java.util.Set;

public class TractorController extends Thing{
    public TractorController(String baseURI, String relativeURI, String title) {
        super(baseURI, relativeURI, title);

        DataSchema numberSchema = new NumberSchema.Builder().build();

        DataSchema numberListSchema = new ArraySchema.Builder().addItem(numberSchema).build();

        DataSchema integerSchema = new IntegerSchema.Builder().build();

        Set<String> compassValues = new HashSet<>();
        compassValues.add("N");
        compassValues.add("NW");
        compassValues.add("NE");
        compassValues.add("W");
        compassValues.add("S");
        compassValues.add("SE");
        compassValues.add("SW");
        compassValues.add("E");
        DataSchema compassEnumSchema = new StringSchema.Builder().addEnum(compassValues).build();

        DataSchema driveCmdsSchema = new ObjectSchema.Builder()
                .addProperty("set_velocity", new NumberSchema.Builder().addMinimum((double) 0).addMaximum(2.0).build())
                .addProperty("set_curvature", new NumberSchema.Builder().addMinimum(-0.2).addMaximum(0.2).build())
                .addProperty("set_vehicle_state", new IntegerSchema.Builder().addMinimum(0).addMaximum(3).build())
                .addProperty("sys_cond", new IntegerSchema.Builder().addMinimum(0).addMaximum(4).build())
                .build();



        DataSchema validationErrorSchema = new ObjectSchema.Builder()
                .addProperty("loc", new ArraySchema.Builder().addItem(new StringSchema.Builder().build()).build())
                .addProperty("msg", new StringSchema.Builder().build())
                .addProperty("type", new StringSchema.Builder().build())
                .addRequiredProperties("loc", "msg","type")
                .build();

        DataSchema HTTPValidationErrorSchema = new ObjectSchema.Builder()
                .addProperty("detail", new ArraySchema.Builder().addItem(validationErrorSchema).build())
                .addRequiredProperties("detail")
                .build();

        DataSchema stampSchema = new ObjectSchema.Builder()
                .addProperty("secs", new IntegerSchema.Builder().build())
                .addProperty("nsecs", new IntegerSchema.Builder().build())
                .addRequiredProperties("secs", "nsecs")
                .build();

        DataSchema HeaderSchema = new ObjectSchema.Builder()
                .addProperty("seq", new IntegerSchema.Builder().build())
                .addProperty("stamp", stampSchema)
                .addProperty("frameId", new StringSchema.Builder().build())
                .build();

        DataSchema HeadingSchema = new ObjectSchema.Builder()
                .addProperty("header", HeaderSchema)
                .addProperty("heading", compassEnumSchema)
                .addProperty("angle", new IntegerSchema.Builder().build())
                .addRequiredProperties("header", "heading", "angle")
                .build();

        Set<String> implementsEnumsValues = new HashSet<>();
        implementsEnumsValues.add("plough");
        implementsEnumsValues.add("sprayer");
        implementsEnumsValues.add("mower");
        implementsEnumsValues.add("spreader");
        implementsEnumsValues.add("packer");
        implementsEnumsValues.add("undefined");

        DataSchema ImplementsEnumsSchema = new StringSchema.Builder()
                .addEnum(implementsEnumsValues)
                .build();



        DataSchema quaternionSchema = new ObjectSchema.Builder()
                .addProperty("x", new NumberSchema.Builder().build())
                .addProperty("y", new NumberSchema.Builder().build())
                .addProperty("z", new NumberSchema.Builder().build())
                 .addProperty("w", new NumberSchema.Builder().build())
                .build();

        DataSchema vector3Schema = new ObjectSchema.Builder()
                .addProperty("x", new NumberSchema.Builder().build())
                .addProperty("y", new NumberSchema.Builder().build())
                .addProperty("z", new NumberSchema.Builder().build())
                .build();

        DataSchema velocitySchema = new ObjectSchema.Builder()
                .addProperty("header", HeaderSchema)
                .addProperty("linear_velocity", new NumberSchema.Builder().build())
                .addProperty("angular_velocity", new NumberSchema.Builder().build())
                .addRequiredProperties("header")
                .build();

        DataSchema setValues = new ObjectSchema.Builder()
                .addProperty("was_succes", new BooleanSchema.Builder().build())
                .addProperty("description", new StringSchema.Builder().build())
                .build();

        Set<String> positionsEnumsValues = new HashSet<>();
        positionsEnumsValues.add("front");
        positionsEnumsValues.add("back");
        positionsEnumsValues.add("right");
        positionsEnumsValues.add("left");

        DataSchema PositionsEnumsSchema = new StringSchema.Builder()
                .addEnum(positionsEnumsValues)
                .build();

        Set<String> sysModeEnumValues = new HashSet<>();
        sysModeEnumValues.add("undefined");
        sysModeEnumValues.add("cancel");
        sysModeEnumValues.add("error");
        sysModeEnumValues.add("errack");
        sysModeEnumValues.add("estop");
        sysModeEnumValues.add("idle");
        sysModeEnumValues.add("mano");
        sysModeEnumValues.add("mano_mov");
        sysModeEnumValues.add("wpo");
        sysModeEnumValues.add("wpo_mov");
        sysModeEnumValues.add("wpo_fin");
        sysModeEnumValues.add("wpo_err");
        sysModeEnumValues.add("rto");
        sysModeEnumValues.add("rto_mov");
        sysModeEnumValues.add("rto_fin");
        sysModeEnumValues.add("rto_err");


        DataSchema sysModeEnumSchema = new StringSchema.Builder()
                .addEnum(sysModeEnumValues)
                .build();

        Set<String> tasksEnumValues = new HashSet<>();
        tasksEnumValues.add("undefined");
        tasksEnumValues.add("spray");
        tasksEnumValues.add("sing");
        tasksEnumValues.add("plough");
        tasksEnumValues.add("dance");


        DataSchema tasksEnumSchema = new StringSchema.Builder()
                .addEnum(tasksEnumValues)
                .build();

        DataSchema ImuSchema = new ObjectSchema.Builder()
                .addProperty("header", HeaderSchema)
                .addProperty("orientation", quaternionSchema)
                .addProperty("orientation_covariance",numberListSchema)
                .addProperty("angular_velocity", vector3Schema)
                .addProperty("angular_velocity_covariance", numberListSchema)
                .addProperty("linear_acceleration", vector3Schema)
                .addProperty("linear_acceleration_covariance", numberListSchema)
                .addRequiredProperties("header", "orientation","orientation_covariance", "angular_velocity",
                        "angular_velocity_covariance","linear_acceleration","linear_acceleration_covariance" )
                .build();

        DataSchema ManualOpCmdsSchema = new ObjectSchema.Builder()
                .addProperty("drive_cmds", driveCmdsSchema)
                .addProperty("task", tasksEnumSchema)
                .addRequiredProperties("drive_cmds", "task")
                .build();

        DataSchema MissionGoalSchema = new ArraySchema.Builder()
                .addItem(new ObjectSchema.Builder()
                        .addProperty("latitude", new NumberSchema.Builder()
                                .addMinimum(-90.0)
                                .addMinimum(90.0)
                                .build())
                        .addProperty("longitude", new NumberSchema.Builder()
                                .addMinimum(-180.0)
                                .addMinimum(180.0)
                                .build())
                        .addProperty("heading", new NumberSchema.Builder()
                                .addMinimum((double) 0)
                                .addMaximum(359.0)
                                .build())
                        .addProperty("task", tasksEnumSchema)
                        .build())
                .build();


        DataSchema NavSatSatus = new ObjectSchema.Builder()
                .addProperty("STATUS_NO_FIX", integerSchema)
                .addProperty("STATUS_FIX", integerSchema)
                .addProperty("STATUS_SBAS_FIX", integerSchema)
                .addProperty("STATUS_GBAS_FIX", integerSchema)
                .addProperty("SERVICE_GPS", integerSchema)
                .addProperty("SERVICE_GLONASS", integerSchema)
                .addProperty("SERVICE_COMPASS", integerSchema)
                .addProperty("SERVICE_GALILEO", integerSchema)
                .addProperty("status", integerSchema)
                .addProperty("service", integerSchema)

                .build();

        DataSchema NavSatFixSchema = new ObjectSchema.Builder()
                .addProperty("header", HeaderSchema)
                .addProperty("status", NavSatSatus)
                .addProperty("latitude", new NumberSchema.Builder()
                        .addMinimum(-90.0)
                        .addMinimum(90.0)
                        .build())
                .addProperty("longitude", new NumberSchema.Builder()
                        .addMinimum(-180.0)
                        .addMinimum(180.0)
                        .build())
                .addProperty("altitude",numberSchema )
                .addProperty("position_covariance", numberListSchema)
                .addProperty("COVARIANCE_TYPE_UNKNOWN", integerSchema)
                .addProperty("COVARIANCE_TYPE_APPROXIMATED", integerSchema)
                .addProperty("COVARIANCE_TYPE_DIAGONAL_KNOWN", integerSchema)
                .addProperty("COVARIANCE_TYPE_KNOWN", integerSchema)
                .addProperty("position_covariance_type", integerSchema)
                .build();





        DataSchema currentCmdSchema = new ObjectSchema.Builder()
                .addProperty("drive_cmds", driveCmdsSchema)
                .addProperty("execDuration", numberSchema)
                .addProperty("task", tasksEnumSchema)
                .addRequiredProperties("drive_cmds")

                .build();

        DataSchema rtoFeedbackSchema = new ObjectSchema.Builder()
                .addProperty("current_cmd_number", numberSchema)
                .addProperty("current_cmd", currentCmdSchema)
                .addRequiredProperties("current_cmd")
                .build();

        DataSchema setImplementSchema = new ObjectSchema.Builder()
                .addProperty("was_sucess", new BooleanSchema.Builder().build())
                .addProperty("curr_implement", ImplementsEnumsSchema)
                .build();

        DataSchema setSysMode = new ObjectSchema.Builder()
                .addProperty("was_sucess", new BooleanSchema.Builder().build())
                .addProperty("curr_sysMode", sysModeEnumSchema)
                .build();



        DataSchema sysCondEnumSchema = new IntegerSchema.Builder().addMinimum(0).addMaximum(4).build();






        DataSchema timeSchema = new ObjectSchema.Builder()
                .addProperty("secs", integerSchema)
                .addProperty("nsecs", integerSchema)
                .build();

        DataSchema timedCmdsSchema = new ObjectSchema.Builder()
                .addProperty("drive_cmds", driveCmdsSchema)
                .addProperty("execDuration", new NumberSchema.Builder()
                        .addMinimum(0.1)
                        .addMaximum(10.0)
                        .build()
                )
                .addProperty("task", tasksEnumSchema)
                .build();

        DataSchema vehStateEnumSchema = new IntegerSchema.Builder().addMinimum(0).addMaximum(4).build();

        DataSchema vcuMsgSchema = new ObjectSchema.Builder()
                .addProperty("header", HeaderSchema)
                .addProperty("vehicle_state", integerSchema)
                .addProperty("vehicle_state", vehStateEnumSchema)
                .addProperty("curverture_state", integerSchema)
                .addProperty("propulsion_state", vehStateEnumSchema)
                .addProperty("curverture_max_value", integerSchema)
                .addProperty("curverture_act_value", integerSchema)
                .addProperty("propulsion_max_value", integerSchema)
                .addProperty("propulsion_act_value", integerSchema)
                .build();




        DataSchema wpoFeedbackSchema = new ObjectSchema.Builder()
                .addProperty("num_current_mission_goal", integerSchema)
                .addProperty("current_mission_goal", currentCmdSchema)
                .addRequiredProperties("current_mission_goal")
                .build();

        //Modes

        Form getCurrentModeForm = new Form.Builder(baseURI+"/eTractorModes/currentMode")
                .setMethodName("GET")
                .build();

        PropertyAffordance getCurrentMode = new PropertyAffordance.Builder("getCurrentMode", getCurrentModeForm).build();

        properties.add(getCurrentMode);

        Form putCurrentModeForm = new Form.Builder(baseURI+"/eTractorModes/mode/{mode}")
                .setMethodName("PUT")
                .build();

        ActionAffordance putCurrentMode = new ActionAffordance.Builder("putCurrentMode", putCurrentModeForm)
                .addUriVariable("mode", sysModeEnumSchema)
                .addUriVariable("feedback_callback_url", new StringSchema.Builder().build())
                .build();

        actions.add(putCurrentMode);

        Form getAllModesForm = new Form.Builder(baseURI + "/eTractorModes")
                .setMethodName("GET")
                .build();

        PropertyAffordance getAllModes = new PropertyAffordance.Builder("getAllModes", getAllModesForm)
                .addDataSchema(new ArraySchema.Builder().addItem(sysModeEnumSchema).build())
                .build();

        properties.add(getAllModes);

        //waypoint operated

        Form putWpoMissionGoalsForm = new Form.Builder(baseURI + "/waypointOperated/missionGoals{?restart}")
                .setMethodName("PUT")
                .build();

        ActionAffordance putWpoMissionGoals = new ActionAffordance.Builder("putWpoMissionGoals", putWpoMissionGoalsForm)
                .addInputSchema(MissionGoalSchema)
                .addUriVariable("restart", new BooleanSchema.Builder().build()) //Check also with boolean
                .build();

        actions.add(putWpoMissionGoals);

        Form putWpoMissionGoalsWithFeedbackForm = new Form.Builder(baseURI + "/waypointOperated/missionGoals{?restart,feedback_callback_url}")
                .setMethodName("PUT")
                .build();

        ActionAffordance putWpoMissionGoalsWithFeedback = new ActionAffordance.Builder("putWpoMissionGoalsWithFeedback", putWpoMissionGoalsWithFeedbackForm)
                .addInputSchema(MissionGoalSchema)
                .addUriVariable("restart", new BooleanSchema.Builder().build())
                .addUriVariable("feedback_callback_url", new StringSchema.Builder().build())
                .build();

        actions.add(putWpoMissionGoalsWithFeedback);

        Form putWpoCancelForm = new Form.Builder(baseURI + "/waypointOperated/cancelGoals")
                .setMethodName("PUT")
                .build();

        ActionAffordance putWpoCancel = new ActionAffordance.Builder("putWpoCancel", putWpoCancelForm)
                .build();

        actions.add(putWpoCancel);

        Form isProcessingForm = new Form.Builder(baseURI + "/waypointOperated/isProcessing")
                .setMethodName("GET")
                .build();

        PropertyAffordance isProcessing = new PropertyAffordance.Builder("isProcessing", isProcessingForm)
                .build();

        properties.add(isProcessing);

        Form currentGoalForm = new Form.Builder(baseURI + "/waypointOperated/currentGoal")
                .setMethodName("GET")
                .build();

        PropertyAffordance currentGoal = new PropertyAffordance.Builder("currentGoal", currentGoalForm)
                .addDataSchema(MissionGoalSchema)
                .build();

        properties.add(currentGoal);

        //routine_operated

        Form putMissionCmdsForm = new Form.Builder(baseURI + "/routineOperated/missionCmds{?feedback_callback_url}")
                .setMethodName("PUT")
                .build();

        ActionAffordance putMissionCmds = new ActionAffordance.Builder("putMissionCmds", putMissionCmdsForm)
                .addInputSchema(timedCmdsSchema)
                .addUriVariable("feedback_callback_url", new StringSchema.Builder().build())
                .build();

        actions.add(putMissionCmds);

        Form putRtoCancelForm = new Form.Builder(baseURI + "/routineOperated/cancelCmds")
                .setMethodName("PUT")
                .build();

        ActionAffordance putRtoCancel = new ActionAffordance.Builder("putRtoCancel", putRtoCancelForm)
                .build();

        actions.add(putRtoCancel);

        Form isRoutineProcessingForm = new Form.Builder(baseURI + "/routineOperated/isProcessing")
                .setMethodName("GET")
                .build();

        PropertyAffordance isRoutineProcessing = new PropertyAffordance.Builder("isRoutineProcessing", isRoutineProcessingForm)
                .build();

        properties.add(isRoutineProcessing);

        Form currentCmdForm = new Form.Builder(baseURI + "/routineOperated/currentCmd")
                .setMethodName("GET")
                .build();

        PropertyAffordance currentCmd = new PropertyAffordance.Builder("currentCmd", currentCmdForm)
                .addDataSchema(timedCmdsSchema)
                .build();

        properties.add(currentCmd);

        //sensors

        Form getVcuMsgForm = new Form.Builder(baseURI + "/sensors/vcuMsg")
                .setMethodName("GET")
                .build();

        PropertyAffordance getVcuMsg = new PropertyAffordance.Builder("getVcuMsg", getVcuMsgForm)
                .addDataSchema(vcuMsgSchema)
                .build();

        properties.add(getVcuMsg);

        Form driveCmdsForm = new Form.Builder(baseURI + "/sensors/driveCmds")
                .setMethodName("GET")
                .build();

        PropertyAffordance driveCmds = new PropertyAffordance.Builder("getDriveCmds",driveCmdsForm )
                .addDataSchema(driveCmdsSchema)
                .build();

        Form getImuForm = new Form.Builder(baseURI + "/sensors/imu/{imu_position}")
                .setMethodName("GET")
                .build();

        PropertyAffordance getImu = new PropertyAffordance.Builder("getImu", getImuForm)
                .addUriVariable("imu_position", PositionsEnumsSchema)
                .addDataSchema(ImuSchema)
                .build();

        properties.add(getImu);

        Form getGpsForm = new Form.Builder(baseURI + "/sensors/gps")
                .setMethodName("GET")
                .build();

        PropertyAffordance getGps = new PropertyAffordance.Builder("getGps", getGpsForm)
                .addDataSchema(NavSatFixSchema)
                .build();

        properties.add(getGps);

        Form getGPSBufferedMsgsForm = new Form.Builder(baseURI + "/sensors/gps/buffered_gps{?reset}")
                .setMethodName("GET")
                .build();

        ActionAffordance getGPSBufferedMsgs = new ActionAffordance.Builder("getGPSBufferedMsgs", getGPSBufferedMsgsForm)
                .addOutputSchema(NavSatFixSchema)
                .build();

        Form getCameraHandleRosTopicForm = new Form.Builder(baseURI + "/sensors/CameraHandleRosTopic/{cam_position}")
                .setMethodName("GET")
                .build();

        PropertyAffordance getCameraHandleRosTopic = new PropertyAffordance.Builder("getCameraHandleRosTopic", getCameraHandleRosTopicForm)
                .addUriVariable("cam_position", PositionsEnumsSchema)
                .addDataSchema(new StringSchema.Builder().build())
                .build();

        properties.add(getCameraHandleRosTopic);

        Form getCameraHandleRtpStreamForm = new Form.Builder(baseURI + "/sensors/CameraHandleRtpStream/{cam_position}")
                .setMethodName("GET")
                .build();

        PropertyAffordance getCameraHandleRtpStream = new PropertyAffordance.Builder("getCameraHandleRtpStream", getCameraHandleRtpStreamForm)
                .addUriVariable("cam_position", PositionsEnumsSchema)
                .addDataSchema(new StringSchema.Builder().build())
                .build();

        properties.add(getCameraHandleRosTopic);

        Form startRTPSStreamForm = new Form.Builder(baseURI + "/sensors/startRTPSStream/{cam_position}")
                .setMethodName("PUT")
                .build();

        ActionAffordance startRTPSStream = new ActionAffordance.Builder( "startRTPSStream", startRTPSStreamForm)
                .addOutputSchema(setValues)
                .build();

        actions.add(startRTPSStream);

        Form stopRTPSStreamForm = new Form.Builder(baseURI + "/sensors/stopRTPSStream/{cam_position}")
                .setMethodName("PUT")
                .build();

        ActionAffordance stopRTPSStream = new ActionAffordance.Builder("stopRTPSStream", stopRTPSStreamForm)
                .addOutputSchema(setValues)
                .build();

        actions.add(stopRTPSStream);



        Form getHeadingForm = new Form.Builder(baseURI + "/sensors/heading")
                .setMethodName("GET")
                .build();

        PropertyAffordance getHeading = new PropertyAffordance.Builder("getHeading", getHeadingForm)
                .addDataSchema(HeadingSchema)
                .build();

        properties.add(getHeading);

        Form getVelocityForm = new Form.Builder(baseURI + "/sensors/velocity")
                .setMethodName("GET")
                .build();

        PropertyAffordance getVelocity = new PropertyAffordance.Builder("getVelocity", getVelocityForm)
                .addDataSchema(velocitySchema)
                .build();

        properties.add(getVelocity);


        //implements

        Form getImplementsForm = new Form.Builder(baseURI + "/implements")
                .setMethodName("GET")
                .build();

        PropertyAffordance getImplements = new PropertyAffordance.Builder("getImplements", getImplementsForm)
                .addDataSchema(new ArraySchema.Builder().addItem(ImplementsEnumsSchema).build())
                .build();

        properties.add(getImplements);

        Form getCurrentImplementForm = new Form.Builder(baseURI + "/currImplement")
                .setMethodName("GET")
                .build();

        PropertyAffordance getCurrentImplement = new PropertyAffordance.Builder("getCurrentImplement", getCurrentImplementForm)
                .addDataSchema(ImplementsEnumsSchema)
                .build();

        properties.add(getCurrentImplement);

        Form putCurrentImplementForm = new Form.Builder(baseURI + "/implements/implement{?implement}")
                .setMethodName("PUT")
                .build();

        ActionAffordance putCurrentImplement = new ActionAffordance.Builder("putCurrentImplement", putCurrentImplementForm)
                .addUriVariable("implement", ImplementsEnumsSchema)
                .addOutputSchema(setImplementSchema)
                .build();

        actions.add(putCurrentImplement);

        //manually operated

        Form manualDriveCmdsForm = new Form.Builder(baseURI + "/manualDriveCmds")
                .setMethodName("PUT")
                .build();

        ActionAffordance manualDriveCmds = new ActionAffordance.Builder("manualDriveCmds", manualDriveCmdsForm)
                .addInputSchema(driveCmdsSchema)
                .addOutputSchema(new DataSchema.Builder().build())
                .build();

        actions.add(manualDriveCmds);

        Form readRootForm = new Form.Builder(baseURI + "/").setMethodName("GET").build();

        PropertyAffordance readRoot = new PropertyAffordance.Builder("readRoot", readRootForm)
                .addDataSchema(new StringSchema.Builder().build())
                .build();

        properties.add(readRoot);

        Form getHeartbeatForm = new Form.Builder(baseURI + "/ros_hb")
                .setMethodName("GET")
                .build();

        PropertyAffordance getHeartbeat = new PropertyAffordance.Builder("getHeartbeat", getHeartbeatForm)
                .addDataSchema(new ObjectSchema.Builder()
                        .addProperty("data", new BooleanSchema.Builder().build())
                        .build())
                .build();

        properties.add(getHeartbeat);

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
