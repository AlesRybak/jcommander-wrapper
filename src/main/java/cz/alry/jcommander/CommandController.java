package cz.alry.jcommander;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameters;

/**
 * @author Ales Rybak (ales.rybak@ibacz.eu)
 */
public class CommandController<G> {

    private JCommander jcommander;

    private G globalParams;
    private HelpCommandParams helpCommandParams = new HelpCommandParams();

    Map<String, CommandRunner> runners;
    Map<String, Object> paramObjects;

    public CommandController(G params) {
        globalParams = params;
        runners = new HashMap<>();
        paramObjects = new HashMap<>();

        jcommander = new JCommander(globalParams);
        jcommander.addCommand(helpCommandParams);
    }

    public void addCommand(Object commandParams, CommandRunner commandRunner) {
        jcommander.addCommand(commandParams);

        String[] commandNames = getCommandNamesFromParamsObject(commandParams);

        for (String commandName : commandNames) {
            runners.put(commandName, commandRunner);
            paramObjects.put(commandName, commandParams);
        }
    }

    private String[] getCommandNamesFromParamsObject(Object commandParams) {
        Parameters parameters = commandParams.getClass().getAnnotation(Parameters.class);
        return parameters.commandNames();
    }

    @SuppressWarnings("unchecked")
    public void run(String[] args) {
        jcommander.parse(args);
        String commandName = jcommander.getParsedCommand();

        if (commandName == null || commandName.equals(HelpCommandParams.COMMAND_NAME)) {
            runHelpCommand();
        } else {
            CommandRunner commandRunner = runners.get(commandName);
            Object paramObject = paramObjects.get(commandName);
            commandRunner.run(globalParams, paramObject);
        }
    }

    private void runHelpCommand() {
        List<String> commands = helpCommandParams.getCommands();
        if (commands == null || commands.isEmpty()) {
            jcommander.usage();
        } else {
            jcommander.usage(commands.get(0));
        }
    }

}
