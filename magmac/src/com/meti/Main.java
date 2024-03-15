package com.meti;

import com.meti.compile.Application;
import com.meti.compile.DirectorySource;
import com.meti.compile.SingleSource;

import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        var source = Paths.get(".", "Personal", "Magma", "magmac", "test");
        var option = new Application(new DirectorySource(source)).run();
        option.consume(values -> {
            System.out.println(values);
        }, err -> {
            err.printStackTrace();
        });
    }
}
