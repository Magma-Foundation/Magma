package com.meti.app;

import com.meti.api.io.NIOFile;

public class Main {
    public static void main(String[] args) {
        try {
            new Application(new SingleSource(NIOFile.Root.resolveChild("index.mg"))).run();
        } catch (ApplicationException e) {
            e.printStackTrace();
        }
    }
}
