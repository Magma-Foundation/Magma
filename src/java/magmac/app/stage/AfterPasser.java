package magmac.app.stage;

import magmac.api.Tuple2;
import magmac.app.compile.node.Node;
import magmac.app.stage.parse.ParseState;

import java.util.Optional;

public class AfterPasser implements Passer {
    @Override
    public Optional<Tuple2<ParseState, Node>> pass(ParseState state, Node node) {
        if (node.is("class")) {
            return Optional.of(new Tuple2<>(state, node));
        }

        return Optional.empty();
    }
}