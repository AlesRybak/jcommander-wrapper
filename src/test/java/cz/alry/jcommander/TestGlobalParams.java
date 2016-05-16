package cz.alry.jcommander;

import com.beust.jcommander.Parameter;

/**
 * @author Ales Rybak (ales.rybak@ibacz.eu)
 */
public class TestGlobalParams {

    @Parameter(names = "debug")
    private boolean debugEnabled = false;

}
