package com.muller.lappli.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.muller.lappli.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CentralAssemblyTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CentralAssembly.class);
        CentralAssembly centralAssembly1 = new CentralAssembly();
        centralAssembly1.setId(1L);
        CentralAssembly centralAssembly2 = new CentralAssembly();
        centralAssembly2.setId(centralAssembly1.getId());
        assertThat(centralAssembly1).isEqualTo(centralAssembly2);
        centralAssembly2.setId(2L);
        assertThat(centralAssembly1).isNotEqualTo(centralAssembly2);
        centralAssembly1.setId(null);
        assertThat(centralAssembly1).isNotEqualTo(centralAssembly2);
    }
}
