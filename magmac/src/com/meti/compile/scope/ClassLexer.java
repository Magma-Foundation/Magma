package com.meti.compile.scope;

import com.meti.collect.JavaList;
import com.meti.collect.option.Option;
import com.meti.collect.stream.Collectors;
import com.meti.compile.Lexer;
import com.meti.compile.TypeCompiler;
import com.meti.compile.node.Content;
import com.meti.compile.node.MapNode;
import com.meti.compile.node.Node;
import com.meti.compile.node.StringAttribute;
import com.meti.java.JavaString;

import static com.meti.collect.option.Options.$$;
import static com.meti.collect.option.Options.$Option;

public record ClassLexer(JavaString stripped) implements Lexer {
    @Override
    public Option<Node> lex() {
        return $Option(() -> {
            var bodyStart = stripped.firstIndexOfChar('{').$();
            var extendsIndex = stripped.firstIndexOfSlice("extends ");

            var args = stripped.sliceTo(extendsIndex.orElse(bodyStart))
                    .strip()
                    .split(" ")
                    .collect(Collectors.toList());

            var nameList = args.popLast().$();
            var flags = nameList.b();
            var name = nameList.a();

            if(!flags.contains(new JavaString("class"))) $$();

            var content = stripped.sliceFrom(bodyStart);
            var contentOutput = new Content(content, 0);

            var builder = MapNode.Builder(new JavaString("class"))
                    .withListOfStrings("flags", flags)
                    .withString("name", name)
                    .withNode("value", contentOutput);

            var withSuperClass = extendsIndex.flatMap(index -> index.next("extends ".length()))
                    .flatMap(index -> index.to(bodyStart))
                    .map(stripped::sliceBetween)
                    .map(JavaString::strip)
                    .flatMap(superClassString -> new TypeCompiler(superClassString).compile())
                    .map(superClassName -> builder.withString("extends", superClassName))
                    .orElse(builder);

            return withSuperClass.complete();
        });
    }
}