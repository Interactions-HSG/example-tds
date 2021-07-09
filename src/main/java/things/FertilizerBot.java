package things;

import ch.unisg.ics.interactions.wot.td.affordances.ActionAffordance;
import ch.unisg.ics.interactions.wot.td.affordances.Form;
import ch.unisg.ics.interactions.wot.td.schemas.NumberSchema;

import java.util.Arrays;

public class FertilizerBot extends Tractorbot {

    public FertilizerBot(String baseURI, String relativeURI, String title) {
        super(baseURI, relativeURI, title);

        Form irrigateForm = new Form.Builder(baseURI + "things/tractorbot/actions/irrigate")
                .setMethodName("POST")
                .build();

        Form fertilizeForm = new Form.Builder(baseURI + "things/tractorbot/actions/fertilize")
                .setMethodName("POST")
                .build();

        ActionAffordance irrigate = new ActionAffordance.Builder(irrigateForm)
                .addName("irrigate")
                .addTitle("Irrigation")
                .addSemanticType("http://semantics.interactions.ics.unisg.ch/hackathon21#IrrigationAction")
                .addInputSchema(new NumberSchema.Builder().build())
                .build();

        ActionAffordance fertilize = new ActionAffordance.Builder(fertilizeForm)
                .addName("fertilize")
                .addTitle("Fertilization")
                .addSemanticType("http://semantics.interactions.ics.unisg.ch/hackathon21#FertilizeAction")
                .addInputSchema(new NumberSchema.Builder().build())
                .build();

        actions.addAll(Arrays.asList(fertilize, irrigate));
    }

}
