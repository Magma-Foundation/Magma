package magmac.app.lang.web;

import magmac.app.lang.java.JavaLang;

public final class Symbol extends magmac.app.lang.common.Symbol implements TypescriptLang.TypeScriptType, JavaLang.JavaLambdaParameter, JavaLang.JavaLambdaHeader, TypescriptLang.Value {
    public Symbol(String value) {
        super(value);
    }

}
