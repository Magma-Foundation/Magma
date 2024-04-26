package com.meti.compile;

import com.meti.collect.JavaString;
import com.meti.rule.*;

public class Lang {
    public static final String CLASS_KEYWORD_WITH_SPACE = "class ";
    public static final String EXPORT_KEYWORD = "export";
    public static final String PUBLIC_KEYWORD = "public";
    public static final String PUBLIC_KEYWORD_WITH_SPACE = PUBLIC_KEYWORD + " ";
    public static final String IMPORT_KEYWORD_WITH_SPACE = "import ";
    public static final char STATEMENT_END = ';';
    public static final String STATIC_KEYWORD = "static";
    public static final String STATIC_KEYWORD_WITH_SPACE = STATIC_KEYWORD + " ";
    public static final JavaString STATIC_STRING = new JavaString(STATIC_KEYWORD);
    public static final char BLOCK_START = '{';
    public static final char BLOCK_END = '}';
    public static final Both BLOCK = new Both(BLOCK_START, new StringRule("content"), BLOCK_END);
    public static final String INT_KEYWORD = "int";
    public static final String I32_KEYWORD = "I32";
    public static final String LONG_KEYWORD = "long";
    public static final String I64_KEYWORD = "I64";
    public static final char VALUE_SEPARATOR = '=';
    public static final String FINAL_KEYWORD = "final";
    public static final String LET_KEYWORD_WITH_SPACE = "let ";
    public static final String CONST_KEYWORD_WITH_SPACE = "const ";
    public static final String TEMP_SEPARATOR = "() =>";
    public static final String DEF_KEYWORD_WITH_SPACE = "def ";
}
