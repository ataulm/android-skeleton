package com.ataulm.basic;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class AppNameProviderTest {

    private static final String APP_NAME = "Basic";

    AppNameProvider appNameProvider;

    @Mock
    Context mockContext;

    @Before
    public void setUp() {
        initMocks(this);
        when(mockContext.getString(Matchers.anyInt())).thenReturn(APP_NAME);
        appNameProvider = new AppNameProvider(mockContext);
    }

    @Test
    public void fetchAppName() {
        String appName = appNameProvider.getAppName();

        assertThat(appName).isEqualTo(APP_NAME);
    }

}
