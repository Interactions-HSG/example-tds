package things;

import ch.unisg.ics.interactions.wot.td.ThingDescription;
import ch.unisg.ics.interactions.wot.td.affordances.ActionAffordance;
import ch.unisg.ics.interactions.wot.td.affordances.EventAffordance;
import ch.unisg.ics.interactions.wot.td.affordances.PropertyAffordance;
import ch.unisg.ics.interactions.wot.td.io.TDGraphWriter;
import vocabularies.NS;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class Thing {

    protected final String baseURI;
    protected final String relativeURI;
    protected final String title;

    Map<String, String> namespaces = NS.getDefaultNamespaces();
    List<PropertyAffordance> properties = new ArrayList<>();
    List<ActionAffordance> actions = new ArrayList<>();
    List<EventAffordance> events = new ArrayList<>();

    Thing(String baseURI, String relativeURI, String title) {
        this.baseURI = baseURI;
        this.relativeURI = relativeURI;
        this.title = title;
    }

    public abstract ThingDescription exposeTD();

    public String serializeTD() {
        ThingDescription td = exposeTD();
        TDGraphWriter writer = new TDGraphWriter(td);
        namespaces.forEach((prefix, namespace) -> writer.setNamespace(prefix, namespace));
        return writer.write();
    }

    public String getBaseURI() {
        return baseURI;
    }

    public String getRelativeURI() {
        return relativeURI;
    }

    public String getTitle() {
        return title;
    }

    public Map<String, String> getNamespaces() {
        return namespaces;
    }

    public List<PropertyAffordance> getProperties() {
        return properties;
    }

    public List<ActionAffordance> getActions() {
        return actions;
    }

    public List<EventAffordance> getEvents() {
        return events;
    }
}
