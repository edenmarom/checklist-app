package com.example.myapplication.ui.newList;
import static android.content.ContentValues.TAG;
import static com.example.myapplication.ui.login.LoginViewModel.currentUser;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentNewListBinding;
import com.example.myapplication.model.ListItem;
import com.example.myapplication.model.Model;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class NewListFragment extends Fragment {

    private FragmentNewListBinding binding;
    private View root;
    private List<String> listItem_ = new ArrayList<String>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        binding = FragmentNewListBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        binding.AddButton.setOnClickListener(view1->{
            insertNewList();
            getActivity().onBackPressed();
        });

        binding.BAddItem.setOnClickListener(view1-> {
            EditText ET = (EditText) getView().findViewById(R.id.ET_add_item);
                Toast.makeText(getContext(), ET.getText().toString(), Toast.LENGTH_SHORT).show();
                listItem_.add(ET.getText().toString());
                ET.setText("");
                ET.setHint("add item");
        });

        return root;
    }

    private void insertNewList() {
        String name_ET = binding.listName.getText().toString();
        List<String> listItemDB = listItem_;
        EditText address_ET = binding.listLocation;
        String address_s = address_ET.getText().toString().trim();
        Address address = getLatitudeAndLongitudeFromGoogleMapForAddress(address_s);
        List<String> locationDB = new ArrayList<>();
        locationDB.add(String.valueOf(address.getLatitude()));
        locationDB.add(String.valueOf(address.getLongitude()));
        String userIdDB = currentUser.getUserId();
        List<String> participantsDB = new ArrayList<>();
        participantsDB.add(0,"E5aKiS0H37Yx8nudiYexLAviiVF3");//TODO EDEN TO BE REMOVED
        String imgIdlDB = "";
        ListItem l = new ListItem("",name_ET,listItemDB,locationDB,userIdDB,participantsDB,imgIdlDB);
        Model.instance().insertNewList(l, (listId)->{
            l.setListId(String.valueOf(listId));
        });
    }

    public Address getLatitudeAndLongitudeFromGoogleMapForAddress(String searchedAddress){
        Geocoder coder = new Geocoder(getContext(), Locale.getDefault());
        List<Address> address;
        try
        {
            address = coder.getFromLocationName(searchedAddress,5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            return location;

        }
        catch(Exception e)
        {
            Log.d(TAG, "MY_ERROR : ############Address Not Found");
            return null;
        }
    }
}
