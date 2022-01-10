package com.meti.compile.magma;

import com.meti.compile.CompileException;
import com.meti.compile.attribute.Attribute;
import com.meti.compile.attribute.AttributeException;
import com.meti.compile.attribute.NodeAttribute;
import com.meti.compile.attribute.NodesAttribute;
import com.meti.compile.common.Field;
import com.meti.compile.common.block.BlockLexer;
import com.meti.compile.common.integer.IntegerLexer;
import com.meti.compile.common.integer.IntegerType;
import com.meti.compile.common.returns.ReturnLexer;
import com.meti.compile.common.variable.VariableLexer;
import com.meti.compile.lex.LexException;
import com.meti.compile.lex.Lexer;
import com.meti.compile.node.Node;
import com.meti.compile.node.Primitive;
import com.meti.compile.node.Text;
import com.meti.option.None;
import com.meti.option.Option;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public record MagmaLexer(Text text) {
    static Node lexNode(Text text) throws LexException {
        if (text.isEmpty()) throw new LexException("Input may not be empty.");

        var lexers = List.of(
                new ImportLexer(text),
                new StructureLexer(text),
                new BlockLexer(text),
                new FunctionLexer(text),
                new ReturnLexer(text),
                new IntegerLexer(text),
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

    static Node lexType(Text text) throws LexException {
        var values = Primitive.values();
        for (Primitive value : values) {
            if (text.computeTrimmed().equals(value.name())) {
                return value;
            }
        }

        var isSigned = text.startsWithChar('I');
        var isUnsigned = text.startsWithChar('U');
        if (isSigned || isUnsigned) {
            var bitsText = text.slice(1);
            var bits = Integer.parseInt(bitsText.computeTrimmed());
            return new IntegerType(isSigned, bits);
        } else {
            throw new LexException("Cannot lex type: " + text.compute());
        }
    }

    private Node attachDeclaration(Node node) throws AttributeException, LexException {
        var types = node.apply(Attribute.Group.Declaration).collect(Collectors.toList());
        var current = node;
        for (Attribute.Type type : types) {
            var oldField = current.apply(type).asNode();
            Node newField;
            if (oldField.is(Node.Type.Field)) {
                var oldType = oldField.apply(Attribute.Type.Type).asNode()
                        .apply(Attribute.Type.Value)
                        .asText();
                var newType = lexType(oldType);
                newField = oldField.with(Attribute.Type.Type, new NodeAttribute(newType));
            } else {
                newField = lexField(oldField.apply(Attribute.Type.Value).asText());
            }
            current = current.with(type, new NodeAttribute(newField));
        }
        return current;
    }

    private Node attachDeclarations(Node withFields) throws AttributeException, LexException {
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

    private Node attachNode(Node root) throws AttributeException, LexException {
        var types = root.apply(Attribute.Group.Node).collect(Collectors.toList());
        var current = root;
        for (Attribute.Type type : types) {
            var oldNode = root.apply(type).asNode();
            var newNode = lexAST(oldNode.apply(Attribute.Type.Value).asText());
            current = current.with(type, new NodeAttribute(newNode));
        }
        return current;
    }

    private Node attachNodes(Node root) throws AttributeException, LexException {
        var types = root.apply(Attribute.Group.Nodes).collect(Collectors.toList());
        var current = root;
        for (Attribute.Type type : types) {
            var oldNodes = root.apply(type).asStreamOfNodes().collect(Collectors.toList());
            var newNodes = new ArrayList<Node>();
            for (Node oldNode : oldNodes) {
                newNodes.add(lexAST(oldNode.apply(Attribute.Type.Value).asText()));
            }
            current = current.with(type, new NodesAttribute(newNodes));
        }
        return current;
    }

    public Node lex() throws CompileException {
        return lexAST(text);
    }

    private Node lexAST(Text text) throws LexException, AttributeException {
        var node = lexNode(text);
        var withFields = attachDeclaration(node);
        var withDeclarations = attachDeclarations(withFields);
        var withNode = attachNode(withDeclarations);
        return attachNodes(withNode);
    }

    private Node lexField(Text text) throws LexException {
        var separator = text.firstIndexOfChar(':')
                .orElseThrow(() -> new LexException("Invalid field: " + text.computeTrimmed()));
        var name = text.slice(0, separator);
        var typeText = text.slice(separator + 1);
        var type = lexType(typeText);
        return new Field(name, type);
    }
}
