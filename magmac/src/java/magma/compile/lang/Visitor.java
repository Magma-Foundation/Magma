package magma.compile.lang;

import magma.api.Tuple;
import magma.api.result.Ok;
import magma.api.result.Result;
import magma.compile.Error_;
import magma.compile.annotate.State;
import magma.compile.rule.Node;

/**
 * Interface representing a visitor that performs operations on nodes before and after the main visit.
 */
public interface Visitor {

    /**
     * Method to be called before the main visit operation on a node.
     * By default, it returns the node and state unchanged wrapped in a successful result.
     *
     * @param node  the node to be pre-visited
     * @param state the current state during the visit
     * @return a Result containing a Tuple of the node and state, or an error
     */
    default Result<Tuple<Node, State>, Error_> preVisit(Node node, State state) {
        return new Ok<>(new Tuple<>(node, state));
    }

    /**
     * Method to be called after the main visit operation on a node.
     * By default, it returns the node and state unchanged wrapped in a successful result.
     *
     * @param node  the node to be post-visited
     * @param state the current state during the visit
     * @return a Result containing a Tuple of the node and state, or an error
     */
    default Result<Tuple<Node, State>, Error_> postVisit(Node node, State state) {
        return new Ok<>(new Tuple<>(node, state));
    }
}