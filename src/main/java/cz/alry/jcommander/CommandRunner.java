package cz.alry.jcommander;

/**
 * @author Ales Rybak (ales.rybak@ibacz.eu)
 */
public interface CommandRunner<G, C> {

    void run(G globalParameters, C commandParameters);

}
