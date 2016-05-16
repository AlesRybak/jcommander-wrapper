package cz.alry.jcommander;

import java.util.List;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

/**
 * @author Ales Rybak (ales.rybak@ibacz.eu)
 */
@Parameters(
    commandNames = {HelpCommandParams.COMMAND_NAME},
    commandDescription = "Display commands and their usage"
)
public class HelpCommandParams {

    public static final String COMMAND_NAME = "help";

    @Parameter
    private List<String> commands;

    public List<String> getCommands() {
        return commands;
    }

}
