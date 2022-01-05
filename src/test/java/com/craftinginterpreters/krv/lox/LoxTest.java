package com.craftinginterpreters.krv.lox;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

class LoxTest {

    @Test
    void test1() throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get("src/test/resources/com/craftinginterpreters/krv/lox/global.lox"));
        Lox.run(new String(bytes, Charset.defaultCharset()));
    }

    @Test
    void testMain() throws IOException {
        Lox.main(new String[]{});
    }
}
