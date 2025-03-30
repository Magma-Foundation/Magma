#include "Map_.h"
struct Map__K_V with(struct K key, struct V value);
struct Option_V find(struct K key);
struct Stream_Tuple_K_V stream();
struct Map__K_V ensure(struct K key, struct V(*whenPresent)(struct V), struct Supplier_V whenEmpty);
struct Map__K_V withAll(struct Map__K_V other);
int isEmpty();
struct Map__K_V remove(struct K key);
int containsKey(struct K key);

