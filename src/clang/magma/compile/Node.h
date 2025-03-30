#ifndef magma_compile_Node
#define magma_compile_Node
#include "../../magma/collect/list/List_.h"
#include "../../magma/collect/map/Map_.h"
#include "../../magma/collect/stream/Stream.h"
#include "../../magma/option/Option.h"
#include "../../magma/option/Tuple.h"
struct Node{};
struct Node withString(struct String propertyKey, struct String propertyValue);
struct Option_String findString(struct String propertyKey);
struct Node withNodeList(struct String propertyKey, struct List__Node propertyValues);
struct Option_List__Node findNodeList(struct String propertyKey);
struct String display();
struct String format(struct int depth);
struct Node mapNodeList(struct String propertyKey, struct Function_List__Node_List__Node mapper);
int is(struct String type);
struct Node retype(struct String type);
struct Node merge(struct Node other);
struct Stream_Tuple_String_String streamStrings();
struct Stream_Tuple_String_List__Node streamNodeLists();
struct Node withNode(struct String propertyKey, struct Node propertyValue);
struct Option_Node findNode(struct String propertyKey);
struct Stream_Tuple_String_Node streamNodes();
struct Node mapNode(struct String propertyKey, struct Function_Node_Node mapper);
struct Node withNodeLists(struct Map__String_List__Node nodeLists);
struct Node withNodes(struct Map__String_Node nodes);
struct Node removeNode(struct String propertyKey);
#endif
