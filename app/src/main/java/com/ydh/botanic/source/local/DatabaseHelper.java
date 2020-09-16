package com.ydh.botanic.source.local;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.ydh.botanic.models.Plant;
import com.ydh.botanic.utils.Converters;

@Database(entities = {Plant.class}, version = 2, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class DatabaseHelper extends RoomDatabase{

    public abstract PlantDao plantDao();

    private static DatabaseHelper instance;

    public static synchronized DatabaseHelper getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    DatabaseHelper.class, "store_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(callback)
                    .build();
        }
        return  instance;
    }

    private static  RoomDatabase.Callback callback = new RoomDatabase.Callback(){

        @Override
        public void onCreate(@lombok.NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            System.out.println("DB Creada: " + db.getPath());
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            System.out.println("DB OPEN: " + db.getPath());
        }
    };

//    public abstract PlantDao getPlantDao();
}
