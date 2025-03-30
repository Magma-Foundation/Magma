#include "None.h"
struct Option_R map(struct R(*mapper)(struct T)){return None_();
}
struct T orElseGet(struct T(*other)()){return other.get();
}
struct Tuple_Boolean_T toTuple(struct T other){return Tuple_(false, other);
}
struct void ifPresent(struct Consumer_T consumer){
}
struct T orElse(struct T other){return other;
}
struct Option_T filter(struct Predicate_T predicate){return None_();
}
int isPresent(){return false;
}
struct R match(struct R(*ifPresent)(struct T), struct R(*ifEmpty)()){return ifEmpty.get();
}
int isEmpty(){return true;
}
struct Option_T or(struct Option_T(*other)()){return other.get();
}
struct Option_R flatMap(struct Option_R(*mapper)(struct T)){return None_();
}

