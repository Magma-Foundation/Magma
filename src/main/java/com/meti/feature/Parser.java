package com.meti.feature;

import com.meti.InterpretationError;
import com.meti.safe.option.Option;
import com.meti.safe.result.Result;
import com.meti.state.State;

public interface Parser {
    Option<Result<State, InterpretationError>> parse();
}
