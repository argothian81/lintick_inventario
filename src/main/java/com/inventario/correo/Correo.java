/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inventario.correo;

import com.inventario.model.DataException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 *
 * @author USUARIO
 */
public class Correo {

    private static final Logger logger = LoggerFactory.getLogger(Correo.class);
    Session session;
    Properties prop;
    
    public Correo() throws DataException {
        logger.info("Cargando valores para envío de correo.");
        
        try {
            leerPropiedades();

            Authenticator auth = new Authenticator() {
                            //override the getPasswordAuthentication method
                            @Override
                            protected PasswordAuthentication getPasswordAuthentication() {
                                    return new PasswordAuthentication(prop.getProperty("smtp.auth"), prop.getProperty("smtp.pass"));
                            }
                    };

            session = Session.getDefaultInstance(prop, auth);
        
        } catch (DataException e) {
            throw e;
        } catch (Exception e) {
            logger.error("No se pudo cargar archivo de correo." + e.getMessage());
            throw new DataException(500, "No se pudo cargar Sesion de correo." + e.getMessage());
        }
    }
    
    private Properties leerPropiedades() throws DataException {
        logger.info("Cargando archivo de correo.");
        InputStream archivo = getClass().getClassLoader().getResourceAsStream("mail.properties");
        
        try {
            prop = new Properties();
            prop.load(archivo);
            
        } catch (IOException ex) {
            logger.error("No se pudo cargar archivo de correo." + ex.getMessage());
            throw new DataException(500, "Error cargando correo");
        }
        
        return prop;
    }
    
    /**
     * 
     * @param mensaje
     * @return
     * @throws DataException 
     */
    public boolean enviarCorreo(String mensaje) throws DataException{
        logger.info("Cargando archivo de correo.");
        
        try {
            MimeMessage msg = new MimeMessage(session);
            //set message headers
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");
            
            msg.setFrom(new InternetAddress(prop.getProperty("smtp.from"), "NoReply-JD"));
            
            msg.setSubject("Actualización de inventario", "UTF-8");
            
            msg.setText(mensaje, "UTF-8");
            
            msg.setSentDate(new Date());
            
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(prop.getProperty("smtp.to"), false));
            
            Transport.send(msg);
            
        } catch (MessagingException ex) {
            logger.error("No se pudo enviar el correo. " + ex.getMessage());
            throw new DataException(500, "No se pudo enviar el correo.");
        } catch (UnsupportedEncodingException ex) {
            logger.error("No se pudo enviar el correo. " + ex.getMessage());
            throw new DataException(500, "No se pudo enviar el correo.");
        } catch (Exception ex) {
            logger.error("No se pudo enviar el correo. " + ex.getMessage());
            throw new DataException(500, "No se pudo enviar el correo.");
        }
        
        return true;
    }
    
}
