package things;

import ch.unisg.ics.interactions.wot.td.ThingDescription;
import ch.unisg.ics.interactions.wot.td.affordances.ActionAffordance;
import ch.unisg.ics.interactions.wot.td.affordances.Form;
import ch.unisg.ics.interactions.wot.td.schemas.StringSchema;
import org.eclipse.rdf4j.model.vocabulary.DCTERMS;

import static org.eclipse.rdf4j.model.util.Values.iri;
import static org.eclipse.rdf4j.model.util.Values.literal;

public class DLTClient extends Thing{


    public DLTClient(String baseURI, String relativeURI, String title) {
        super(baseURI, relativeURI, title);

        Form sendTransactionForm = new Form.Builder(baseURI)
                .setMethodName("POST")
                .build();

        ActionAffordance sendTransaction = new ActionAffordance.Builder("sendTransaction", sendTransactionForm)
                .build();

        actions.add(sendTransaction);

        Form getTransactionForm = new Form.Builder(baseURI)
                .setMethodName("GET")
                .build();

        ActionAffordance getTransaction = new ActionAffordance.Builder("getTransaction", getTransactionForm)
                .build();

        actions.add(getTransaction);
    }

    @Override
    public ThingDescription exposeTD() {
        return new ThingDescription.Builder(title)
                .addSemanticType("http://w3id.org/eve#Artifact")
                .addSemanticType("http://example.org/intellIoT#EngraverMachine")
                .addProperties(properties)
                .addActions(actions)
                .addThingURI(relativeURI)
                .addTriple(iri(relativeURI), DCTERMS.DESCRIPTION,
                        literal("An engraver machine used on the IntellIoT project"))
                .build();
    }
}
