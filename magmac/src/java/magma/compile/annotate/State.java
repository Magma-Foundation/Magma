package magma.compile.annotate;

/**
 * Interface representing the state during compilation, with methods to manage and compute depth of the state.
 */
public interface State {

    /**
     * Enters a new state, often used to represent going deeper in a hierarchical structure.
     *
     * @return the new state after entering
     */
    State enter();

    /**
     * Computes the depth of the current state, typically representing how deep the state is in a hierarchical structure.
     *
     * @return an integer representing the depth of the current state
     */
    int computeDepth();

    /**
     * Exits the current state, often used to represent going up in a hierarchical structure.
     *
     * @return the new state after exiting
     */
    State exit();
}