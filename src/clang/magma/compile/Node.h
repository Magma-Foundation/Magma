#ifndef magma_compile_Node
#define magma_compile_Node
#include "../../magma/collect/list/List_.h"
#include "../../magma/collect/map/Map_.h"
#include "../../magma/collect/stream/Stream.h"
#include "../../magma/option/Option.h"
#include "../../magma/option/Tuple.h"
#include "../../magma/result/Result.h"
struct Node{
};
// expand Option_String = Option<struct String>
// expand List__Node = List_<struct Node>
// expand Option_List__Node = Option<struct List__Node>
// expand List__Node = List_<struct Node>
// expand List__Node = List_<struct Node>
// expand List__Node = List_<struct Node>
// expand Stream_Tuple_String_String = Stream<struct Tuple_String_String>
// expand Tuple_String_String = Tuple<struct String, struct String>
// expand Stream_Tuple_String_List__Node = Stream<struct Tuple_String_List__Node>
// expand Tuple_String_List__Node = Tuple<struct String, struct List__Node>
// expand List__Node = List_<struct Node>
// expand Option_Node = Option<struct Node>
// expand Stream_Tuple_String_Node = Stream<struct Tuple_String_Node>
// expand Tuple_String_Node = Tuple<struct String, struct Node>
// expand Map__String_List__Node = Map_<struct String, struct List__Node>
// expand List__Node = List_<struct Node>
// expand Map__String_Node = Map_<struct String, struct Node>
struct Node withString(struct String propertyKey, struct String propertyValue);
struct Option_String findString(struct String propertyKey);
struct Node withNodeList(struct String propertyKey, struct List__Node propertyValues);
struct Option_List__Node findNodeList(struct String propertyKey);
struct String display();
struct String format(int depth);
struct Node mapNodeList(struct String propertyKey, struct List__Node(*mapper)(struct List__Node));
int is(struct String type);
struct Node retype(struct String type);
struct Node merge(struct Node other);
struct Stream_Tuple_String_String streamStrings();
struct Stream_Tuple_String_List__Node streamNodeLists();
struct Node withNode(struct String propertyKey, struct Node propertyValue);
struct Option_Node findNode(struct String propertyKey);
struct Stream_Tuple_String_Node streamNodes();
struct Node mapNode(struct String propertyKey, struct Node(*mapper)(struct Node));
struct Node withNodeLists(struct Map__String_List__Node nodeLists);
struct Node withNodes(struct Map__String_Node nodes);
struct Node removeNode(struct String propertyKey);
int hasNode(struct String propertyKey);
int hasNodeList(struct String propertyKey);
#endif

