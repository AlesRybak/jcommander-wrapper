package cz.alry.jcommander;

/**
 * @author Ales Rybak (ales.rybak@ibacz.eu)
 */
public class TestCommandRunner implements CommandRunner<TestGlobalParams, TestCommandParams> {

    @Override
    public void run(TestGlobalParams globalParameters, TestCommandParams testCommandParams) {
        System.out.println("Hello " + testCommandParams.getTestParam());
    }
}
