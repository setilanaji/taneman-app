package com.ydh.botanic.source.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.ydh.botanic.models.Plant;

import java.util.List;

import static androidx.room.OnConflictStrategy.IGNORE;
import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface PlantDao {

    @Query("SELECT * from plants")
    List<Plant> getPlants();

    @Insert(onConflict = REPLACE)
    void insert(Plant plant);

    @Insert(onConflict = IGNORE)
    long[] insertPlants(Plant... plants);

//    void updatePlant();

    @Query("SELECT * FROM plants WHERE name LIKE '%' || :query || '%' OR local LIKE '%' || :query || '%' " +
            "ORDER BY name DESC LIMIT (:pageNumber * 30)")
    LiveData<List<Plant>> seacrhPlants(String query, int pageNumber);

    @Query("SELECT * FROM plants WHERE id = :plantId")
    LiveData<Plant> getPlant(String plantId);
}
