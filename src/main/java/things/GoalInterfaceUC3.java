package things;

import ch.unisg.ics.interactions.wot.td.ThingDescription;
import ch.unisg.ics.interactions.wot.td.affordances.ActionAffordance;
import ch.unisg.ics.interactions.wot.td.affordances.Form;
import ch.unisg.ics.interactions.wot.td.schemas.ObjectSchema;
import ch.unisg.ics.interactions.wot.td.schemas.StringSchema;
import ch.unisg.ics.interactions.wot.td.security.APIKeySecurityScheme;

public class GoalInterfaceUC3 extends Thing {
    public GoalInterfaceUC3(String baseURI, String relativeURI, String title) {
        super(baseURI, relativeURI, title);
        Form sendNotificationForm = new Form.Builder(baseURI+"/notification")
                .setMethodName("POST")
                .build();
        ActionAffordance sendNotification = new ActionAffordance.Builder("sendNotification", sendNotificationForm)
                .addInputSchema(new ObjectSchema.Builder()
                        .addProperty("status", new StringSchema.Builder().build())
                        .addProperty("custom", new StringSchema.Builder().build())
                        .addRequiredProperties("status")
                        .build())
                .build();
        actions.add(sendNotification);
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
