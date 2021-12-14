package things;

import ch.unisg.ics.interactions.wot.td.ThingDescription;
import ch.unisg.ics.interactions.wot.td.affordances.ActionAffordance;
import ch.unisg.ics.interactions.wot.td.affordances.Form;
import ch.unisg.ics.interactions.wot.td.affordances.PropertyAffordance;
import ch.unisg.ics.interactions.wot.td.clients.TDHttpRequest;
import ch.unisg.ics.interactions.wot.td.clients.TDHttpResponse;
import ch.unisg.ics.interactions.wot.td.schemas.DataSchema;
import ch.unisg.ics.interactions.wot.td.schemas.IntegerSchema;
import ch.unisg.ics.interactions.wot.td.schemas.ObjectSchema;
import ch.unisg.ics.interactions.wot.td.schemas.StringSchema;
import ch.unisg.ics.interactions.wot.td.security.APIKeySecurityScheme;
import ch.unisg.ics.interactions.wot.td.security.SecurityScheme;
import ch.unisg.ics.interactions.wot.td.vocabularies.TD;
import org.junit.Test;
import vocabularies.FOAF;
import vocabularies.MINES;

import java.io.IOException;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static things.ThingTestUtil.*;

public class PhantomXReactorTest {

    /* set THING_NAME to "leabot2" to test the other leubot phantom x reactor. */
    private static final String THING_NAME = "leubot1";
    private static final String BASE_URI = "https://api.interactions.ics.unisg.ch";
    private static final String VERSION = "v1.3.4";
    private static final String TD_URL = "http://yggdrasil.interactions.ics.unisg" +
            ".ch/environments/61/workspaces/102/artifacts";

    private static final PhantomXReactor PHANTOMX =
            new PhantomXReactor(BASE_URI + "/" + THING_NAME + "/" + VERSION + "/",
                    TD_URL + "/" + THING_NAME, THING_NAME);

    private static final ThingDescription THING_DESCRIPTION = PHANTOMX.exposeTD();

    private static final String OPERATOR_NAME = "Danai V";
    private static final String OPERATOR_EMAIL = "danai.v@example.com";

    private static int LOG_IN_STATUS = 403;
    private static String LOG_IN_TOKEN = "";

    private static void assertIsAgentSchema(DataSchema schema) {
        assertTrue(schema instanceof ObjectSchema);
        assertTrue(schema.isA(FOAF.agent));

        ObjectSchema objSchema = (ObjectSchema) schema;

        assertTrue(objSchema.getFirstPropertyNameBySemnaticType(FOAF.name).isPresent());
        assertTrue(objSchema.getFirstPropertyNameBySemnaticType(FOAF.mbox).isPresent());

        String nameName = objSchema.getFirstPropertyNameBySemnaticType(FOAF.name).get();
        String mboxName = objSchema.getFirstPropertyNameBySemnaticType(FOAF.mbox).get();

        assertEquals("name", nameName);
        assertEquals("email", mboxName);

        assertTrue(objSchema.hasRequiredProperty(nameName));
        assertTrue(objSchema.hasRequiredProperty(mboxName));

        DataSchema nameSchema = objSchema.getProperty(nameName).get();
        DataSchema mboxSchema = objSchema.getProperty(mboxName).get();

        assertTrue(nameSchema instanceof StringSchema);
        assertTrue(mboxSchema instanceof StringSchema);
    }

