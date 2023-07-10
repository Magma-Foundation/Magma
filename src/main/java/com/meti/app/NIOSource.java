package com.meti.app;

import com.meti.core.Result;
import com.meti.core.Results;
import com.meti.java.JavaList;
import com.meti.java.JavaString;
import com.meti.nio.Location;

import java.io.IOException;
import java.nio.file.Files;

public record NIOSource(Location parent, Location location) {
    JavaList<JavaString> computePackage() {
        var list = parent.relativize(location)
                .iter()
                .map(Location::asString)
                .collect(JavaList.asList());

        return list.lastIndex()
                .map(list::sliceTo)
                .unwrapOrElse(list);
    }

    JavaString computeName() {
        var fileName = location.last()
                .map(Location::asString)
                .unwrapOrElse(JavaString.empty());

        return fileName.firstIndexOfChar('.')
                .map(fileName::sliceToEnd)
                .unwrapOrElse(fileName);
    }

    public Result<JavaString, IOException> read() {
        return Results.asResult(() -> new JavaString(Files.readString(this.location.unwrap())));
    }
}