package com.meti.compile;

import com.meti.collect.JavaList;
import com.meti.collect.JavaMap;
import com.meti.collect.Pair;
import com.meti.collect.option.None;
import com.meti.collect.option.Option;
import com.meti.collect.result.ExceptionalStream;
import com.meti.collect.result.Result;
import com.meti.collect.result.Results;
import com.meti.collect.result.ThrowableOption;
import com.meti.collect.stream.Collectors;
import com.meti.collect.stream.PairStream;
import com.meti.collect.stream.Streams;
import com.meti.compile.attempt.CatchLexer;
import com.meti.compile.attempt.TryLexer;
import com.meti.compile.external.ImportLexer;
import com.meti.compile.external.PackageLexer;
import com.meti.compile.java.IntegerLexer;
import com.meti.compile.node.*;
import com.meti.compile.procedure.InvocationLexer;
import com.meti.compile.procedure.MethodLexer;
import com.meti.compile.scope.*;
import com.meti.compile.string.StringLexer;
import com.meti.compile.string.TextBlockLexer;
import com.meti.java.JavaString;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static com.meti.collect.option.Options.$$;
import static com.meti.collect.option.Options.$Option;
import static com.meti.collect.option.Some.Some;
import static com.meti.collect.result.Err.Err;
import static com.meti.collect.result.Ok.Ok;
import static com.meti.collect.result.Results.$Result;

public record Compiler(String input) {
    public static Result<JavaList<Node>, CompileException> lexExpression(JavaString line, int indent) {
        return lexHead(line, indent)
                .stream()
                .map(Compiler::lexTree)
                .into(ExceptionalStream::new)
                .mapInner(JavaList::stream)
                .flatMap(Results::invertStream)
                .collect(Collectors.exceptionally(Collectors.toList()));
    }

    public static JavaList<Node> lexHead(JavaString line, int indent) {
        return Streams.<Function<String, Lexer>>from(
                exp -> new IntegerLexer(JavaString.from(exp)),
                exp -> new TextBlockLexer(JavaString.from(exp)),
                exp -> new StringLexer(JavaString.from(exp)),
                exp -> new CatchLexer(JavaString.from(exp), indent),
                exp -> new ReturnLexer(JavaString.from(exp), indent), exp -> new TryLexer(JavaString.from(exp), indent), PackageLexer::new, exp -> new ClassLexer(JavaString.from(exp)), exp -> new BlockLexer(exp, indent), exp -> new MethodLexer(JavaString.from(exp), indent), exp -> new DefinitionLexer(JavaString.from(exp), indent), exp -> new ImportLexer(JavaString.from(exp)), exp -> new InvocationLexer(JavaString.from(exp)), exp -> new LambdaLexer(JavaString.from(exp)), exp -> new FieldLexer(JavaString.from(exp)), VariableLexer::new, exp -> new InterfaceLexer(JavaString.from(exp))).map(constructor -> constructor.apply(line.strip().inner())).map(lexer -> lexer.lex().next()).flatMap(Streams::fromOption).collect(Collectors.toList());
    }

    public static Result<JavaList<Node>, CompileException> lexTree(Node node) {
        return node.apply("value")
                .flatMap(Attribute::asNode)
                .map(Compiler::lexContent)
                .map(result -> result.mapValue(values1 -> {
                    return values1.stream()
                            .map(NodeAttribute::new)
                            .map(value -> node.with("value", value).orElse(node))
                            .collect(Collectors.toList());
                }))
                .orElse(Ok(JavaList.from(node)));
    }

    private static JavaList<Pair<JavaString, Attribute>> createPairs(Pair<JavaString, JavaList<Attribute>> pair, JavaList<Attribute> attributes) {
        return attributes.stream().map(attribute -> new Pair<>(pair.left(), attribute)).collect(Collectors.toList());
    }

