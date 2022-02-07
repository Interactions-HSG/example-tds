package things;

import ch.unisg.ics.interactions.wot.td.ThingDescription;
import ch.unisg.ics.interactions.wot.td.affordances.ActionAffordance;
import ch.unisg.ics.interactions.wot.td.affordances.Form;
import ch.unisg.ics.interactions.wot.td.affordances.PropertyAffordance;
import ch.unisg.ics.interactions.wot.td.schemas.DataSchema;
import ch.unisg.ics.interactions.wot.td.schemas.ObjectSchema;
import ch.unisg.ics.interactions.wot.td.schemas.StringSchema;
import ch.unisg.ics.interactions.wot.td.security.APIKeySecurityScheme;
import ch.unisg.ics.interactions.wot.td.security.NoSecurityScheme;
import ch.unisg.ics.interactions.wot.td.vocabularies.TD;

public class TwilioServer extends Thing {
    public TwilioServer(String baseURI, String relativeURI, String title) {
        super(baseURI, relativeURI, title);
        DataSchema sendMessageSchema = new ObjectSchema.Builder()
                .addProperty("message", new StringSchema.Builder().build())
                .addProperty("phone1", new StringSchema.Builder().build())
                .addProperty("phone2", new StringSchema.Builder().build())
                .addProperty("account_sid", new StringSchema.Builder().build())
                .addProperty("api_key", new StringSchema.Builder().build())
                .addRequiredProperties("message", "phone1", "phone2", "account_sid", "api_key")
                .build();

        Form sendMessageForm = new Form.Builder(baseURI)
                .addOperationType(TD.invokeAction)
                .build();
        ActionAffordance sendMessage = new ActionAffordance.Builder("sendMessage", sendMessageForm)
                .addInputSchema(sendMessageSchema)
                .build();
        actions.add(sendMessage);

        /*Form f = new Form.Builder(baseURI+"property")
                .addOperationType(TD.readProperty)
                .build();
        PropertyAffordance p = new PropertyAffordance.Builder("property",f).build();
        properties.add(p);*/
    }

    @Override
    public ThingDescription exposeTD() {
        return new ThingDescription.Builder(title)
                .addSemanticType("http://w3id.org/eve#Artifact")
                .addSecurityScheme(new NoSecurityScheme())
                .addThingURI(relativeURI)
                .addBaseURI(baseURI)
                .addActions(actions)
                .addProperties(properties)
                .build();
    }
}
