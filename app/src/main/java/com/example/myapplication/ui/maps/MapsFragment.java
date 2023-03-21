package com.example.myapplication.ui.maps;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentEditListBinding;
import com.example.myapplication.model.FirebaseModel;
import com.example.myapplication.model.Model;
import com.example.myapplication.ui.EditItem.EditListViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.myapplication.databinding.FragmentMapsBinding;

import java.util.List;


public class MapsFragment extends Fragment {

    private FragmentMapsBinding binding;
    private View root;
//    private MapsViewModel mViewModel;

    private FirebaseModel firebaseModel = new FirebaseModel();


    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        @Override
        public void onMapReady(GoogleMap googleMap) {

            String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION};
            requestPermissions(perms, 0); // Use this method in Fragment classes

            //get all location
//            Model.instance().getLocations();
            firebaseModel.locationChangeListner(locations -> {
                List<List<String>> l = locations;
                for (List<String> location :locations){
                    LatLng loc = new LatLng(Double.parseDouble(location.get(0)), Double.parseDouble(location.get(1)));
                    googleMap.addMarker(new MarkerOptions().position(loc));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
                }

            });

        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentMapsBinding.inflate(inflater, container, false);
        root = binding.getRoot();


        return root;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }


}
