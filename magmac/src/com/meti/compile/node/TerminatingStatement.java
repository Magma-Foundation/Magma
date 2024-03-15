package com.meti.compile.node;

import java.util.Objects;

public final class TerminatingStatement extends Statement {

    public TerminatingStatement(Node child, int indent) {
        super(child, indent);
    }

    @Override
    protected String suffix() {
        return ";";
    }

    public Node child() {
        return child;
    }

    public int indent() {
        return indent;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Statement) obj;
        return Objects.equals(this.child, that.child) &&
               this.indent == that.indent;
    }

    @Override
    public int hashCode() {
        return Objects.hash(child, indent);
    }

    @Override
    public String toString() {
        return "Statement[" +
               "child=" + child + ", " +
               "indent=" + indent + ']';
    }

}
