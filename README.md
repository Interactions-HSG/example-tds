# example-tds

Samples of [W3C Web of Things (WoT) Thing Descriptions (TDs)](https://www.w3.org/TR/wot-thing-description/). The TDs were generated with the [WoT-TD-Java](https://github.com/Interactions-HSG/wot-td-java) library.

### Prerequisites
* JDK 8+
* Use `git clone --recursive` to make sure that the project is checked out including the `wot-td-java` submodule

### Generate a TD

1) Create a new class that extends the Thing class to represent the type of your Thing (e.g. TestServer extends Thing). More details are given in the next section.
   
2) In the TDGenerator class, create a new instance of your thing.
 You need to specify the URL to connect to the Thing, the URL where the TD will be made available, a description of your thing.
 (e.g. TestServer testServer = new TestServer("http://localhost:80", "http://localhost:8080/workspaces/test/artifacts/test-artifact", "Test Artifact"); )

3) Generate your TD to a given file name (e.g. writeToFile(testServer.serializeTD(), "test_server.ttl"); ) The file will be located in the "tds" folder.

### Create a new Thing class

1) Create a new class that extends the class Thing.

2) Create a public constructor and initialize it with super. See next section for how to complete it.

3) Override the method exposeTD (an abstract method in Thing). The following method should work for most use cases:

@Override
    public ThingDescription exposeTD() {
        return new ThingDescription.Builder(title)
                .addThingURI(relativeURI)
                .addBaseURI(baseURI)
                .addActions(actions)
                .addProperties(properties)
                .addEvents(events)
                .build();
    }

### Create the constructor of a Thing class.

In the constructor, you have to add your affordances. There are three types of affordances: properties, actions, and events. The lists with these respective names store these affordances. 

To create a new affordance, you need to create a new object of class PropertyAffordance, ActionAffordance, or EventAffordance and then add it to the correct list. See the next sections to get more information on how to create affordances.

### Create a Form

A form indicates how to use an affordance. 

Here is an example: Form propertyForm = new Form.Builder(baseURI + "/property").setMethodName("GET").build();

In the builder, you need to define the URL of the affordance (the baseURI can be used to construct this URL), and the method name. Other parameters can also be set (The IDE should indicate them).

### Create a Data Schema

A data schema indicates the JSON schema that the affordance uses as input or output.

A data schema is created with the Builder class for the class of schema. If my schema is an ObjectSchema, I should use the Builder class ObjectSchema.Builder to create my builder, then complete the schema with the methods of the Builder class and finally use the build method to build my schema.

### Create an affordance.

An affordance should be created with one of the class: PropertyAffordance, ActionAffordance, or EventAffordance depending on its type.

These classes have Builder classes to create an affordance. The parameters of the Builder classes are a name and a form. 




