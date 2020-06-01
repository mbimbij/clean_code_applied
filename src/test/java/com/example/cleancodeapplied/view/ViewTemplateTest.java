package com.example.cleancodeapplied.view;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ViewTemplateTest {

    @Test
    void noReplacement() {
        ViewTemplate template = ViewTemplate.fromHtmlContent("some static content");
        assertThat(template.getHtml()).isEqualTo("some static content");
    }

    @Test
    void simpleReplacement() {
        ViewTemplate template = ViewTemplate.fromHtmlContent("replace ${this}");
        template.replace("this", "replacement");
        assertThat(template.getHtml()).isEqualTo("replace replacement");
    }
}