package magma.app.compile.value;

import magma.api.Type;
import magma.api.option.Option;
import magma.app.compile.CompileState;

public interface Value extends Argument, Caller {
    Type resolve(CompileState state);

    Option<String> generateAsEnumValue(String structureName);
}
