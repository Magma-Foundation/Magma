#include "Joiner.h"
int __lambda0__(){return inner;
}
public Joiner(){this("");
}
Option<String> createInitial(){return ();
}
Option<String> fold(Option<String> maybeCurrent, String element){return (maybeCurrent.map(__lambda0__+delimiter+element).orElse(element));
}
