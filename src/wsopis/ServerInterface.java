package wsopis;

import java.io.File;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerInterface extends Remote {
    boolean clientPressedRegister(String s1, String s2, String s3, String s4, String s5, String s6) throws RemoteException;
    
    int signUp(String s1, String s2) throws RemoteException;
    
    void completeRegisterStaff(String email, String spec, String password) throws RemoteException;
    
    void completeRegisterJournalist(String email, String spec) throws RemoteException;
    
    void completeRegisterPlayer(String email, File file) throws RemoteException;
    
    void addCode(String code, String surname) throws RemoteException;
    
    void deleteCode(String code, String surname) throws RemoteException;
    
    void deleteRegisteredUser(String email) throws RemoteException;
    
    String printAccreditationList() throws RemoteException;
    
    void saveSchedule(String schedule) throws RemoteException;
    
    String printSchedule() throws RemoteException;
}
