import native {
    AfterEach, Test,
    { assertFalse, assertTrue } from Assertions,
} from org.junit.jupiter.api;

import native {
    { Files, Path, Paths } from nio.file,
    IOException from io
} from java;

const Target = Paths.get(".", "index.java");
const Source = Paths.get(".", "index.ms");

def run() => {
    if (Files.exists(Source)) {
        Files.createFile(Target);
    }
}

@Test
def with_source() => {
    Files.createFile(Source);
    run();
    assertTrue(Files.exists(Target));
}

@Test
def without_source() => {
    run();
    assertFalse(Files.exists(Target));
}

@AfterEach
def tearDown() => {
    Files.deleteIfExists(Target);
    Files.deleteIfExists(Source);
}