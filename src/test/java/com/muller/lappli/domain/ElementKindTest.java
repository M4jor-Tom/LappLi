package com.muller.lappli.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.muller.lappli.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ElementKindTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ElementKind.class);
        ElementKind elementKind1 = new ElementKind();
        elementKind1.setId(1L);
        ElementKind elementKind2 = new ElementKind();
        elementKind2.setId(elementKind1.getId());
        assertThat(elementKind1).isEqualTo(elementKind2);
        elementKind2.setId(2L);
        assertThat(elementKind1).isNotEqualTo(elementKind2);
        elementKind1.setId(null);
        assertThat(elementKind1).isNotEqualTo(elementKind2);
    }
}
