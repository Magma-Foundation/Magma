#include "TreeTransformingStage.h"
struct public TreeTransformingStage(struct Transformer transformer);
struct Result_Node_CompileError transform(struct Node root, struct State state);
struct Result_Node_CompileError transformNodeLists(struct Node withNodes, struct State state);
struct Result_Node_CompileError mapNodes(struct Node node, struct Tuple_String_Node tuple, struct State state);
struct Result_Node_CompileError mapNodeList(struct Node node, struct Tuple_String_List__Node tuple, struct State state);
struct Result_List__Node_CompileError mapNodeListElement(struct List__Node elements, struct Node element, struct State state);
