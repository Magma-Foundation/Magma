package com.meti;

import com.meti.io.NIOPath;
import com.meti.module.DirectoryModule;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            new Application(new DirectoryModule(NIOPath.Root.resolveChild("src"))).run();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
