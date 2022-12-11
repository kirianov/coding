package com.kirianov.coding.ui.details;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.kirianov.coding.R;
import com.kirianov.coding.databinding.FragmentDetailsBinding;
import com.kirianov.coding.model.ItemDetails;
import com.kirianov.coding.utils.Logger;

public class DetailsFragment extends Fragment {

    private FragmentDetailsBinding binding;
    private DetailsViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.log(getArguments());
        viewModel = new ViewModelProvider(this).get(DetailsViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Logger.log(getArguments());
        binding = FragmentDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        Logger.log(getArguments());
        Bundle arguments = getArguments();
        if (arguments == null) {
            return;
        }
        String dbn = arguments.getString("dbn");
        final String overviewParagraph = arguments.getString("overviewParagraph");
        Logger.log(dbn);
        if (dbn.isEmpty()) {
            return;
        }

        viewModel = new ViewModelProvider(requireActivity()).get(DetailsViewModel.class);

        viewModel.getDetailsLiveData().observe(getViewLifecycleOwner(), new Observer<ItemDetails>() {
            @Override
            public void onChanged(ItemDetails item) {
                Logger.log(item);
                setData(item, overviewParagraph); // better way: pass data through Repository/Database pattern ("bundle" as workaround)
                hideProgressbar();
            }
        });

        viewModel.getErrorLiveDate().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String error) {
                if (error != null && !error.isEmpty()) {
                    showError(error);
                    hideData();
                    hideProgressbar();
                }
            }
        });

        showProgressbar();
        viewModel.showDetails(dbn);
    }

    @Override
    public void onResume() {
        super.onResume();
        Logger.log();
        hideData();
        hideError();
    }

    private void setData(ItemDetails item, String overviewParagraph) {
        Logger.log(item);
        if (item == null) {
            showError(getResources().getString(R.string.details_error));
            hideData();
        } else {
            binding.schoolName.setText(item.getSchoolName());
            binding.overviewParagraph.setText(overviewParagraph); // passed from parent fragment
            binding.satMath.setText(item.getSatMathScore());
            binding.satReading.setText(item.getSatReadingScore());
            binding.satWriting.setText(item.getSatWritingScore());
            binding.satTakers.setText(item.getNumSatTakers());
            showData();
            hideError();
        }
        hideProgressbar();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Logger.log();
        hideData();
        hideError();
        hideProgressbar();
        binding.getRoot().removeAllViews();
        binding = null;
    }

    private void showProgressbar() {
        binding.progressbar.setVisibility(VISIBLE);
    }

    private void hideProgressbar() {
        binding.progressbar.setVisibility(GONE);
    }

    private void showError(String error) {
        binding.error.setText(error);
        binding.error.setVisibility(VISIBLE);
    }

    private void hideError() {
        binding.error.setVisibility(GONE);
    }

    private void showData() {
        // TODO TBD is migrate to one view-wrapper with all data sub-views inside?
        binding.schoolName.setVisibility(VISIBLE);
        binding.overviewParagraph.setVisibility(VISIBLE);
        binding.satMath.setVisibility(VISIBLE);
        binding.satReading.setVisibility(VISIBLE);
        binding.satWriting.setVisibility(VISIBLE);
        binding.satTakers.setVisibility(VISIBLE);
        binding.satMathLabel.setVisibility(VISIBLE);
        binding.satReadingLabel.setVisibility(VISIBLE);
        binding.satWritingLabel.setVisibility(VISIBLE);
        binding.satTakersLabel.setVisibility(VISIBLE);
    }

    private void hideData() {
        binding.schoolName.setVisibility(GONE);
        binding.overviewParagraph.setVisibility(GONE);
        binding.satMath.setVisibility(GONE);
        binding.satReading.setVisibility(GONE);
        binding.satWriting.setVisibility(GONE);
        binding.satTakers.setVisibility(GONE);
        binding.satMathLabel.setVisibility(GONE);
        binding.satReadingLabel.setVisibility(GONE);
        binding.satWritingLabel.setVisibility(GONE);
        binding.satTakersLabel.setVisibility(GONE);
    }
}
