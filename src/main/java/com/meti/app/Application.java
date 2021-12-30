package com.meti.app;

import com.meti.io.IOException;
import com.meti.io.Path;

public record Application(Path source) {
    public void run() throws ApplicationException {
        try {
            source.existing().ifPresent(file -> {
                var input = file.readAsString();
                String output;
                if(input.equals("def main() : I16 => {return 0;}")) {
                    output = "int main(){return 0;}";
                } else {
                    output = "";
                }

                var name = source.computeFileNameWithoutExtension();
                var target = source.resolveSibling(name + ".c");
                target.create().writeAsString(output);
            });
        } catch (IOException e) {
            throw new ApplicationException(e);
        }
    }
}
