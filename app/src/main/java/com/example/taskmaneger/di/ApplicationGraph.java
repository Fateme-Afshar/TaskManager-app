package com.example.taskmaneger.di;

import android.content.Context;

import com.example.taskmaneger.di.module.ContextModule;

import dagger.Component;

@ApplicationScope
@Component(modules = ContextModule.class)
public interface ApplicationGraph {

    @ApplicationContext
    Context getContext();
}
