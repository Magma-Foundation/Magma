package magma.compile.rule;

import magma.api.Result;
import magma.compile.CompileException;
import magma.compile.rule.result.RuleResult;

/**
 * The Rule interface provides methods for converting between strings
 * and nodes. It includes methods to transform an input string into a node
 * and to transform a node back into a string.
 */
public interface Rule {
    RuleResult toNode(String input);

    Result<String, CompileException> fromNode(Node node);
}