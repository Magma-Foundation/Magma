#include "MutableDividingState.h"
struct public MutableDividingState(struct List__Character queue){this(queue, Lists.empty(), StringBuilder(), 0);
}
struct public MutableDividingState(struct List__Character queue, struct List__String segments, struct StringBuilder buffer, int depth){this.queue = queue;
        this.segments = segments;
        this.buffer = buffer;
        this.depth = depth;
}
struct DividingState append(struct char c){buffer.append(c);return this;
}
int isLevel(){return depth == 0;
}
struct DividingState exit(){this.depth = depth - 1;return this;
}
struct DividingState enter(){this.depth = depth + 1;return this;
}
struct DividingState advance(){this.segments = segments.add(buffer.toString());
        this.buffer = new StringBuilder();return this;
}
struct List__String segments(){return segments;
}
int isShallow(){return depth == 1;
}
struct Option_Tuple_Character_DividingState append(){return pop().map(__lambda0__);
}
struct Option_Tuple_Character_DividingState pop(){return queue.popFirst().map(__lambda1__);
}
struct Option_Character peek(){return queue.findFirst();
}
struct Option_DividingState appendAndDiscard(){return append().map(Tuple.right);
}
auto __lambda0__();
auto __lambda1__();

