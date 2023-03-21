package com.example.myapplication.ui.sharedLists;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.myapplication.databinding.FragmentSharedlistsBinding;
import com.example.myapplication.model.Model;

public class SharedListsFragment extends Fragment {

    FragmentSharedlistsBinding binding;
    NavController navController;
    SharedListAdapter adapter;
    SharedListsViewModel viewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSharedlistsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        navController = NavHostFragment.findNavController(this);

        binding.RVSharedList.setHasFixedSize(true);
        binding.RVSharedList.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new SharedListAdapter(getLayoutInflater(), viewModel.getData().getValue());
        binding.RVSharedList.setAdapter(adapter);

        binding.progressBar.setVisibility(View.GONE);

        viewModel.getData().observe(getViewLifecycleOwner(),list->{
            adapter.setData(list);
        });
        reloadData();

        Model.instance().EventSharedListLoadingState.observe(getViewLifecycleOwner(),status->{
            binding.swipeRefresh.setRefreshing(status == Model.LoadingState.LOADING);
        });

        binding.swipeRefresh.setOnRefreshListener(()->{
            reloadData();
        });

        return root;
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(SharedListsViewModel.class);
    }

    public static void reloadData(){
        Model.instance().refreshMySharedLists();
    }

}
