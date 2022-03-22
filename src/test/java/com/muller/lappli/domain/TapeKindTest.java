package com.muller.lappli.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.muller.lappli.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TapeKindTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TapeKind.class);
        TapeKind tapeKind1 = new TapeKind();
        tapeKind1.setId(1L);
        TapeKind tapeKind2 = new TapeKind();
        tapeKind2.setId(tapeKind1.getId());
        assertThat(tapeKind1).isEqualTo(tapeKind2);
        tapeKind2.setId(2L);
        assertThat(tapeKind1).isNotEqualTo(tapeKind2);
        tapeKind1.setId(null);
        assertThat(tapeKind1).isNotEqualTo(tapeKind2);
    }
}
