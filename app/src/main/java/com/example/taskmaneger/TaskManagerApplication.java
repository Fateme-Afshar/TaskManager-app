package com.example.taskmaneger;

import android.app.Application;

import com.example.taskmaneger.di.ApplicationGraph;
import com.example.taskmaneger.di.DaggerApplicationGraph;
import com.example.taskmaneger.di.module.ContextModule;

public class TaskManagerApplication extends Application {
    private static ApplicationGraph sApplicationGraph;

    @Override
    public void onCreate() {
        super.onCreate();

        sApplicationGraph= DaggerApplicationGraph.builder().contextModule(new ContextModule(this)).build();
    }

    public static ApplicationGraph getApplicationGraph() {
        return sApplicationGraph;
    }
}
