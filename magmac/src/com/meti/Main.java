package com.meti;

import com.meti.collect.option.Option;
import com.meti.collect.result.Result;
import com.meti.compile.Application;
import com.meti.compile.CompileException;
import com.meti.compile.Source;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        var source = Paths.get(".", "Personal", "Magma", "magmac", "test", "com", "meti", "ApplicationTest.java");
        var option = new Application(new Source(source)).run();
        option.ifPresent(result -> {
            result.consume(value -> {
                System.out.println(value);
            }, err -> {
                err.printStackTrace();
            });
        });
    }
}
