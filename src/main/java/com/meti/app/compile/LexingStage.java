package com.meti.app.compile;

import com.meti.app.Attribute;
import com.meti.app.NodeListAttribute;
import com.meti.core.Err;
import com.meti.core.Ok;
import com.meti.core.Result;
import com.meti.core.Tuple;
import com.meti.iterate.ResultIterator;
import com.meti.java.Key;
import com.meti.java.List;
import com.meti.java.String_;

import static com.meti.core.Results.$Result;
import static com.meti.java.JavaList.intoList;
import static com.meti.java.JavaString.fromSlice;

public record LexingStage(String_ input) {
    Result<Node, CompileException> lexTree() {
        if (input().isEmpty()) {
            return Err.apply(new CompileException("Input cannot be empty."));
        }

        return $Result(CompileException.class, () -> {
            var node = new JavaLexer(input).lex().unwrapOrElse(Ok.apply(new Content(input))).$();
            var withNodeLists = attachNodeLists(node).$();
            return attachNodes(withNodeLists).$();
        }).mapErr(err -> new CompileException("Failed to lex input: " + input.unwrap(), err));
    }

    public Result<Node, CompileException> attachNodes(Node withNodeLists) {
        return withNodeLists.ofGroup(Node.Group.NodeList)
                .map(key -> lexNodeAttribute(key, withNodeLists))
                .into(ResultIterator::new)
                .foldLeftInner(withNodeLists, (accumulated, element) -> accumulated.with(element.a(), element.b()));
    }

    public Result<Node, CompileException> attachNodeLists(Node node) {
        return node.ofGroup(Node.Group.NodeList).map(key -> lexNodeList(node, key))
                .into(ResultIterator::new)
                .foldLeftInner(node, (accumulated, element) -> accumulated.with(element.a(), element.b()));
    }

    public Result<Tuple<Key<String_>, NodeListAttribute>, CompileException> lexNodeList(Node node, Key<String_> key) {
        return node.apply(key)
                .asListOfNodes()
                .map(list -> lexNodeList1(key, list))
                .unwrapOrElseGet(() -> createInvalid(key, "set of nodes"));
    }

    private <T> Result<T, CompileException> createInvalid(Key<String_> key, String type) {
        var format = "Key '%s' was not a %s.";
        var message = format.formatted(key, type);
        return Err.apply(new CompileException(message));
    }

    public Result<Tuple<Key<String_>, NodeAttribute>, CompileException> lexNodeAttribute(Key<String_> key, Node withNodeLists) {
        return withNodeLists.apply(key).asNode().map(list -> lexUnwrapped(list)
                .mapValue(NodeAttribute::new)
                .mapValue(value -> new Tuple<>(key, value))).unwrapOrElseGet(() -> createInvalid(key, "node"));
    }

    private Result<Tuple<Key<String_>, NodeListAttribute>, CompileException> lexNodeList1(Key<String_> key, List<? extends Node> list) {
        return list.iter()
                .map(this::lexUnwrapped)
                .into(ResultIterator::new)
                .collectAsResult(intoList())
                .mapValue(NodeListAttribute::new)
                .mapValue(value -> new Tuple<>(key, value));
    }

    private Result<Node, CompileException> lexUnwrapped(Node node1) {
        return node1.applyOptionally(fromSlice("value")).flatMap(Attribute::asString)
                .map(line -> lexTree())
                .unwrapOrElse(Err.apply(new CompileException("No value present in list.")));
    }
}