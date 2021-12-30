package com.meti.app;

import com.meti.io.NIOPath;

public class Main {
    public static void main(String[] args) {
        try {
            new Application(NIOPath.Root.resolveChild("index.mg")).run();
        } catch (ApplicationException e) {
            e.printStackTrace();
        }
    }
}
