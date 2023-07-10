package openapi;


import ch.unisg.ics.interactions.wot.td.affordances.ActionAffordance;
import ch.unisg.ics.interactions.wot.td.affordances.Form;
import ch.unisg.ics.interactions.wot.td.affordances.InteractionAffordance;
import ch.unisg.ics.interactions.wot.td.affordances.PropertyAffordance;
import ch.unisg.ics.interactions.wot.td.schemas.DataSchema;
import io.swagger.parser.OpenAPIParser;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.media.*;
import io.swagger.v3.parser.core.models.SwaggerParseResult;

import java.util.*;

public class OpenTD {

    public String convert(String url){
        SwaggerParseResult result = new OpenAPIParser().readLocation(url, null, null);
        OpenAPI api = result.getOpenAPI();
        Map<String, Schema> apiSchemas = api.getComponents().getSchemas();
        Map<String, PathItem> pathItemMap = api.getComponents().getPathItems();
        Map<String, DataSchema> schemas = new Hashtable<>();
        for (String key: apiSchemas.keySet()){
            schemas.put(key, createSchema(apiSchemas.get(key)));
        }
        Map<String, Form> forms = new Hashtable<>();
        Set<ActionAffordance> actions = new HashSet<>();
        Set<PropertyAffordance> properties = new HashSet<>();
        return "";
    }

    public static Map<String, InteractionAffordance> createAffordances(Map<String, PathItem> pathItemMap, Map<String, DataSchema> schemas){
        Map<String, Form> forms = new Hashtable<>();
        Map<String, InteractionAffordance> affordanceMap = new Hashtable<>();
        for (String key: pathItemMap.keySet()){
            PathItem pathItem = pathItemMap.get(key);
            Operation getOperation = pathItem.getGet();
            Operation postOperation = pathItem.getPost();
            Operation putOperation = pathItem.getPut();
            Operation deleteOperation = pathItem.getDelete();
            String target = pathItem.toString(); //TODO: check
            if (getOperation != null){
                String name = "get"+target;
                Form form = createForm(target, "GET");
                forms.put(name+"Form", form);
                PropertyAffordance.Builder builder = new PropertyAffordance.Builder(name, form);

                PropertyAffordance affordance = builder.build();
                affordanceMap.put(name, affordance);
            }
            if (postOperation != null){
                String name = "post"+target;
                Form form = createForm(target, "POST");
                forms.put(name+"Form", form);
                ActionAffordance.Builder builder = new ActionAffordance.Builder(name, form);

                ActionAffordance affordance = builder.build();
                affordanceMap.put(name, affordance);
            }
            if (putOperation != null){
                String name = "put"+target;
                Form form = createForm(target, "PUT");
                forms.put(name+"Form", form);
                ActionAffordance.Builder builder = new ActionAffordance.Builder(name, form);

                ActionAffordance affordance = builder.build();
                affordanceMap.put(name, affordance);
            }
            if (deleteOperation != null){
                String name = "delete"+target;
                Form form = createForm(target, "DELETE");
                forms.put(name+"Form", form);
                ActionAffordance.Builder builder = new ActionAffordance.Builder(name, form);

                ActionAffordance affordance = builder.build();
                affordanceMap.put(name, affordance);
            }

        }
        return affordanceMap;
    }

    public static Form createForm(String target, String methodName, String contentType){
        Form form = new Form.Builder(target)
                .setMethodName(methodName)
                .setContentType(contentType)
                .build();
        return form;
    }

    public static Form createForm(String target, String methodName){
        Form form = new Form.Builder(target)
                .setMethodName(methodName)
                .build();
        return form;
    }

    public static DataSchema createSchema(Schema apiSchema){
        DataSchema schema = null;
        if (apiSchema instanceof BooleanSchema){
            schema = new ch.unisg.ics.interactions.wot.td.schemas.BooleanSchema.Builder()
                    .build();
        } else if (apiSchema instanceof NumberSchema){
            schema = new ch.unisg.ics.interactions.wot.td.schemas.NumberSchema.Builder()
                    .build();
        } else if (apiSchema instanceof StringSchema){
            schema = new ch.unisg.ics.interactions.wot.td.schemas.StringSchema.Builder()
                    .build();
        } else if (apiSchema instanceof ArraySchema){
            schema = new ch.unisg.ics.interactions.wot.td.schemas.ArraySchema.Builder()
                    .build();
        } else if (apiSchema instanceof ObjectSchema){
            ch.unisg.ics.interactions.wot.td.schemas.ObjectSchema.Builder builder = new ch.unisg.ics.interactions.wot.td.schemas.ObjectSchema.Builder();
            ObjectSchema oSchema = (ObjectSchema) apiSchema;
            Map<String, Schema> p = oSchema.getProperties();
            for (String key: p.keySet()){
                Schema value = p.get(key);
                builder.addProperty(key, createSchema(value));
            }
            List<String> required = oSchema.getRequired();
            String[] r = (String[]) required.toArray();
            builder.addRequiredProperties(r);
            schema = builder.build();
        }
        return schema;
    }


}
