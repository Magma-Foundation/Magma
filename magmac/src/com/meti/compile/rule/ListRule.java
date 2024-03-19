package com.meti.compile.rule;

import com.meti.collect.option.None;
import com.meti.collect.option.Option;
import com.meti.collect.option.Some;
import com.meti.java.JavaString;

import java.util.Objects;

import static com.meti.collect.option.None.None;
import static com.meti.collect.option.Some.Some;

public class ListRule implements Rule {
    private final Rule value;
    private final Rule delimiter;

    private ListRule(Rule value, Rule delimiter) {
        this.value = value;
        this.delimiter = delimiter;
    }

    public static ListRule List(Rule value, Rule delimiter) {
        return new ListRule(value, delimiter);
    }

    @Override
    public Option<RuleResult> apply(JavaString input) {
        var state = new State(input, new MapRuleResult(input.start()));

        while (true) {
            var tuple1 = value.apply(state.input)
                    .map(state::getState)
                    .toResolvedTuple(state);

            if (tuple1.a()) {
                state = tuple1.b();
            } else {
                break;
            }

            JavaString finalRemainingInput = state.input;
            var tuple = delimiter.apply(state.input)
                    .map(RuleResult::length)
                    .map(finalRemainingInput::sliceFrom)
                    .toResolvedTuple(state.input);

            if (tuple.a()) {
                state.input = tuple.b();
            } else {
                break;
            }
        }

        return state.result.isEmpty() ? None() : Some(state.result);
    }

    static final class State {
        private final RuleResult result;
        private JavaString input;

        State(JavaString input, RuleResult result) {
            this.input = input;
            this.result = result;
        }

        private State getState(RuleResult valueResult) {
            var newResult = MapRuleResult.merge(this.result, valueResult);
            var newInput = this.input.sliceFrom(valueResult.length());
            return new State(newInput, newResult);
        }

        public JavaString input() {
            return input;
        }

        public RuleResult result() {
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) return true;
            if (obj == null || obj.getClass() != this.getClass()) return false;
            var that = (State) obj;
            return Objects.equals(this.input, that.input) &&
                   Objects.equals(this.result, that.result);
        }

        @Override
        public int hashCode() {
            return Objects.hash(input, result);
        }

        @Override
        public String toString() {
            return "State[" +
                   "input=" + input + ", " +
                   "result=" + result + ']';
        }
    }
}
