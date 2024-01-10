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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;
import android.util.Log;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

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
                                    .addOnSuccessListener(aVoid -> {
                                       showStatusUpdateSuccessDialog();
                                       sendCheckInNotification(document.getString("userId"));
                                    })
                                    .addOnFailureListener(e -> showStatusUpdateFailedDialog());
                        }
                    } else {
                        showNoMatchingEventDialog();
                    }
                });
    }

    private void sendCheckInNotification(String userId) {
        // Fetch user's email using userId
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection("users")
                .document(userId)
                .get()
                .addOnCompleteListener(userTask -> {
                    if (userTask.isSuccessful() && userTask.getResult() != null) {
                        String userEmail = userTask.getResult().getString("email");
//                        String userEmail = "trongpro0307@gmail.com";
//                        sendEmailNotification(userEmail);
                    } else {
                        // Handle the case when user information is not found
                    }
                });
    }

//    private void sendEmailNotification(String userEmail) {
//        final String username = "phamminzon@gmail.com";
//        final String password = "mt03072002";
//
//        Properties props = new Properties();
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.smtp.host", "smtp.gmail.com");
//        props.put("mail.smtp.port", "587");
//
//        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(username, password);
//            }
//        });
//
//        try {
//            Message message = new MimeMessage(session);
//            message.setFrom(new InternetAddress(username));
//            message.setRecipients(Message.RecipientType.TO,
//                    InternetAddress.parse(userEmail));
//            message.setSubject("Check-in Successful");
//            message.setText("Dear User,\n\nYou have successfully checked in to the event.");
//
//            Transport.send(message);
//
//            Log.i("Email", "Email sent successfully");
//
//        } catch (MessagingException e) {
//            Log.e("Email", "Error sending email", e);
//        }
//    }

    private void showStatusUpdateSuccessDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Success");
        builder.setMessage("Status updated successfully");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // You can perform additional actions if needed
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void showStatusUpdateFailedDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Error");
        builder.setMessage("Failed to update status");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // You can perform additional actions if needed
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void showNoMatchingEventDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("No Matching Event");
        builder.setMessage("No registered event found with the scanned QR code");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // You can perform additional actions if needed
                dialog.dismiss();
            }
        });
        builder.show();
    }

}