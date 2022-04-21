package things;

import ch.unisg.ics.interactions.wot.td.ThingDescription;
import ch.unisg.ics.interactions.wot.td.affordances.EventAffordance;
import ch.unisg.ics.interactions.wot.td.affordances.Form;
import ch.unisg.ics.interactions.wot.td.schemas.DataSchema;
import ch.unisg.ics.interactions.wot.td.schemas.ObjectSchema;
import ch.unisg.ics.interactions.wot.td.schemas.StringSchema;
import ch.unisg.ics.interactions.wot.td.security.APIKeySecurityScheme;

public class TractorController extends Thing{
    TractorController(String baseURI, String relativeURI, String title) {
        super(baseURI, relativeURI, title);

        Form subscribeTopicForm = new Form.Builder("/subscribeTopic")
                .addOperationType("subscribeevent")
                .setMethodName("PUT")
                .setContentType("application/json")
                .addSubProtocol("websocket")
                .build();

        DataSchema subscribeTopicNotificationSchema = new ObjectSchema.Builder()
                .addProperty("receiver", new StringSchema.Builder().build())
                .addProperty("msg", new StringSchema.Builder().build())
                .addRequiredProperties("receiver", "msg")
                .build();

        EventAffordance subscribeTopic = new EventAffordance.Builder("subscribeTopic", subscribeTopicForm)
                .addNotificationSchema(subscribeTopicNotificationSchema)
                .build();
    }

    @Override
    public ThingDescription exposeTD() {
        return new ThingDescription.Builder(title)
                .addSecurityScheme(new APIKeySecurityScheme(APIKeySecurityScheme
                        .TokenLocation.HEADER, "X-API-Key"))
                .addThingURI(relativeURI)
                .addBaseURI(baseURI)
                .addActions(actions)
                .addProperties(properties)
                .addEvents(events)
                .build();
    }
}