    private static void assertIsPostureSchema(DataSchema schema) {
        assertTrue(schema instanceof ObjectSchema);
        assertTrue(schema.isA(MINES.posture));

        ObjectSchema objSchema = (ObjectSchema) schema;

        assertTrue(objSchema.getFirstPropertyNameBySemnaticType(MINES.baseJointValue).isPresent());
        assertTrue(objSchema.getFirstPropertyNameBySemnaticType(MINES.shoulderJointValue)
                .isPresent());
        assertTrue(objSchema.getFirstPropertyNameBySemnaticType(MINES.elbowJointValue).isPresent());
        assertTrue(objSchema.getFirstPropertyNameBySemnaticType(MINES.wristAngleJointValue)
                .isPresent());
        assertTrue(objSchema.getFirstPropertyNameBySemnaticType(MINES.wristRotationJointValue)
                .isPresent());
        assertTrue(objSchema.getFirstPropertyNameBySemnaticType(MINES.gripperJointValue)
                .isPresent());

        String baseJointValueName = objSchema
                .getFirstPropertyNameBySemnaticType(MINES.baseJointValue).get();
        String shoulderJointValueName = objSchema
                .getFirstPropertyNameBySemnaticType(MINES.shoulderJointValue).get();
        String elbowJointValueName =
                objSchema.getFirstPropertyNameBySemnaticType(MINES.elbowJointValue).get();
        String wristAngleJointValueName = objSchema
                .getFirstPropertyNameBySemnaticType(MINES.wristAngleJointValue).get();
        String wristRotationJointValueName = objSchema
                .getFirstPropertyNameBySemnaticType(MINES.wristRotationJointValue).get();
        String gripperJointValueName = objSchema
                .getFirstPropertyNameBySemnaticType(MINES.gripperJointValue).get();

        assertEquals("Base", baseJointValueName);
        assertEquals("Shoulder", shoulderJointValueName);
        assertEquals("Elbow", elbowJointValueName);
        assertEquals("WristAngle", wristAngleJointValueName);
        assertEquals("WristRotation", wristRotationJointValueName);
        assertEquals("Gripper", gripperJointValueName);

        assertTrue(objSchema.hasRequiredProperty(baseJointValueName));
        assertTrue(objSchema.hasRequiredProperty(shoulderJointValueName));
        assertTrue(objSchema.hasRequiredProperty(elbowJointValueName));
        assertTrue(objSchema.hasRequiredProperty(wristAngleJointValueName));
        assertTrue(objSchema.hasRequiredProperty(wristRotationJointValueName));
        assertTrue(objSchema.hasRequiredProperty(gripperJointValueName));

        DataSchema baseJointValueSchema = objSchema.getProperty(baseJointValueName).get();
        DataSchema shoulderJointValueSchema = objSchema.getProperty(shoulderJointValueName).get();
        DataSchema elbowJointValueSchema = objSchema.getProperty(elbowJointValueName).get();
        DataSchema wristAngleJointValueSchema = objSchema.getProperty(wristAngleJointValueName)
                .get();
        DataSchema wristRotationJointValueSchema = objSchema
                .getProperty(wristRotationJointValueName).get();
        DataSchema gripperJointValueSchema = objSchema.getProperty(gripperJointValueName).get();

        assertTrue(baseJointValueSchema instanceof IntegerSchema);
        assertTrue(shoulderJointValueSchema instanceof IntegerSchema);
        assertTrue(elbowJointValueSchema instanceof IntegerSchema);
        assertTrue(wristAngleJointValueSchema instanceof IntegerSchema);
        assertTrue(wristRotationJointValueSchema instanceof IntegerSchema);
        assertTrue(gripperJointValueSchema instanceof IntegerSchema);
    }

    private static void assertIsPropObjectSchema(DataSchema schema) {
        assertTrue(schema instanceof ObjectSchema);

        ObjectSchema objSchema = (ObjectSchema) schema;

        assertTrue(objSchema.getFirstPropertyNameBySemnaticType(MINES.jointName).isPresent());
        assertTrue(objSchema.getFirstPropertyNameBySemnaticType(MINES.jointValue).isPresent());

        String jointNameName = objSchema.getFirstPropertyNameBySemnaticType(MINES.jointName).get();
        String jointValueName =
                objSchema.getFirstPropertyNameBySemnaticType(MINES.jointValue).get();

        assertEquals("name", jointNameName);
        assertEquals("value", jointValueName);

        assertTrue(objSchema.hasRequiredProperty(jointNameName));
        assertTrue(objSchema.hasRequiredProperty(jointValueName));

        DataSchema jointNameSchema = objSchema.getProperty(jointNameName).get();
        DataSchema jointValueSchema = objSchema.getProperty(jointValueName).get();

        assertTrue(jointNameSchema instanceof StringSchema);
        assertTrue(jointValueSchema instanceof IntegerSchema);
    }

