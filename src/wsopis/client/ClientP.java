package wsopis.client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import wsopis.ServerInterface;

public class ClientP extends javax.swing.JFrame {

    public ClientP(String s) {
        try {
            email = s;
            this.setTitle("Beta client for players");
            initComponents();       
            this.setLocationRelativeTo(null);
            registry = LocateRegistry.getRegistry(Constants.ip, 1098);
            stub = (ServerInterface) registry.lookup("Hello");
            textArea1.setText(stub.printSchedule());

        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
        } 
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        textArea1 = new java.awt.TextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        textArea1.setEditable(false);
        textArea1.setPreferredSize(new java.awt.Dimension(380, 300));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(textArea1, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(textArea1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private static Registry registry;
    private static ServerInterface stub;
    private String email;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.TextArea textArea1;
    // End of variables declaration//GEN-END:variables
}
