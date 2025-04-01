#include "MutableDividingState.h"
int __lambda0__(){return (tuple.left(), tuple.right().append(tuple.left()));
}
int __lambda1__(){return (tuple.left(), MutableDividingState(tuple.right(), segments, buffer, depth));
}
public MutableDividingState(List_<char> queue){this(queue, Lists.empty(), StringBuilder(), 0);
}
public MutableDividingState(List_<char> queue, List_<String> segments, StringBuilder buffer, int depth){this.queue = queue;
        this.segments = segments;
        this.buffer = buffer;
        this.depth = depth;
}
DividingState append(char c){buffer.append(c);return this;
}
int isLevel(){return depth == 0;
}
DividingState exit(){this.depth = depth - 1;return this;
}
DividingState enter(){this.depth = depth + 1;return this;
}
DividingState advance(){this.segments = segments.add(buffer.toString());
        this.buffer = new StringBuilder();return this;
}
List_<String> segments(){return segments;
}
int isShallow(){return depth == 1;
}
Option<Tuple<char, DividingState>> append(){return pop().map(__lambda0__);
}
Option<Tuple<char, DividingState>> pop(){return queue.popFirst().map(__lambda1__);
}
Option<char> peek(){return queue.findFirst();
}
Option<DividingState> appendAndDiscard(){return append().map(Tuple.right);
}
