package things;

import ch.unisg.ics.interactions.wot.td.ThingDescription;
import ch.unisg.ics.interactions.wot.td.affordances.ActionAffordance;
import ch.unisg.ics.interactions.wot.td.affordances.Form;
import ch.unisg.ics.interactions.wot.td.affordances.PropertyAffordance;
import ch.unisg.ics.interactions.wot.td.clients.TDHttpRequest;
import ch.unisg.ics.interactions.wot.td.clients.TDHttpResponse;
import ch.unisg.ics.interactions.wot.td.schemas.*;
import ch.unisg.ics.interactions.wot.td.security.APIKeySecurityScheme;
import ch.unisg.ics.interactions.wot.td.security.SecurityScheme;
import ch.unisg.ics.interactions.wot.td.vocabularies.TD;
import org.junit.Test;
import vocabularies.CHERRY;
import vocabularies.FOAF;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static things.ThingTestUtil.*;

public class XArmTest {

    /* set THING_NAME to "cherrybot" to test the real robot */
    private static final String THING_NAME = "pretendabot";

    private static final XArm XARM = new XArm("https://api.interactions.ics.unisg" +
            ".ch/"+ THING_NAME +"/", "urn:" + THING_NAME, THING_NAME);

    private static final ThingDescription XARM_TD = XARM.exposeTD();

    private static final String OPERATOR_NAME = "Danai V";
    private static final String OPERATOR_EMAIL = "danai.v@example.com";

    private static int LOG_IN_STATUS = 403;
    private static String LOG_IN_TOKEN = "";

    @Test
    public void TestThingURI() {
        assertTrue(XARM_TD.getThingURI().isPresent());
        assertEquals("urn:" + THING_NAME, XARM_TD.getThingURI().get());
    }

    @Test
    public void TestBaseURI() {
        assertTrue(XARM_TD.getBaseURI().isPresent());
        assertEquals("https://api.interactions.ics.unisg.ch/" + THING_NAME + "/",
                XARM_TD.getBaseURI().get());
    }

    @Test
    public void TestTitle() {
        assertEquals(THING_NAME, XARM_TD.getTitle());
    }

    @Test
    public void TestSemanticTypes() {
        Set<String> types = XARM_TD.getSemanticTypes();
        assertEquals(1, types.size());
        assertEquals("http://w3id.org/eve#Artifact", types.iterator().next());
    }

    @Test
    public void TestSecuritySchemes() {
        //TODO update after merging with fix/security
        List<SecurityScheme> securitySchemes = XARM_TD.getSecuritySchemes();
        assertEquals(1, securitySchemes.size());

        SecurityScheme securityScheme = securitySchemes.get(0);
        assertTrue(securityScheme instanceof APIKeySecurityScheme);

        APIKeySecurityScheme apiKeySecurityScheme = (APIKeySecurityScheme) securityScheme;
        assertEquals(APIKeySecurityScheme.TokenLocation.HEADER, apiKeySecurityScheme.getIn());
        assertTrue(apiKeySecurityScheme.getName().isPresent());
        assertEquals("Authentication", apiKeySecurityScheme.getName().get());
    }

    @Test
    public void TestAffordances() throws IOException, InterruptedException {
        List<PropertyAffordance> properties = XARM_TD.getProperties();
        List<ActionAffordance> actions = XARM_TD.getActions();

        assertEquals(4, properties.size());
        assertEquals(4, actions.size());

        testSetOperator();
        Thread.sleep(1000);
        testReadOperator();
        Thread.sleep(1000);
        testReadTCP();
        Thread.sleep(1000);
        testReadTCPTarget();
        Thread.sleep(1000);
        testReadGripper();
        Thread.sleep(1000);
        testSetTCPTarget();
        Thread.sleep(5000);
        testSetGripper();
        Thread.sleep(1000);
        testInitialize();
        Thread.sleep(1000);
    }

