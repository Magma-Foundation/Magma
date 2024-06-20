package magma.compile.lang;

import magma.api.Tuple;
import magma.api.result.Err;
import magma.api.result.Ok;
import magma.api.result.Result;
import magma.compile.CompileError;
import magma.compile.Error_;
import magma.compile.rule.Node;
import magma.compile.rule.text.StripRule;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MagmaFormatter extends Generator {
    private static List<Node> attachFormatting(List<Node> children, String prefix, int depth) {
        List<Node> list = new ArrayList<>();
        int i = 0;
        while (i < children.size()) {
            Node child = children.get(i);
            if (!child.is("empty")) {
                if (i == 0 && depth == 0) {
                    list.add(child);
                } else {
                    Node node = child.withString(StripRule.DEFAULT_LEFT, prefix);
                    list.add(node);
                }
            }
            i++;
        }
        return list;
    }

    @Override
    protected Result<Tuple<Node, State>, Error_> postVisit(Node node, State state) {
        return postVisitBlock(node)
                .map(result -> result.mapValue(inner -> new Tuple<>(inner, state)))
                .orElse(new Ok<>(new Tuple<>(node, state)));
    }

    private Optional<Result<Node, Error_>> postVisitBlock(Node node) {
        if (!node.is("block")) return Optional.empty();

        var depthString = node.findString("depth");
        if (depthString.isEmpty()) {
            return Optional.of(new Err<>(new CompileError("No depth present.", node.toString())));
        }

        var depth = Integer.parseInt(depthString.get()) - 1;
        var prefix = "\n" + "\t".repeat(depth);
        var newBlock = node
                .mapNodes("children", children -> attachFormatting(children, prefix, depth))
                .withString("after-content", "\n" + "\t".repeat(depth == 0 ? 0 : depth - 1));

        return Optional.of(new Ok<>(newBlock));
    }
}
