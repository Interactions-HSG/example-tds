package things;

import ch.unisg.ics.interactions.wot.td.ThingDescription;
import ch.unisg.ics.interactions.wot.td.io.TDGraphWriter;
import vocabularies.NS;

import java.util.Map;

class Thing {

    final ThingDescription td;
    Map<String, String> namespaces;

    Thing(ThingDescription td) {
        this.td = td;
        this.namespaces = NS.getDefaultNamespaces();
    }

    public String serialize() {
        TDGraphWriter writer = new TDGraphWriter(td);
        namespaces.forEach((prefix, namespace) -> writer.setNamespace(prefix, namespace));
        return writer.write();
    }
}