    private static void assertIsBaseJointSchema(DataSchema schema) {
        assertIsJointSchema(schema, MINES.baseJoint, 512, 1023);
    }

    private static void assertIsShoulderJointSchema(DataSchema schema) {
        assertIsJointSchema(schema, MINES.shoulderJoint, 205, 810);
    }

    private static void assertIsElbowJointSchema(DataSchema schema) {
        assertIsJointSchema(schema, MINES.elbowJoint, 400, 650);
    }

    private static void assertIsWristAngleJointSchema(DataSchema schema) {
        assertIsJointSchema(schema, MINES.wristAngleJoint, 200, 830);
    }

    private static void assertIsWristRotateJointSchema(DataSchema schema) {
        assertIsJointSchema(schema, MINES.wristRotateJoint, 0, 1023);
    }

    private static void assertIsGripperJointSchema(DataSchema schema) {
        assertIsJointSchema(schema, MINES.gripperJoint, 0, 512);
    }

    private static void assertIsJointSchema(DataSchema schema, String jointType,
                                            int minimumJointValue, int maximumJointValue) {
        assertTrue(schema instanceof ObjectSchema);
        assertTrue(schema.isA(jointType));

        ObjectSchema objSchema = (ObjectSchema) schema;

        assertTrue(objSchema.getFirstPropertyNameBySemnaticType(MINES.jointValue).isPresent());
        String jointValueName =
                objSchema.getFirstPropertyNameBySemnaticType(MINES.jointValue).get();

        assertEquals("value", jointValueName);
        assertTrue(objSchema.hasRequiredProperty(jointValueName));

        DataSchema jointValueSchema = objSchema.getProperty(jointValueName).get();
        assertTrue(jointValueSchema instanceof IntegerSchema);

        assertTrue(((IntegerSchema) jointValueSchema).getMinimum().isPresent());
        assertTrue(((IntegerSchema) jointValueSchema).getMaximum().isPresent());

        assertEquals(minimumJointValue, ((IntegerSchema) jointValueSchema).getMinimum().get(),
                0);
        assertEquals(maximumJointValue, ((IntegerSchema) jointValueSchema).getMaximum().get(),
                0);
    }

    @Test
    public void TestThingURI() {
        assertTrue(THING_DESCRIPTION.getThingURI().isPresent());
        assertEquals(TD_URL + "/" + THING_NAME, THING_DESCRIPTION.getThingURI().get());
    }

    @Test
    public void TestBaseURI() {
        assertTrue(THING_DESCRIPTION.getBaseURI().isPresent());
        assertEquals(BASE_URI + "/" + THING_NAME + "/" + VERSION + "/",
                THING_DESCRIPTION.getBaseURI().get());
    }

    @Test
    public void TestTitle() {
        assertEquals(THING_NAME, THING_DESCRIPTION.getTitle());
    }

    @Test
    public void TestSemanticTypes() {
        Set<String> types = THING_DESCRIPTION.getSemanticTypes();
        assertEquals(2, types.size());
        assertTrue(types.contains("http://w3id.org/eve#Artifact"));
        assertTrue(types.contains(MINES.phantomX));
    }

    @Test
    public void TestSecuritySchemes() {
        //TODO update after merging with fix/security
        List<SecurityScheme> securitySchemes = THING_DESCRIPTION.getSecuritySchemes();
        assertEquals(1, securitySchemes.size());

        SecurityScheme securityScheme = securitySchemes.get(0);
        assertTrue(securityScheme instanceof APIKeySecurityScheme);

        APIKeySecurityScheme apiKeySecurityScheme = (APIKeySecurityScheme) securityScheme;
        assertEquals(APIKeySecurityScheme.TokenLocation.HEADER, apiKeySecurityScheme.getIn());
        assertTrue(apiKeySecurityScheme.getName().isPresent());
        assertEquals("X-API-Key", apiKeySecurityScheme.getName().get());
    }

