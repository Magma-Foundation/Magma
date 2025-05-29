package magmac.app.lang.node;

import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.lang.Serializable;

public class PlantUMLRoot implements Serializable {
    @Override
    public Node serialize() {
        return new MapNode();
    }
}
