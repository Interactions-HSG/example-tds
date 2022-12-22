package things;

import ch.unisg.ics.interactions.wot.td.ThingDescription;
import ch.unisg.ics.interactions.wot.td.affordances.ActionAffordance;
import ch.unisg.ics.interactions.wot.td.affordances.Form;
import ch.unisg.ics.interactions.wot.td.schemas.ArraySchema;
import ch.unisg.ics.interactions.wot.td.schemas.ObjectSchema;
import ch.unisg.ics.interactions.wot.td.schemas.StringSchema;

public class ImageAnnotator extends Thing {

  private static ObjectSchema outputSchema;

  static {
    outputSchema = new ObjectSchema.Builder()
            .addSemanticType("http://www.w3.org/ns/oa#Annotation")
            .addProperty("id",  new StringSchema.Builder()
                    .addSemanticType("http://www.w3.org/1999/02/22-rdf-syntax-ns#ID")
                    .build())
            .addProperty("created", new StringSchema.Builder()
                    .addSemanticType("http://www.w3.org/ns/oa#created")
                    .build())
            .addProperty("updated", new StringSchema.Builder()
                    .addSemanticType("http://www.w3.org/ns/oa#updated")
                    .build())
            .addProperty("uri", new StringSchema.Builder()
                    .addSemanticType("http://purl.org/dc/dcmitype/StillImage")
                    .build())
            .addProperty("text", new StringSchema.Builder()
                    .addSemanticType("http://purl.org/dc/dcmitype/Text")
                    .build())
            .addProperty("target", new ArraySchema.Builder()
                    .addSemanticType("http://www.w3.org/ns/oa#hasTarget")
                    .addItem(new ObjectSchema.Builder()
                            .addSemanticType("http://www.w3.org/ns/oa#SpecificResource")
                            .addProperty("source", new StringSchema.Builder()
                                    .addSemanticType("http://www.w3.org/ns/oa#hasSource")
                                    .build())
                            .build())
                    .build())
            .build();
  }

  public ImageAnnotator(String baseURI, String relativeURI, String title) {
    super(baseURI, relativeURI, title);
    this.namespaces.put("htv", "http://www.w3.org/2011/http#");
    this.namespaces.put("as", "http://www.w3.org/ns/activitystreams#");
    this.namespaces.put("dctypes", "http://purl.org/dc/dcmitype/");
    this.namespaces.put("hmas", "https://purl.org/hmas/core#");
    this.namespaces.put("tod", "http://example.org/tackling-online-disinformation/ontology#");
    this.namespaces.put("oa", "http://www.w3.org/ns/oa#");
    this.namespaces.put("rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");

    Form annotateForm = new Form.Builder(baseURI + "/annotations")
            .build();

    ActionAffordance annotateAction = new ActionAffordance.Builder("annotate image", annotateForm)
            .addTitle("Annotate Image")
            .addSemanticType("http://example.org/tackling-online-disinformation/ontology#AnnotateImage")
            .addInputSchema(new ObjectSchema.Builder()
                    .addProperty("imageUrl", new StringSchema.Builder()
                            .addSemanticType("http://purl.org/dc/dcmitype/StillImage")
                            .build())
                    .build())
            .addOutputSchema(outputSchema)
            .build();

    actions.add(annotateAction);
  }

  public static ObjectSchema getOutputSchema() {
    return outputSchema;
  }

  @Override
  public ThingDescription exposeTD() {
    return new ThingDescription.Builder(title)
            .addThingURI(relativeURI)
            .addSemanticType("https://purl.org/hmas/core#Artifact")
            .addSemanticType("http://www.w3.org/ns/activitystreams#Application")
            .addBaseURI(baseURI)
            .addActions(actions)
            .build();
  }
}
