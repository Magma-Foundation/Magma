#include "OrState.h"
// expand OrState_T = OrState<struct T>
// expand Result_T_List__CompileError = Result<struct T, struct List__CompileError>
// expand List__CompileError = List_<struct CompileError>
// expand OrState_T = OrState<struct T>
// expand Option_T = Option<struct T>
// expand List__CompileError = List_<struct CompileError>
struct public OrState(){this(new None<>(), Lists.empty());}struct OrState_T withValue(struct T value){return new OrState<>(new Some<>(value), errors);}struct Result_T_List__CompileError toResult(){return maybeValue
                .<Result<T, List_<CompileError>>>map(Ok::new)
                .orElseGet(() -> new Err<>(errors));}struct OrState_T withError(struct CompileError error){return new OrState<>(maybeValue, errors.add(error));}