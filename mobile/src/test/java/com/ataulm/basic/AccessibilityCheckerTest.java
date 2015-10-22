package com.ataulm.basic;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.view.accessibility.AccessibilityManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(JUnit4.class)
public class AccessibilityCheckerTest {

    @Mock
    AccessibilityManager mockAccessibilityManager;

    private AccessibilityChecker accessibilityChecker;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        accessibilityChecker = new AccessibilityChecker(mockAccessibilityManager);
    }

    @Test
    public void spokenFeedbackFlagIsUsed() {
        ArgumentCaptor<Integer> argumentCaptor = ArgumentCaptor.forClass(Integer.class);
        accessibilityChecker.isSpokenFeedbackEnabled();
        verify(mockAccessibilityManager).getEnabledAccessibilityServiceList(argumentCaptor.capture());
        Integer flags = argumentCaptor.getValue();

        assertThat(flags & AccessibilityServiceInfo.FEEDBACK_SPOKEN).isEqualTo(AccessibilityServiceInfo.FEEDBACK_SPOKEN);
    }

}