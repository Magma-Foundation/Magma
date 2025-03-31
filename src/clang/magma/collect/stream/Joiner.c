#include "Joiner.h"
magma.collect.stream.public Joiner(){this("");
}
magma.option.Option<String> createInitial(){return ();
}
magma.option.Option<String> fold(magma.option.Option<String> maybeCurrent, String element){return (maybeCurrent.map(__lambda0__+delimiter+element).orElse(element));
}
auto __lambda0__();

