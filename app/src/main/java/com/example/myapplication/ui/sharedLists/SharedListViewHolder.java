package com.example.myapplication.ui.sharedLists;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.R;
import com.example.myapplication.model.ListItem;
import com.squareup.picasso.Picasso;
import java.util.List;

public class SharedListViewHolder extends RecyclerView.ViewHolder {

    TextView nameTv;
    Button editB;
    ImageView image;
    TextView listItemTV;
    TextView updateTV;
    List<ListItem> data;
    NavController navController;
    Fragment fragment;
    String id;

    public SharedListViewHolder(@NonNull View itemView, SharedListAdapter.OnItemClickListener listener, List<ListItem> data, Fragment fragment) {
        super(itemView);
        this.data = data;
        this.fragment = fragment;

        navController = NavHostFragment.findNavController(fragment);

        nameTv = itemView.findViewById(R.id.title_listItem);
        editB = itemView.findViewById(R.id.edit_B);
        image = itemView.findViewById(R.id.image_listItem);
        listItemTV = itemView.findViewById(R.id.listItem_TV);
        updateTV = itemView.findViewById(R.id.date_listItem);

        editB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Bundle bundle = new Bundle();
                bundle.putString("id",id);
                navController.navigate(R.id.editListFragment2,bundle);
            }
        });

        //TODO ADI- add on click like eliav did this crashes the app
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = getAdapterPosition();
                listener.onItemClick(pos);
            }
        });
    }
    public void bind(ListItem l, int pos) {
        id = l.getListId();
        nameTv.setText(l.getName());

        if (l.getImgUrl()  != null && l.getImgUrl().length() > 5) {
            Picasso.get().load(l.getImgUrl()).placeholder(R.drawable.avatar).into(image);
        }else{
            image.setImageResource(R.drawable.avatar);
        }

        //TODO: insert all data
//        listItemTV.setText(l.getListItem().get(0).replace("[","").replace("]","").replace(",","\n"));

    }

}
