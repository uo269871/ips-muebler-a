package util;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import business.logic.Producto;

public class Correo {
	 
	public void sendEmail(String correo, Date date, List<Producto> productos){
		Properties propiedad = new Properties();
        propiedad.setProperty("mail.smtp.host", "smtp.gmail.com");
        propiedad.setProperty("mail.smtp.starttls.enable", "true");
        propiedad.setProperty("mail.smtp.port", "587");
        propiedad.setProperty("mail.smtp.auth", "true");
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        
        Session sesion = Session.getDefaultInstance(propiedad);
        String correoEnvia = "muebleriaips@gmail.com";
        String contrasena = "soyadmin";
        String receptor = correo;
        String asunto = "Transporte pedido";
        String mensaje= "Su transporte ha pasado de pendiente a en tránsito, el transporte le llegará el " + cal.get(Calendar.DAY_OF_MONTH) + "/" + (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.YEAR);
        for (Producto p : productos) {
        	mensaje += "\n" + p.toString();
        }
        
        MimeMessage mail = new MimeMessage(sesion);
        
        try {
        	mail.setFrom(new InternetAddress (correoEnvia));
			mail.addRecipient(Message.RecipientType.TO, new InternetAddress (receptor));
	        mail.setSubject(asunto);
	        mail.setText(mensaje);
	            
	        Transport transportar = sesion.getTransport("smtp");
	        transportar.connect(correoEnvia,contrasena);
	        transportar.sendMessage(mail, mail.getRecipients(Message.RecipientType.TO));          
	        transportar.close();
		} catch (AddressException e) {
			// TODO Auto-generated catch block
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
		} catch (Exception e) {
			
		}
	}
}
