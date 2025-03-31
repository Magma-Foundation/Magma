#include "ListCollector.h"
magma.collect.list.List_<T> createInitial(){return Lists.empty();
}
magma.collect.list.List_<T> fold(magma.collect.list.List_<T> tList, T t){return tList.add(t);
}