    @Test
    public void TestAffordances() throws IOException, InterruptedException {
        List<PropertyAffordance> properties = THING_DESCRIPTION.getProperties();
        List<ActionAffordance> actions = THING_DESCRIPTION.getActions();

        assertEquals(8, properties.size());
        assertEquals(9, actions.size());

        testLogIn();
        Thread.sleep(1000);
        testUser();
        Thread.sleep(1000);
        testPosture();
        Thread.sleep(1000);

        testBase();
        Thread.sleep(1000);
        testShoulder();
        Thread.sleep(1000);
        testElbow();
        Thread.sleep(1000);
        testWristAngle();
        Thread.sleep(1000);
        testWristRotation();
        Thread.sleep(1000);
        testGripper();
        Thread.sleep(1000);

        testSetBase();
        Thread.sleep(3000);
        testSetShoulder();
        Thread.sleep(3000);
        testSetElbow();
        Thread.sleep(3000);
        testSetWristAngle();
        Thread.sleep(3000);
        testSetWristRotation();
        Thread.sleep(3000);
        testSetGripper();
        Thread.sleep(3000);

        testReset();
        Thread.sleep(3000);
        testLogOut();
    }

    private void testLogIn() throws IOException {
        ActionAffordance action = testActionMetadata(THING_DESCRIPTION, "logIn",
                MINES.logIn,
                true, false);

        assertTrue(action.getFirstFormForOperationType(TD.invokeAction).isPresent());
        Form form = action.getFirstFormForOperationType(TD.invokeAction).get();

        DataSchema schema = action.getInputSchema().get();
        assertIsAgentSchema(schema);

        HashMap<String, Object> reqPayload = new HashMap<>();
        reqPayload.put(FOAF.name, OPERATOR_NAME);
        reqPayload.put(FOAF.mbox, OPERATOR_EMAIL);

        TDHttpRequest req = new TDHttpRequest(form, TD.invokeAction);
        req.setObjectPayload((ObjectSchema) schema, reqPayload);
        TDHttpResponse res = req.execute();

        LOG_IN_STATUS = res.getStatusCode();
        assertTrue(LOG_IN_STATUS == 201 || LOG_IN_STATUS == 403);

        if (LOG_IN_STATUS == 201) {
            Map<String, String> headers = res.getHeaders();
            assertTrue(headers.containsKey("Location"));

            String userBaseURI = BASE_URI + "/" + THING_NAME + "/" + VERSION + "/user";
            assertTrue(headers.get("Location").contains(userBaseURI));
            LOG_IN_TOKEN = headers.get("Location").replace(userBaseURI + "/", "");
        }
    }

    private void testUser() throws IOException {
        PropertyAffordance prop = testPropertyMetadata(THING_DESCRIPTION, "user",
                FOAF.agent,
                true);

        assertTrue(prop.getFirstFormForOperationType(TD.readProperty).isPresent());
        Form form = prop.getFirstFormForOperationType(TD.readProperty).get();

        DataSchema schema = prop.getDataSchema();
        assertIsAgentSchema(schema);

        TDHttpRequest req = new TDHttpRequest(form, TD.readProperty);
        TDHttpResponse res = req.execute();

        Map<String, Object> resPayload = res.getPayloadAsObject((ObjectSchema) schema);
        assertTrue(resPayload.containsKey(FOAF.name));
        assertTrue(resPayload.containsKey(FOAF.mbox));

        if (LOG_IN_STATUS == 201) {
            assertEquals(res.getStatusCode(), 200);
            assertEquals(OPERATOR_NAME, resPayload.get(FOAF.name));
            assertEquals(OPERATOR_EMAIL, resPayload.get(FOAF.mbox));
        } else {
            assertEquals(LOG_IN_STATUS, 409);
        }
    }

