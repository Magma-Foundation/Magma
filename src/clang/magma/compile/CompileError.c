#include "CompileError.h"
magma.compile.public CompileError(String message, magma.compile.context.Context maybeContext){this(message, maybeContext, Lists.empty());
}
magma.compile.public CompileError(String message, magma.compile.context.Context maybeContext, magma.collect.list.List_<magma.compile.CompileError> errors){this.message = message;
        this.maybeContext = new Some<>(maybeContext);
        this.errors = errors;
}
magma.compile.public CompileError(String message){this.message = message;
        this.maybeContext = new None<>();
        this.errors = Lists.empty();
}
String format(int depth, int index, magma.collect.list.List_<magma.compile.CompileError> sorted){String format = "\n%s%d) %s";
        String spacer = "| ".repeat(depth);
        String child = sorted.get(index).format(depth + 1);return format.formatted(spacer, index+1, child);
}
int depth(){return 1+errors.stream().map(CompileError.depth).foldMapping(Function.identity(), Math.max).orElse(0);
}
String display(){return format(0);
}
String format(int depth){List_<CompileError> sorted = errors.sort((error, error2) -> error.depth() - error2.depth());

        String joined = new HeadedStream<>(new RangeHead(sorted.size()))
                .map(index -> format(depth, index, sorted))
                .collect(new Joiner(""))
                .orElse("");

        String contextString = maybeContext.map(Context::display)
                .map(result -> ": " + Strings.unwrap(result))
                .orElse("");return message+contextString+joined;
}

