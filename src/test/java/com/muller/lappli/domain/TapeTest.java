package com.muller.lappli.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.muller.lappli.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TapeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tape.class);
        Tape tape1 = new Tape();
        tape1.setId(1L);
        Tape tape2 = new Tape();
        tape2.setId(tape1.getId());
        assertThat(tape1).isEqualTo(tape2);
        tape2.setId(2L);
        assertThat(tape1).isNotEqualTo(tape2);
        tape1.setId(null);
        assertThat(tape1).isNotEqualTo(tape2);
    }
}
