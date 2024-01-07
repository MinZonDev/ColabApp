package com.example.collaborators.ui.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.collaborators.Activites.RegisterActivity;
import com.example.collaborators.Activites.ResetPasswordActivity;
import com.example.collaborators.R;
import com.example.collaborators.databinding.FragmentAccountBinding;
import com.example.collaborators.ui.qrcode.QrcodeViewModel;
import com.google.firebase.auth.FirebaseAuth;

public class AccountFragment extends Fragment {

    private FragmentAccountBinding binding;
    private TextView txtNameUser, txtMSSV, txtSDT, txtGmail;
    private Button btnSua1, btnSua2, btnDoiPassword, btnLogOut, btnBack, btnLuu;
    private ImageButton btnAvatar, btnDoiAvatar;
    private FirebaseAuth mAuth;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        mAuth = FirebaseAuth.getInstance();

        QrcodeViewModel dashboardViewModel =
                new ViewModelProvider(this).get(QrcodeViewModel.class);

        binding = FragmentAccountBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        btnLogOut = root.findViewById(R.id.btnLogOut);
        btnDoiPassword = root.findViewById(R.id.btnDoiPassword);


        btnDoiPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open the ResetPasswordActivity
                Intent intent = new Intent(getActivity(), ResetPasswordActivity.class);
                startActivity(intent);
            }
        });


        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Thực hiện logout Firebase
                mAuth.signOut();
                Intent intent = new Intent(getActivity(), RegisterActivity.class);
                startActivity(intent);
                getActivity().finish();
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