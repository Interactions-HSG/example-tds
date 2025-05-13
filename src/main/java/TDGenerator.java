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

        Lights lights = new Lights("http://localhost:1080/api/lights/1/",
                "urn:lights", "A set of lights in a room that can be turned on or off");

        Blinds blinds = new Blinds("http://localhost:1080/api/blinds/1/",
                "urn:blinds", "A set of blinds in a room that can be raised or lowered");

        WristbandSimulator wristbandSim = new WristbandSimulator("http://localhost:1080/api/wristband/1/",
                "urn:wristband-simu", "A wristband that can be used for monitoring a user's state");

        CalendarService calendar = new CalendarService("http://localhost:1080/api/calendar/1/",
                "urn:calendar-service", "A calendar service that can be used for managing a user's events");

        MoistureDetectorTractor tractor1 = new MoistureDetectorTractor("http://localhost:1080/api/tractors/1/",
                "urn:tractor1", "A tractor that can be used for monitoring the moisture level of soil in a farm.");

        IrrigatorTractor tractor2 = new IrrigatorTractor("http://localhost:1080/api/tractors/2/",
                "urn:tractor2", "A tractor that can be used for irrigating sections in a farm.");

        InteractionsLabReal interactionsLabReal = new InteractionsLabReal("https://api.interactions.ics.unisg.ch/was/rl/",
                "urn:interactions_lab", "The lab of the Interactions team");

        InteractionsLabSimulator interactionsLab = new InteractionsLabSimulator("http://localhost:1880/was/rl/",
                "urn:interactions_lab", "The lab of the Interactions team");

        WeatherStation weatherStation = new WeatherStation(
                "https://api.interactions.ics.unisg.ch/InfluxDB?measurement=weather_station",
                "urn:rbg30_weather_station",
                "KNX Weather Station at Rosenberg 30");

        Engraver engraver = new Engraver("http://engraver.intelliot.org/services", "urn:intelliot" +
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

        writeToFile(lights.serializeTD(), "lights");
        writeToFile(blinds.serializeTD(), "blinds");
        writeToFile(wristbandSim.serializeTD(), "wristband-simu");
        writeToFile(calendar.serializeTD(), "calendar-service");
        writeToFile(tractor1.serializeTD(), "tractor1");
        writeToFile(tractor2.serializeTD(), "tractor2");
        writeToFile(interactionsLabReal.serializeTD(), "interactions-lab-real");
        writeToFile(interactionsLab.serializeTD(), "interactions-lab");
        writeToFile(weatherStation.serializeTD(), "weather-station");
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
