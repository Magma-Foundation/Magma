#include "HeadedStream.h"
struct R foldWithInitial(struct R initial, struct BiFunction_R_T_R folder){R current = initial;while (true) {
            R finalCurrent = current;
            Tuple<Boolean, R> tuple = head.next()
                    .map(inner -> folder.apply(finalCurrent, inner))
                    .toTuple(current);

            if (tuple.left()) {
                current = tuple.right();
            } else {
                break;
            }
        }return current;}struct Stream_R map(struct R(*mapper)(struct T)){return new HeadedStream<>(() -> head.next().map(mapper));}struct C collect(struct Collector_T_C collector){return foldWithInitial(collector.createInitial(), collector::fold);}struct Option_T next(){return head.next();}struct Option_R foldMapping(struct R(*mapper)(struct T), struct BiFunction_R_T_R folder){return head.next().map(mapper).map(initial -> foldWithInitial(initial, folder));}struct Stream_T filter(struct Predicate_T predicate){return flatMap(element -> new HeadedStream<>(predicate.test(element)
                ? new SingleHead<>(element)
                : new EmptyHead<>()));}struct Stream_R flatMap(struct Stream_R(*mapper)(struct T)){return this.<Stream<R>>foldWithInitial(new HeadedStream<>(new EmptyHead<>()),
                (rStream, t) -> rStream.concat(mapper.apply(t)));}struct magma_result_Result_R_X foldToResult(struct R initial, struct BiFunction_R_T_Result_R_X folder){return this.<Result<R, X>>foldWithInitial(new Ok<>(initial), (current, t) -> current.flatMapValue(inner -> folder.apply(inner, t)));}struct Stream_T concat(struct Stream_T other){return new HeadedStream<>(() -> head.next().or(other::next));}