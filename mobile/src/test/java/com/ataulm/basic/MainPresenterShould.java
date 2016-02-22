package com.ataulm.basic;

import com.ataulm.basic.interactor.lines.RetrieveLinesInteractor;
import com.ataulm.basic.model.line.Line;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class MainPresenterShould {

    @Mock
    RetrieveLinesInteractor interactor;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void retrieve_lis_of_lines_when_view_is_ready() throws Exception {
//        MainPresenter presenter = new MainPresenter(interactor);MainPresenter presenter = new MainPresenter(interactor);

    }

}
