package magmac.app.lang.web;

import magmac.api.Tuple2;
import magmac.api.iter.Iter;
import magmac.app.compile.node.Node;
import magmac.app.compile.node.NodeList;

/**
 * Applies indentation information to TypeScript AST nodes. Each node receives
 * a "before" string and optionally an "after" string used by {@link StripRule}
 * during generation.
 */
public final class Indentation {
    private Indentation() {}

    public static Node apply(Node root) {
        root.findNodeList("children").ifPresent(list -> {
            var iter = list.iter();
            var maybe = iter.next();
            while (maybe.isPresent()) {
                var child = maybe.orElse(null);
                indent(child, 0);
                maybe = iter.next();
            }
        });
        return root;
    }

    private static void indent(Node node, int depth) {
        node.withString("before", createIndent(depth));
        if (node.is("block") || node.is("method") || node.is("class")
                || node.is("interface") || node.is("enum")) {
            node.withString("after", createIndent(depth));
        } else {
            node.withString("after", "");
        }

        Iter<Tuple2<String, NodeList>> lists = node.iterNodeLists();
        var maybeList = lists.next();
        while (maybeList.isPresent()) {
            NodeList childList = maybeList.orElse(null).right();
            var iter = childList.iter();
            var maybeChild = iter.next();
            while (maybeChild.isPresent()) {
                indent(maybeChild.orElse(null), depth + 1);
                maybeChild = iter.next();
            }
            maybeList = lists.next();
        }
    }

    private static String createIndent(int depth) {
        return "\n" + "\t".repeat(Math.max(depth, 0));
    }
}
