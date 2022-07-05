package com.muller.lappli.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.muller.lappli.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MyNewOperationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MyNewOperation.class);
        MyNewOperation myNewOperation1 = new MyNewOperation();
        myNewOperation1.setId(1L);
        MyNewOperation myNewOperation2 = new MyNewOperation();
        myNewOperation2.setId(myNewOperation1.getId());
        assertThat(myNewOperation1).isEqualTo(myNewOperation2);
        myNewOperation2.setId(2L);
        assertThat(myNewOperation1).isNotEqualTo(myNewOperation2);
        myNewOperation1.setId(null);
        assertThat(myNewOperation1).isNotEqualTo(myNewOperation2);
    }
}
