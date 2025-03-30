#ifndef magma_option_None
#define magma_option_None
struct None{};
// expand Option_R = Option<struct R>
// expand Supplier_T = Supplier<struct T>
// expand Tuple_Boolean_T = Tuple<struct Boolean, struct T>
// expand Consumer_T = Consumer<struct T>
// expand Option_T = Option<struct T>
// expand Predicate_T = Predicate<struct T>
// expand Supplier_R = Supplier<struct R>
// expand Option_T = Option<struct T>
// expand Supplier_Option_T = Supplier<struct Option_T>
// expand Option_T = Option<struct T>
// expand Option_R = Option<struct R>
// expand Option_R = Option<struct R>
struct Option_R map(struct R(*mapper)(struct T));
struct T orElseGet(struct Supplier_T other);
struct Tuple_Boolean_T toTuple(struct T other);
struct void ifPresent(struct Consumer_T consumer);
struct T orElse(struct T other);
struct Option_T filter(struct Predicate_T predicate);
int isPresent();
struct R match(struct R(*ifPresent)(struct T), struct Supplier_R ifEmpty);
int isEmpty();
struct Option_T or(struct Supplier_Option_T other);
struct Option_R flatMap(struct Option_R(*mapper)(struct T));
#endif
