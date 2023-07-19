package com.meti.app;

import com.meti.core.Result;
import com.meti.core.Results;
import com.meti.java.JavaList;
import com.meti.java.JavaString;
import com.meti.java.List;
import com.meti.java.String_;
import com.meti.nio.Location;

import java.io.IOException;
import java.nio.file.Files;

/*
import {
    { Location } from nio,
    { List, JavaList, JavaString } from java,
    { Result, Results} from core
} from com.meti;

import {
    { IOException from io },
    { Files } from nio.file
} from java;
 */

public record NIOSource(Location parent, Location location) {
    List<String_> computePackage() {
        var list = parent.relativize(location)
                .iter()
                .map(Location::findFileNameAsString)
                .collect(JavaList.asList());

        return list.lastIndexOptionally()
                .map(list::sliceTo)
                .unwrapOrElse(list);
    }

    String_ computeName() {
        var fileName = location.last()
                .map(Location::findFileNameAsString)
                .unwrapOrElse(JavaString.empty());

        return fileName.firstIndexOfChar('.')
                .map(fileName::sliceTo)
                .unwrapOrElse(fileName);
    }

    public Result<String_, IOException> read() {
        return Results.$Result(() -> JavaString.from(Files.readString(this.location.unwrap())));
    }
}