package com.meti;

import com.meti.rule.*;

import static com.meti.compile.Lang.*;

public class MagmaLang {
    public static final FirstRule MAGMA_IMPORT = new FirstRule("from", new Both(IMPORT_KEYWORD_WITH_SPACE + BLOCK_START + " ", new StringRule("child"), " " + BLOCK_END + " "), new RequireRightChar(new StringRule("parent"), STATEMENT_END));
    public static final RequireLeft MAGMA_OBJECT = new RequireLeft("object ", new FirstRule(" ",
            new StringRule("name"), BLOCK));
    public static final FirstRule MAGMA_DEFINITION = new FirstRule(VALUE_SEPARATOR + "", new FirstRule(" : ", new StringListRule("segments", " "), new RequireRightSlice(" ", new StringRule("type"))), new Both(" ", new StringRule("value"), ";"));
    public static final FirstRule MAGMA_FUNCTION = new FirstRule(TEMP_SEPARATOR, new FirstRule(CLASS_KEYWORD_WITH_SPACE + DEF_KEYWORD_WITH_SPACE, new OrRule(new RequireRightSlice(" ", new StringListRule("modifiers", " ")), new EmptyRule()), new StringRule("name")), new RequireLeft(' ', BLOCK));
}