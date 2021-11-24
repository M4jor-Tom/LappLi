package com.muller.lappli.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.muller.lappli.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ElementTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Element.class);
        Element element1 = new Element();
        element1.setId(1L);
        Element element2 = new Element();
        element2.setId(element1.getId());
        assertThat(element1).isEqualTo(element2);
        element2.setId(2L);
        assertThat(element1).isNotEqualTo(element2);
        element1.setId(null);
        assertThat(element1).isNotEqualTo(element2);
    }
}
