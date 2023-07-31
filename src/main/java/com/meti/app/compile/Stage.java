package com.meti.app.compile;

import com.meti.app.compile.attribute.Attribute;
import com.meti.app.compile.attribute.NodeAttribute;
import com.meti.app.compile.attribute.NodeListAttribute;
import com.meti.core.Err;
import com.meti.core.Result;
import com.meti.core.Tuple;
import com.meti.iterate.ResultIterator;
import com.meti.java.Key;
import com.meti.java.List;
import com.meti.java.String_;

import static com.meti.core.Results.$Result;
import static com.meti.java.JavaList.intoList;

/**
 * This abstract class defines a stage in a compilation pipeline.
 * Each stage performs transformation from an input type 'I' to an output type 'O' while handling potential compilation errors.
 *
 * @param <I> Type of the input to this stage
 * @param <O> Type of the output from this stage
 */
public abstract class Stage<I, O> {
    /**
     * Takes an input of type 'I' and returns a 'Result' that either contains a Node or a 'CompileException'.
     * This method is executed before any other operations in the 'perform' method.
     *
     * @param input Input of type 'I'
     * @return A 'Result' containing either a 'Node' or a 'CompileException'
     */
    protected abstract Result<Node, CompileException> before(I input);

    /**
     * This abstract method takes a Node as output and returns a Result object.
     * It is executed after all transformations in the 'perform' method.
     * The subclasses need to provide a concrete implementation for this method.
     *
     * @param output The Node output that needs to be converted
     * @return A 'Result' containing either an output of type 'O' or a 'CompileException'
     */
    protected abstract Result<O, CompileException> after(Node output);

    /**
     * This is the main method of the class. It receives an input of type 'I' and uses it to perform the compilation stage.
     * It applies a series of transformations, including attaching nodes and node lists, and returns the final result.
     *
     * @param input Input of type 'I' for the stage
     * @return A 'Result' containing either an output of type 'O' or a 'CompileException'
     */
    Result<O, CompileException> perform(I input) {
        return $Result(CompileException.class, () -> {
            var node = before(input).$();
            var withNodeLists = attachNodeLists(node).$();
            var withNodes = attachNodes(withNodeLists).$();
            return after(withNodes).$();
        });
    }


    protected Result<O, CompileException> performOnContent(Node node) {
        return toInput(node).mapValueToResult(this::perform);
    }

    /**
     * An abstract method that converts a Node to an object of type 'I'.
     * Subclasses need to provide a concrete implementation for this method.
     *
     * @param node The Node that needs to be converted to an object of type 'I'
     * @return A 'Result' containing either an object of type 'I' or a 'CompileException'
     */
    protected abstract Result<I, CompileException> toInput(Node node);

    private <T> Result<T, CompileException> createInvalid(Key<String_> key, Attribute attribute, String type) {
        var format = "Key '%s' with value '%s' was not a %s.";
        var message = format.formatted(key, attribute, type);
        return Err.apply(new CompileException(message));
    }

    private Result<Tuple<Key<String_>, NodeAttribute>, CompileException> performOnNodeAttribute(Node root, Key<String_> key) {
        return root.apply(key).asNode().map(list -> performOnContent(list)
                        .mapValue(this::fromOutput)
                        .mapValue(NodeAttribute::new)
                        .mapValue(value -> new Tuple<>(key, value)))
                .unwrapOrElseGet(() -> createInvalid(key, root.apply(key), "node"));
    }

    /**
     * An abstract method that converts an object of type 'O' to a Node.
     * Subclasses need to provide a concrete implementation for this method.
     *
     * @param value The object of type 'O' to be converted to a Node
     * @return The converted Node
     */
    protected abstract Node fromOutput(O value);

    private Result<Tuple<Key<String_>, NodeListAttribute>, CompileException> performOnNodeList(Key<String_> key, List<? extends Node> list) {
        return list.iter()
                .map(this::performOnContent)
                .into(ResultIterator::new)
                .mapInner(this::fromOutput)
                .collectAsResult(intoList())
                .mapValue(NodeListAttribute::new)
                .mapValue(value -> new Tuple<>(key, value));
    }

    private Result<Tuple<Key<String_>, NodeListAttribute>, CompileException> performOnNodeListAttribute(Node node, Key<String_> key) {
        return node.apply(key)
                .asListOfNodes()
                .map(list -> performOnNodeList(key, list))
                .unwrapOrElseGet(() -> createInvalid(key, node.apply(key), "set of nodes"));
    }

    private Result<Node, CompileException> attachNodes(Node withNodeLists) {
        return withNodeLists.ofGroup(Node.Group.NodeList)
                .map(key -> performOnNodeAttribute(withNodeLists, key))
                .into(ResultIterator::new)
                .foldLeftInner(withNodeLists, (accumulated, element) -> accumulated.with(element.a(), element.b()));
    }

    private Result<Node, CompileException> attachNodeLists(Node node) {
        return node.ofGroup(Node.Group.NodeList)
                .map(key -> performOnNodeListAttribute(node, key))
                .into(ResultIterator::new)
                .foldLeftInner(node, (accumulated, element) -> accumulated.with(element.a(), element.b()));
    }
}