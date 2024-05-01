package com.meti;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.meti.FeatureTest.assertRun;
import static com.meti.Lang.*;

public class ApplicationTest {
    @Test
    void definitionName() {
        assertRun(renderJavaClass(FeatureTest.TEST_UPPER_SYMBOL, "", JAVA_DEFINITION),
                renderMagmaFunction(FeatureTest.TEST_UPPER_SYMBOL, "", MAGMA_DEFINITION));
    }


    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void className(String name) {
        assertRun(renderJavaClass(name), renderMagmaFunction(name));
    }

    @ParameterizedTest
    @ValueSource(strings = {"first", "second"})
    void packageStatement(String name) {
        assertRun(PACKAGE_KEYWORD_WITH_SPACE + name + STATEMENT_END + renderJavaClass(FeatureTest.TEST_UPPER_SYMBOL), renderMagmaFunction(FeatureTest.TEST_UPPER_SYMBOL));
    }

    @Test
    void classPublic() {
        assertRun(renderJavaClass(FeatureTest.TEST_UPPER_SYMBOL, PUBLIC_KEYWORD_WITH_SPACE), renderMagmaFunction(FeatureTest.TEST_UPPER_SYMBOL, EXPORT_KEYWORD_WITH_SPACE));
    }
}