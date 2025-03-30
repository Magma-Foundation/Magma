#include "Joiner.h"
struct public Joiner(){
this();}
struct Option_String createInitial(){
return new None<>();}
struct Option_String fold(struct Option_String maybeCurrent, struct String element){
return new Some<>(maybeCurrent.map(inner -> inner + delimiter + element).orElse(element));}
