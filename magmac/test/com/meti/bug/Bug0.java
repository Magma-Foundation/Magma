package com.meti.bug;

import com.meti.rule.ListDelimitingRule;
import com.meti.rule.NodeElementRule;
import com.meti.rule.RequireRule;
import com.meti.rule.WhitespaceRule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.meti.stage.JavaLexingStage.CLASS_MEMBER;

public class Bug0 {

    @Test
    void empty(){
        var present = new ListDelimitingRule(new RequireRule(";"), new NodeElementRule("body", CLASS_MEMBER))
                .lexImpl("")
                .isPresent();

        Assertions.assertTrue(present);
    }


    @Test
    void delimiter(){
        var present = new ListDelimitingRule(new RequireRule(";"), WhitespaceRule.PADDING)
                .lexImpl(";")
                .isPresent();

        Assertions.assertTrue(present);
    }


    @Test
    void line(){
        var present = new ListDelimitingRule(new RequireRule(";"), new NodeElementRule("body", CLASS_MEMBER))
                .lexImpl("int first = 1;")
                .isPresent();

        Assertions.assertTrue(present);
    }

    @Test
    void test(){
        var present = new ListDelimitingRule(new RequireRule(";"), new NodeElementRule("body", CLASS_MEMBER))
                .lexImpl("int first = 1; int second = 2;")
                .isPresent();

        Assertions.assertTrue(present);
    }

    @Test
    void noEnd(){
        var present = new ListDelimitingRule(new RequireRule(";"), new NodeElementRule("body", CLASS_MEMBER))
                .lexImpl("int first = 1; int second = 2")
                .isPresent();

        Assertions.assertTrue(present);
    }
}