    private void testSetOperator() throws IOException {
        ActionAffordance action = testActionMetadata(XARM_TD, "registerOperator",
                CHERRY.registerOperator,
                true, false);

        assertTrue(action.getFirstFormForOperationType(TD.invokeAction).isPresent());
        Form form = action.getFirstFormForOperationType(TD.invokeAction).get();

        DataSchema schema = action.getInputSchema().get();
        assertIsOperatorSchema(schema);

        HashMap<String, Object> reqPayload = new HashMap<>();
        reqPayload.put(FOAF.name, OPERATOR_NAME);
        reqPayload.put(FOAF.mbox, OPERATOR_EMAIL);

        TDHttpRequest req = new TDHttpRequest(form, TD.invokeAction);
        req.setObjectPayload((ObjectSchema) schema, reqPayload);
        TDHttpResponse res = req.execute();

        LOG_IN_STATUS = res.getStatusCode();
        assertTrue(LOG_IN_STATUS == 200 || LOG_IN_STATUS == 403);
    }

    private void testReadOperator() throws IOException {
        PropertyAffordance prop = testPropertyMetadata(XARM_TD, "operator",
                CHERRY.operator,
                true);

        assertTrue(prop.getFirstFormForOperationType(TD.readProperty).isPresent());
        Form form = prop.getFirstFormForOperationType(TD.readProperty).get();

        DataSchema schema = prop.getDataSchema();
        assertIsOperatorWithTokenSchema(schema);

        TDHttpRequest req = new TDHttpRequest(form, TD.readProperty);
        TDHttpResponse res = req.execute();

        Map<String, Object> resPayload = res.getPayloadAsObject((ObjectSchema) schema);
        assertTrue(resPayload.containsKey(FOAF.name));
        assertTrue(resPayload.containsKey(FOAF.mbox));
        assertTrue(resPayload.containsKey(CHERRY.operatorToken));

        if (LOG_IN_STATUS == 200) {

            assertEquals(OPERATOR_NAME, resPayload.get(FOAF.name));
            assertEquals(OPERATOR_EMAIL, resPayload.get(FOAF.mbox));
            LOG_IN_TOKEN = (String) resPayload.get(CHERRY.operatorToken);

        } else if (OPERATOR_NAME.equals(resPayload.get(FOAF.name)) &&
                OPERATOR_EMAIL.equals(resPayload.get(FOAF.mbox))) {

            LOG_IN_TOKEN = (String) resPayload.get(CHERRY.operatorToken);
            LOG_IN_STATUS = 200;
        }
    }

    private void testReadTCP() throws IOException {
        testReadTCPAffordance("tcp", CHERRY.tcp);
    }

    private void testReadTCPTarget() throws IOException {
        testReadTCPAffordance("tcpTarget", CHERRY.tcpTarget);
    }

    private void testReadGripper() throws IOException {
        PropertyAffordance prop = testPropertyMetadata(XARM_TD, "gripper",
                CHERRY.gripper, true);

        assertTrue(prop.getFirstFormForOperationType(TD.readProperty).isPresent());
        Form form = prop.getFirstFormForOperationType(TD.readProperty).get();

        DataSchema schema = prop.getDataSchema();
        assertIsGripper(schema);

        TDHttpRequest req = new TDHttpRequest(form, TD.readProperty);

        if (LOG_IN_STATUS == 200) {
            addAPIKey(XARM_TD, req, LOG_IN_TOKEN);
            TDHttpResponse res = req.execute();
            assertEquals(200, res.getStatusCode());

            Object resPayload = res.getPayloadWithSchema(schema);
            assertTrue(resPayload instanceof Integer);
        } else {
            TDHttpResponse res = req.execute();
            assertEquals(401, res.getStatusCode());
        }
    }

