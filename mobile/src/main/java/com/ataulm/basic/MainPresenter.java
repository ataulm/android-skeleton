package com.ataulm.basic;

import com.ataulm.basic.interactor.lines.RetrieveLinesInteractor;
import com.ataulm.basic.interactor.lines.RetrieveLinesInteractor.RetrieveLinesInteractorOutput;
import com.ataulm.basic.model.line.Line;

import java.util.List;

public class MainPresenter implements RetrieveLinesInteractorOutput{

    RetrieveLinesInteractor retrieveLinesInteractor;
    private final MainView view;
    private final InteractorInvoker invoker;

    public MainPresenter(RetrieveLinesInteractor retrieveLinesInteractor, MainView view, InteractorInvoker invoker) {
        this.retrieveLinesInteractor = retrieveLinesInteractor;
        this.view = view;
        this.invoker = invoker;
    }

    public void onViewReady() {
        retrieveLinesInteractor.setOutput(this);
        invoker.execute(retrieveLinesInteractor);
    }

    public void onViewDestroy() {

    }

    @Override
    public void onLinesLoaded(List<Line> lines) {
        view.showLines(lines);
    }

}
