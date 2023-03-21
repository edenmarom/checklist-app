package com.example.myapplication.ui.sharedLists;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.R;
import com.example.myapplication.model.SharedListItem;
import com.squareup.picasso.Picasso;
import java.util.List;

public class SharedListViewHolder extends RecyclerView.ViewHolder {

    TextView nameTv;
    Button editB;
    ImageView image;
    TextView listItemTV;
    List<SharedListItem> data;
    String id;
    List<String> participantsTV;

    public SharedListViewHolder(@NonNull View itemView, List<SharedListItem> data) {
        super(itemView);
        this.data = data;

        nameTv = itemView.findViewById(R.id.shared_title_listItem);
        editB = itemView.findViewById(R.id.shared_edit_B);
        image = itemView.findViewById(R.id.shared_image_listItem);
        listItemTV = itemView.findViewById(R.id.shared_listItem_TV);
        participantsTV = itemView.findViewById(R.id.shared_participants_TV);


        editB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Bundle bundle = new Bundle();
                bundle.putString("id",id);
                Navigation.findNavController(view).navigate(R.id.editListFragment2,bundle);
            }
        });

    }

    public void bind(SharedListItem l) {
        id = l.getListId();
        nameTv.setText(l.getName());
        listItemTV.setText(l.getListItem()
                .toString().replaceAll(",","\n").replaceAll(" ","")
                .replaceAll("\\[|\\]", ""));
        participantsTV.addAll(l.getParticipants());

        if (l.getImgUrl()  != null && l.getImgUrl().length() > 5) {
            Picasso.get().load(l.getImgUrl()).placeholder(R.drawable.list).into(image);
        }else{
            image.setImageResource(R.drawable.list);
        }
    }

}
