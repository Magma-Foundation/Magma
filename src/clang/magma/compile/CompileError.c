#include "CompileError.h"
struct public CompileError(struct String message, struct Context context){this(message, context, Lists.empty());
}
struct public CompileError(struct String message, struct Context context, struct List__CompileError errors){this.message = message;
        this.context = context;
        this.errors = errors;
}
struct String format(int depth, int index, struct List__CompileError sorted){String format = "\n%s%d) %s";
        String spacer = "| ".repeat(depth);
        String child = sorted.get(index).format(depth + 1);return format.formatted(spacer, index+1, child);
}
int depth(){return 1+errors.stream().map(CompileError.depth).foldMapping(Function.identity(), Math.max).orElse(0);
}
struct String display(){return format(0);
}
struct String format(int depth){List_<CompileError> sorted = errors.sort((error, error2) -> error.depth() - error2.depth());

        String joined = new HeadedStream<>(new RangeHead(sorted.size()))
                .map(index -> format(depth, index, sorted))
                .collect(new Joiner(""))
                .orElse("");return message+": "+Strings.unwrap(context.display())+joined;
}