    private static JavaList<JavaList<Pair<JavaString, Attribute>>> getCollect(JavaList<Pair<JavaString, Attribute>> first) {
        return first.stream().map(JavaList::from).collect(Collectors.toList());
    }

    private static Result<JavaMap<JavaString, JavaList<Attribute>>, CompileException> lexNodeLists(Node node) {
        return node.streamPairs(NodeListAttribute.Type)
                .map(Compiler::lexNodeListAttribute)
                .into(ExceptionalStream::new)
                .collectExceptionally(Collectors.toMap(JavaList::addAll));
    }

    private static Result<Pair<JavaString, JavaList<Attribute>>, CompileException> lexNodeListAttribute(Pair<JavaString, Attribute> pair) {
        return $Result(() -> {
            var nodeList = pair.right().asListOfNodes()
                    .into(ThrowableOption::new)
                    .orElseThrow(() -> new CompileException("Not a list of nodes."))
                    .$();

            var options = lexContentList(nodeList).$()
                    .stream()
                    .<Attribute>map(NodeListAttribute::new)
                    .collect(Collectors.toList());

            return new Pair<>(pair.left(), options.isEmpty()
                    ? JavaList.from(new NodeListAttribute(JavaList.empty()))
                    : options);
        });
    }

    private static JavaMap<JavaString, JavaList<Attribute>> findNodes(Node node) throws CompileException {
        return node.streamPairs(NodeAttribute.Type).into(PairStream::new).mapRight(child -> child.asNode().into(ThrowableOption::new).orElseThrow(() -> new CompileException("Not a node: " + child))).map(Results::invertTupleByRight).into(ExceptionalStream::new).mapInner(inner -> inner.mapRight(Compiler::lexContent)).mapInner(Results::invertTupleByRight).map(Results::flatten).into(ExceptionalStream::new).mapInner(pair -> pair.mapRight(list -> list.stream().<Attribute>map(NodeAttribute::new).collect(Collectors.toList()))).collectExceptionally(Collectors.toMap(JavaList::addAll)).$();
    }

    private static Result<JavaList<JavaList<Node>>, CompileException> lexContentList(JavaList<? extends Node> content) {
        return content.stream().map(Compiler::lexContent).into(ExceptionalStream::new).foldRightFromInitialExceptionally(new JavaList<>(), (javaListJavaList, nodeJavaList) -> javaListJavaList.stream().cross(nodeJavaList::stream).map(pair -> pair.left().add(pair.right())).collect(Collectors.toList()));
    }

    private static Result<JavaList<Node>, CompileException> lexContent(Node content) {
        if (content.is(Content.Id)) {
            return $Result(() -> {
                var value = content.apply("value").flatMap(Attribute::asString).into(ThrowableOption::new).orElseThrow(() -> new CompileException("No value present on content.")).$();
                var indent = content.apply("indent").flatMap(Attribute::asInteger).into(ThrowableOption::new).orElseThrow(() -> new CompileException("No indent present on content.")).$();
                return lexExpression(value, indent).$();
            });
        } else {
            return Err(new CompileException("Not content: '" + content));
        }
    }


    private static Result<Node, CompileException> transformAST(Node root) {
        return $Result(() -> {
            var transformed = transformNode(root).$();
            var withValues = transformed.streamPairs(NodeAttribute.Type).map(tuple -> tuple.mapRight(Attribute::asNode)).map(tuple -> tuple.into(Pair::requireRight)).flatMap(Streams::fromOption).map(tuple -> tuple.mapRight(Compiler::transformAST)).map(Results::invertTupleByRight).into(ExceptionalStream::new).foldRightFromInitialExceptionally(transformed, (node, javaStringNodeTuple) -> node.with(javaStringNodeTuple.left().inner(), new NodeAttribute(javaStringNodeTuple.right())).orElse(node)).$();
            var node = withValues.apply("children").flatMap(Attribute::asListOfNodes).<List<? extends Node>>map(JavaList::unwrap).map(children -> Streams.fromNativeList(children).map(Compiler::transformAST).collect(Collectors.exceptionally(Collectors.toList()))).into(Results::invertOption).$().flatMap(children -> withValues.with("children", new NodeListAttribute(children))).orElse(withValues);

            return node;
        });
    }

