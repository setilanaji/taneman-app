package com.ydh.botanic.source.repositories;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ydh.botanic.models.Plant;
import com.ydh.botanic.source.local.DatabaseHelper;
import com.ydh.botanic.source.local.PlantDao;
import com.ydh.botanic.source.remote.responses.PlantResponse;
import com.ydh.botanic.source.remote.services.PlantService;
import com.ydh.botanic.utils.ApiUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//import com.ydh.botanic.source.remote.RetrofitInstance;
//import java.sql.Date;

public class PlantRepository {

    private List<Plant> plants = new ArrayList<>();

    private MutableLiveData<List<Plant>> mutableLiveData = new MutableLiveData<>();

    private ProgressDialog progressDialog;

    public PlantDao plantDao;

    public PlantRepository(Application application){
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(application);
        plantDao = databaseHelper.plantDao();
    }

    public LiveData<List<Plant>> getLiveData(Context context){
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        progressDialog.setMessage(context.getString(R.string.generic_message_progress));
        progressDialog.show();

        Executor executor = Executors.newSingleThreadExecutor();

        executor.execute(() -> {
            List<Plant> plantList = plantDao.getPlants();
            if (plantList.size() > 0) {

                mutableLiveData.postValue(plantList);
                progressDialog.dismiss();
            }else {


                PlantService plantService = ApiUtil.getPlantService();
                Call<PlantResponse> call = plantService.getPlants("en");

                call.enqueue(new Callback<PlantResponse>() {
                    @Override
                    public void onResponse(Call<PlantResponse> call, Response<PlantResponse> response) {

                        if(response.isSuccessful()){
                            List<Plant>  resultPlants = response.body().getPlants();
                            if(resultPlants != null){

                                for (Plant item: resultPlants) {
                                    new InsertPlantAsyncTask(plantDao).execute(item);

                                }
                                plants = response.body().getPlants();
                                mutableLiveData.postValue(plants);


                            }
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<PlantResponse> call, Throwable t) {
                        progressDialog.dismiss();
                    }
                });
            }
        });

        mutableLiveData.postValue(plants);


        return mutableLiveData;
    }

    private  static  class InsertPlantAsyncTask extends AsyncTask<Plant, Void, Void> {

        private PlantDao plantDao;

        public InsertPlantAsyncTask(PlantDao plantDao){
            this.plantDao = plantDao;
        }

        @Override
        protected Void doInBackground(Plant... categories) {

            plantDao.insert(categories[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            System.out.println("Guardado User");
        }
    }


}
