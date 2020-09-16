package com.ydh.botanic.utils;

import com.ydh.botanic.source.remote.RetrofitInstance;
import com.ydh.botanic.source.remote.services.AccountService;
import com.ydh.botanic.source.remote.services.PlantService;
import com.ydh.botanic.source.remote.services.TaxonomyService;

import static com.ydh.botanic.utils.Constants.BASE_URL;
import static com.ydh.botanic.utils.Constants.BASE_URL_GBIF;

public class ApiUtil {

    public static PlantService getPlantService() {
        return RetrofitInstance.getClient(BASE_URL + "plants/", true).create(PlantService.class);
    }

    public static AccountService getAccountService() {
        return RetrofitInstance.getClient(BASE_URL + "account/", true).create(AccountService.class);
    }

    public static TaxonomyService getTaxonomyService() {
        return RetrofitInstance.getClient(BASE_URL_GBIF, false).create(TaxonomyService.class);
    }
}
