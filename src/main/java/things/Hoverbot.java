package things;

import ch.unisg.ics.interactions.wot.td.ThingDescription;
import ch.unisg.ics.interactions.wot.td.affordances.ActionAffordance;
import ch.unisg.ics.interactions.wot.td.affordances.Form;
import ch.unisg.ics.interactions.wot.td.affordances.PropertyAffordance;
import ch.unisg.ics.interactions.wot.td.schemas.StringSchema;
import ch.unisg.ics.interactions.wot.td.vocabularies.TD;

public class Hoverbot extends Thing {

    public Hoverbot(String baseURI, String relativeURI, String title) {
        super(baseURI, relativeURI, title);
        this.namespaces.put("htv", "http://www.w3.org/2011/http#");
        this.namespaces.put("hsg", "http://semantics.interactions.ics.unisg.ch/hackathon21#");
    }

    public void initialize() {
        Form streamForm = new Form.Builder(baseURI + "stream")
                .addOperationType(TD.readProperty)
                .setMethodName("GET")
                .setContentType("video/mpeg")
                .build();

        Form takeSnapshotForm = new Form.Builder(baseURI + "capture")
                .addOperationType(TD.invokeAction)
                .setContentType("video/mpeg")
                .build();

        properties.add(new PropertyAffordance.Builder("stream", streamForm)
                .addSemanticType("http://semantics.interactions.ics.unisg.ch/hackathon21#MpegVideoStream")
                .addDataSchema(new StringSchema.Builder().build())
                .build());

        actions.add(new ActionAffordance.Builder("takeSnapshot", takeSnapshotForm)
                .addSemanticType("http://semantics.interactions.ics.unisg.ch/hackathon21#Image")
                .build());
    }

    @Override
    public ThingDescription exposeTD() {
        return new ThingDescription.Builder(title)
                .addThingURI(relativeURI)
                .addBaseURI(baseURI)
                .addSemanticType("http://semantics.interactions.ics.unisg.ch/hackathon21#Camera")
                .addProperties(properties)
                .addActions(actions)
                .build();
    }
}
