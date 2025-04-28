/* public */struct Main {/* private */struct List<T> {
	/* List<T> add(T element) */;
	/* Iterator<T> iter() */;
};
/* private */struct Head<T> {
	/* Optional<T> next() */;
};
/* private */struct Collector<T, C> {
	/* C createInitial() */;
	/* C fold(C current, T element) */;
};
/* private */struct Iterator<T>(Head<T> head) {
};
/* private */struct Tuple<A, B>(A left, B right) {
};
/* private static */struct RangeHead implements Head<Integer> {
	/* private final */ int length;
	/* private int counter = 0 */;
};
/* private */struct JavaList<T>(java.util.List<T> list) implements Main.List<T> {
};
/* private static */struct State {
	/* private final */ char* input;
	/* private */ List<char*> segments;
	/* private */ int index;
	/* private */ /* StringBuilder */ buffer;
	/* private */ int depth;
};
/* private */struct Joiner() implements Collector<String, Optional<String>> {
};
/* private static */struct Lists {
};

	/* private static final List<String> methods = Lists.empty() */;
};
