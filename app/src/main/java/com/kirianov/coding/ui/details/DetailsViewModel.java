package com.kirianov.coding.ui.details;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.kirianov.coding.model.ItemDetails;
import com.kirianov.coding.network.NetworkClient;
import com.kirianov.coding.network.responses.DetailsItemResponse;
import com.kirianov.coding.utils.Logger;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsViewModel extends ViewModel {

    private final MutableLiveData<ItemDetails> detailsLiveData;
    private final MutableLiveData<String> errorLiveDate;

    public DetailsViewModel() {
        detailsLiveData = new MutableLiveData<>();
        errorLiveDate = new MutableLiveData<>();
    }

    public MutableLiveData<ItemDetails> getDetailsLiveData() {
        return detailsLiveData;
    }

    public MutableLiveData<String> getErrorLiveDate() {
        return errorLiveDate;
    }

    public void showDetails(String id) {
        Logger.log();
        fetchDataFromBackend(id);
        // TODO once I have time better to add one more architecture layer - Repository (cache from memory, cache from database, fetch from backend)
    }

    private void fetchDataFromBackend(String id) {
        Logger.log();

        NetworkClient.getInstance().getNetworkServices().getDetails(id).enqueue(new Callback<List<DetailsItemResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<DetailsItemResponse>> call, @NonNull Response<List<DetailsItemResponse>> response) {
                Logger.log(response.code() + " on " + call.request().url());
                if (response.code() == 200 && response.body() != null) {
                    if (response.body().size() > 0) {
                        DetailsItemResponse detailsItemResponse = response.body().get(0);
                        ItemDetails item = mapping(detailsItemResponse);
                        Logger.log(item);
                        detailsLiveData.postValue(item);
                    } else {
                        detailsLiveData.postValue(null);
                    }
                    // TODO pass data to Repository layer
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<DetailsItemResponse>> call, @NonNull Throwable throwable) {
                Logger.log(throwable.getMessage() + " on " + call.request().url());
                errorLiveDate.postValue(throwable.getMessage());
            }
        });
    }

    protected ItemDetails mapping(DetailsItemResponse detailsItemResponse) {
        Logger.log();
        // TODO if we will have different Response and Model structures, need to crete Mapper from Response to Model/Repository

        ItemDetails item = null;
        if (detailsItemResponse == null) {
            return item;
        }

        // TODO care about not-clear data: trim strings, check length, check not-digit values for numeric fields, etc...
        item = new ItemDetails();
        item.setDbn(detailsItemResponse.getDbn());
        item.setSchoolName(detailsItemResponse.getSchoolName());
        item.setSatMathScore(detailsItemResponse.getSatMathScore());
        item.setSatReadingScore(detailsItemResponse.getSatReadingScore());
        item.setSatWritingScore(detailsItemResponse.getSatWritingScore());
        item.setNumSatTakers(detailsItemResponse.getNumSatTakers());

        return item;
    }
}
