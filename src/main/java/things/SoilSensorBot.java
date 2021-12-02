package things;

import ch.unisg.ics.interactions.wot.td.affordances.Form;
import ch.unisg.ics.interactions.wot.td.affordances.PropertyAffordance;
import ch.unisg.ics.interactions.wot.td.schemas.ArraySchema;
import ch.unisg.ics.interactions.wot.td.vocabularies.TD;

public class SoilSensorBot extends Tractorbot {

    public SoilSensorBot(String baseURI, String relativeURI, String title) {
        super(baseURI, relativeURI, title);

        Form soilConditionForm = new Form.Builder(baseURI + "things/tractorbot/properties/soilcondition")
                .addOperationType(TD.readProperty)
                .build();

        PropertyAffordance soilCondition = new PropertyAffordance.Builder("soilCondition", soilConditionForm)
                .addSemanticType("http://semantics.interactions.ics.unisg.ch/hackathon21#SoilCondition")
                .addDataSchema(new ArraySchema.Builder().build())
                .build();

        properties.add(soilCondition);
    }
}
