#include "Joiner.h"
auto __lambda0__();
magma.collect.stream.public Joiner(){this("");
}
magma.option.Option<String> createInitial(){return ();
}
magma.option.Option<String> fold(magma.option.Option<String> maybeCurrent, String element){return (maybeCurrent.map(__lambda0__+delimiter+element).orElse(element));
}
