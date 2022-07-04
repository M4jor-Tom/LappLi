package com.muller.lappli.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.muller.lappli.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MyNewComponentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MyNewComponent.class);
        MyNewComponent myNewComponent1 = new MyNewComponent();
        myNewComponent1.setId(1L);
        MyNewComponent myNewComponent2 = new MyNewComponent();
        myNewComponent2.setId(myNewComponent1.getId());
        assertThat(myNewComponent1).isEqualTo(myNewComponent2);
        myNewComponent2.setId(2L);
        assertThat(myNewComponent1).isNotEqualTo(myNewComponent2);
        myNewComponent1.setId(null);
        assertThat(myNewComponent1).isNotEqualTo(myNewComponent2);
    }
}
