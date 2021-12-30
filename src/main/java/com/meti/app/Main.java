package com.meti.app;

import com.meti.api.io.NIOFile;

public class Main {
    public static void main(String[] args) {
        try {
            new Application(new SingleSourceDirectory(NIOFile.Root.resolveChild("index.mg")), new PathTargetDirectory(NIOFile.Root)).run();
        } catch (ApplicationException e) {
            e.printStackTrace();
        }
    }
}
