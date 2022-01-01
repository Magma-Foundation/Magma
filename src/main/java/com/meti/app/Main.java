package com.meti.app;

import com.meti.api.io.NIOFile;

public class Main {
    public static void main(String[] args) {
        try {
            var sourceDirectory = new SingleSourceDirectory(NIOFile.Root.resolveChild("index.mg"));
            var targetDirectory = new PathTargetDirectory(NIOFile.Root);
            var application = new Application(sourceDirectory, targetDirectory);
            var details = application.run();
            System.out.println("Compiled " + details.count() + " files in " + details.elapsed());
        } catch (ApplicationException e) {
            e.printStackTrace();
        }
    }
}
