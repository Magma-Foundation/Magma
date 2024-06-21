package magma.compile.lang;

import magma.api.Tuple;
import magma.api.result.Result;
import magma.compile.Error_;
import magma.compile.rule.Node;

public interface Generator {
    Result<Tuple<Node, State>, Error_> generate(Node node, State depth);
}
