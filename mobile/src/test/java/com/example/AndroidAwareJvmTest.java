package com.example;

import android.content.Context;
import android.graphics.Color;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AndroidAwareJvmTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    AndroidAwareClassUsingPojo cut;

    @Mock
    Context context;

    @Mock
    Pojo pojo;

    @Before
    public void setUp() {
        cut = new AndroidAwareClassUsingPojo(context, pojo);
    }

    @Test
    public void updatesColor() {
        when(context.getColor(anyInt())).thenReturn(Color.RED);

        cut.updatePojoColor();

        verify(pojo).setColor(Color.RED);
    }

}