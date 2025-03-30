#include "/../../jvm/collect/map/Maps.h"
#include "/../../jvm/collect/stream/Streams.h"
#include "/../../magma/collect/list/List_.h"
#include "/../../magma/collect/map/Map_.h"
#include "/../../magma/collect/stream/Joiner.h"
#include "/../../magma/collect/stream/Stream.h"
#include "/../../magma/option/None.h"
#include "/../../magma/option/Option.h"
#include "/../../magma/option/Some.h"
#include "/../../magma/option/Tuple.h"
#include "/../../java/util/function/Function.h"
struct MapNode{struct Option_String maybeTypestruct Map__String_String stringsstruct Map__String_Node nodesstruct Map__String_List__Node nodeLists};
struct public MapNode(){
}
struct public MapNode(struct Option_String maybeType, struct Map__String_String strings, struct Map__String_Node nodes, struct Map__String_List__Node nodeLists){
}
struct public MapNode(struct String type){
}
struct String formatEntry(struct int depth, struct String key, struct String value){
}
struct Node withString(struct String propertyKey, struct String propertyValue){
}
struct Option_String findString(struct String propertyKey){
}
struct Node withNodeList(struct String propertyKey, struct List__Node propertyValues){
}
struct Option_List__Node findNodeList(struct String propertyKey){
}
struct String display(){
}
struct String format(struct int depth){
}
struct String formatList(struct Tuple_String_List__Node entry, struct int depth){
}
struct Node mapNodeList(struct String propertyKey, struct Function_List__Node_List__Node mapper){
}
int is(struct String type){
}
struct Node retype(struct String type){
}
struct Node merge(struct Node other){
}
struct Stream_Tuple_String_String streamStrings(){
}
struct Stream_Tuple_String_List__Node streamNodeLists(){
}
struct Node withNode(struct String propertyKey, struct Node propertyValue){
}
struct Option_Node findNode(struct String propertyKey){
}
struct Stream_Tuple_String_Node streamNodes(){
}
struct Node mapNode(struct String propertyKey, struct Function_Node_Node mapper){
}
struct Node withNodeLists(struct Map__String_List__Node nodeLists){
}
struct Node withNodes(struct Map__String_Node nodes){
}
