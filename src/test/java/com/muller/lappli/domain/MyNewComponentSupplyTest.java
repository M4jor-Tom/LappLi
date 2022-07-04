package com.muller.lappli.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.muller.lappli.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MyNewComponentSupplyTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MyNewComponentSupply.class);
        MyNewComponentSupply myNewComponentSupply1 = new MyNewComponentSupply();
        myNewComponentSupply1.setId(1L);
        MyNewComponentSupply myNewComponentSupply2 = new MyNewComponentSupply();
        myNewComponentSupply2.setId(myNewComponentSupply1.getId());
        assertThat(myNewComponentSupply1).isEqualTo(myNewComponentSupply2);
        myNewComponentSupply2.setId(2L);
        assertThat(myNewComponentSupply1).isNotEqualTo(myNewComponentSupply2);
        myNewComponentSupply1.setId(null);
        assertThat(myNewComponentSupply1).isNotEqualTo(myNewComponentSupply2);
    }
}
