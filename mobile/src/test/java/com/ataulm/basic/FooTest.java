package com.ataulm.basic;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class FooTest {

    @Mock
    Bar mockBar;

    Foo foo;

    @Before
    public void setUp() {
        initMocks(this);
        when(mockBar.add(1, 1)).thenReturn(2);
        foo = new Foo(mockBar);
    }

    @Test
    public void bruh() {
        int actual = foo.twice(1);

        assertThat(actual).isEqualTo(2);
    }

}