    private void testPosture() throws IOException {
        PropertyAffordance prop = testPropertyMetadata(THING_DESCRIPTION, "posture",
                MINES.posture,
                true);

        assertTrue(prop.getFirstFormForOperationType(TD.readProperty).isPresent());
        Form form = prop.getFirstFormForOperationType(TD.readProperty).get();

        DataSchema schema = prop.getDataSchema();
        assertIsPostureSchema(schema);

        TDHttpRequest req = new TDHttpRequest(form, TD.readProperty);
        TDHttpResponse res = req.execute();

        assertEquals(res.getStatusCode(), 200);
        Map<String, Object> resPayload = res.getPayloadAsObject((ObjectSchema) schema);
        assertTrue(resPayload.containsKey(MINES.baseJointValue));
        assertTrue(resPayload.containsKey(MINES.shoulderJointValue));
        assertTrue(resPayload.containsKey(MINES.elbowJointValue));
        assertTrue(resPayload.containsKey(MINES.wristAngleJointValue));
        assertTrue(resPayload.containsKey(MINES.wristRotationJointValue));
        assertTrue(resPayload.containsKey(MINES.gripperJointValue));

        assertTrue(resPayload.get(MINES.baseJointValue) instanceof Integer);
        assertTrue(resPayload.get(MINES.shoulderJointValue) instanceof Integer);
        assertTrue(resPayload.get(MINES.elbowJointValue) instanceof Integer);
        assertTrue(resPayload.get(MINES.wristAngleJointValue) instanceof Integer);
        assertTrue(resPayload.get(MINES.wristRotationJointValue) instanceof Integer);
        assertTrue(resPayload.get(MINES.gripperJointValue) instanceof Integer);
    }

    private void testJoint(String name, String type) throws IOException {
        PropertyAffordance prop = testPropertyMetadata(THING_DESCRIPTION, name,
                type,
                true);

        assertTrue(prop.getFirstFormForOperationType(TD.readProperty).isPresent());
        Form form = prop.getFirstFormForOperationType(TD.readProperty).get();

        DataSchema schema = prop.getDataSchema();
        assertIsPropObjectSchema(schema);

        TDHttpRequest req = new TDHttpRequest(form, TD.readProperty);
        TDHttpResponse res = req.execute();

        assertEquals(res.getStatusCode(), 200);
        Map<String, Object> resPayload = res.getPayloadAsObject((ObjectSchema) schema);
        assertTrue(resPayload.containsKey(MINES.jointName));
        assertTrue(resPayload.containsKey(MINES.jointValue));

        assertTrue(resPayload.get(MINES.jointName) instanceof String);
        assertTrue(resPayload.get(MINES.jointValue) instanceof Integer);
    }

    private void testBase() throws IOException {
        testJoint("base", MINES.base);
    }

    private void testShoulder() throws IOException {
        testJoint("shoulder", MINES.shoulder);
    }

    private void testElbow() throws IOException {
        testJoint("elbow", MINES.elbow);
    }

    private void testWristAngle() throws IOException {
        testJoint("wristAngle", MINES.wristAngle);
    }

    private void testWristRotation() throws IOException {
        testJoint("wristRotation", MINES.wristRotation);
    }

    private void testGripper() throws IOException {
        testJoint("gripper", MINES.gripper);
    }

    private void testSetBase() throws IOException {
        ActionAffordance action = testActionMetadata(THING_DESCRIPTION, "setBase",
                MINES.setBase, true, false);

        assertTrue(action.getFirstFormForOperationType(TD.invokeAction).isPresent());
        Form form = action.getFirstFormForOperationType(TD.invokeAction).get();

        assertTrue(action.getInputSchema().isPresent());
        DataSchema schema = action.getInputSchema().get();
        assertIsBaseJointSchema(schema);

        HashMap<String, Object> reqPayload = new HashMap<>();
        reqPayload.put(MINES.jointValue, 768);

        TDHttpRequest req = new TDHttpRequest(form, TD.invokeAction);
        req.setObjectPayload((ObjectSchema) schema, reqPayload);

        if (LOG_IN_STATUS == 201) {
            addAPIKey(THING_DESCRIPTION, req, LOG_IN_TOKEN);
            TDHttpResponse res = req.execute();
            assertEquals(202, res.getStatusCode());
        } else {
            TDHttpResponse res = req.execute();
            assertEquals(401, res.getStatusCode());
        }
    }

