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
        }return current;
}
struct Stream_R map(struct R(*mapper)(struct T)){return HeadedStream_(__lambda0__);
}
struct C collect(struct Collector_T_C collector){return foldWithInitial(collector.createInitial(), collector.fold);
}
struct Option_T next(){return head.next();
}
struct Option_R foldMapping(struct R(*mapper)(struct T), struct BiFunction_R_T_R folder){return head.next().map(mapper).map(__lambda1__);
}
struct Stream_T filter(struct Predicate_T predicate){return flatMap(__lambda2__);
}
struct Stream_R flatMap(struct Stream_R(*mapper)(struct T)){return this.foldWithInitial(HeadedStream_(EmptyHead_()), __lambda3__);
}
struct magma_result_Result_R_X foldToResult(struct R initial, struct BiFunction_R_T_Result_R_X folder){return this.foldWithInitial(Ok_(initial), __lambda5__);
}
struct Stream_T concat(struct Stream_T other){return HeadedStream_(__lambda6__);
}
auto __lambda0__();
auto __lambda1__();
auto __lambda2__();
auto __lambda3__();
auto __lambda5__();
auto __lambda6__();

