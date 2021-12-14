package vocabularies;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;

// Vocabulary for UFACTORY xArm 7, used in AI4Industry summer school
public final class MINES {

    public static final String PREFIX = "https://ci.mines-stetienne.fr/kg/ontology#";

    public static final String phantomX = PREFIX + "PhantomX";

    //Properties
    public static final String posture = PREFIX + "Posture";
    public static final String elbow = PREFIX + "Elbow";
    public static final String wristAngle = PREFIX + "WristAngle";
    public static final String wristRotation = PREFIX + "WristRotation";
    public static final String base = PREFIX + "Base";
    public static final String gripper = PREFIX + "Gripper";
    public static final String shoulder = PREFIX + "Shoulder";

    //Actions
    public static final String reset = PREFIX + "Reset";
    public static final String setElbow = PREFIX + "SetElbow";
    public static final String setWristAngle = PREFIX + "SetWristAngle";
    public static final String setWristRotation = PREFIX + "SetWristRotation";
    public static final String setBase = PREFIX + "SetBase";
    public static final String setGripper = PREFIX + "SetGripper";
    public static final String setShoulder = PREFIX + "SetShoulder";
    public static final String logOut = PREFIX + "LogOut";
    public static final String logIn = PREFIX + "LogIn";

    //Input schemas
    public static final String elbowJoint = PREFIX + "ElbowJoint";
    public static final String wristAngleJoint = PREFIX + "WristAngleJoint";
    public static final String wristRotateJoint = PREFIX + "WristRotateJoint";
    public static final String baseJoint = PREFIX + "BaseJoint";
    public static final String gripperJoint = PREFIX + "GripperJoint";
    public static final String shoulderJoint = PREFIX + "ShoulderJoint";

    //Properties of object schemas
    public static final String elbowJointValue = PREFIX + "ElbowJointValue";
    public static final String wristAngleJointValue = PREFIX + "WristAngleJointValue";
    public static final String wristRotationJointValue = PREFIX + "WristRotationJointValue";
    public static final String baseJointValue = PREFIX + "BaseJointValue";
    public static final String gripperJointValue = PREFIX + "GripperJointValue";
    public static final String shoulderJointValue = PREFIX + "ShoulderJointValue";

    public static final String jointName = PREFIX + "JointName";
    public static final String jointValue = PREFIX + "JointValue";

    public static final String userToken = PREFIX + "UserToken";



    public static IRI createIRI(String fragment) {
        return SimpleValueFactory.getInstance().createIRI(PREFIX + fragment);
    }
}