    private void testSetShoulder() throws IOException {
        ActionAffordance action = testActionMetadata(THING_DESCRIPTION, "setShoulder",
                MINES.setShoulder, true, false);

        assertTrue(action.getFirstFormForOperationType(TD.invokeAction).isPresent());
        Form form = action.getFirstFormForOperationType(TD.invokeAction).get();

        assertTrue(action.getInputSchema().isPresent());
        DataSchema schema = action.getInputSchema().get();
        assertIsShoulderJointSchema(schema);

        HashMap<String, Object> reqPayload = new HashMap<>();
        reqPayload.put(MINES.jointValue, 410);

        TDHttpRequest req = new TDHttpRequest(form, TD.invokeAction);
        req.setObjectPayload((ObjectSchema) schema, reqPayload);

        if (LOG_IN_STATUS == 201) {
            addAPIKey(THING_DESCRIPTION, req, LOG_IN_TOKEN);
            TDHttpResponse res = req.execute();
            assertEquals(202, res.getStatusCode());
        } else {
            TDHttpResponse res = req.execute();
            assertEquals(401, res.getStatusCode());
        }
    }

    private void testSetElbow() throws IOException {
        ActionAffordance action = testActionMetadata(THING_DESCRIPTION, "setElbow",
                MINES.setElbow, true, false);

        assertTrue(action.getFirstFormForOperationType(TD.invokeAction).isPresent());
        Form form = action.getFirstFormForOperationType(TD.invokeAction).get();

        assertTrue(action.getInputSchema().isPresent());
        DataSchema schema = action.getInputSchema().get();
        assertIsElbowJointSchema(schema);

        HashMap<String, Object> reqPayload = new HashMap<>();
        reqPayload.put(MINES.jointValue, 410);

        TDHttpRequest req = new TDHttpRequest(form, TD.invokeAction);
        req.setObjectPayload((ObjectSchema) schema, reqPayload);

        if (LOG_IN_STATUS == 201) {
            addAPIKey(THING_DESCRIPTION, req, LOG_IN_TOKEN);
            TDHttpResponse res = req.execute();
            assertEquals(202, res.getStatusCode());
        } else {
            TDHttpResponse res = req.execute();
            assertEquals(401, res.getStatusCode());
        }
    }

    private void testSetWristAngle() throws IOException {
        ActionAffordance action = testActionMetadata(THING_DESCRIPTION, "setWristAngle",
                MINES.setWristAngle, true, false);

        assertTrue(action.getFirstFormForOperationType(TD.invokeAction).isPresent());
        Form form = action.getFirstFormForOperationType(TD.invokeAction).get();

        assertTrue(action.getInputSchema().isPresent());
        DataSchema schema = action.getInputSchema().get();
        assertIsWristAngleJointSchema(schema);

        HashMap<String, Object> reqPayload = new HashMap<>();
        reqPayload.put(MINES.jointValue, 590);

        TDHttpRequest req = new TDHttpRequest(form, TD.invokeAction);
        req.setObjectPayload((ObjectSchema) schema, reqPayload);

        if (LOG_IN_STATUS == 201) {
            addAPIKey(THING_DESCRIPTION, req, LOG_IN_TOKEN);
            TDHttpResponse res = req.execute();
            assertEquals(202, res.getStatusCode());
        } else {
            TDHttpResponse res = req.execute();
            assertEquals(401, res.getStatusCode());
        }
    }

