package com.craftinginterpreters.krv.lox;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LoxTest {

    @Test
    void test1() throws IOException {
        Lox.runFile("src/test/resources/com/craftinginterpreters/krv/lox/global.lox");
    }

    @Test
    void testMain() throws IOException {
        Lox.main(new String[]{});
    }
}
