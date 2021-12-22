package com.muller.lappli.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.muller.lappli.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IntersticeAssemblyTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IntersticeAssembly.class);
        IntersticeAssembly intersticeAssembly1 = new IntersticeAssembly();
        intersticeAssembly1.setId(1L);
        IntersticeAssembly intersticeAssembly2 = new IntersticeAssembly();
        intersticeAssembly2.setId(intersticeAssembly1.getId());
        assertThat(intersticeAssembly1).isEqualTo(intersticeAssembly2);
        intersticeAssembly2.setId(2L);
        assertThat(intersticeAssembly1).isNotEqualTo(intersticeAssembly2);
        intersticeAssembly1.setId(null);
        assertThat(intersticeAssembly1).isNotEqualTo(intersticeAssembly2);
    }
}
