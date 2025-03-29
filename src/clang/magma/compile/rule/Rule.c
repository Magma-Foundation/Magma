package magma.compile.rule;class package magma.compile.rule;{package magma.compile.rule;

import magma.compile.CompileError;class import magma.compile.CompileError;{

import magma.compile.CompileError;
import magma.compile.Node;class import magma.compile.Node;{
import magma.compile.Node;
import magma.result.Result;class import magma.result.Result;{
import magma.result.Result;

public interface Rule {
    Result<Node, CompileError> parse(String input);

    Result<String, CompileError> generate(Node node);
}
class public interface Rule {
    Result<Node, CompileError> parse(String input);

    Result<String, CompileError> generate(Node node);
}{

public interface Rule {
    Result<Node, CompileError> parse(String input);

    Result<String, CompileError> generate(Node node);
}
