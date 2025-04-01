#include "Formatter.h"
auto __lambda0__(){return (state, value);
}
auto __lambda1__(){return (state, value);
}
magma.result.Result<magma.compile.Node, magma.compile.CompileError> afterPass0(magma.compile.transform.State state, magma.compile.Node node){if (node.is("block")) {
            return new Ok<>(node.withString("after-children", "\n"));
        }if (node.is("function")) {
            return new Ok<>(node.withString("after-braces", "\n"));
        }return (node);
}
magma.result.Result<magma.compile.Node, magma.compile.CompileError> beforePass0(magma.compile.transform.State state, magma.compile.Node node){return (node);
}
magma.result.Result<magma.option.Tuple<magma.compile.transform.State, magma.compile.Node>, magma.compile.CompileError> beforePass(magma.compile.transform.State state, magma.compile.Node node){return beforePass0(state, node).mapValue(__lambda0__);
}
magma.result.Result<magma.option.Tuple<magma.compile.transform.State, magma.compile.Node>, magma.compile.CompileError> afterPass(magma.compile.transform.State state, magma.compile.Node node){return afterPass0(state, node).mapValue(__lambda1__);
}
