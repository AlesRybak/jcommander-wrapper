package cz.alry.jcommander;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.contrib.java.lang.system.SystemOutRule;

/**
 * @author Ales Rybak (ales.rybak@ibacz.eu)
 */
public class CommandControllerTest {

    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog().mute();

    @Test
    public void commanderCanRunSimpleScenario() {
        // given
        CommandController<TestGlobalParams> controller = new CommandController<>(new TestGlobalParams());
        controller.addCommand(new TestCommandParams(), new TestCommandRunner());

        // when
        controller.run(new String[]{"test", "-p", "aha"});

        // then
        assertEquals("Hello aha", systemOutRule.getLog().trim());
    }

    @Test
    public void commanderCanRunHelpCommand() {
        // given
        CommandController<TestGlobalParams> controller = new CommandController<>(new TestGlobalParams());
        controller.addCommand(new TestCommandParams(), new TestCommandRunner());

        // when
        controller.run(new String[]{"help"});

        // then
        assertThat(systemOutRule.getLog().trim(), containsString("Usage: help"));
        assertThat(systemOutRule.getLog().trim(), containsString("test [options]"));
    }

    @Test
    public void commanderCanRunHelpOnCommand() {
        // given
        CommandController<TestGlobalParams> controller = new CommandController<>(new TestGlobalParams());
        controller.addCommand(new TestCommandParams(), new TestCommandRunner());

        // when
        controller.run(new String[]{"help", "test"});

        // then
        assertThat(systemOutRule.getLog().trim(), containsString("test [options]"));
    }

}
