package com.trochun.lab3;

import android.content.Context;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public class FileDataStore implements DataStore {

    private static String DEFAULT_FILE_NAME = "datastore";

    private File mFile;

    public FileDataStore(Context context) {
        this(DEFAULT_FILE_NAME, context);
    }

    public FileDataStore(String fileName, Context context) {
        mFile = new File(context.getFilesDir(), fileName);
    }

    @Override
    public Completable add(DataModel record) {
        return Completable.create(source -> {
            try (DataOutputStream out = new DataOutputStream(new FileOutputStream(mFile, true))) {
                writeOne(record, out);
                source.onComplete();
            } catch (IOException e) {
                source.onError(new DataStoreException(e));
            }
        });
    }

    @Override
    public Single<List<DataModel>> all() {
        return Single.create(source -> {
            try (DataInputStream in = new DataInputStream(new FileInputStream(mFile))) {
                List<DataModel> records = new LinkedList<>();
                while (in.available() > 0) {
                    records.add(readOne(in));
                }
                source.onSuccess(records);
            } catch (IOException e) {
                source.onError(new DataStoreException(e));
            }
        });
    }

    private void writeOne(DataModel record, DataOutputStream out) throws IOException {
        out.writeUTF(record.getFrom());
        out.writeUTF(record.getTo());
        out.writeBoolean(record.isMorning());
    }

    private DataModel readOne(DataInputStream in) throws IOException {
        return new DataModel(
                in.readUTF(),
                in.readUTF(),
                in.readBoolean()
        );
    }
}
