#include "RangeHead.h"
public RangeHead(int extent){this.extent = extent;
}
Option<int> next(){if (counter >= extent) return new None<>();

        int value = counter;
        counter++;return (value);
}
