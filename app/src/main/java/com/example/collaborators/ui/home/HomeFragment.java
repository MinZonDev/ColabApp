package com.example.collaborators.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.collaborators.Activites.EventanalyzedActivity;
import com.example.collaborators.Activites.ListUpcomingActivity;
import com.example.collaborators.R;
import com.example.collaborators.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    private Button btnSuKienSapDienRa;
    private Button btnSuKienDuocPhanCong;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        btnSuKienSapDienRa = root.findViewById(R.id.btnSuKienSapDienRa);
        btnSuKienDuocPhanCong = root.findViewById(R.id.btnSuKienDuocPhanCong);


        btnSuKienSapDienRa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ListUpcomingActivity.class);
                startActivity(intent);
            }
        });

        btnSuKienDuocPhanCong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), EventanalyzedActivity.class);
                startActivity(intent);
            }
        });





        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}