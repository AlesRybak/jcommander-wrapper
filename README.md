# jcommander-wrapper
Simple wrapper to make usage of [JCommander](https://github.com/cbeust/jcommander) library even easier. It is targeted on easy creation of multi-command tools.

## Usage

In your project you need to create:
* global params class
* command runner and command params classes for each command
* and the main class

### Global params class

Global parameters class is simple POJO anotated with JCommander annotations. Something like this:

```
public class GlobalParams {

    @Parameter(
            names = {"-v", "--verbose"},
            description = "Make the output more verbose")
    private boolean verbose = false;

    public boolean isVerbose() {
        return verbose;
    }

}
```

### Command specific params

For each command you need command specific parameters class. Again JCommander annotated POJO, this time also with command name. Example follows:

```
@Parameters(
    commandNames = {"dummy"}
)
public class DummyParams {

    @Parameter(
            names = {"-t"},
            required = true,
            description = "The text to output")
    private String text;

    public String getText() {
        return text;
    }
}
```

### Command runner

Command runner is a class where you get global and command specific parameter objects and perform the desired actions. It should implement the abstract class `CommandRunner` like this:

```
public class DummyRunner implements CommandRunner<GlobalParams, DummyParams> {

    public void run(GlobalParams globalParams, DummyParams dummyParams) {
        // perform your logic here
        System.out.println(dummyParams.getText());
    }

}
```

### The main class

This is standard Java main class where you create JCommand wrapper, register your commands and start the execution.

```
public class ExampleApp {

    public static void main( String[] args ) {
        try {
        
            // create JCommand controller
            CommandController<GlobalParams> controller = new CommandController<>(new GlobalParams());
            
            // register your commands
            controller.addCommand(new DummyParams(), new DummyRunner());
            
            // start the execution
            controller.run(args);
            
        } catch (ParameterException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

}
``` 

## Maven

There are also two Maven plugins which come often handy when creating CLI Java tools:
* `maven-shade-plugin` to create single JAR for your application
* `really-executable-jar-maven-plugin` to create runnable linux executable
You can use them like this in your _pom.xml_:
```
...
    <build>
        <plugins>
            ...
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-shade-plugin</artifactId>
                <version>2.4.3</version>
                <executions>
                    <execution>
                        <phase>package</phase>
                        <goals>
                            <goal>shade</goal>
                        </goals>
                        <configuration>
                            <transformers>
                                <transformer implementation="org.apache.maven.plugins.shade.resource.ManifestResourceTransformer">
                                    <mainClass>cz.alry.jcommander.example.ExampleApp</mainClass>
                                </transformer>
                            </transformers>
                            <createDependencyReducedPom>false</createDependencyReducedPom>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
            <plugin>
                <groupId>org.skife.maven</groupId>
                <artifactId>really-executable-jar-maven-plugin</artifactId>
                <version>1.5.0</version>
                <configuration>
                    <programFile>example-app</programFile>
                </configuration>
                <executions>
                    <execution>
                        <phase>package</phase>
                        <goals>
                            <goal>really-executable-jar</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
...
```
