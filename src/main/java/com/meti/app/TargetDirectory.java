package com.meti.app;

import com.meti.api.io.File;
import com.meti.api.io.IOException;

public interface TargetDirectory {
    File write(String output, Reference reference_) throws IOException;
}
