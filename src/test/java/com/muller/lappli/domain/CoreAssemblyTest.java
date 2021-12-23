package com.muller.lappli.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.muller.lappli.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CoreAssemblyTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CoreAssembly.class);
        CoreAssembly coreAssembly1 = new CoreAssembly();
        coreAssembly1.setId(1L);
        CoreAssembly coreAssembly2 = new CoreAssembly();
        coreAssembly2.setId(coreAssembly1.getId());
        assertThat(coreAssembly1).isEqualTo(coreAssembly2);
        coreAssembly2.setId(2L);
        assertThat(coreAssembly1).isNotEqualTo(coreAssembly2);
        coreAssembly1.setId(null);
        assertThat(coreAssembly1).isNotEqualTo(coreAssembly2);
    }
}
