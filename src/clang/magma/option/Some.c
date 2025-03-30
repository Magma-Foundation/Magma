#include "Some.h"
struct Option_R map(struct R(*mapper)(struct T)){
return new Some<>(mapper.apply(value));}
struct T orElseGet(struct Supplier_T other){
return value;}
struct Tuple_Boolean_T toTuple(struct T other){
return new Tuple<>(true, value);}
struct void ifPresent(struct Consumer_T consumer){
consumer.accept(value);}
struct T orElse(struct T other){
return value;}
struct Option_T filter(struct Predicate_T predicate){
return predicate.test(value) ? this : new None<>();}
int isPresent(){
return true;}
struct R match(struct R(*ifPresent)(struct T), struct Supplier_R ifEmpty){
return ifPresent.apply(value);}
int isEmpty(){
return false;}
struct Option_T or(struct Supplier_Option_T other){
return this;}
struct Option_R flatMap(struct Option_R(*mapper)(struct T)){
return mapper.apply(value);}
