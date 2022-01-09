package com.meti.compile;

import com.meti.compile.attribute.Attribute;
import com.meti.compile.attribute.AttributeException;
import com.meti.compile.attribute.ListNodeAttribute;
import com.meti.compile.attribute.NodeAttribute;
import com.meti.compile.node.Node;
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
                new StructureLexer(text),
                new BlockLexer(text),
                new FunctionLexer(text),
                new ReturnLexer(text),
                new IntegerLexer(text));

        Option<Node> current = new None<>();
        for (Lexer lexer : lexers) {
            var next = lexer.lex();
            if (next.isPresent()) {
                current = next;
                break;
            }
        }

        return current.orElseThrow(() -> new LexException("Unknown input: " + text.compute()));
    }

    static Node lexType(Text text) throws LexException {
        var isSigned = text.startsWithChar('I');
        var isUnsigned = text.startsWithChar('U');
        if (isSigned || isUnsigned) {
            var bitsText = text.slice(1);
            var bits = Integer.parseInt(bitsText.compute());
            return new IntegerType(isSigned, bits);
        } else {
            throw new LexException("Cannot lex type: " + text);
        }
    }

    private Node attachFields(Node node) throws AttributeException, LexException {
        var types = node.apply(Attribute.Group.Field).collect(Collectors.toList());
        var current = node;
        for (Attribute.Type type : types) {
            var oldField = current.apply(type).asNode();
            var oldType = oldField.apply(Attribute.Type.Type).asNode()
                    .apply(Attribute.Type.Value)
                    .asInput();
            var newType = lexType(oldType);
            var newField = oldField.with(Attribute.Type.Type, new NodeAttribute(newType));
            current = current.with(type, new NodeAttribute(newField));
        }
        return current;
    }

    private Node attachNodes(Node withFields) throws AttributeException, LexException {
        var types = withFields.apply(Attribute.Group.Nodes).collect(Collectors.toList());
        var current = withFields;
        for (Attribute.Type type : types) {
            var oldNodes = current.apply(type).asStreamOfNodes().collect(Collectors.toList());
            var newNodes = new ArrayList<Node>();
            for (Node oldNode : oldNodes) {
                newNodes.add(lexAST(oldNode.apply(Attribute.Type.Value).asInput()));
            }
            current = current.with(type, new ListNodeAttribute(newNodes));
        }
        return current;
    }

    Node lex() throws CompileException {
        return lexAST(text);
    }

    private Node lexAST(Text text) throws LexException, AttributeException {
        var node = lexNode(text);
        var withFields = attachFields(node);
        return attachNodes(withFields);
    }
}
