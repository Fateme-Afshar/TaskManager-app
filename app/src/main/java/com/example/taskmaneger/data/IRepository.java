package com.example.taskmaneger.data;

import java.util.List;

public interface IRepository <T>{
    T get(long id);
    List<T> getList();
    void insert(T t);
    void delete(T t);
    void update(T t);
}
