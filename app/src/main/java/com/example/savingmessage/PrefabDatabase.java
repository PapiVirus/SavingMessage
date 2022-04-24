package com.example.savingmessage;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {SavingMessageEntity.class}, version = 1, exportSchema = false)
public abstract class PrefabDatabase extends RoomDatabase{
    public interface PrefabListener{
        void onPrefabReturned(SavingMessageEntity prefab);
    }

    public abstract SavingMessageDAO daoSM();

    private static PrefabDatabase INSTANCE;


    public static PrefabDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (PrefabDatabase.class) {
                if (INSTANCE == null) {

                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            PrefabDatabase.class, "prefab_database")

                            .addCallback(createPrefabDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback createPrefabDatabaseCallback =
        new RoomDatabase.Callback() {
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
                for (int i = 0; i < DefaultContent.TITLE.length; i++) {
                    insert(new SavingMessageEntity(0, DefaultContent.TITLE[i], DefaultContent.CALLER[i]));
                }
            }
    };

    public static void insert(SavingMessageEntity prefab) {

        (new Thread(() -> INSTANCE.daoSM().insert(prefab))).start();

    }


    public static void getPrefab(int id, PrefabListener listener){
        Handler handler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(Message m){
                super.handleMessage(m);
                listener.onPrefabReturned((SavingMessageEntity) m.obj);
            }
        };
        (new Thread(() -> {
            Message m = handler.obtainMessage();
            m.obj = INSTANCE.daoSM().getById(id);
            handler.sendMessage(m);
        })).start();
    }

    public static void delete(int userID){
        (new Thread(() -> INSTANCE.daoSM().delete(userID))).start();
    }

    public static void update(SavingMessageEntity prefab){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                INSTANCE.daoSM().update(prefab);
            }
        });
        thread.start();
    }
}
