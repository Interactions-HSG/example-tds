package things;

import ch.unisg.ics.interactions.wot.td.ThingDescription;
import ch.unisg.ics.interactions.wot.td.affordances.ActionAffordance;
import ch.unisg.ics.interactions.wot.td.affordances.Form;
import ch.unisg.ics.interactions.wot.td.affordances.PropertyAffordance;
import ch.unisg.ics.interactions.wot.td.clients.TDHttpRequest;
import ch.unisg.ics.interactions.wot.td.schemas.DataSchema;
import ch.unisg.ics.interactions.wot.td.security.APIKeySecurityScheme;
import ch.unisg.ics.interactions.wot.td.security.SecurityScheme;
import ch.unisg.ics.interactions.wot.td.vocabularies.TD;

import java.util.Optional;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public final class ThingTestUtil {

    static void addAPIKey(ThingDescription td, TDHttpRequest req, String token) {
        SecurityScheme securityScheme = td.getSecuritySchemes().get(0);
        req.setAPIKey((APIKeySecurityScheme) securityScheme, token);
    }

    static ActionAffordance testActionMetadata(ThingDescription td, String name, String type,
                                               boolean hasInputSchema,
                                               boolean hasOutputSchema) {
        Optional<ActionAffordance> actionByName = td.getActionByName(name);
        Optional<ActionAffordance> actionByType =
                td.getFirstActionBySemanticType(type);

        assertTrue(actionByName.isPresent());
        assertTrue(actionByType.isPresent());

        Optional<Form> formByName = actionByName.get().getFirstForm();
        Optional<Form> formByType = actionByType.get().getFirstForm();

        assertTrue(formByName.isPresent());
        assertTrue(formByType.isPresent());


        Optional<DataSchema> inputSchemaByName = actionByName.get().getInputSchema();
        Optional<DataSchema> inputSchemaByType = actionByType.get().getInputSchema();

        assertEquals(hasInputSchema, inputSchemaByName.isPresent());
        assertEquals(hasInputSchema, inputSchemaByType.isPresent());

        Optional<DataSchema> outputSchemaByName = actionByName.get().getOutputSchema();
        Optional<DataSchema> outputSchemaByType = actionByType.get().getOutputSchema();

        assertEquals(hasOutputSchema, outputSchemaByName.isPresent());
        assertEquals(hasOutputSchema, outputSchemaByType.isPresent());

        return actionByName.get();
    }

    static PropertyAffordance testPropertyMetadata(ThingDescription td, String name, String type,
                                                   boolean hasSchema) {
        Optional<PropertyAffordance> propertyByName = td.getPropertyByName(name);
        Optional<PropertyAffordance> propertyByType =
                td.getFirstPropertyBySemanticType(type);

        assertTrue(propertyByName.isPresent());
        assertTrue(propertyByType.isPresent());

        Optional<Form> formByName =
                propertyByName.get().getFirstFormForOperationType(TD.readProperty);
        Optional<Form> formByType =
                propertyByType.get().getFirstFormForOperationType(TD.readProperty);

        assertTrue(formByName.isPresent());
        assertTrue(formByType.isPresent());

        if (hasSchema) {
            DataSchema schemaByName = propertyByName.get().getDataSchema();
            DataSchema schemaByType = propertyByType.get().getDataSchema();

            assertNotEquals(DataSchema.EMPTY, schemaByName.getDatatype());
            assertNotEquals(DataSchema.EMPTY, schemaByType.getDatatype());
        }

        return propertyByName.get();
    }

}
