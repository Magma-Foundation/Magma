package magma.app.compile.value;

import magma.api.option.Option;
import magma.app.compile.CompileState;
import magma.app.compile.type.Type;

public interface Value extends Argument, Caller {
    Type resolve(CompileState state);

    Option<String> generateAsEnumValue(String structureName);
}
