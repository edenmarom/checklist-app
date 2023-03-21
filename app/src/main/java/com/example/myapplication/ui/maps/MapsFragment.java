package com.example.myapplication.ui.maps;
import androidx.fragment.app.Fragment;
import android.Manifest;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.myapplication.R;
import com.example.myapplication.model.Model;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.List;

public class MapsFragment extends Fragment {

    static int listNum = 0;
    List<List<String>> locationsList;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        @Override
        public void onMapReady(GoogleMap googleMap) {
            if (googleMap != null){
                String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION};
                requestPermissions(perms, 0);

                Model.instance().getAllLocations(locations -> {
                    locationsList = locations;
                    addMarkers(locationsList,googleMap);
                });
            }
        }
    };

    private void addMarkers(List<List<String>> locations, GoogleMap googleMap){
        if (locations != null && !locations.isEmpty()) {
            for (List<String> location : locations) {
                LatLng loc = new LatLng(Double.parseDouble(location.get(0)), Double.parseDouble(location.get(1)));
                MarkerOptions options = new MarkerOptions();
                options.position(loc);
                listNum++;
                options.title("List " + listNum);
                googleMap.addMarker(options);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_maps, container, false);
        SupportMapFragment supportMapFragment=(SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.map);

        if (supportMapFragment != null) {
            supportMapFragment.getMapAsync(callback);
        }

        return view;
    }
}
