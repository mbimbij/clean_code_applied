package com.example.cleancodeapplied;

import com.example.cleancodeapplied.utilities.Main;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class MainTest {
    @Test
    void creation() throws IOException {
        Main main = new Main();

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(main.getServer()).isNotNull();
            softAssertions.assertThat(main.getCodecastGateway()).isNotNull();
//            softAssertions.assertThat(main.getLicenseGateway()).isNotNull();
//            softAssertions.assertThat(main.getUserGateway()).isNotNull();
        });
    }
}
