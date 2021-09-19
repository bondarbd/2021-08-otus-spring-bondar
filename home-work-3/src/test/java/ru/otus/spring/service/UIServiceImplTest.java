package ru.otus.spring.service;

import lombok.SneakyThrows;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;
@DisplayName("Check UI service")
class UIServiceImplTest {

    private static final String TEXT_TO_PRINT = "Hello test";

    private UIService uiService;
    private PrintStream backup;
    private ByteArrayOutputStream byteArrayOutputStream;

    @BeforeEach
    void setUp(){
        backup = System.out;
        byteArrayOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(byteArrayOutputStream));
        uiService = new UIServiceImpl();
    }

    @AfterEach
    void setOut(){
        System.setOut(backup);
    }

    @Test
    @DisplayName("Test console out is ok")
    @SneakyThrows
    void testOut(){
        uiService.write(TEXT_TO_PRINT);
        assertThat(byteArrayOutputStream).hasToString(TEXT_TO_PRINT + "\r\n");
    }
}
