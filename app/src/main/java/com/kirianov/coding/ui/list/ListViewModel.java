package com.kirianov.coding.ui.list;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.kirianov.coding.model.Item;
import com.kirianov.coding.network.NetworkClient;
import com.kirianov.coding.network.responses.ListItemResponse;
import com.kirianov.coding.utils.Logger;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListViewModel extends ViewModel {

    private final MutableLiveData<List<Item>> listLiveData;
    private final MutableLiveData<String> errorLiveDate;

    public ListViewModel() {
        listLiveData = new MutableLiveData<>();
        errorLiveDate = new MutableLiveData<>();
    }

    public MutableLiveData<List<Item>> getListLiveData() {
        return listLiveData;
    }

    public MutableLiveData<String> getErrorLiveDate() {
        return errorLiveDate;
    }

    public void showList() {
        Logger.log();
        fetchDataFromBackend();
        // TODO once I have time better to add one more architecture layer - Repository (cache from memory, cache from database, fetch from backend)
    }

    private void fetchDataFromBackend() {
        Logger.log();

        NetworkClient.getInstance().getNetworkServices().getSchoolList().enqueue(new Callback<List<ListItemResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<ListItemResponse>> call, @NonNull Response<List<ListItemResponse>> response) {
                Logger.log(response.code() + " on " + call.request().url());
                if (response.code() == 200 && response.body() != null) {
                    List<ListItemResponse> itemsAsResponse = response.body();
                    List<Item> items = mapping(itemsAsResponse);
                    listLiveData.postValue(items);
                    // TODO pass data to Repository layer
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<ListItemResponse>> call, @NonNull Throwable throwable) {
                Logger.log(throwable.getMessage() + " on " + call.request().url());
                errorLiveDate.postValue(throwable.getMessage());
            }
        });
    }

    protected List<Item> mapping(List<ListItemResponse> itemsAsResponse) {
        Logger.log();
        // TODO if we will have different Response and Model structures, need to crete Mapper from Response to Model/Repository

        List<Item> items = new ArrayList<>();
        if (itemsAsResponse == null) {
            return items;
        }

        // TODO care about not-clear data: trim strings, check length, check not-digit values for numeric fields, etc...
        for (ListItemResponse itemAsResponse : itemsAsResponse) {
            Item item = new Item();
            item.setDbn(itemAsResponse.getDbn());
            item.setSchoolName(itemAsResponse.getSchoolName());
            item.setOverviewParagraph(itemAsResponse.getOverviewParagraph());
            item.setLocation(itemAsResponse.getLocation());
            item.setPhoneNumber(itemAsResponse.getPhoneNumber());
            item.setLatitude(itemAsResponse.getLatitude());
            item.setLongitude(itemAsResponse.getLongitude());
            items.add(item);
        }

        return items;
    }
}
