package magmac.app.lang.web;

import magmac.app.lang.common.Symbol;
import magmac.app.lang.java.JavaLang;

public final class TypescriptSymbol extends Symbol implements TypescriptLang.TypeScriptType, JavaLang.JavaLambdaParameter, JavaLang.JavaLambdaHeader, TypescriptLang.TypescriptValue {
    public TypescriptSymbol(String value) {
        super(value);
    }

}