    private void testReadTCPAffordance(String name, String type) throws IOException {
        PropertyAffordance prop = testPropertyMetadata(XARM_TD, name,
                type, true);

        assertTrue(prop.getFirstFormForOperationType(TD.readProperty).isPresent());
        Form form = prop.getFirstFormForOperationType(TD.readProperty).get();

        DataSchema schema = prop.getDataSchema();
        assertIsTcpTargetSchema(schema);

        TDHttpRequest req = new TDHttpRequest(form, TD.readProperty);

        if (LOG_IN_STATUS == 200) {
            addAPIKey(XARM_TD, req, LOG_IN_TOKEN);
            TDHttpResponse res = req.execute();
            assertEquals(200, res.getStatusCode());

            Map<String, Object> resPayload = res.getPayloadAsObject((ObjectSchema) schema);
            assertTrue(resPayload.containsKey(CHERRY.coordinatesSchema));
            assertTrue(resPayload.containsKey(CHERRY.rotationSchema));

            Map cooPayload = (Map) resPayload
                    .get(CHERRY.coordinatesSchema);
            assertTrue(cooPayload.containsKey(CHERRY.xCoordinate));
            assertTrue(cooPayload.containsKey(CHERRY.yCoordinate));
            assertTrue(cooPayload.containsKey(CHERRY.zCoordinate));

            assertTrue(cooPayload.get(CHERRY.xCoordinate) instanceof Number);
            assertTrue(cooPayload.get(CHERRY.yCoordinate) instanceof Number);
            assertTrue(cooPayload.get(CHERRY.zCoordinate) instanceof Number);

            Map rotPayload = (Map) resPayload
                    .get(CHERRY.rotationSchema);
            assertTrue(rotPayload.containsKey(CHERRY.roll));
            assertTrue(rotPayload.containsKey(CHERRY.pitch));
            assertTrue(rotPayload.containsKey(CHERRY.yaw));

            assertTrue(rotPayload.get(CHERRY.roll) instanceof Number);
            assertTrue(rotPayload.get(CHERRY.pitch) instanceof Number);
            assertTrue(rotPayload.get(CHERRY.yaw) instanceof Number);

        } else {
            TDHttpResponse res = req.execute();
            assertEquals(401, res.getStatusCode());
        }

    }

    private void testSetTCPTarget() throws IOException {
        ActionAffordance action = testActionMetadata(XARM_TD, "setTarget",
                CHERRY.setTarget, true, false);

        assertTrue(action.getFirstFormForOperationType(TD.invokeAction).isPresent());
        Form form = action.getFirstFormForOperationType(TD.invokeAction).get();

        assertTrue(action.getInputSchema().isPresent());
        DataSchema schema = action.getInputSchema().get();
        assertIsTcpMovementSchema(schema);

        HashMap<String, Object> cooPayload = new HashMap<>();
        cooPayload.put(CHERRY.xCoordinate, 300);
        cooPayload.put(CHERRY.yCoordinate, 0);
        cooPayload.put(CHERRY.zCoordinate, 400);

        HashMap<String, Object> rotPayload = new HashMap<>();
        rotPayload.put(CHERRY.roll, 180);
        rotPayload.put(CHERRY.pitch, 0);
        rotPayload.put(CHERRY.yaw, 0);

        HashMap<String, Object> targetPayload = new HashMap<>();
        targetPayload.put(CHERRY.coordinatesSchema, cooPayload);
        targetPayload.put(CHERRY.rotationSchema, rotPayload);

        HashMap<String, Object> reqPayload = new HashMap<>();
        reqPayload.put(CHERRY.tcpTargetSchema, targetPayload);
        reqPayload.put(CHERRY.speed, 50);

        TDHttpRequest req = new TDHttpRequest(form, TD.invokeAction);
        req.setObjectPayload((ObjectSchema) schema, reqPayload);

        if (LOG_IN_STATUS == 200) {
            addAPIKey(XARM_TD, req, LOG_IN_TOKEN);
            TDHttpResponse res = req.execute();
            assertEquals(200, res.getStatusCode());
        } else {
            TDHttpResponse res = req.execute();
            assertEquals(401, res.getStatusCode());
        }
    }

