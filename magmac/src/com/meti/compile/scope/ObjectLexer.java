package com.meti.compile.scope;

import com.meti.collect.JavaList;
import com.meti.collect.option.Option;
import com.meti.collect.stream.Collectors;
import com.meti.compile.Lexer;
import com.meti.compile.node.Content;
import com.meti.compile.node.Node;
import com.meti.java.JavaString;

import java.util.ArrayList;
import java.util.List;

import static com.meti.collect.option.Options.$$;
import static com.meti.collect.option.Options.$Option;

public record ObjectLexer(JavaString stripped) implements Lexer {
    @Override
    public Option<Node> lex() {
        return $Option(() -> {
            var bodyStart = stripped.firstIndexOfChar('{').$();
            var args = stripped.sliceTo(bodyStart)
                    .strip()
                    .split(" ")
                    .collect(Collectors.toList());

            var nameList = args.popLast().$();
            var flags = nameList.b();
            var name = nameList.a();

            if(!flags.contains(new JavaString("class"))) $$();

            var content = stripped.sliceFrom(bodyStart);
            var contentOutput = new Content(content, 0);

            var outputFlags = new JavaList<JavaString>();
            if (flags.contains(new JavaString("public"))) {
                outputFlags = JavaList.from(new JavaString("export"));
            }

            return new ObjectNode(outputFlags, name, contentOutput);
        });
    }
}