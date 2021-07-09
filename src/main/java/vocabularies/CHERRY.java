package vocabularies;

public class CHERRY {

    public static final String PREFIX = "http://www.example.org/cherrybot#";

    // Action types
    public static final String postOperator = PREFIX + "PostOperator";
    public static final String initialize = PREFIX + "Initialize";
    public static final String putTarget = PREFIX + "PutTarget";
    public static final String putGripper = PREFIX + "PutGripper";

    // Property types
    public static final String getOperator = PREFIX + "GetOperator";
    public static final String getTarget = PREFIX + "GetTarget";
    public static final String getTCP = PREFIX + "getTCP";
    public static final String getGripper = PREFIX + "getGripper";

    // Object schema types
    public static final String operator = PREFIX + "Operator";
    public static final String tcpMovement = PREFIX + "TcpMovement";
    public static final String target = PREFIX + "Target";
    public static final String coordinates = PREFIX + "Coordinates";
    public static final String rotation = PREFIX + "Rotation";
    public static final String operatorWithToken = PREFIX + "Operator_with_Token";

}