    private void testSetGripper() throws IOException {
        ActionAffordance action = testActionMetadata(XARM_TD, "setGripper",
                CHERRY.setGripper, true, false);

        assertTrue(action.getFirstFormForOperationType(TD.invokeAction).isPresent());
        Form form = action.getFirstFormForOperationType(TD.invokeAction).get();

        assertTrue(action.getInputSchema().isPresent());
        DataSchema schema = action.getInputSchema().get();
        assertIsGripper(schema);

        TDHttpRequest req = new TDHttpRequest(form, TD.invokeAction);
        req.setPrimitivePayload(schema, 50);

        if (LOG_IN_STATUS == 200) {
            addAPIKey(XARM_TD, req, LOG_IN_TOKEN);
            TDHttpResponse res = req.execute();
            assertEquals(200, res.getStatusCode());
        } else {
            TDHttpResponse res = req.execute();
            assertEquals(401, res.getStatusCode());
        }
    }

    private void testInitialize() throws IOException {
        ActionAffordance action = testActionMetadata(XARM_TD, "initialize",
                CHERRY.initialize, false, false);

        assertTrue(action.getFirstFormForOperationType(TD.invokeAction).isPresent());
        Form form = action.getFirstFormForOperationType(TD.invokeAction).get();

        TDHttpRequest req = new TDHttpRequest(form, TD.invokeAction);

        if (LOG_IN_STATUS == 200) {
            addAPIKey(XARM_TD, req, LOG_IN_TOKEN);
            TDHttpResponse res = req.execute();
            assertEquals(200, res.getStatusCode());
        } else {
            TDHttpResponse res = req.execute();
            assertEquals(401, res.getStatusCode());
        }
    }

    private static void assertIsTcpMovementSchema(DataSchema schema) {
        assertTrue(schema instanceof ObjectSchema);
        assertTrue(schema.isA(CHERRY.tcpMovementSchema));

        ObjectSchema objSchema = (ObjectSchema) schema;

        assertTrue(objSchema.getFirstPropertyNameBySemnaticType(CHERRY.tcpTargetSchema).isPresent());
        assertTrue(objSchema.getFirstPropertyNameBySemnaticType(CHERRY.speed).isPresent());

        String tcpTargetName =
                objSchema.getFirstPropertyNameBySemnaticType(CHERRY.tcpTargetSchema).get();
        String speedName = objSchema.getFirstPropertyNameBySemnaticType(CHERRY.speed).get();

        assertEquals("target", tcpTargetName);
        assertEquals("speed", speedName);

        assertTrue(objSchema.hasRequiredProperty(tcpTargetName));
        assertTrue(objSchema.hasRequiredProperty(speedName));

        DataSchema tcpTargetSchema = objSchema.getProperty(tcpTargetName).get();
        DataSchema speedSchema = objSchema.getProperty(speedName).get();

        assertIsTcpTargetSchema(tcpTargetSchema);
        assertTrue(speedSchema instanceof IntegerSchema);

        assertTrue(((IntegerSchema) speedSchema).getMinimum().isPresent());
        assertTrue(((IntegerSchema) speedSchema).getMaximum().isPresent());

        assertEquals(10, ((IntegerSchema) speedSchema).getMinimum().get(), 0);
        assertEquals(400, ((IntegerSchema) speedSchema).getMaximum().get(), 0);
    }

