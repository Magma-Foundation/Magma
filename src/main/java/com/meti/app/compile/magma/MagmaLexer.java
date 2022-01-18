package com.meti.app.compile.magma;

import com.meti.api.collect.java.List;
import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.app.compile.CompileException;
import com.meti.app.compile.attribute.Attribute;
import com.meti.app.compile.attribute.NodeAttribute;
import com.meti.app.compile.attribute.NodesAttribute;
import com.meti.app.compile.common.EmptyField;
import com.meti.app.compile.common.ReferenceLexer;
import com.meti.app.compile.common.alternate.ElseLexer;
import com.meti.app.compile.common.binary.BinaryLexer;
import com.meti.app.compile.common.block.BlockLexer;
import com.meti.app.compile.common.bool.BooleanLexer;
import com.meti.app.compile.common.condition.ConditionLexer;
import com.meti.app.compile.common.integer.IntegerLexer;
import com.meti.app.compile.common.integer.IntegerTypeLexer;
import com.meti.app.compile.common.invoke.InvocationLexer;
import com.meti.app.compile.common.returns.ReturnLexer;
import com.meti.app.compile.common.string.StringLexer;
import com.meti.app.compile.common.variable.VariableLexer;
import com.meti.app.compile.lex.LexException;
import com.meti.app.compile.lex.Lexer;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.PrimitiveLexer;
import com.meti.app.compile.node.RootText;
import com.meti.app.compile.node.Text;

import java.util.ArrayList;
import java.util.stream.Collectors;

public record MagmaLexer(Text text) {
    static Node lexNode(Text text) throws CompileException {
        if (text.isEmpty()) throw new LexException("Input may not be empty.");

        var lexers = java.util.List.of(
                new ElseLexer(text),
                new StringLexer(text),
                new ConditionLexer(text),
                new BooleanLexer(text),
                new ImportLexer(text),
                new StructureLexer(text),
                new BlockLexer(text),
                new FunctionLexer(text),
                new DeclarationLexer(text),
                new InvocationLexer(text),
                new ReturnLexer(text),
                new IntegerLexer(text),
                new BinaryLexer(text),
                new UnaryLexer(text),
                new VariableLexer(text));

        Option<Node> current = new None<>();
        for (Lexer lexer : lexers) {
            var next = lexer.lex();
            if (next.isPresent()) {
                current = next;
                break;
            }
        }

        return current.orElseThrow(() -> new LexException("Unknown input: " + text.computeTrimmed()));
    }

    private static Node lexType(Text text) throws CompileException {
        java.util.List<Lexer> lexers = java.util.List.<Lexer>of(
                new FunctionTypeLexer(text),
                new ReferenceLexer(text),
                new PrimitiveLexer(text),
                new IntegerTypeLexer(text));

        Option<Node> found = new None<>();
        for (Lexer lexer : lexers) {
            var option = lexer.lex();
            if (option.isPresent()) {
                found = option;
                break;
            }
        }

        return found.orElseThrow(() -> new LexException("Unknown type: \"" + text.compute() + "\""));
    }

    static Node lexTypeAST(Text text) throws CompileException {
        var root = lexType(text);
        var withType = withType(root);
        return withTypes(withType);
    }

    private static Node withType(Node root) throws CompileException {
        var types = root.apply(Attribute.Group.Type).collect(Collectors.toList());
        var current = root;
        for (Attribute.Type type : types) {
            var newChild = lexTypeAST(current.apply(type).asNode().apply(Attribute.Type.Value).asText());
            current = current.with(type, new NodeAttribute(newChild));
        }
        return current;
    }

    private static Node withTypes(Node root) throws CompileException {
        var types = root.apply(Attribute.Group.Types).collect(Collectors.toList());
        var current = root;
        for (Attribute.Type type : types) {
            var oldTypes = current.apply(type).asStreamOfNodes().collect(Collectors.toList());

            var newTypes = new ArrayList<Node>();
            for (Node oldType : oldTypes) {
                newTypes.add(lexTypeAST(oldType.apply(Attribute.Type.Value).asText()));
            }

            current = current.with(type, new NodesAttribute(newTypes));
        }
        return current;
    }

