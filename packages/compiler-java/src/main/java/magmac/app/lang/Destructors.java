package magmac.app.lang;

import magmac.api.None;
import magmac.api.Option;
import magmac.api.Some;
import magmac.app.compile.node.InitialDestructor;
import magmac.app.compile.node.InitialDestructorImpl;
import magmac.app.compile.node.Node;

public final class Destructors {
    public static Option<InitialDestructor> destructWithType(String type, Node node) {
        if (node.is(type)) {
            return new Some<>(new InitialDestructorImpl(node));
        }

        return new None<>();
    }

    public static InitialDestructor destruct(Node node) {
        return new InitialDestructorImpl(node);
    }
}
