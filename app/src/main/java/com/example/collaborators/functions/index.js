const functions = require('firebase-functions');
const admin = require('firebase-admin');
const nodemailer = require('nodemailer');

admin.initializeApp();

// Nodemailer configuration
const transporter = nodemailer.createTransport({
  service: 'gmail',
  auth: {
    user: 'phamminzon@gmail.com',
    pass: 'mt03072002'
  }
});

exports.updateStatusAndSendEmail = functions.firestore
  .document('registeredEvent/{eventId}')
  .onUpdate(async (change, context) => {
    const newValue = change.after.data();
    const previousValue = change.before.data();

    // Check if the status is updated to 2 (successful check-in)
    if (newValue.status === 2 && previousValue.status !== 2) {
      // Get user ID and email from the registeredEvent document
      const userId = newValue.userId;
      const userEmail = await getUserEmail(userId);

      // Send success email
      await sendSuccessEmail(userEmail);
    }

    return null;
  });

async function getUserEmail(userId) {
  const userRecord = await admin.auth().getUser(userId);
  return userRecord.email;
}

async function sendSuccessEmail(userEmail) {
  const mailOptions = {
    from: 'phamminzon@gmail.com',
    to: userEmail,
    subject: 'Check-in Successful',
    text: 'Dear student, your check-in for the event was successful. Thank you!'
  };

  return transporter.sendMail(mailOptions);
}