    private static Option<Result<Node, CompileException>> transformBlock(Node node) {
        if (!node.is("block")) return None.None();

        var realIndent = node.apply("indent").flatMap(Attribute::asInteger).orElse(0) + 1;
        var children = node.apply("children").flatMap(Attribute::asListOfNodes).orElse(JavaList.empty());

        var newChildren = children.stream().map(child -> {
            if (child.is("implementation") || child.is("try") || child.is("catch")) {
                return new Statement(child, realIndent);
            } else {
                return new TerminatingStatement(child, realIndent);
            }
        }).collect(Collectors.toList());

        return Some(Ok(node.with("children", new NodeListAttribute(newChildren)).orElse(node)));
    }

    private static Result<Node, CompileException> transformNode(Node node) {
        return transformBlock(node).or(() -> transformClass(node)).orElse(Ok(node));
    }

    private static Option<Result<Node, CompileException>> transformClass(Node node) {
        return $Option(() -> {
            if (!node.is("class")) return $$();

            var flags = node.apply("flags").$().asListOfStrings().$();
            var name = node.apply("name").$().asString().$();

            var outputFlags = new JavaList<JavaString>();
            if (flags.contains(JavaString.from("public"))) {
                outputFlags = JavaList.from(JavaString.from("export"));
            }

            var children = new JavaList<Node>(new ArrayList<>(node.apply("body").flatMap(Attribute::asNode).flatMap(value -> value.apply("children")).flatMap(Attribute::asListOfNodes).$().unwrap()));

            var newChildren = node.apply("extends").flatMap(Attribute::asString).map(extendsValue -> children.add(MapNode.Builder(JavaString.from("with")).withString("type", extendsValue).complete())).orElse(children);

            var contentOutput = new BlockNode(0, newChildren);

            return Ok(MapNode.Builder(JavaString.from("object")).withListOfStrings("flags", outputFlags).withString("name", name).withNode("body", contentOutput).complete());
        });
    }

    private static Result<Result<Node, CompileException>, CompileException> getResultCompileExceptionResult(JavaString line) {
        return $Result(() -> {
            var list = lexExpression(line, 0).$();
            return list.first().into(ThrowableOption::new).orElseThrow(() -> new CompileException("Failed to lex: '" + line + "'"));
        });
    }

    private static Result<JavaString, CompileException> renderExpression(Node node) {
        return $Result(() -> {
            var root = node.apply("value")
                    .flatMap(Attribute::asNode)
                    .map(Compiler::renderExpression)
                    .map(result -> result.mapValue(value -> node.with("value", new NodeAttribute(new Content(value, 0))).orElse(node)))
                    .orElse(Ok(node))
                    .$();

            return root.render()
                    .map(JavaString::from)
                    .into(ThrowableOption::new)
                    .orElseThrow(() -> new CompileException("Failed to render: '%s'".formatted(node)))
                    .$();
        });
    }

    Result<JavaString, CompileException> compile() throws CompileException {
        return $Result(() -> {
            var tree = new Splitter(this.input())
                    .split()
                    .map(JavaString::from)
                    .map(Compiler::getResultCompileExceptionResult)
                    .map(Results::flatten)
                    .collect(Collectors.exceptionally(Collectors.toList())).$();

            var outputTree = tree.stream()
                    .filter(element -> !element.is("package"))
                    .map(Compiler::transformAST)
                    .collect(Collectors.exceptionally(Collectors.toList()))
                    .$();

            return outputTree.stream()
                    .map(Compiler::renderExpression)
                    .collect(Collectors.exceptionally(Collectors.joining())).mapValue(value -> value.orElse(JavaString.Empty));
        }).$();
    }
}