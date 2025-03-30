#include "ListCollector.h"
struct List__T createInitial(){return Lists.empty();}struct List__T fold(struct List__T tList, struct T t){return tList.add(t);}