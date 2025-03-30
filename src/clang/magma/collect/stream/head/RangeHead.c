#include "RangeHead.h"
struct public RangeHead(struct int extent){this.extent = extent;}struct Option_Integer next(){if (counter >= extent) return new None<>();

        int value = counter;
        counter++;
        return new Some<>(value);}