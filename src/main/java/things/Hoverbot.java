package things;

import ch.unisg.ics.interactions.wot.td.ThingDescription;
import ch.unisg.ics.interactions.wot.td.affordances.ActionAffordance;
import ch.unisg.ics.interactions.wot.td.affordances.Form;
import ch.unisg.ics.interactions.wot.td.affordances.PropertyAffordance;
import ch.unisg.ics.interactions.wot.td.schemas.StringSchema;
import ch.unisg.ics.interactions.wot.td.vocabularies.TD;

public class Hoverbot extends Thing{

    Hoverbot(ThingDescription td) {
        super(td);
        this.namespaces.put("htv", "http://www.w3.org/2011/http#");
        this.namespaces.put("hsg", "http://semantics.interactions.ics.unisg.ch/hackathon21#");
    }

    public static Hoverbot instantiate(String baseURI, String relativeURI, String title) {

        Form streamForm = new Form.Builder(baseURI + "stream")
                .addOperationType(TD.readProperty)
                .setMethodName("GET")
                .setContentType("video/mpeg")
                .build();

        Form takeSnapshotForm = new Form.Builder(baseURI + "capture")
                .addOperationType(TD.invokeAction)
                .setContentType("video/mpeg")
                .build();

        PropertyAffordance stream = new PropertyAffordance.Builder(new StringSchema.Builder().build(), streamForm)
                .addName("stream")
                .addSemanticType("http://semantics.interactions.ics.unisg.ch/hackathon21#MpegVideoStream")
                .build();

        ActionAffordance takeSnapshot = new ActionAffordance.Builder(takeSnapshotForm)
                .addName("takeSnapshot")
                .addSemanticType("http://semantics.interactions.ics.unisg.ch/hackathon21#Image")
                .build();

        ThingDescription td = (new ThingDescription.Builder(title))
                .addThingURI(relativeURI)
                .addBaseURI(baseURI)
                .addSemanticType("http://semantics.interactions.ics.unisg.ch/hackathon21#Camera")
                .addProperty(stream)
                .addAction(takeSnapshot)
                .build();

        return new Hoverbot(td);
    }
}
