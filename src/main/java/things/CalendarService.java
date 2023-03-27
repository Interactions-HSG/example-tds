package things;

import ch.unisg.ics.interactions.wot.td.ThingDescription;
import ch.unisg.ics.interactions.wot.td.affordances.Form;
import ch.unisg.ics.interactions.wot.td.affordances.PropertyAffordance;
import ch.unisg.ics.interactions.wot.td.schemas.StringSchema;
import ch.unisg.ics.interactions.wot.td.vocabularies.TD;

public class CalendarService extends Thing {
  public CalendarService(String baseURI, String relativeURI, String title) {
    super(baseURI, relativeURI, title);
    this.namespaces.put("htv", "http://www.w3.org/2011/http#");
    this.namespaces.put("was", "https://was-course.interactions.ics.unisg.ch/wake-up-ontology#");

    Form form = new Form.Builder(baseURI + "upcoming")
            .setMethodName("GET")
            .addOperationType(TD.readProperty)
            .build();

    properties.add(new PropertyAffordance.Builder("upcoming event", form)
            .addSemanticType("https://was-course.interactions.ics.unisg.ch/wake-up-ontology#ReadUpcomingEvent")
            .addDataSchema(new StringSchema.Builder().build())
            .build());
  }

  @Override
  public ThingDescription exposeTD() {
    return new ThingDescription.Builder(title)
            .addThingURI(relativeURI)
            .addBaseURI(baseURI)
            .addSemanticType("https://was-course.interactions.ics.unisg.ch/wake-up-ontology#CalendarService")
            .addProperties(properties)
            .build();
  }
}
