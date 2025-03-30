#include "Joiner.h"
struct public Joiner(){this("");
}
struct Option_String createInitial(){return None_();
}
struct Option_String fold(struct Option_String maybeCurrent, struct String element){return Some_(maybeCurrent.map(__lambda0__).orElse(element));
}
auto __lambda0__();

