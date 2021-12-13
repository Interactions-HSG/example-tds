package vocabularies;

public class CHERRY {

    public static final String PREFIX = "https://interactions.ics.unisg.ch/cherrybot#";

    // Action types
    public static final String registerOperator = PREFIX + "RegisterOperator";
    public static final String removeOperator = PREFIX + "RemoveOperator";
    public static final String initialize = PREFIX + "Initialize";
    public static final String setTarget = PREFIX + "SetTarget";
    public static final String setGripper = PREFIX + "SetGripper";

    // Property types
    public static final String operator = PREFIX + "Operator";
    public static final String tcpTarget = PREFIX + "TcpTarget";
    public static final String tcp = PREFIX + "Tcp";
    public static final String gripper = PREFIX + "Gripper";

    // Object schema types
    public static final String operatorSchema = PREFIX + "OperatorSchema";
    public static final String gripperIntegerSchema = PREFIX + "GripperValue";
    public static final String tcpMovementSchema = PREFIX + "TcpMovementSchema";
    public static final String tcpTargetSchema = PREFIX + "TcpTargetSchema";
    public static final String coordinatesSchema = PREFIX + "CoordinatesSchema";
    public static final String rotationSchema = PREFIX + "RotationSchema";
    public static final String operatorWithTokenSchema = PREFIX + "OperatorWithTokenSchema";

    // Properties of object schemas
    public static final String operatorToken = PREFIX + "OperatorToken";
    public static final String xCoordinate = PREFIX + "XCoordinate";
    public static final String yCoordinate = PREFIX + "YCoordinate";
    public static final String zCoordinate = PREFIX + "ZCoordinate";
    public static final String roll = PREFIX + "Roll";
    public static final String pitch = PREFIX + "Pitch";
    public static final String yaw = PREFIX + "Yaw";
    public static final String speed = PREFIX + "Speed";
}
