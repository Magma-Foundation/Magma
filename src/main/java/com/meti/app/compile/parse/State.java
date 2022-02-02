package com.meti.app.compile.parse;

import com.meti.api.core.F1;
import com.meti.app.compile.node.Node;

import static com.meti.app.compile.node.EmptyNode.EmptyNode_;

class State {
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

    public Node getCurrent() {
        return current;
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

    public <E extends Exception> boolean queryCurrent(F1<Node, Boolean, E> predicate) throws E {
        return predicate.apply(current);
    }
}
