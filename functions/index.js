const functions = require("firebase-functions");
const admin = require("firebase-admin");
const sgMail = require("@sendgrid/mail");

const key = "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";
admin.initializeApp();
sgMail.setApiKey(key);

exports.sendCheckinEmail = functions.firestore
    .document("registeredEvent/{eventId}")
    .onUpdate(async (change, context) => {
      const newValue = change.after.data();

      if (newValue && newValue.status === 2) {
        const userEmail = newValue.userEmail;
        const msg = {
          to: userEmail,
          from: "phamminzon@gmail.com",
          subject: "Check-in Successful",
          text: "You have successfully checked in.",
          html: "<p>You have successfully checked in.</p>",
        };

        try {
          await sgMail.send(msg);
          console.log("Email sent successfully");
        } catch (error) {
          console.error("Error sending email", error);
        }
      }

      return null;
    });
