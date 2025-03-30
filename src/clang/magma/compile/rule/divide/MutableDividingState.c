#include "MutableDividingState.h"
struct public MutableDividingState(List_<struct Character> queue}{this(queue, Lists.empty(), new StringBuilder(), 0);}struct public MutableDividingState(List_<struct Character> queue, List_<struct String> segments, struct StringBuilder buffer, struct int depth}{this.queue = queue;
        this.segments = segments;
        this.buffer = buffer;
        this.depth = depth;}struct DividingState append(struct char c}{buffer.append(c);return this;}int isLevel(}{return depth == 0;}struct DividingState exit(}{this.depth = depth - 1;return this;}struct DividingState enter(}{this.depth = depth + 1;return this;}struct DividingState advance(}{this.segments = segments.add(buffer.toString());
        this.buffer = new StringBuilder();return this;}List_<struct String> segments(}{return segments;}int isShallow(}{return depth == 1;}Option<Tuple<struct Character, struct DividingState>> append(}{return queue.popFirst().map(tuple -> {
            return new Tuple<>(tuple.left(), new MutableDividingState(tuple.right(), segments, buffer, depth));
        });}Option<struct DividingState> appendAndDiscard(}{return append().map(Tuple::right);}