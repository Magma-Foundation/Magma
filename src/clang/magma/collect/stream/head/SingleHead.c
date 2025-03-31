#include "SingleHead.h"
magma.collect.stream.head.public SingleHead(T value){this.value = value;
}
magma.option.Option<T> next(){if (retrieved) return new None<>();

        retrieved = true;return (value);
}

