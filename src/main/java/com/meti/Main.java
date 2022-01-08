package com.meti;

import com.meti.io.NIOPath;
import com.meti.module.DirectoryModule;

public class Main {
    public static void main(String[] args) {
        try {
            new BuildingApplication(new DirectoryModule(NIOPath.Root.resolveChild("src"))).run();
        } catch (ApplicationException e) {
            e.printStackTrace();
        }
    }
}
