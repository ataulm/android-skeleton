package com.ataulm.basic.interactor.lines;

import com.ataulm.basic.model.line.Line;
import com.ataulm.basic.model.line.RemoteLineGateway;

import java.util.List;

public class RetrieveLinesInteractor implements Runnable{

    RemoteLineGateway remoteLineGateway;

    private RetrieveLinesInteractorOutput output;

    public RetrieveLinesInteractor(RemoteLineGateway remoteLineGateway) {
        this.remoteLineGateway = remoteLineGateway;
    }

    public void setOutput(RetrieveLinesInteractorOutput output) {
        this.output = output;
    }

    @Override
    public void run() {
        output.onLinesLoaded(remoteLineGateway.getLines());
    }

    public interface RetrieveLinesInteractorOutput {
        void onLinesLoaded(List<Line> lines);
    }

}
