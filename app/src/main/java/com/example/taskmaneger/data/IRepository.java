package com.example.taskmaneger.data;

import androidx.lifecycle.LiveData;

import java.util.List;

public interface IRepository <T>{
    LiveData<T> get(long id);
    LiveData<List<T>> getList();
    void insert(T t);
    void delete(T t);
    void update(T t);
}
