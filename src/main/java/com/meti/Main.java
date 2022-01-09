package com.meti;

import com.meti.io.NIOPath;
import com.meti.module.DirectoryModule;

public class Main {
    public static void main(String[] args) {
        try {
            var sourceFolder = NIOPath.Root.resolveChild("src");
            var module = new DirectoryModule(sourceFolder);
            new BuildingApplication(module).run();
        } catch (ApplicationException e) {
            e.printStackTrace();
        }
    }
}
