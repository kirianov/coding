package com.kirianov.coding.ui.list;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kirianov.coding.databinding.ListRegularItemEvenBinding;
import com.kirianov.coding.databinding.ListRegularItemOddBinding;
import com.kirianov.coding.model.Item;
import com.kirianov.coding.utils.Logger;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_REGULAR_ITEM_ODD = 0; // two types of recycler view items (odd and even line)
    private static final int TYPE_REGULAR_ITEM_EVEN = 1;

    private volatile List<Item> items = new ArrayList<>();
    private volatile OnChangeListener listener;

    static class ViewHolderItemOdd extends RecyclerView.ViewHolder {
        ListRegularItemOddBinding binding;
        private ViewHolderItemOdd(ListRegularItemOddBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    static class ViewHolderItemEven extends RecyclerView.ViewHolder {
        ListRegularItemEvenBinding binding;
        private ViewHolderItemEven(ListRegularItemEvenBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position % 2 == 0 ? TYPE_REGULAR_ITEM_ODD : TYPE_REGULAR_ITEM_EVEN;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_REGULAR_ITEM_ODD) {
            ListRegularItemOddBinding binding = ListRegularItemOddBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            Logger.log();
            return new ViewHolderItemOdd(binding);
        } else if (viewType == TYPE_REGULAR_ITEM_EVEN) {
            ListRegularItemEvenBinding binding = ListRegularItemEvenBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            Logger.log();
            return new ViewHolderItemEven(binding);
        }
        throw new RuntimeException("There is no type that matches the type " + viewType + ", make sure your using types correctly.");
    }

    @Override
    public synchronized void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof ViewHolderItemOdd) {

            final ViewHolderItemOdd holderItem = (ViewHolderItemOdd)holder;
            final Item item = items.get(position);
            Logger.log("position=" + position + " ODD " + item);
            final ListRegularItemOddBinding binding = holderItem.binding;

            // TODO TDB we can update UI by viewBinding in XML too, but in this class we using just programmatically

            if (item == null) {
                binding.layout.setVisibility(View.GONE);
                binding.layout.setOnClickListener(null);
                return;
            }

            binding.layout.setVisibility(View.VISIBLE);

            String schoolName = item.getSchoolName();
            binding.schoolName.setText(schoolName);
            binding.schoolName.setVisibility(!schoolName.isEmpty() ? View.VISIBLE : View.GONE);

            String overviewParagraph = item.getOverviewParagraph();
            binding.paragraph.setText(overviewParagraph);
            binding.paragraph.setVisibility(overviewParagraph != null && overviewParagraph.length() > 0 ? View.VISIBLE : View.GONE);

            String location = item.getLocation();
            binding.location.setText(location);
            binding.location.setVisibility(location != null && location.length() > 0 ? View.VISIBLE : View.GONE);

            String phoneNumber = item.getPhoneNumber();
            binding.phoneNumber.setText(phoneNumber);
            binding.phoneNumber.setVisibility(phoneNumber != null && phoneNumber.length() > 0 ? View.VISIBLE : View.GONE);

            binding.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Logger.log(item.getDbn());
                    onItemClick(item);
                }
            });
        } else if (holder instanceof ViewHolderItemEven) {

            final ViewHolderItemEven holderItem = (ViewHolderItemEven)holder;
            final Item item = items.get(position);
            Logger.log("position=" + position + " EVEN " + item);
            final ListRegularItemEvenBinding binding = holderItem.binding;

            if (item == null) {
                binding.layout.setVisibility(View.GONE);
                binding.layout.setOnClickListener(null);
                return;
            }

            binding.layout.setVisibility(View.VISIBLE);

            String schoolName = item.getSchoolName();
            binding.schoolName.setText(schoolName);
            binding.schoolName.setVisibility(!schoolName.isEmpty() ? View.VISIBLE : View.GONE);

            String overviewParagraph = item.getOverviewParagraph();
            binding.paragraph.setText(overviewParagraph);
            binding.paragraph.setVisibility(overviewParagraph != null && overviewParagraph.length() > 0 ? View.VISIBLE : View.GONE);

            String location = item.getLocation();
            binding.location.setText(location);
            binding.location.setVisibility(location != null && location.length() > 0 ? View.VISIBLE : View.GONE);

            String phoneNumber = item.getPhoneNumber();
            binding.phoneNumber.setText(phoneNumber);
            binding.phoneNumber.setVisibility(phoneNumber != null && phoneNumber.length() > 0 ? View.VISIBLE : View.GONE);

            binding.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Logger.log(item.getDbn());
                    onItemClick(item);
                }
            });
        }
    }

    public synchronized void setData(List<Item> items) {
        Logger.log(items != null ? items.size() : 0);
        this.items = items == null ? new ArrayList<>() : new ArrayList<>(items);
        notifyDataSetChanged(); // in this demo app we update all bunch of items at moment, better to use 'smart update' utils, like DiffUtils or write own logic
    }

    @Override
    public synchronized int getItemCount() {
        return items != null ? items.size() : 0;
    }

    private synchronized void onItemClick(Item Item) {
        if (listener != null) {
            listener.onItemClick(Item);
        }
    }

    public synchronized void setOnItemClickListener(OnChangeListener listener) {
        this.listener = listener;
    }

    public interface OnChangeListener {
        void onItemClick(Item Item);
    }
}
