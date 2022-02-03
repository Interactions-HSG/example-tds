package vocabularies;

public class INTELLIOT {

    public static final String PREFIX = "https://intelliot.org/things#";

    public static final String robotController = PREFIX + "RobotController";

    // Data schemas
    public static final String operatorProfile = PREFIX + "OperatorProfile";
    public static final String handle = PREFIX + "Handle";
    public static final String robotHandle = PREFIX + "RobotHandle";
    public static final String cameraHandle = PREFIX + "CameraHandle";
    public static final String inMovement = PREFIX + "InMovement";
    public static final String pose = PREFIX + "Pose";
    public static final String poseValue = PREFIX + "PoseValue";
    public static final String gripper = PREFIX + "Gripper";
    public static final String poseNotification = PREFIX + "PoseNotification";
    public static final String poseUpdate = PREFIX + "PoseUpdate";
    public static final String callbackLocation = PREFIX + "CallbackLocation";

    public static final String xCoordinate = PREFIX + "XCoordinate";
    public static final String yCoordinate = PREFIX + "YCoordinate";
    public static final String zCoordinate = PREFIX + "ZCoordinate";
    public static final String alphaCoordinate = PREFIX + "AlphaCoordinate";
    public static final String betaCoordinate = PREFIX + "BetaCoordinate";
    public static final String gammaCoordinate = PREFIX + "GammaCoordinate";

    public static final String joint1Coordinate = PREFIX + "Joint1Coordinate";
    public static final String joint2Coordinate = PREFIX + "Joint2Coordinate";
    public static final String joint3Coordinate = PREFIX + "Joint3Coordinate";
    public static final String joint4Coordinate = PREFIX + "Joint4Coordinate";
    public static final String joint5Coordinate = PREFIX + "Joint5Coordinate";
    public static final String joint6Coordinate = PREFIX + "Joint6Coordinate";

    public static final String jointCoordinates = PREFIX + "JointCoordinates";
    public static final String tcpCoordinate = PREFIX + "TCPCoordinate";
    public static final String namedPose = PREFIX + "NamedPose";
    public static final String aiCoordinate = PREFIX + "AICoordinate";

    // Property affordances
    public static final String readOperator = PREFIX + "ReadOperator";
    public static final String readHandles = PREFIX + "ReadHandles";
    public static final String readStatus = PREFIX + "ReadStatus";
    public static final String readPose = PREFIX + "ReadPose";
    public static final String readGripper = PREFIX + "ReadGripper";

    // Action affordances
    public static final String register = PREFIX + "Register";
    public static final String deregister = PREFIX + "Deregister";
    public static final String setPose = PREFIX + "SetPose";
    public static final String setGripper = PREFIX + "SetGripper";

    // Event affordances
    public static final String subscribePose = PREFIX + "SubscribePose";

}
