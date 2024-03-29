package com.example.myapplication.ui.sharedLists;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.R;
import com.example.myapplication.model.SharedListItem;
import java.util.List;

public class SharedListAdapter extends RecyclerView.Adapter<SharedListViewHolder> {

    LayoutInflater inflater;
    List<SharedListItem> data;

    public SharedListAdapter(LayoutInflater inflater, List<SharedListItem> data) {
        this.inflater = inflater;
        this.data = data;
    }

    public void setData(List<SharedListItem> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SharedListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.shared_list_item, parent, false);
        return new SharedListViewHolder(view, data);
    }


    @Override
    public void onBindViewHolder(@NonNull SharedListViewHolder holder, int position) {
        SharedListItem l = data.get(position);
        holder.bind(l);
    }

    @Override
    public int getItemCount() {
        if (data == null) return 0;
        return data.size();
    }
}
