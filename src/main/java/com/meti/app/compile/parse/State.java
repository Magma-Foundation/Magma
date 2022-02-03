package com.meti.app.compile.parse;

import com.meti.api.core.F1;
import com.meti.api.json.AbstractJSONable;
import com.meti.api.json.JSONException;
import com.meti.api.json.JSONNode;
import com.meti.api.json.ObjectNode;
import com.meti.app.compile.node.Node;

import java.util.Objects;

import static com.meti.app.compile.node.EmptyNode.EmptyNode_;

public class State extends AbstractJSONable {
    private final Node current;
    private final Scope scope;

    public State() {
        this(EmptyNode_);
    }

    public State(Node current) {
        this(current, new Scope());
    }

    public State(Node current, Scope scope) {
        this.current = current;
        this.scope = scope;
    }

    public State apply(Node element) {
        return new State(element, scope);
    }

    public <T, E extends Exception> T applyToScope(F1<Scope, T, E> predicate) throws E {
        return predicate.apply(scope);
    }

    public Node getCurrent() {
        return current;
    }

    @Override
    public int hashCode() {
        return Objects.hash(current, scope);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof State state)) return false;
        return Objects.equals(current, state.current) &&
               Objects.equals(scope, state.scope);
    }

    public <E extends Exception> State mapCurrent(F1<Node, Node, E> mapper) throws E {
        return new State(mapper.apply(current), scope);
    }

    public <E extends Exception> State mapScope(F1<Scope, Scope, E> mapper) throws E {
        var oldScope = getScope();
        var newScope = mapper.apply(oldScope);
        return new State(current, newScope);
    }

    public Scope getScope() {
        return scope;
    }

    public <T, E extends Exception> T applyToCurrent(F1<Node, T, E> mapper) throws E {
        return mapper.apply(current);
    }

    @Override
    public JSONNode toJSON() throws JSONException {
        return new ObjectNode()
                .addJSONable("current", current)
                .addJSONable("scope", scope);
    }

    public State enter() {
        return new State(current, scope.enter(current));
    }

    public State exit() {
        return new State(current, scope.exit());
    }
}
