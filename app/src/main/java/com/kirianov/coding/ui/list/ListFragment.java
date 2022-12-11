package com.kirianov.coding.ui.list;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kirianov.coding.MainActivity;
import com.kirianov.coding.R;
import com.kirianov.coding.databinding.FragmentListBinding;
import com.kirianov.coding.model.Item;
import com.kirianov.coding.utils.Logger;

import java.util.List;

public class ListFragment extends Fragment {

    private FragmentListBinding binding;
    private ListAdapter listAdapter;
    private ListViewModel viewModel;
    private boolean loaded;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.log();
        viewModel = new ViewModelProvider(this).get(ListViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Logger.log(this);
        binding = FragmentListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        Logger.log();

        listAdapter = new ListAdapter();
        listAdapter.setOnItemClickListener(new ListAdapter.OnChangeListener() {
            @Override
            public void onItemClick(Item item) {
                Logger.log(item);
                Bundle bundle = new Bundle();
                bundle.putString("dbn", item.getDbn()); // TODO move "dbn" and other strings to common variables of app/activity/fragment
                bundle.putString("overviewParagraph", item.getOverviewParagraph()); // pass additional data to details fragment from parent, better pass through Repository/Database/Storage pattern
                MainActivity activity = (MainActivity)ListFragment.this.getActivity();
                if (activity != null && !activity.isDestroyed()) {
                    activity.getNavigationView().navigate(R.id.action_listFragment_to_detailsFragment, bundle);
                }
                loaded = true;
            }
        });

        binding.list.setHasFixedSize(true);
        binding.list.setLayoutManager(new LinearLayoutManager(view.getContext()));
        binding.list.setNestedScrollingEnabled(false);
        binding.list.setAdapter(listAdapter);

        viewModel = new ViewModelProvider(requireActivity()).get(ListViewModel.class);

        viewModel.getListLiveData().observe(getViewLifecycleOwner(), new Observer<List<Item>>() {
            @Override
            public void onChanged(List<Item> items) {
                Logger.log(items != null ? items.size() : 0);
                listAdapter.setData(items);
                hideProgressbar();
                hideError();
            }
        });

        viewModel.getErrorLiveDate().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String error) {
                if (error != null && !error.isEmpty()) {
                    showError(error);
                    listAdapter.setData(null);
                    hideProgressbar();
                }
            }
        });

        if (!loaded) {
            // TODO Once we have Repository, we can show cached data firstly, then re-fetch new data from backend (if UI timeout expired), then update fragment second time
            showProgressbar();
            // TODO TBD we can migrate UI update (data, visibility, animation) from programmatically to viewBinding in XML
            viewModel.showList();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void showProgressbar() {
        binding.progressbar.setVisibility(View.VISIBLE);
        binding.list.setVisibility(View.GONE);
    }

    private void hideProgressbar() {
        binding.list.setVisibility(View.VISIBLE);
        binding.progressbar.setVisibility(View.GONE);
    }

    private void showError(String error) {
        binding.error.setText(error);
        binding.error.setVisibility(VISIBLE);
    }

    private void hideError() {
        binding.error.setVisibility(GONE);
    }

}
