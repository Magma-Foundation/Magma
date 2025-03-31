#include "SetCollector.h"
magma.collect.set.Set_<T> createInitial(){return Sets.empty();
}
magma.collect.set.Set_<T> fold(magma.collect.set.Set_<T> tSet, T t){return tSet.add(t);
}

