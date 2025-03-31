#include "RangeHead.h"
magma.collect.stream.head.public RangeHead(int extent){this.extent = extent;
}
magma.option.Option<int> next(){if (counter >= extent) return new None<>();

        int value = counter;
        counter++;return (value);
}

