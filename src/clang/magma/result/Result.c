package magma.result;class package magma.result;{package magma.result;

import magma.option.Option;class import magma.option.Option;{

import magma.option.Option;
import magma.option.Tuple;class import magma.option.Tuple;{
import magma.option.Tuple;

import java.util.function.Function;class import java.util.function.Function;{

import java.util.function.Function;
import java.util.function.Supplier;class import java.util.function.Supplier;{
import java.util.function.Supplier;

public interface Result<T, X> {
    Option<T> findValue();

    Option<X> findError();

    <R> Result<R, X> mapValue(Function<T, R> mapper);

    <R> Result<R, X> flatMapValue(Function<T, Result<R, X>> mapper);

    <R> Result<T, R> mapErr(Function<X, R> mapper);

    <R> R match(Function<T, R> whenOk, Function<X, R> whenErr);

    <R> Result<Tuple<T, R>, X> and(Supplier<Result<R, X>> other);
}
class public interface Result<T, X> {
    Option<T> findValue();

    Option<X> findError();

    <R> Result<R, X> mapValue(Function<T, R> mapper);

    <R> Result<R, X> flatMapValue(Function<T, Result<R, X>> mapper);

    <R> Result<T, R> mapErr(Function<X, R> mapper);

    <R> R match(Function<T, R> whenOk, Function<X, R> whenErr);

    <R> Result<Tuple<T, R>, X> and(Supplier<Result<R, X>> other);
}{

public interface Result<T, X> {
    Option<T> findValue();

    Option<X> findError();

    <R> Result<R, X> mapValue(Function<T, R> mapper);

    <R> Result<R, X> flatMapValue(Function<T, Result<R, X>> mapper);

    <R> Result<T, R> mapErr(Function<X, R> mapper);

    <R> R match(Function<T, R> whenOk, Function<X, R> whenErr);

    <R> Result<Tuple<T, R>, X> and(Supplier<Result<R, X>> other);
}
