package cz.alry.jcommander;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

/**
 * @author Ales Rybak (ales.rybak@ibacz.eu)
 */
@Parameters(
    commandNames = {"test"}
)
public class TestCommandParams {

    @Parameter(names = "-p")
    private String testParam;

    public String getTestParam() {
        return testParam;
    }
}
