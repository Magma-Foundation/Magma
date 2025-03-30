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
public MapNode(){
}
public MapNode(struct Option_String maybeType, struct Map__String_String strings, struct Map__String_Node nodes, struct Map__String_List__Node nodeLists){
}
public MapNode(String type){
}
String formatEntry(int depth, String key, String value){
}
Node withString(String propertyKey, String propertyValue){
}
struct Option_String findString(String propertyKey){
}
Node withNodeList(String propertyKey, struct List__Node propertyValues){
}
struct Option_List__Node findNodeList(String propertyKey){
}
String display(){
}
String format(int depth){
}
String formatList(struct Tuple_String_List__Node entry, int depth){
}
Node mapNodeList(String propertyKey, struct Function_List__Node_List__Node mapper){
}
int is(String type){
}
Node retype(String type){
}
Node merge(Node other){
}
struct Stream_Tuple_String_String streamStrings(){
}
struct Stream_Tuple_String_List__Node streamNodeLists(){
}
Node withNode(String propertyKey, Node propertyValue){
}
struct Option_Node findNode(String propertyKey){
}
struct Stream_Tuple_String_Node streamNodes(){
}
Node mapNode(String propertyKey, struct Function_Node_Node mapper){
}
Node withNodeLists(struct Map__String_List__Node nodeLists){
}
Node withNodes(struct Map__String_Node nodes){
}
