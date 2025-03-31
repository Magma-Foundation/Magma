#include "MutableDividingState.h"
magma.compile.rule.divide.public MutableDividingState(magma.collect.list.List_<char> queue){this(queue, Lists.empty(), StringBuilder(), 0);
}
magma.compile.rule.divide.public MutableDividingState(magma.collect.list.List_<char> queue, magma.collect.list.List_<String> segments, magma.compile.rule.divide.StringBuilder buffer, int depth){this.queue = queue;
        this.segments = segments;
        this.buffer = buffer;
        this.depth = depth;
}
magma.compile.rule.divide.DividingState append(char c){buffer.append(c);return this;
}
magma.compile.rule.divide.boolean isLevel(){return depth == 0;
}
magma.compile.rule.divide.DividingState exit(){this.depth = depth - 1;return this;
}
magma.compile.rule.divide.DividingState enter(){this.depth = depth + 1;return this;
}
magma.compile.rule.divide.DividingState advance(){this.segments = segments.add(buffer.toString());
        this.buffer = new StringBuilder();return this;
}
magma.collect.list.List_<String> segments(){return segments;
}
magma.compile.rule.divide.boolean isShallow(){return depth == 1;
}
magma.option.Option<magma.option.Tuple<char, magma.compile.rule.divide.DividingState>> append(){return pop().map(__lambda0__);
}
magma.option.Option<magma.option.Tuple<char, magma.compile.rule.divide.DividingState>> pop(){return queue.popFirst().map(__lambda1__);
}
magma.option.Option<char> peek(){return queue.findFirst();
}
magma.option.Option<magma.compile.rule.divide.DividingState> appendAndDiscard(){return append().map(Tuple.right);
}
auto __lambda0__();
auto __lambda1__();