    private static void assertIsOperatorSchema(DataSchema schema) {

        assertTrue(schema instanceof ObjectSchema);
        assertTrue(schema.isA(CHERRY.operatorSchema));

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

    private static void assertIsOperatorWithTokenSchema(DataSchema schema) {

        assertTrue(schema instanceof ObjectSchema);
        assertTrue(schema.isA(CHERRY.operatorWithTokenSchema));

        ObjectSchema objSchema = (ObjectSchema) schema;

        assertTrue(objSchema.getFirstPropertyNameBySemnaticType(FOAF.name).isPresent());
        assertTrue(objSchema.getFirstPropertyNameBySemnaticType(FOAF.mbox).isPresent());
        assertTrue(objSchema.getFirstPropertyNameBySemnaticType(CHERRY.operatorToken).isPresent());

        String nameName = objSchema.getFirstPropertyNameBySemnaticType(FOAF.name).get();
        String mboxName = objSchema.getFirstPropertyNameBySemnaticType(FOAF.mbox).get();
        String operatorTokenName = objSchema
                .getFirstPropertyNameBySemnaticType(CHERRY.operatorToken).get();

        assertEquals("name", nameName);
        assertEquals("email", mboxName);
        assertEquals("token", operatorTokenName);

        assertTrue(objSchema.hasRequiredProperty(nameName));
        assertTrue(objSchema.hasRequiredProperty(mboxName));
        assertTrue(objSchema.hasRequiredProperty(operatorTokenName));

        DataSchema nameSchema = objSchema.getProperty(nameName).get();
        DataSchema mboxSchema = objSchema.getProperty(mboxName).get();
        DataSchema operatorTokenSchema = objSchema.getProperty(operatorTokenName).get();

        assertTrue(nameSchema instanceof StringSchema);
        assertTrue(mboxSchema instanceof StringSchema);
        assertTrue(operatorTokenSchema instanceof StringSchema);
    }

    private static void assertIsTcpTargetSchema(DataSchema schema) {
        assertTrue(schema instanceof ObjectSchema);
        assertTrue(schema.isA(CHERRY.tcpTargetSchema));

        ObjectSchema objSchema = (ObjectSchema) schema;

        assertTrue(objSchema.getFirstPropertyNameBySemnaticType(CHERRY.coordinatesSchema).isPresent());
        assertTrue(objSchema.getFirstPropertyNameBySemnaticType(CHERRY.rotationSchema).isPresent());

        String cooName =
                objSchema.getFirstPropertyNameBySemnaticType(CHERRY.coordinatesSchema).get();
        String rotName = objSchema.getFirstPropertyNameBySemnaticType(CHERRY.rotationSchema).get();

        assertEquals("coordinate", cooName);
        assertEquals("rotation", rotName);

        assertTrue(objSchema.hasRequiredProperty(cooName));
        assertTrue(objSchema.hasRequiredProperty(rotName));

        DataSchema cooSchema = objSchema.getProperty(cooName).get();
        DataSchema rotSchema = objSchema.getProperty(rotName).get();

       assertIsCoordinatesSchema(cooSchema);
       assertIsRotationSchema(rotSchema);
    }

    private static void assertIsCoordinatesSchema(DataSchema schema) {
        assertTrue(schema instanceof ObjectSchema);
        assertTrue(schema.isA(CHERRY.coordinatesSchema));

        ObjectSchema objSchema = (ObjectSchema) schema;

        assertTrue(objSchema.getFirstPropertyNameBySemnaticType(CHERRY.xCoordinate).isPresent());
        assertTrue(objSchema.getFirstPropertyNameBySemnaticType(CHERRY.yCoordinate).isPresent());
        assertTrue(objSchema.getFirstPropertyNameBySemnaticType(CHERRY.zCoordinate).isPresent());

        String xCooName = objSchema.getFirstPropertyNameBySemnaticType(CHERRY.xCoordinate).get();
        String yCooName = objSchema.getFirstPropertyNameBySemnaticType(CHERRY.yCoordinate).get();
        String zCooName = objSchema.getFirstPropertyNameBySemnaticType(CHERRY.zCoordinate).get();

        assertEquals("x", xCooName);
        assertEquals("y", yCooName);
        assertEquals("z", zCooName);

        assertTrue(objSchema.hasRequiredProperty(xCooName));
        assertTrue(objSchema.hasRequiredProperty(yCooName));
        assertTrue(objSchema.hasRequiredProperty(zCooName));

        DataSchema xCooSchema = objSchema.getProperty(xCooName).get();
        DataSchema yCooSchema = objSchema.getProperty(yCooName).get();
        DataSchema zCooSchema = objSchema.getProperty(zCooName).get();

        assertTrue(xCooSchema instanceof NumberSchema);
        assertTrue(yCooSchema instanceof NumberSchema);
        assertTrue(zCooSchema instanceof NumberSchema);

        assertTrue(((NumberSchema) xCooSchema).getMinimum().isPresent());
        assertTrue(((NumberSchema) yCooSchema).getMinimum().isPresent());
        assertTrue(((NumberSchema) zCooSchema).getMinimum().isPresent());

        assertTrue(((NumberSchema) xCooSchema).getMaximum().isPresent());
        assertTrue(((NumberSchema) yCooSchema).getMaximum().isPresent());
        assertTrue(((NumberSchema) zCooSchema).getMaximum().isPresent());

        assertEquals(-720, ((NumberSchema) xCooSchema).getMinimum().get(), 0);
        assertEquals(-720, ((NumberSchema) yCooSchema).getMinimum().get(), 0);
        assertEquals(-178.3, ((NumberSchema) zCooSchema).getMinimum().get(), 0);

        assertEquals(720, ((NumberSchema) xCooSchema).getMaximum().get(), 0);
        assertEquals(720, ((NumberSchema) yCooSchema).getMaximum().get(), 0);
        assertEquals(1010, ((NumberSchema) zCooSchema).getMaximum().get(), 0);
    }

    private static void assertIsRotationSchema(DataSchema schema) {
        assertTrue(schema instanceof ObjectSchema);
        assertTrue(schema.isA(CHERRY.rotationSchema));

        ObjectSchema objSchema = (ObjectSchema) schema;

        assertTrue(objSchema.getFirstPropertyNameBySemnaticType(CHERRY.roll).isPresent());
        assertTrue(objSchema.getFirstPropertyNameBySemnaticType(CHERRY.pitch).isPresent());
        assertTrue(objSchema.getFirstPropertyNameBySemnaticType(CHERRY.yaw).isPresent());

        String rollName = objSchema.getFirstPropertyNameBySemnaticType(CHERRY.roll).get();
        String pitchName = objSchema.getFirstPropertyNameBySemnaticType(CHERRY.pitch).get();
        String yawName = objSchema.getFirstPropertyNameBySemnaticType(CHERRY.yaw).get();

        assertEquals("roll", rollName);
        assertEquals("pitch", pitchName);
        assertEquals("yaw", yawName);

        assertTrue(objSchema.hasRequiredProperty(rollName));
        assertTrue(objSchema.hasRequiredProperty(pitchName));
        assertTrue(objSchema.hasRequiredProperty(yawName));

        DataSchema rollSchema = objSchema.getProperty(rollName).get();
        DataSchema pitchSchema = objSchema.getProperty(pitchName).get();
        DataSchema yawSchema = objSchema.getProperty(yawName).get();

        assertTrue(rollSchema instanceof NumberSchema);
        assertTrue(pitchSchema instanceof NumberSchema);
        assertTrue(yawSchema instanceof NumberSchema);

        assertTrue(((NumberSchema) rollSchema).getMinimum().isPresent());
        assertTrue(((NumberSchema) pitchSchema).getMinimum().isPresent());
        assertTrue(((NumberSchema) yawSchema).getMinimum().isPresent());

        assertTrue(((NumberSchema) rollSchema).getMaximum().isPresent());
        assertTrue(((NumberSchema) pitchSchema).getMaximum().isPresent());
        assertTrue(((NumberSchema) yawSchema).getMaximum().isPresent());

        assertEquals(-180, ((NumberSchema) rollSchema).getMinimum().get(), 0);
        assertEquals(-180, ((NumberSchema) pitchSchema).getMinimum().get(), 0);
        assertEquals(-180, ((NumberSchema) yawSchema).getMinimum().get(), 0);

        assertEquals(180, ((NumberSchema) rollSchema).getMaximum().get(), 0);
        assertEquals(180, ((NumberSchema) pitchSchema).getMaximum().get(), 0);
        assertEquals(180, ((NumberSchema) yawSchema).getMaximum().get(), 0);
    }

    private static void assertIsGripper(DataSchema schema) {
        assertTrue(schema instanceof IntegerSchema);

        assertTrue(((IntegerSchema) schema).getMinimum().isPresent());
        assertTrue(((IntegerSchema) schema).getMaximum().isPresent());

        assertEquals(0, ((IntegerSchema) schema).getMinimum().get(), 0);
        assertEquals(800, ((IntegerSchema) schema).getMaximum().get(), 0);
    }
}
