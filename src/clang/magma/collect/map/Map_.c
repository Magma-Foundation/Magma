#include "Map_.h"
// expand Map__K_V = Map_<struct K, struct V>
// expand Option_V = Option<struct V>
// expand Stream_Tuple_K_V = Stream<struct Tuple_K_V>
// expand Tuple_K_V = Tuple<struct K, struct V>
// expand Map__K_V = Map_<struct K, struct V>
// expand Supplier_V = Supplier<struct V>
// expand Map__K_V = Map_<struct K, struct V>
// expand Map__K_V = Map_<struct K, struct V>
// expand Map__K_V = Map_<struct K, struct V>
struct Map__K_V with(struct K key, struct V value);
struct Option_V find(struct K key);
struct Stream_Tuple_K_V stream();
struct Map__K_V ensure(struct K key, struct V(*whenPresent)(struct V), struct Supplier_V whenEmpty);
struct Map__K_V withAll(struct Map__K_V other);
int isEmpty();
struct Map__K_V remove(struct K key);
int containsKey(struct K key);
