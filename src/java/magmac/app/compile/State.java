package magmac.app.compile;

import magmac.app.compile.node.Node;

import java.util.List;

public record State(List<Node> children) {
}
