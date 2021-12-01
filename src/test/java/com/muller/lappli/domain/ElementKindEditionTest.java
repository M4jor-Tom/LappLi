package com.muller.lappli.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.muller.lappli.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ElementKindEditionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ElementKindEdition.class);
        ElementKindEdition elementKindEdition1 = new ElementKindEdition();
        elementKindEdition1.setId(1L);
        ElementKindEdition elementKindEdition2 = new ElementKindEdition();
        elementKindEdition2.setId(elementKindEdition1.getId());
        assertThat(elementKindEdition1).isEqualTo(elementKindEdition2);
        elementKindEdition2.setId(2L);
        assertThat(elementKindEdition1).isNotEqualTo(elementKindEdition2);
        elementKindEdition1.setId(null);
        assertThat(elementKindEdition1).isNotEqualTo(elementKindEdition2);
    }
}
