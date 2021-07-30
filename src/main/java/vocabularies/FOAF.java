package vocabularies;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;

// FOAF Vocabulary
public final class FOAF {

    public static final String PREFIX = "http://xmlns.com/foaf/0.1/";

    public static final String agent = PREFIX + "Agent";
    public static final String mbox = PREFIX + "Mbox";
    public static final String name = PREFIX + "Name";

    public static IRI createIRI(String fragment) {
        return SimpleValueFactory.getInstance().createIRI(PREFIX + fragment);
    }
}
