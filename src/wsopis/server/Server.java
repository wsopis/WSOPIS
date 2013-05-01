package wsopis.server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import wsopis.ServerInterface;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import javax.mail.Message.RecipientType;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;

public class Server implements ServerInterface {

    public static void main(String args[]) {                
        try {                        
            loaddb();
            Server obj = new Server();
            ServerInterface stub = (ServerInterface) UnicastRemoteObject.exportObject(obj, 0);
            Registry registry = LocateRegistry.createRegistry(1098);
            registry.bind("Hello", stub);
            System.err.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
        }        
    }
    

    @Override
    public boolean clientPressedRegister(String email, String password, String surname, String name, String age, String type) {
        boolean check = false;
        try {            
            String host = "smtp.gmail.com";
            String from = "contactwsopis";
            String pass = "vfvjxrfvjz";
            Properties props = System.getProperties();
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.user", from);
            props.put("mail.smtp.password", pass);
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            String[] to = {email};
            Session session = Session.getDefaultInstance(props, null);
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            InternetAddress[] toAddress = new InternetAddress[to.length];          
            for (int i = 0; i < to.length; i++) {
                toAddress[i] = new InternetAddress(to[i]);
            }
            for (int i = 0; i < toAddress.length; i++) {
                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
            }
            message.setSubject("Registration results");
            if (addToDB(email, password, surname, name, age, type)) {
                message.setText("You have been successfully registered in WSOP Information System.\n"
                        + "Your login is: " + email + 
                        "\nYour password is: " + password);
                check = true;
            }
            else {
                message.setText("Your registration request have been canceled. \nTry again or contact support.");
                check = false;
            }
            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, pass);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();               
        } catch (AddressException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        return check;
    }
    
    @Override
    public int signUp(String s1, String s2) throws RemoteException {
        int check = -1;
        if (doc.getElementsByTagName(getTagName(s1)).getLength() > 0) {            
            Element e = (Element) doc.getElementsByTagName(getTagName(s1)).item(0);           
            if ((e.getAttribute("email").compareTo(s1) == 0) && (e.getAttribute("password").compareTo(s2) == 0)) {
                if (e.getAttribute("type").compareTo("Player") == 0) 
                    check = 4;                                    
                else 
                    if (e.getAttribute("type").compareTo("Journalist") == 0) 
                        check = 3;                                        
                    else 
                        if (e.getAttribute("specialization").compareTo("Registration operator") == 0) 
                            check = 1;
                        else 
                            if (e.getAttribute("specialization").compareTo("Accreditation operator") == 0)
                                check = 0;
                            else 
                                if (e.getAttribute("specialization").compareTo("Schedule operator") == 0)
                                    check = 2;
            }
        }
        return check;
    }
    
    @Override
    public void completeRegisterStaff(String email, String spec, String password) {
        if (password.compareTo("abcd1234") == 0) {
            Element e = (Element) doc.getElementsByTagName(getTagName(email)).item(0);
            e.setAttribute("specialization", spec);
            savedb();
        }
    }
    
    @Override
    public void completeRegisterJournalist(String email, String spec) {
        Element e = (Element) doc.getElementsByTagName(getTagName(email)).item(0);
        e.setAttribute("accreditation_code", spec);
        savedb();
        try {
            String emailOfOperator = "contactwsopis@gmail.com";
            for (int i = 0; i < doc.getFirstChild().getChildNodes().getLength(); i++) {
                e = (Element) doc.getFirstChild().getChildNodes().item(i);
                if (e.getAttribute("specialization").compareTo("Registration operator") == 0) {
                    emailOfOperator = e.getAttribute("email");
                    break;
                }                    
            }            
            String host = "smtp.gmail.com";
            String from = "contactwsopis";
            String pass = "vfvjxrfvjz";
            Properties props = System.getProperties();
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.user", from);
            props.put("mail.smtp.password", pass);
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            String[] to = {emailOfOperator};
            Session session = Session.getDefaultInstance(props, null);
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            InternetAddress[] toAddress = new InternetAddress[to.length];          
            for (int i = 0; i < to.length; i++) {
                toAddress[i] = new InternetAddress(to[i]);
            }
            for (int i = 0; i < toAddress.length; i++) {
                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
            }
            message.setSubject("Registration journalist");
            message.setText("Journalist email is: " + email + ".\n" + "Accreditation code: " + spec + ".");                         
            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, pass);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();                  
        } catch (AddressException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        savedb();
    }
    