    private Node attachDeclaration(Node node) throws CompileException {
        var types = node.apply(Attribute.Group.Definition).collect(Collectors.toList());
        var current = node;
        for (Attribute.Type type : types) {
            var oldField = current.apply(type).asNode();
            Node newField;
            if (oldField.is(Node.Type.Declaration) || oldField.is(Node.Type.Initialization)) {
                var oldType = oldField.apply(Attribute.Type.Type).asNode();
                Node newType;
                if (oldType.is(Node.Type.Content)) {
                    var typeText = oldType.apply(Attribute.Type.Value).asText();
                    newType = lexTypeAST(typeText);
                } else {
                    newType = oldType;
                }

                var withType = oldField.with(Attribute.Type.Type, new NodeAttribute(newType));
                Node withValue;
                if (withType.is(Node.Type.Initialization)) {
                    var valueText = withType.apply(Attribute.Type.Value).asNode().apply(Attribute.Type.Value).asText();
                    var value = lexNodeAST(valueText);
                    withValue = withType.with(Attribute.Type.Value, new NodeAttribute(value));
                } else {
                    withValue = withType;
                }

                newField = withValue;
            } else {
                newField = lexField(oldField.apply(Attribute.Type.Value).asText());
            }
            current = current.with(type, new NodeAttribute(newField));
        }
        return current;
    }

    private Node attachDeclarations(Node withFields) throws CompileException {
        var collect = withFields.apply(Attribute.Group.Declarations).collect(Collectors.toList());
        var current = withFields;
        for (Attribute.Type type : collect) {
            var oldDeclarations = withFields.apply(type).asStreamOfNodes().collect(Collectors.toList());
            var newDeclarations = new ArrayList<Node>();
            for (Node declaration : oldDeclarations) {
                newDeclarations.add(lexField(declaration.apply(Attribute.Type.Value).asText()));
            }

            current = current.with(type, new NodesAttribute(newDeclarations));
        }
        return current;
    }

    private Node attachNode(Node root) throws CompileException {
        var types = root.apply(Attribute.Group.Node).collect(Collectors.toList());
        var current = root;
        for (Attribute.Type type : types) {
            var oldNode = root.apply(type).asNode();
            var newNode = lexNodeAST(oldNode.apply(Attribute.Type.Value).asText());
            current = current.with(type, new NodeAttribute(newNode));
        }
        return current;
    }

    private Node attachNodes(Node root) throws CompileException {
        var types = root.apply(Attribute.Group.Nodes).collect(Collectors.toList());
        var current = root;
        for (Attribute.Type type : types) {
            var oldNodes = root.apply(type).asStreamOfNodes().collect(Collectors.toList());
            var newNodes = new ArrayList<Node>();
            for (Node oldNode : oldNodes) {
                newNodes.add(lexNodeAST(oldNode.apply(Attribute.Type.Value).asText()));
            }
            current = current.with(type, new NodesAttribute(newNodes));
        }
        return current;
    }

    public Node lex() throws CompileException {
        return lexNodeAST(text);
    }

    private Node lexField(Text text) throws CompileException {
        var separator = text.firstIndexOfChar(':')
                .orElseThrow(() -> new LexException("Invalid field: " + text.computeTrimmed()));
        var name = text.slice(0, separator);
        var typeText = text.slice(separator + 1);
        var type = lexTypeAST(typeText);
        return new EmptyField(name, type, List.createList());
    }

    private Node lexNodeAST(Text text) throws CompileException {
        try {
            var node = lexNode(text);
            var withFields = attachDeclaration(node);
            var withDeclarations = attachDeclarations(withFields);
            var withNode = attachNode(withDeclarations);
            return attachNodes(withNode);
        } catch (CompileException e) {
            throw new CompileException("Failed to lex node: \"" + text + "\"", e);
        }
    }
}
