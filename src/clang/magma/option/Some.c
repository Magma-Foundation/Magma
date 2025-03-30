#include "Some.h"
struct Option_R map(struct R(*mapper)(struct T)){return Some_(mapper.apply(value));
}
struct T orElseGet(struct T(*other)()){return value;
}
struct Tuple_Boolean_T toTuple(struct T other){return Tuple_(true, value);
}
struct void ifPresent(struct Consumer_T consumer){consumer.accept(value);
}
struct T orElse(struct T other){return value;
}
struct Option_T filter(struct Predicate_T predicate){return predicate.test(value)?this:None_();
}
int isPresent(){return true;
}
struct R match(struct R(*ifPresent)(struct T), struct R(*ifEmpty)()){return ifPresent.apply(value);
}
int isEmpty(){return false;
}
struct Option_T or(struct Option_T(*other)()){return this;
}
struct Option_R flatMap(struct Option_R(*mapper)(struct T)){return mapper.apply(value);
}

