#include "CompileError.h"
struct public CompileError(struct String message, struct Context context){this(message, context, Lists.empty());
}
struct public CompileError(struct String message, struct Context context, struct List__CompileError errors){this.message = message;
        this.context = context;
        this.errors = errors;
}
struct String format(struct int depth, struct int index, struct List__CompileError sorted){return  + .repeat(depth) + (index + 1) +  + sorted.get(index).format(depth + 1);
}
struct int depth(){return 1 + errors.stream()
                .map(CompileError::depth)
                .foldMapping(Function.identity(), Math::max)
                .orElse(0);
}
struct String display(){return format(0);
}
struct String format(struct int depth){List_<CompileError> sorted = errors.sort((error, error2) -> error.depth() - error2.depth());

        String joined = new HeadedStream<>(new RangeHead(sorted.size()))
                .map(index -> format(depth, index, sorted))
                .collect(new Joiner())
                .orElse();return message +  + context.display() + joined;
}

