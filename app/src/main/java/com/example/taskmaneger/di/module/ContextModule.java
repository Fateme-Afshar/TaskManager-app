package com.example.taskmaneger.di.module;

import android.content.Context;

import com.example.taskmaneger.di.ApplicationContext;
import com.example.taskmaneger.di.ApplicationScope;

import dagger.Module;
import dagger.Provides;

@Module
public class ContextModule {
    private final Context mContext;

    public ContextModule(Context context) {
        mContext = context;
    }

    @Provides
    @ApplicationScope
    @ApplicationContext
    public Context provideContext(){
        return mContext;
    }
}
