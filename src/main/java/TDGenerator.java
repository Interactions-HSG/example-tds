import ch.unisg.ics.interactions.wot.td.ThingDescription;
import ch.unisg.ics.interactions.wot.td.affordances.ActionAffordance;
import ch.unisg.ics.interactions.wot.td.affordances.Form;
import ch.unisg.ics.interactions.wot.td.affordances.PropertyAffordance;
import ch.unisg.ics.interactions.wot.td.clients.TDHttpRequest;
import ch.unisg.ics.interactions.wot.td.clients.TDHttpResponse;
import ch.unisg.ics.interactions.wot.td.io.TDGraphReader;
import ch.unisg.ics.interactions.wot.td.schemas.DataSchema;
import ch.unisg.ics.interactions.wot.td.schemas.ObjectSchema;
import ch.unisg.ics.interactions.wot.td.vocabularies.TD;
import things.*;
import vocabularies.CHERRY;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

public class TDGenerator {

    public static final String TD_URL = "https://raw.githubusercontent.com/Interactions-HSG/retreat_2021_hackathon/main/SemanticData/tractorbot.ttl?token=AHE5LC226AVXLB7FQJPPHSLA5YCXW";

    static Logger log = Logger.getLogger(TDGenerator.class.getName());

    public static void main(String[] args) {
        System.out.println("Hello World");

        RobotController  uRxRobotController = new RobotController("http://micro1.fritz.box:38080/robot-controller/",
                "urn:intelliot-urx-robot-controller", "URx Robot Controller");

        Engraver engraver = new Engraver("http://micro1.fritz.box:38080/engraver-laser/api", "urn:intelliot" +
                "-engraver",
                "Engraver machine");

        CupProvider cupProvider = new CupProvider("http://localhost:1080/", "urn:cup_provider",
                "cupProvider");

        MiroGate miroGate = new MiroGate("coap://130.82.171.10:5683", "urn:mirogate",
                "Mirogate");


        FertilizerBot uhura = new FertilizerBot("http://10.10.10.106/", "urn:tractorbot_uhura", "Smart tractor");

        SoilSensorBot spock = new SoilSensorBot("http://10.10.10.103/", "urn:tractorbot_spock", "Smart tractor");

        Hoverbot hoverBot = new Hoverbot("http://10.10.10.105/", "urn:hoverbot_garuda", "hoverbot");

        PhantomXReactor leubot1 = new PhantomXReactor("https://api.interactions.ics.unisg.ch/leubot1/v1.3.4/",
                "http://yggdrasil.interactions.ics.unisg.ch/environments/61/workspaces/102/artifacts/leubot1", "leubot1");

        PhantomXReactor leubot2 = new PhantomXReactor("https://api.interactions.ics.unisg.ch/leubot2/v1.3.4/",
                "http://yggdrasil.interactions.ics.unisg.ch/environments/61/workspaces/102/artifacts/leubot2", "leubot2");

        XArm cherryBot = new XArm("https://api.interactions.ics.unisg.ch/cherrybot/", "urn:cherrybot", "cherryBot");

        XArm pretendaBot = new XArm("https://api.interactions.ics.unisg.ch/pretendabot/", "urn:pretendabot", "pretendBot");

        TwilioServer twilioServer = new TwilioServer("http://localhost:591/", "http://localhost:8080/environments/intelliot/workspaces/wksp1/artifacts/twilio", "twilioServer");

        HILService hilService = new HILService("http://micro1.fritz.box:38080/hil-service/service", "urn:intelliot-hil", "hilService");

        AIService aiService = new AIService("http://micro1.fritz.box:38080/camera-ai", "urn:intelliot-ai", "aiService");

        AIService aiServiceAWS = new AIService("http://apps.intelliot.siemens.cloud/camera-ai", "urn:intelliot-ai", "aiService");

        RobotController robotControllerAWS =  new RobotController("http://apps.intelliot.siemens.cloud/robot-controller/",
                "urn:intelliot-urx-robot-controller", "URx Robot Controller");

        Engraver engraverAWS = new Engraver("http://apps.intelliot.siemens.cloud/engraver-laser/api", "urn:intelliot" +
                "-engraver",
                "Engraver machine");

        HILService hilServiceAWS = new HILService("http://apps.intelliot.siemens.cloud/hil-service/service", "urn:intelliot-hil", "hilService");

        Engraver2 engraver2AWS = new Engraver2("http://apps.intelliot.siemens.cloud/engraver-laser/api", "urn:intelliot-engraver",
                "Engraver machine");

        Engraver2 engraver2 = new Engraver2("http://micro1.fritz.box:38080/engraver-laser/api", "urn:intelliot-engraver",
                "Engraver machine");


        EngraverActuators engraverActuatorsAWS = new EngraverActuators("http://apps.intelliot.siemens.cloud/engraver-laser/actuator-api", "urn:intelliot-actuators", "Engraver Actuators");

        EngraverActuators engraverActuators = new EngraverActuators("http://micro1.fritz.box:38080/engraver-laser/actuator-api", "urn:intelliot-actuators", "Engraver Actuators");


        MillingMachine millingMachine = new MillingMachine("http://micro1.fritz.box:38080/engraver-milling", "http://edge.fritz.box:8888/workspaces/uc1/artifacts/milling",
                "Milling machine");

        //TractorController tractorController = new TractorController("http://example.org/tractor_controller", "urn:tractor_controller","Tractor Controller");

        TractorController tractorController = new TractorController("http://192.168.55.1", "http://localhost:8080/workspaces/uc1/artifacts/tractor_controller","Tractor Controller");

        WaypointService1 waypointService1 = new WaypointService1("http://localhost:80", "http://localhost:8080/workspaces/uc1/artifacts/waypoint_service", "Waypoint Service");

        WaypointService waypointService = new WaypointService("http://localhost:80", "http://localhost:8080/workspaces/uc1/artifacts/waypoint_service", "Waypoint Service");

        UC1HILService uc1HILService = new UC1HILService("http://localhost:80/hil-service/services","http://localhost:8080/workspaces/uc1/artifacts/hil_service", "HIL Service" );

        //IKHRoboticPlatform ikhRoboticPlatform = new IKHRoboticPlatform("85.74.232.128:3001", "http://localhost:8080/workspaces/uc1/artifacts/ikh_robotic_platform", "IKH Robotic Platform");

        IKHRoboticPlatform ikhRoboticPlatform = new IKHRoboticPlatform("http://10.42.0.1:3001", "http://localhost:8080/workspaces/uc1/artifacts/ikh_robotic_platform", "IKH Robotic Platform");

        HotWater hotWater =  new HotWater("http://localhost", "http://localhost","Hot Water");

        RobotController2 vmRobot = new RobotController2("http://130.82.171.209:1080/robot-controller", "http://localhost:8080/workspaces/uc3/artifacts/robot-controller", "Robot Controller");

        HILService vmHil = new HILService("http://130.82.171.209:1081", "http://localhost:8080/workspaces/uc3/artifacts/hil-service", "HIL Service");

        AIService vmAI = new AIService("http://130.82.171.209:1082", "http://localhost:8080/workspaces/uc3/artifacts/camera-ai", "Camera AI");

        Engraver2 vmEngraver = new Engraver2("http://130.82.171.209:1083", "http://localhost:8080/workspaces/uc3/artifacts/engraver", "Engraver");

        EngraverActuators vmActuators = new EngraverActuators("http://130.82.171.209:1084","http://localhost:8080/workspaces/uc3/artifacts/actuators", "Actuators" );


        RobotController2 uc3Robot = new RobotController2("http://micro1.fritz.box:38080/robot-controller/robot-controller", "http://localhost:8080/workspaces/uc3/artifacts/robot-controller", "Robot Controller");

        HILService uc3Hil = new HILService("http://micro1.fritz.box:38080/hil-service/service", "http://localhost:8080/workspaces/uc3/artifacts/hil-service", "HIL Service");

        AIService uc3AI = new AIService("http://micro1.fritz.box:38080/camera-ai", "http://localhost:8080/workspaces/uc3/artifacts/camera-ai", "Camera AI");

        Engraver2 uc3Engraver = new Engraver2("http://micro1.fritz.box:38080/engraver-laser/api", "http://localhost:8080/workspaces/uc3/artifacts/engraver", "Engraver");

        EngraverActuators uc3Actuators = new EngraverActuators("http://micro1.fritz.box:38080/engraver-laser/actuator-api","http://localhost:8080/workspaces/uc3/artifacts/actuators", "Actuators" );


        RobotController2 uc3RobotLocalhostMockserver = new RobotController2("http://localhost:8011/robot-controller", "http://localhost:8080/workspaces/uc3/artifacts/robot-controller", "Robot Controller");

        HILService uc3HilLocalhostMockserver = new HILService("http://localhost:8011/hil-service/service", "http://localhost:8080/workspaces/uc3/artifacts/hil-service", "HIL Service");

        AIService uc3AILocalhostMockserver = new AIService("http://localhost:8011/camera-ai", "http://localhost:8080/workspaces/uc3/artifacts/camera-ai", "Camera AI");

        Engraver2 uc3EngraverLocalhostMockserver = new Engraver2("http://localhost:8011/engraver-laser/api", "http://localhost:8080/workspaces/uc3/artifacts/engraver", "Engraver");

        EngraverActuators uc3ActuatorsLocalhostMockserver = new EngraverActuators("http://localhost:8011/engraver-laser/actuator-api","http://localhost:8080/workspaces/uc3/artifacts/actuators", "Actuators" );


        DLTClient localDLTClient = new DLTClient("http://localhost:9090", "http://localhost:8080/workspaces/uc1/artifacts/dlt-client", "DLT Client");

        DLTClient uc3DLTClient = new DLTClient("http://edge.fritz.box:9090", "http://edge.fritz.box:8888/workspaces/uc1/artifacts/dlt-client", "DLT Client");

        TestServer testServer = new TestServer("http://localhost:80", "http://localhost:8080/workspaces/test/artifacts/test-artifact", "Test Artifact");

        GoalInterfaceUC3 goalInterfaceUC3 = new GoalInterfaceUC3("http://localhost:5000", "http://localhost:8080/workspaces/uc3/artifacts/goal-interface", "Goal Interface UC3");

        IOBox ioBox = new IOBox("http://micro1.fritz.box:38080/", "http://edge.fritz.box:8080/workspaces/uc3/artifacts/iobox", "Interoperability Box");

        writeToFile(uRxRobotController.serializeTD(), "urx-robot-controller");
        writeToFile(engraver.serializeTD(), "engraver");
        writeToFile(cupProvider.serializeTD(), "cup-provider");
        writeToFile(spock.serializeTD(), "spock-tractorbot");
        writeToFile(uhura.serializeTD(), "uhura-tractorbot");
        writeToFile(hoverBot.serializeTD(), "hoverbot");
        writeToFile(miroGate.serializeTD(), "mirogate");
        writeToFile(leubot1.serializeTD(), "leubot1");
        writeToFile(leubot2.serializeTD(), "leubot2");
        writeToFile(cherryBot.serializeTD(), "cherrybot");
        writeToFile(pretendaBot.serializeTD(), "pretendabot");
        writeToFile(twilioServer.serializeTD(), "twilio_server");
        writeToFile(hilService.serializeTD(), "hil_service");
        writeToFile(aiService.serializeTD(), "ai_service");
        writeToFile(aiServiceAWS.serializeTD(), "ai_service_aws");
        writeToFile(robotControllerAWS.serializeTD(), "robot_controller_aws");
        writeToFile(engraverAWS.serializeTD(), "engraver_aws");
        writeToFile(hilServiceAWS.serializeTD(), "hil_service_aws");
        writeToFile(engraver2AWS.serializeTD(), "engraver2_aws");
        writeToFile(engraverActuatorsAWS.serializeTD(), "engraver_actuators_aws");
        writeToFile(engraverActuators.serializeTD(), "engraver_actuators");
        writeToFile(engraver2.serializeTD(), "engraver2");
        writeToFile(millingMachine.serializeTD(), "milling_machine");
        writeToFile(tractorController.serializeTD(), "tractor_controller");
        writeToFile(waypointService1.serializeTD(), "waypoint_service1");
        writeToFile(waypointService.serializeTD(), "waypoint_service");
        writeToFile(uc1HILService.serializeTD(), "uc1_hil-service");
        writeToFile(ikhRoboticPlatform.serializeTD(), "ikh_robotic_platform");
        writeToFile(hotWater.serializeTD(), "hot_water");
        writeToFile(vmRobot.serializeTD(), "vm_robot");
        writeToFile(vmHil.serializeTD(), "vm_hil");
        writeToFile(vmAI.serializeTD(), "vm_ai");
        writeToFile(vmEngraver.serializeTD(), "vm_engraver");
        writeToFile(vmActuators.serializeTD(), "vm_actuators");
        writeToFile(uc3Robot.serializeTD(), "uc3_robot");
        writeToFile(uc3Hil.serializeTD(), "uc3_hil");
        writeToFile(uc3AI.serializeTD(), "uc3_ai");
        writeToFile(uc3Engraver.serializeTD(), "uc3_engraver");
        writeToFile(uc3Actuators.serializeTD(), "uc3_actuators");
        //Localhost mockserver uc3
        writeToFile(uc3RobotLocalhostMockserver.serializeTD(), "uc3_robot_localhost_mockserver");
        writeToFile(uc3HilLocalhostMockserver.serializeTD(), "uc3_hil_localhost_mockserver");
        writeToFile(uc3AILocalhostMockserver.serializeTD(), "uc3_ai_localhost_mockserver");
        writeToFile(uc3EngraverLocalhostMockserver.serializeTD(), "uc3_engraver_localhost_mockserver");
        writeToFile(uc3ActuatorsLocalhostMockserver.serializeTD(), "uc3_actuators_localhost_mockserver");

        writeToFile(localDLTClient.serializeTD(), "local_dlt_client");
        writeToFile(uc3DLTClient.serializeTD(), "uc3_dlt_client");
        writeToFile(testServer.serializeTD(), "test_server.ttl");
        writeToFile(goalInterfaceUC3.serializeTD(), "goal_interface_uc3.ttl");
        writeToFile(ioBox.serializeTD(), "iobox.ttl");

        try {
            //Read TD from file
            ThingDescription pretendabotTD = TDGraphReader.readFromFile(ThingDescription.TDFormat.RDF_TURTLE,
                    "tds/pretendaBot.ttl");

            // Uncomment for exploiting an action affordance of pretendabot
            // invokePostOperator(pretendabotTD);

            // Uncomment for exploiting a property affordance of pretendabot
            // readOperator(pretendabotTD);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void readOperator(ThingDescription pretendabotTD) throws IOException {
        //Get property affordance http://www.example.org/cherrybot#GetOperator
        Optional<PropertyAffordance> p = pretendabotTD.getFirstPropertyBySemanticType(CHERRY.operator);
        if (p.isPresent()) {
            PropertyAffordance getOperator = p.get();

            //Get form for operation type https://www.w3.org/2019/wot/td#readProperty
            Optional<Form> f = getOperator.getFirstFormForOperationType(TD.readProperty);
            if (f.isPresent()) {
                Form postOperatorForm = f.get();

                //Execute HTTP request
                TDHttpRequest req = new TDHttpRequest(postOperatorForm, TD.readProperty);
                log.info(req.toString());

                TDHttpResponse resp = req.execute();
                int statusCode = resp.getStatusCode();

                //Get response payload for schema http://www.example.org/cherrybot#OperatorWithToken
                if (statusCode == 200 && getOperator.getSemanticTypes().contains(CHERRY.operatorWithTokenSchema)) {
                    ObjectSchema schema = (ObjectSchema) getOperator.getDataSchema();
                    Map<String, Object> operatorWithToken = resp.getPayloadAsObject(schema);

                    operatorWithToken.forEach((name, value) -> {
                        log.info(name + ": " + value);
                    });

                }
            }
        }
    }

    private static void invokePostOperator(ThingDescription pretendabotTD) throws IOException {
        //Get property affordance http://www.example.org/cherrybot#PostOperator
        Optional<ActionAffordance> a = pretendabotTD.getFirstActionBySemanticType(CHERRY.registerOperator);
        if (a.isPresent()) {
            ActionAffordance postOperator = a.get();

            //Get form for operation type https://www.w3.org/2019/wot/td#invokeAction
            Optional<Form> f = postOperator.getFirstFormForOperationType(TD.invokeAction);
            if (f.isPresent()) {
                Form postOperatorForm = f.get();

                //Create request payload for schema http://www.example.org/cherrybot#Operator
                Optional<DataSchema> s = postOperator.getInputSchema();
                if (s.isPresent() && s.get().getSemanticTypes().contains(CHERRY.operatorSchema)) {
                    ObjectSchema inputSchema = (ObjectSchema) s.get();
                    Map<String, Object> payload = new HashMap<>();
                    payload.put("name", "Danai V");
                    payload.put("email", "danai.vachtsevanou@unish.ch");

                    //Execute HTTP request
                    TDHttpRequest req = new TDHttpRequest(postOperatorForm, TD.invokeAction);
                    req.setObjectPayload(inputSchema, payload);
                    log.info(req.toString());

                    req.execute();
                }
            }
        }
    }

    public static void writeToFile(String description, String fileName) {
        try {
            FileWriter fr = new FileWriter("tds/" + fileName + ".ttl", false);
            BufferedWriter br = new BufferedWriter(fr);
            br.write(description);
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
