package com.meti.compile;

import com.meti.collect.result.Result;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.meti.collect.result.Err.Err;
import static com.meti.collect.result.Ok.Ok;

public record PathSource(Path root, Path child) implements Source {
    @Override
    public Result<String, IOException> read() {
        try {
            return Ok(Files.readString(child));
        } catch (IOException e) {
            return Err(e);
        }
    }

    @Override
    public String findName() {
        var name = child.getFileName().toString();
        var index = name.indexOf('.');
        return name.substring(0, index == -1 ? name.length() : index);
    }

    @Override
    public List<String> findPackage() {
        var parent = root.relativize(child).getParent();
        if (parent == null) {
            return Collections.emptyList();
        }

        var list = new ArrayList<String>();
        for (int i = 0; i < parent.getNameCount(); i++) {
            list.add(parent.getName(i).toString());
        }

        return list;
    }
}
