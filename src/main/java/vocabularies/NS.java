package vocabularies;

import java.util.HashMap;
import java.util.Map;

public final class NS {

    public static Map<String, String> getDefaultNamespaces() {

        Map<String, String> defaultNamespaces = new HashMap<>();
        defaultNamespaces.put("td", "https://www.w3.org/2019/wot/td#");
        defaultNamespaces.put("hctl", "https://www.w3.org/2019/wot/hypermedia#");
        defaultNamespaces.put("wotsec", "https://www.w3.org/2019/wot/security#");
        defaultNamespaces.put("dct", "http://purl.org/dc/terms/");
        defaultNamespaces.put("js", "https://www.w3.org/2019/wot/json-schema#");
        return defaultNamespaces;
    }

}
