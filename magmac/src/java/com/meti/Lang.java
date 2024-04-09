package com.meti;

public class Lang {
    static String renderBlock() {
        return renderBlock("");
    }

    static String renderBlock(String fieldString) {
        return "{" + fieldString + "public static void main(String[] args){}}";
    }
}
