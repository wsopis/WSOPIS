package wsopis.client;

import java.awt.event.KeyEvent;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import wsopis.ServerInterface;

public class Client extends javax.swing.JFrame {

    public Client() {
        try {
            this.setTitle("WSOP Information System Client");
            initComponents();       
            this.setLocationRelativeTo(null);
            registry = LocateRegistry.getRegistry(Constants.ip, 1098);
            stub = (ServerInterface) registry.lookup("Hello");
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPasswordField1 = new javax.swing.JPasswordField();
        textField1 = new java.awt.TextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(400, 350));
        setResizable(false);

        jButton1.setText("Register");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Sign up");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel1.setText("E-mail:");

        jLabel2.setText("Password:");

        jPasswordField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jPasswordField1KeyReleased(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addGap(41, 41, 41)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(textField1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPasswordField1))))
                .addContainerGap(83, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(89, 89, 89)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(textField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButton2)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addContainerGap(78, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.setVisible(false);
        regFrame = new RegisterFrame();          
        regFrame.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            int x = stub.signUp(textField1.getText(), jPasswordField1.getText());
                if (x == 0) {
                    this.setVisible(false);
                    c0 = new ClientAO(textField1.getText());
                    c0.setVisible(true);
                }
                if (x == 1) {
                    this.setVisible(false);
                    c1 = new ClientRO(textField1.getText());
                    c1.setVisible(true);
                }
                if (x == 2) {
                    this.setVisible(false);
                    c2 = new ClientSO(textField1.getText());
                    c2.setVisible(true);
                }
                if (x == 3) {
                    this.setVisible(false);
                    c3 = new ClientJ(textField1.getText());
                    c3.setVisible(true);
                }
                if (x == 4) {
                    this.setVisible(false);
                    c4 = new ClientP(textField1.getText());
                    c4.setVisible(true);
                }
                if (x == -1) {
                    wrongData();
                }
        } catch (RemoteException e) {
            System.err.println("Client exception: " + e.toString());
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jPasswordField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPasswordField1KeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            try {
                int x = stub.signUp(textField1.getText(), jPasswordField1.getText());
                if (x == 0) {
                    this.setVisible(false);
                    c0 = new ClientAO(textField1.getText());
                    c0.setVisible(true);
                }
                if (x == 1) {
                    this.setVisible(false);
                    c1 = new ClientRO(textField1.getText());
                    c1.setVisible(true);
                }
                if (x == 2) {
                    this.setVisible(false);
                    c2 = new ClientSO(textField1.getText());
                    c2.setVisible(true);
                }
                if (x == 3) {
                    this.setVisible(false);
                    c3 = new ClientJ(textField1.getText());
                    c3.setVisible(true);
                }
                if (x == 4) {
                    this.setVisible(false);
                    c4 = new ClientP(textField1.getText());
                    c4.setVisible(true);
                }
                if (x == -1) {
                    wrongData();
                }
            } catch (RemoteException e) {
                System.err.println("Client exception: " + e.toString());
            }
        }
    }//GEN-LAST:event_jPasswordField1KeyReleased

    public static void main(String args[]) {    
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }                
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {                
                new Client().setVisible(true);                
            }
        });
    }
    
    private void wrongData() {
        String message = "Wrong data";
        JOptionPane pane = new JOptionPane(message);
        JDialog dialog = pane.createDialog(new JFrame(), "Wrong data!");
        dialog.show();
    }
    
    private ClientAO c0;
    private ClientRO c1;
    private ClientSO c2;
    private ClientJ c3;
    private ClientP c4;
    private static Registry registry;
    private static ServerInterface stub;
    private RegisterFrame regFrame;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPasswordField jPasswordField1;
    private java.awt.TextField textField1;
    // End of variables declaration//GEN-END:variables
}
