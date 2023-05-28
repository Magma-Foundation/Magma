package com.meti.node;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

class RenderStageTest {
    @Test
    void empty() {
        var actual = new EmptyRenderStage(new Content("")).render();
        assertTrue(actual.isEmpty());
    }

    static class EmptyRenderStage extends RenderStage {

        public EmptyRenderStage(Node node) {
            super(node);
        }

        @Override
        protected Renderer createRenderer(Node node) {
            return Optional::empty;
        }

        @Override
        protected RenderStage copy(Node node) {
            return new EmptyRenderStage(node);
        }
    }
}