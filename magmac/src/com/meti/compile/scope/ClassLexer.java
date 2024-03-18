package com.meti.compile.scope;

import com.meti.collect.Range;
import com.meti.collect.option.Option;
import com.meti.compile.Lexer;
import com.meti.compile.TypeCompiler;
import com.meti.compile.node.Content;
import com.meti.compile.node.MapNode;
import com.meti.compile.node.Node;
import com.meti.compile.rule.*;
import com.meti.java.JavaString;

import static com.meti.collect.option.Options.$$;
import static com.meti.collect.option.Options.$Option;

public record ClassLexer(JavaString stripped) implements Lexer {
    @Override
    public Option<Node> lex() {
        return $Option(() -> {
            var result = new InclusiveDelimiterRule("{", "beforeContent", "content")
                    .apply(stripped);

            var beforeContent = result.findText("beforeContent").$();
            var content = result.findText("content").$();

            var extendsRange = beforeContent.firstRangeOfSlice("extends ");
            var end = extendsRange.map(Range::startIndex).orElse(beforeContent.end());
            var javaString = beforeContent.sliceTo(end).strip();

            var result1 = new ExclusiveDelimiterRule(" ",
                    new TextSplitRule("flags", " "),
                    new ValueRule("name"))
                    .apply(javaString);

            var flags = result1.findTextList("flags").$();
            var name = result1.findText("name").$();

            if (!flags.contains(new JavaString("class"))) $$();

            var contentOutput = new Content(content, 0);

            var builder = MapNode.Builder(new JavaString("class"))
                    .withListOfStrings("flags", flags)
                    .withString("name", name)
                    .withNode("value", contentOutput);

            var bodyStart = stripped.firstIndexOfChar('{').$();
            var withSuperClass = extendsRange.flatMap(range -> range.endIndex().to(bodyStart))
                    .map(stripped::sliceBetween)
                    .map(JavaString::strip)
                    .flatMap(superClassString -> new TypeCompiler(superClassString).compile())
                    .map(superClassName -> builder.withString("extends", superClassName))
                    .orElse(builder);

            return withSuperClass.complete();
        });
    }
}