    private void testSetWristRotation() throws IOException {
        ActionAffordance action = testActionMetadata(THING_DESCRIPTION, "setWristRotation",
                MINES.setWristRotation, true, false);

        assertTrue(action.getFirstFormForOperationType(TD.invokeAction).isPresent());
        Form form = action.getFirstFormForOperationType(TD.invokeAction).get();

        assertTrue(action.getInputSchema().isPresent());
        DataSchema schema = action.getInputSchema().get();
        assertIsWristRotateJointSchema(schema);

        HashMap<String, Object> reqPayload = new HashMap<>();
        reqPayload.put(MINES.jointValue, 522);

        TDHttpRequest req = new TDHttpRequest(form, TD.invokeAction);
        req.setObjectPayload((ObjectSchema) schema, reqPayload);

        if (LOG_IN_STATUS == 201) {
            addAPIKey(THING_DESCRIPTION, req, LOG_IN_TOKEN);
            TDHttpResponse res = req.execute();
            assertEquals(202, res.getStatusCode());
        } else {
            TDHttpResponse res = req.execute();
            assertEquals(401, res.getStatusCode());
        }
    }

    private void testSetGripper() throws IOException {
        ActionAffordance action = testActionMetadata(THING_DESCRIPTION, "setGripper",
                MINES.setGripper, true, false);

        assertTrue(action.getFirstFormForOperationType(TD.invokeAction).isPresent());
        Form form = action.getFirstFormForOperationType(TD.invokeAction).get();

        assertTrue(action.getInputSchema().isPresent());
        DataSchema schema = action.getInputSchema().get();
        assertIsGripperJointSchema(schema);

        HashMap<String, Object> reqPayload = new HashMap<>();
        reqPayload.put(MINES.jointValue, 512);

        TDHttpRequest req = new TDHttpRequest(form, TD.invokeAction);
        req.setObjectPayload((ObjectSchema) schema, reqPayload);

        if (LOG_IN_STATUS == 201) {
            addAPIKey(THING_DESCRIPTION, req, LOG_IN_TOKEN);
            TDHttpResponse res = req.execute();
            assertEquals(202, res.getStatusCode());
        } else {
            TDHttpResponse res = req.execute();
            assertEquals(401, res.getStatusCode());
        }
    }

    private void testReset() throws IOException {
        ActionAffordance action = testActionMetadata(THING_DESCRIPTION, "reset",
                MINES.reset, false, false);

        assertTrue(action.getFirstFormForOperationType(TD.invokeAction).isPresent());
        Form form = action.getFirstFormForOperationType(TD.invokeAction).get();

        TDHttpRequest req = new TDHttpRequest(form, TD.invokeAction);

        if (LOG_IN_STATUS == 201) {
            addAPIKey(THING_DESCRIPTION, req, LOG_IN_TOKEN);
            TDHttpResponse res = req.execute();
            assertEquals(202, res.getStatusCode());
        } else {
            TDHttpResponse res = req.execute();
            assertEquals(401, res.getStatusCode());
        }
    }

    private void testLogOut() throws IOException {
        ActionAffordance action = testActionMetadata(THING_DESCRIPTION, "logOut",
                MINES.logOut, false, false) ;

        assertTrue(action.getFirstFormForOperationType(TD.invokeAction).isPresent());
        Form form = action.getFirstFormForOperationType(TD.invokeAction).get();

        assertTrue(action.getUriVariables().isPresent());

        Map<String, DataSchema> uriVariables = action.getUriVariables().get();
        assertTrue(uriVariables.containsKey("token"));

        DataSchema schema = uriVariables.get("token");
        assertTrue(schema instanceof StringSchema);
        assertTrue(schema.isA(MINES.userToken));

        Map<String, Object> uriValues = new HashMap<>();
        //TODO should be string
        uriValues.put("token", LOG_IN_TOKEN);
        TDHttpRequest req = new TDHttpRequest(form, TD.invokeAction, uriVariables, uriValues);
        TDHttpResponse res = req.execute();

        if (LOG_IN_STATUS == 201) {
            assertEquals(204, res.getStatusCode());
        } else {
            assertEquals(404, res.getStatusCode());
        }
    }
}
