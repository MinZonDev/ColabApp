package com.example.collaborators.ui.qrcode;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.collaborators.R;
import com.example.collaborators.databinding.FragmentQrcodeBinding;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;


public class QrcodeFragment extends Fragment {

    private Button btnScanQRCode;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Removed ViewModel initialization as it's not used in the provided code

        View root = inflater.inflate(R.layout.fragment_qrcode, container, false);

        btnScanQRCode = root.findViewById(R.id.btnQuetQR);

        btnScanQRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start the QR code scanner
                startQRCodeScanner();
            }
        });

        return root;
    }

    private void startQRCodeScanner() {
        ScanOptions options = new ScanOptions();
        options.setPrompt("QR code");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        barLauncher.launch(options);
    }

    ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(), result -> {
        if (result.getContents() != null) {
            String eventIdFromQR = result.getContents();
            checkAndUpdateStatus(eventIdFromQR);
        }
    });

    private void checkAndUpdateStatus(String eventIdFromQR) {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        firestore.collection("registeredEvent")
                .whereEqualTo("eventId", eventIdFromQR)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        for (DocumentSnapshot document : task.getResult()) {
                            document.getReference().update("status", 2)
                                    .addOnSuccessListener(aVoid -> showStatusUpdateSuccessDialog())
                                    .addOnFailureListener(e -> showStatusUpdateFailedDialog());
                        }
                    } else {
                        showNoMatchingEventDialog();
                    }
                });
    }

    private void showStatusUpdateSuccessDialog() {
        // Display a dialog indicating a successful status update
        // You can customize this based on your UI requirements
    }

    private void showStatusUpdateFailedDialog() {
        // Display a dialog indicating a failed status update
        // You can customize this based on your UI requirements
    }

    private void showNoMatchingEventDialog() {
        // Display a dialog indicating that no matching registeredEvent was found
        // You can customize this based on your UI requirements
    }
}