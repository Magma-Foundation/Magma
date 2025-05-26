package magmac.app.stage;

import magmac.api.Tuple2;
import magmac.api.collect.Iters;
import magmac.api.collect.collect.MapCollector;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.Rule;
import magmac.app.io.Location;

import java.util.Map;

public class RuleLexer implements Lexer {
    private final Rule rootRule;

    public RuleLexer(Rule rootRule) {
        this.rootRule = rootRule;
    }

    private Map<Location, Node> lexAll0(Map<Location, String> values) {
        return Iters.fromMap(values)
                .map(entry -> this.getLocationNodeTuple2(entry))
                .collect(new MapCollector<>());
    }

    private Tuple2<Location, Node> getLocationNodeTuple2(Tuple2<Location, String> tuple) {
        Location location = tuple.left();
        String input = tuple.right();

        Node root = this.rootRule
                .lex(input)
                .findValue()
                .orElse(new MapNode());

        return new Tuple2<>(location, root);
    }

    @Override
    public Roots lexAll(Map<Location, String> values) {
        return new Roots(this.lexAll0(values));
    }
}
