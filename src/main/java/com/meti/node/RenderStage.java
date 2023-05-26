package com.meti.node;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class RenderStage implements Renderer {
    protected final Node root;

    public RenderStage(Node root) {
        this.root = root;
    }

    Optional<String> renderContent(Node withNodeList) {
        return createRenderer(withNodeList).render();
    }

    protected abstract Renderer createRenderer(Node node);

    @Override
    public Optional<String> render() {
        var withNode = root.stream(Node.Group.Node).reduce(root,
                (node, key) -> node.map(key, new RenderingNodeConverter()).orElse(node),
                (previous, next) -> next);

        var withNodeList = withNode.stream(Node.Group.NodeList).reduce(withNode,
                (node, key) -> node.map(key, new RenderingNodeListConverter()).orElse(withNode),
                (previous, next) -> next);

        return renderContent(withNodeList);
    }

    protected abstract RenderStage copy(Node node);

    class RenderingNodeListConverter extends NodeListConverter {

        @Override
        public List<Node> apply(List<Node> value) {
            return value.stream()
                    .map(RenderStage.this::copy)
                    .map(RenderStage::render)
                    .flatMap(Optional::stream)
                    .map(Content::new)
                    .collect(Collectors.toList());
        }
    }

    class RenderingNodeConverter extends NodeConverter {
        @Override
        public Node apply(Node value) {
            return copy(value)
                    .render()
                    .<Node>map(Content::new)
                    .orElseThrow(() -> new UnsupportedOperationException("Unknown value: " + value));
        }
    }
}
