#include "Map_.h"
expand Map__K_V
expand Option_V
expand Stream_Tuple_K_V
expand Tuple_K_V
expand Map__K_V
expand Supplier_V
expand Map__K_V
expand Map__K_V
expand Map__K_V
struct Map__K_V with(struct K key, struct V value);
struct Option_V find(struct K key);
struct Stream_Tuple_K_V stream();
struct Map__K_V ensure(struct K key, struct V(*whenPresent)(struct V), struct Supplier_V whenEmpty);
struct Map__K_V withAll(struct Map__K_V other);
int isEmpty();
struct Map__K_V remove(struct K key);
int containsKey(struct K key);
