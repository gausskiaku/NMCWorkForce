package com.Metier;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.Toast;

public class Mail {
	Session session = null;
	ProgressDialog pdialog = null;
	EditText reciep, sub, msg;
	String rec, subject, textMessage;
	
	
	public void sendMessage(Context context) {
		
		rec = "gausskiaku@gmail.com";
		subject = "TT ####### resolue";
		textMessage = "Test a partir d'android";
		
		
		
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		
		session = Session.getDefaultInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("gausskiaku@gmail.com", "Gausskiaku05");
			}
		});

		pdialog = ProgressDialog.show(context, "", "Sending Mail...", true);
		
		RetreiveFeedTask task = new RetreiveFeedTask();
		task.execute();
		Toast.makeText(context, "Message sent", Toast.LENGTH_LONG).show();
	}
	
	class RetreiveFeedTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			
			try{
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress("gausskiaku@gmail.com"));
				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(rec));
				message.setSubject(subject);
				message.setContent(textMessage, "text/html; charset=utf-8");
				Transport.send(message);
			} catch(MessagingException e) {
				e.printStackTrace();
			} catch(Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(String result) {
			pdialog.dismiss();
			
			
		}
	}
}
	
	
