package com.trochun.lab3;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface DataStore {

    Completable add(DataModel record);
    Single<List<DataModel>> all();
}
