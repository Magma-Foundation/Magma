package com.meti;

import com.meti.api.io.NIOPath;
import com.meti.module.DirectoryModule;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            var sourceFolder = NIOPath.Root.resolveChild("src").ensureAsDirectory();
            var module = new DirectoryModule(sourceFolder);
            new BuildingApplication(module).run();
        } catch (ApplicationException | IOException e) {
            e.printStackTrace();
        }
    }
}