    @Override
    public void completeRegisterPlayer(String email, File file) {
        try {
            String emailOfOperator = "contactwsopis@gmail.com";            
            for (int i = 0; i < doc.getFirstChild().getChildNodes().getLength(); i++) {
                Element e = (Element) doc.getFirstChild().getChildNodes().item(i);
                if (e.getAttribute("specialization").compareTo("Registration operator") == 0) {
                    emailOfOperator = e.getAttribute("email");
                    break;
                }                    
            }            
            String host = "smtp.gmail.com";
            String from = "contactwsopis";
            String pass = "vfvjxrfvjz";
            Properties props = System.getProperties();
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.user", from);
            props.put("mail.smtp.password", pass);
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            String[] to = {emailOfOperator};
            Session session = Session.getDefaultInstance(props, null);
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            InternetAddress[] toAddress = new InternetAddress[to.length];          
            for (int i = 0; i < to.length; i++) {
                toAddress[i] = new InternetAddress(to[i]);
            }
            for (int i = 0; i < toAddress.length; i++) {
                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
            }
            message.setSubject("Registration player");
            message.setText("Player email is: " + email + "."); 
            MimeBodyPart body = new MimeBodyPart();
            body.setText("Player email is: " + email + ".");            
            MimeBodyPart attachMent = new MimeBodyPart();
            FileDataSource dataSource = new FileDataSource(file);
            attachMent.setDataHandler(new DataHandler(dataSource));
            attachMent.setFileName(file.getName());
            attachMent.setDisposition(MimeBodyPart.ATTACHMENT);
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(body);
            multipart.addBodyPart(attachMent);
            message.setContent(multipart);            
            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, pass);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();                  
        } catch (AddressException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        savedb();
    }
    
    private static boolean addToDB(String email, String password, String surname, String name, String age, String type) {                        
        boolean check = false;
        String tag_str = getTagName(email);                
        Element newData = doc.createElement(tag_str);               
        newData.setAttribute("email", email);
        newData.setAttribute("password", password);
        newData.setAttribute("surname", surname);
        newData.setAttribute("name", name);
        newData.setAttribute("age", age);
        newData.setAttribute("type", type);                                       
        if (doc.getElementsByTagName(tag_str).getLength() == 0) {            
            doc.getFirstChild().appendChild(newData);                        
            savedb();
            check = true;
        }
        return check;
    }
    
    private static void loaddb() {
        try {
            File fXmlFile = new File("database");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();            
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(fXmlFile);          
            doc.getDocumentElement().normalize();                                    
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void savedb() {
        if (doc == null) {
            try {
                DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
                doc = docBuilder.newDocument();
                Element rootElement = doc.createElement("database");
                doc.appendChild(rootElement);
            } catch (ParserConfigurationException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {                                    
            Source source = new DOMSource(doc);            
            File file = new File("database");
            Result result = new StreamResult(file);           
            Transformer xformer = TransformerFactory.newInstance().newTransformer();
            xformer.transform(source, result);            
        } catch (TransformerConfigurationException e) {
        } catch (TransformerException e) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    private static String getTagName(String str) {
        String result = "login_";
        for(int i = 0; i < str.length(); i++) {
            if (str.charAt(i) != '@') 
                result += str.charAt(i);                  
        }
        return result;
    }
    
    private static String makeAccreditationPretty(String s1, String s2) {
        return "code_" + s1 + "_surname_" + s2;
    }
    
    private static Document doc;

    @Override
    public void addCode(String code, String surname) throws RemoteException {
        String str = makeAccreditationPretty(code, surname);
        if (doc.getElementsByTagName("list_of_accreditation").getLength() == 0) {
            Element newData = doc.createElement("list_of_accreditation");               
            newData.setAttribute(str, str);
            doc.getFirstChild().appendChild(newData);
        }
        else {
            Element e = (Element) doc.getElementsByTagName("list_of_accreditation").item(0);
            e.setAttribute(str, str);
        }
        savedb();
    }

    @Override
    public void deleteCode(String code, String surname) throws RemoteException {
        String str = makeAccreditationPretty(code, surname);
        if (doc.getElementsByTagName("list_of_accreditation").getLength() == 0) {            
            Element newData = doc.createElement("list_of_accreditation");                           
            doc.getFirstChild().appendChild(newData);
        }
        else {
            Element e = (Element) doc.getElementsByTagName("list_of_accreditation").item(0);
            e.removeAttribute(str);
        }
        savedb();
    }

    @Override
    public void deleteRegisteredUser(String email) throws RemoteException {
        Element e;
        if (doc.getElementsByTagName(getTagName(email)).getLength() > 0) {
            e = (Element) doc.getElementsByTagName(getTagName(email)).item(0);
            doc.getFirstChild().removeChild(e);
        }        
        savedb();
    }
    
    @Override
    public String printAccreditationList() throws RemoteException { 
        String s = "";
        if (doc.getElementsByTagName("list_of_accreditation").getLength() > 0) {
            for (int i = 0; i < doc.getElementsByTagName("list_of_accreditation").item(0).getAttributes().getLength(); i++) {
                s = s.concat(doc.getElementsByTagName("list_of_accreditation").item(0).getAttributes().item(i).getNodeValue());
                s = s.concat("\n");
            }
        }
        return s;
    }   

    @Override
    public void saveSchedule(String schedule) throws RemoteException {
        Element e = doc.createElement("schedule");
        e.setAttribute("text", schedule);
        doc.getFirstChild().appendChild(e);
        savedb();
    }

    @Override
    public String printSchedule() throws RemoteException {
        if (doc.getElementsByTagName("schedule").getLength() > 0)
            return doc.getElementsByTagName("schedule").item(0).getAttributes().item(0).getNodeValue();
        else
            return "";
    }
}
