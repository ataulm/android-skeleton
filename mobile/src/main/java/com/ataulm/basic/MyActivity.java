package com.ataulm.basic;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.ataulm.basic.interactor.lines.RetrieveLinesInteractor;
import com.ataulm.basic.model.line.Line;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MyActivity extends AppCompatActivity implements MainView {

    private final LondonUndergroundRemoteApi api = new LondonUndergroundRemoteApi();

    @Bind(R.id.linesSelector)
    Spinner spinner;
    private MainPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        ButterKnife.bind(this);
        presenter = new MainPresenter(new RetrieveLinesInteractor(new LondonUndergroundRemoteApi()), new ThreadDecoratedMainView(this), new InteractorInvokerImp());
        presenter.onViewReady();
    }

    @Override
    protected void onDestroy() {
        presenter.onViewDestroy();
        super.onDestroy();
    }

    @Override
    public void showLines(List<Line> lines) {
        System.out.println("awdawd");
    }
}
