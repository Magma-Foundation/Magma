package magma.app;

import magma.api.Type;
import magma.api.option.Option;
import magma.app.compile.Argument;
import magma.app.compile.Caller;
import magma.app.compile.CompileState;

public interface Value extends Argument, Caller {
    Type resolve(CompileState state);

    Option<String> generateAsEnumValue(String structureName);
}
