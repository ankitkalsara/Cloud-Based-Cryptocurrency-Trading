/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userinterface.DocumentVerificationRole;

import Business.EcoSystem;
import Business.Employee.Employee;
import Business.Enterprise.Enterprise;
import Business.Organization.DocumentVerificationOrganization;
import Business.Organization.Organization;
import Business.UserAccount.MyAwsCredentials;
import Business.UserAccount.UserAccount;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import java.awt.CardLayout;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author Ankit
 */
public class DocumentVerificationWorkAreaJPanel extends javax.swing.JPanel {

    /**
     * Creates new form DocumentVerificationWorkAreaJPanel
     */
    private JPanel userProcessContainer;
    private Enterprise enterprise;
    private UserAccount userAccount;
    private DocumentVerificationOrganization documentVerificationOrganization;
    
    public DocumentVerificationWorkAreaJPanel(JPanel userProcessContainer, UserAccount account, Organization organization, Enterprise enterprise) {
        initComponents();
        this.userProcessContainer = userProcessContainer;
        this.userAccount = account;
        this.enterprise  = enterprise;
        this.documentVerificationOrganization = (DocumentVerificationOrganization)organization;
        listallS3();
    }

    public void listallS3()
    {
        String bucketName     = "aedprojectvalidate";
        MyAwsCredentials as= new MyAwsCredentials();
        
        
        AWSCredentials credentials = new BasicAWSCredentials(as.getAccessKeyID(), as.getSecretAccessKey());
        
        
        AmazonS3 s3client = new AmazonS3Client(credentials);        

        ListObjectsRequest listObjectsRequest = new ListObjectsRequest().withBucketName(bucketName);
        ObjectListing objectListing;

        ArrayList <String> det= new ArrayList<String>();
        do {
            objectListing = s3client.listObjects(listObjectsRequest);
            for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) 
            {
                System.out.println( " - " + objectSummary.getKey() + "  " +
                    "(size = " + objectSummary.getSize() + 
                    ")");
                det.add(objectSummary.getKey());
                
            }
            listObjectsRequest.setMarker(objectListing.getNextMarker());
        } while (objectListing.isTruncated());
        
        
        DefaultTableModel model = (DefaultTableModel) unverifiedCustomersJTbl.getModel();
        model.setRowCount(0);
        for (String key : det){
            Object[] row = new Object[1];
            row[0]       = key;
            model.addRow(row);
        }
        
        
        
        
    }
    
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel5 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        unverifiedCustomersJTbl = new javax.swing.JTable();
        viewDocumentsJBtn = new javax.swing.JButton();

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Document Verification WorkArea");

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Verify below Customers:");

        unverifiedCustomersJTbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Customer Address"
            }
        ));
        jScrollPane1.setViewportView(unverifiedCustomersJTbl);

        viewDocumentsJBtn.setText("View Documents");
        viewDocumentsJBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewDocumentsJBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(77, 77, 77)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(viewDocumentsJBtn)
                    .addComponent(jLabel1)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 439, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(384, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addComponent(jLabel1)
                .addGap(26, 26, 26)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(viewDocumentsJBtn)
                .addContainerGap(307, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void viewDocumentsJBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewDocumentsJBtnActionPerformed
        // TODO add your handling code here:
        if (unverifiedCustomersJTbl.getSelectedRow() >= 0) {
            String data = (String)unverifiedCustomersJTbl.getValueAt(unverifiedCustomersJTbl.getSelectedRow(), 0);
            VerificationDocumentsJPanel vdjp = new VerificationDocumentsJPanel(userProcessContainer, userAccount, documentVerificationOrganization, enterprise,data);
            userProcessContainer.add("Verification Documents JPaenl", vdjp);
            CardLayout layout = (CardLayout)userProcessContainer.getLayout();
            layout.next(userProcessContainer);
        } else {
            JOptionPane.showMessageDialog(null, "Please select a row");
        }
        
        
        
        
        
    }//GEN-LAST:event_viewDocumentsJBtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable unverifiedCustomersJTbl;
    private javax.swing.JButton viewDocumentsJBtn;
    // End of variables declaration//GEN-END:variables
}
