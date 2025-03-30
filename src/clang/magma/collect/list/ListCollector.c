#include "ListCollector.h"
// expand List__T = List_<struct T>
// expand List__T = List_<struct T>
// expand List__T = List_<struct T>
struct List__T createInitial(){return Lists.empty();}struct List__T fold(struct List__T tList, struct T t){return tList.add(t);}