package com.meti;

import com.meti.io.PathWrapper;

public class Main {
    public static void main(String[] args) {
        try {
            new Application(PathWrapper.Root.resolve("index.mgf"))
                    .run();
        } catch (ApplicationException e) {
            e.printStackTrace();
        }
    }
}
