package com.mycompany.libsys;

import java.util.ArrayList;
import java.util.Stack;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class LibSys extends javax.swing.JFrame {

    public LibSys() {
        initComponents();
    }
    private Stack<ArrayList<BookInformation>> arrayHistory = new Stack<>();
    private Stack<ArrayList<BookInformation>> redoStack = new Stack<>();
    private ArrayList<BookInformation> BookInfo = new ArrayList();
    private BookMod bookMod = new BookMod();
    abstract class BookModification{
        
    abstract void get();
    
    abstract void add();
    
    abstract void remove();
    
    abstract void update();
    
    abstract void total();
    
    abstract void clear();
    
    abstract void sort();
    
    abstract void undo();
    
    abstract void snapshot();
}
    class BookInformation{
        private String[] info;
        public BookInformation(String index, String title, String qty){
            info = new String[]{String.valueOf(BookInfo.size()),title,qty};
        }
         public String getElement(int index){
        return info[index];
    }
        public String getIndex() {
        return info[0];
    }
        public String getTitle() {
        return info[1];
    }
        public String getQty() {
        return info[2];
    }
    }
    public ArrayList BookInput(){
        String title = Title_field.getText();
        String qty = Quantity_field.getText();
        BookInfo.add(new BookInformation(String.valueOf(BookInfo.size()),title,qty));
       
        return BookInfo;
    }
class BookMod extends BookModification{
    void clear(){
    Index_field.setText("");
    Title_field.setText("");
    Quantity_field.setText("");
    }
    void snapshot(){
    ArrayList<BookInformation> saved = (ArrayList)BookInfo.clone();
    arrayHistory.push(saved);
    }
    
    void add(){
        ArrayList<BookInformation> BookInfo = BookInput();
        DefaultTableModel dt = (DefaultTableModel)Table.getModel();
        dt.setRowCount(0);
        
        for(int i = 0; i < BookInfo.size(); i++)
        {
            BookInformation bookInfo = BookInfo.get(i);
            Object rowData[] = new Object[] {bookInfo.getIndex(), bookInfo.getTitle(), bookInfo.getQty()};
            dt.addRow(rowData);
        }
        clear();
    }
    
    void remove(){
    String index =Index_field.getText();
    int numindex = Integer.parseInt(index);
    DefaultTableModel dt = (DefaultTableModel) Table.getModel();
    for(int x = 0; x < dt.getRowCount();x++){
    if(((String)Table.getValueAt(x,0)).equals(index)){
        BookInfo.remove(numindex);
        dt.removeRow(x);
        
        for(int i = numindex; i < BookInfo.size(); i++){
         BookInformation bookInfo = BookInfo.get(i);
         bookInfo.info[0] = String.valueOf(i);
        }
        
        dt.setRowCount(0);
    for(int i = 0; i < BookInfo.size(); i++){
        BookInformation bookInfo = BookInfo.get(i);
        Object Books[] = new Object[] {bookInfo.getIndex(),bookInfo.getTitle(),bookInfo.getQty()};
        dt.addRow(Books);
    }
    
    }
    }
    clear();
    }
    
    
    void get(){
            String index = Index_field.getText();
    int setIndex = Integer.parseInt(index);
    DefaultTableModel dt = (DefaultTableModel)Table.getModel();
        for(int x = 0 ; x < dt.getRowCount(); x++){
        if (Table.getValueAt(x, 0).equals(index)){
            Object title = BookInfo.get(setIndex).info[1];
            Object quantity = BookInfo.get(setIndex).info[2];
            JOptionPane.showMessageDialog(null,"The Book in index ["+ index +"] is " +title+"\nBook Quantity :"+ quantity );
        }
    } 
    clear();
    }
    
    void update(){
        snapshot();
        DefaultTableModel dt = (DefaultTableModel) Table.getModel();
        String index = Index_field.getText();
    int setIndex = Integer.parseInt(index);
    if(setIndex == Integer.parseInt(BookInfo.get(setIndex).getIndex())){
    BookInformation bookInfo = BookInfo.get(setIndex);
        if(!Title_field.getText().isEmpty() && Quantity_field.getText().isEmpty() ){
        bookInfo.info[1] = Title_field.getText();
        bookInfo.info[2] = bookInfo.getQty();
        }
        else if(!Quantity_field.getText().isEmpty() && Title_field.getText().isEmpty()){
        bookInfo.info[1] = bookInfo.getTitle();
        bookInfo.info[2] = Quantity_field.getText();
        }
         dt.setRowCount(0);
        for(int i = 0; i < BookInfo.size(); i++){
         BookInformation book = BookInfo.get(i);
        Object Books[] = new Object[] {book.getIndex(),book.getTitle(),book.getQty()};
        dt.addRow(Books);
    }
    }
    clear();
}

    void total(){
    int totals = 0;
    DefaultTableModel dt = (DefaultTableModel)Table.getModel();
        for(int x = 0 ; x < dt.getRowCount(); x++){
            String quantity = BookInfo.get(x).info[2];
            int total = Integer.parseInt(quantity);
            totals += total;
            
        }JOptionPane.showMessageDialog(null,"Total Number of books in system is: " + BookInfo.size()+"\n Total Quantity of Books on Storage is: "+ totals );
    }
    
void sort() { 
    snapshot();
    DefaultTableModel dt = (DefaultTableModel) Table.getModel();   
    if(Sort_Ascen.isSelected()){
        int n = BookInfo.size();
        for(int i = 1; i < n; i++){
            BookInformation current = BookInfo.get(i);
        int key = Integer.parseInt(current.getIndex());
        int j = i-1;
        while((j>= 0) && (Integer.parseInt(BookInfo.get(j).getIndex()) > key)){
            BookInfo.set(j+1,BookInfo.get(j));
            j--;
         
        }
        BookInfo.set(j+1,current);
        }
        }
    else if(Sort_Desc.isSelected()){
        int n = BookInfo.size();
        for(int i = 1; i < n; i++){
            BookInformation current = BookInfo.get(i);
        int key = Integer.parseInt(current.getIndex());
        int j = i-1;
        while((j>= 0) && (Integer.parseInt(BookInfo.get(j).getIndex()) < key)){
            BookInfo.set(j+1,BookInfo.get(j));
            j--;
        
        }
        BookInfo.set(j+1,current);
        }
}
    else if(Sort_Alpha.isSelected()){
                int n = BookInfo.size();
        for(int i = 1; i < n; i++){
            BookInformation current = BookInfo.get(i);
        int j = i-1;
        String Title = BookInfo.get(j).getTitle();
        while((j>= 0) && Title.compareTo(current.getTitle()) > 0){
            BookInfo.set(j+1,BookInfo.get(j));
            j--;
        
        }
        BookInfo.set(j+1,current);
        }
    }
         dt.setRowCount(0);
    for(int i = 0; i < BookInfo.size(); i++){
        BookInformation bookInfo = BookInfo.get(i);
        bookInfo.info[0] = String.valueOf(i);
        Object Books[] = new Object[] {bookInfo.getIndex(),bookInfo.getTitle(),bookInfo.getQty()};
        dt.addRow(Books);
    }
    }

    
    void undo(){ 
    if (!arrayHistory.isEmpty()) {
        redoStack.push((ArrayList)BookInfo.clone());
        DefaultTableModel dt = (DefaultTableModel) Table.getModel(); 
        ArrayList<BookInformation> history = arrayHistory.pop();
        dt.setRowCount(0);
        for(BookInformation book: history){
            Object Books[] = new Object[] {book.getIndex(),book.getTitle(),book.getQty()};
            dt.addRow(Books);
        }
        BookInfo = history;
    } else {
        JOptionPane.showMessageDialog(null, "No history to undo");
    }
}
}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Table = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        Index_field = new javax.swing.JTextPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        Title_field = new javax.swing.JTextPane();
        jScrollPane4 = new javax.swing.JScrollPane();
        Quantity_field = new javax.swing.JTextPane();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        ADD_BTN = new javax.swing.JButton();
        UNDO_BTN = new javax.swing.JButton();
        SEARCH_BTN = new javax.swing.JButton();
        REMOVE_BTN = new javax.swing.JButton();
        UPDATE_BTN = new javax.swing.JButton();
        SORT_BTN = new javax.swing.JButton();
        TOTAL_BTN = new javax.swing.JButton();
        Sort_Ascen = new javax.swing.JRadioButton();
        Sort_Desc = new javax.swing.JRadioButton();
        Sort_Alpha = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        Table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Index", "Title", "Quantity"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(Table);

        jScrollPane2.setFont(new java.awt.Font("Yu Gothic UI", 0, 48)); // NOI18N

        Index_field.setFont(new java.awt.Font("Yu Gothic UI", 0, 36)); // NOI18N
        jScrollPane2.setViewportView(Index_field);

        jScrollPane3.setFont(new java.awt.Font("Yu Gothic UI", 0, 48)); // NOI18N

        Title_field.setFont(new java.awt.Font("Yu Gothic UI", 0, 36)); // NOI18N
        jScrollPane3.setViewportView(Title_field);

        jScrollPane4.setFont(new java.awt.Font("Yu Gothic UI", 0, 48)); // NOI18N

        Quantity_field.setFont(new java.awt.Font("Yu Gothic UI", 0, 36)); // NOI18N
        jScrollPane4.setViewportView(Quantity_field);

        jLabel1.setFont(new java.awt.Font("Yu Gothic UI", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("INDEX");

        jLabel2.setFont(new java.awt.Font("Yu Gothic UI", 0, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("TITLE");

        jLabel3.setFont(new java.awt.Font("Yu Gothic UI", 0, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("QUANTITY");

        ADD_BTN.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        ADD_BTN.setText("ADD");
        ADD_BTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ADD_BTNActionPerformed(evt);
            }
        });

        UNDO_BTN.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        UNDO_BTN.setText("UNDO");
        UNDO_BTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UNDO_BTNActionPerformed(evt);
            }
        });

        SEARCH_BTN.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        SEARCH_BTN.setText("SEARCH");
        SEARCH_BTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SEARCH_BTNActionPerformed(evt);
            }
        });

        REMOVE_BTN.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        REMOVE_BTN.setText("REMOVE");
        REMOVE_BTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                REMOVE_BTNActionPerformed(evt);
            }
        });

        UPDATE_BTN.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        UPDATE_BTN.setText("UPDATE");
        UPDATE_BTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UPDATE_BTNActionPerformed(evt);
            }
        });

        SORT_BTN.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        SORT_BTN.setText("SORT");
        SORT_BTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SORT_BTNActionPerformed(evt);
            }
        });

        TOTAL_BTN.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        TOTAL_BTN.setText("TOTAL");
        TOTAL_BTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TOTAL_BTNActionPerformed(evt);
            }
        });

        buttonGroup1.add(Sort_Ascen);
        Sort_Ascen.setText("ASCENDING");

        buttonGroup1.add(Sort_Desc);
        Sort_Desc.setText("DESCENDING");

        buttonGroup1.add(Sort_Alpha);
        Sort_Alpha.setText("ALPHABETICALLY");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(55, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 583, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 55, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel3)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(TOTAL_BTN, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ADD_BTN, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(SEARCH_BTN, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(UPDATE_BTN, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(REMOVE_BTN))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(106, 106, 106)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(UNDO_BTN, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(SORT_BTN, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Sort_Alpha)
                    .addComponent(Sort_Ascen)
                    .addComponent(Sort_Desc))
                .addGap(33, 33, 33))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 28, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(Sort_Ascen)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Sort_Desc)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Sort_Alpha)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(44, 44, 44)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ADD_BTN)
                    .addComponent(UPDATE_BTN)
                    .addComponent(REMOVE_BTN))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SEARCH_BTN)
                    .addComponent(UNDO_BTN))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(TOTAL_BTN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(SORT_BTN))
                .addGap(35, 35, 35))
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents
        BookModification book = new BookMod();
    private void ADD_BTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ADD_BTNActionPerformed
        book.snapshot();
        book.add();
    }//GEN-LAST:event_ADD_BTNActionPerformed

    private void UNDO_BTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UNDO_BTNActionPerformed
        book.undo();   
    }//GEN-LAST:event_UNDO_BTNActionPerformed

    private void SEARCH_BTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SEARCH_BTNActionPerformed
        book.get();
    }//GEN-LAST:event_SEARCH_BTNActionPerformed

    private void REMOVE_BTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_REMOVE_BTNActionPerformed
        book.snapshot();
        book.remove();
    }//GEN-LAST:event_REMOVE_BTNActionPerformed

    private void UPDATE_BTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UPDATE_BTNActionPerformed
        book.snapshot();
        book.update();
    }//GEN-LAST:event_UPDATE_BTNActionPerformed

    private void SORT_BTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SORT_BTNActionPerformed
        book.snapshot();
        book.sort();
    }//GEN-LAST:event_SORT_BTNActionPerformed

    private void TOTAL_BTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TOTAL_BTNActionPerformed
        book.total();
    }//GEN-LAST:event_TOTAL_BTNActionPerformed

    public static void main(String args[]) {
        

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LibSys().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ADD_BTN;
    private javax.swing.JTextPane Index_field;
    private javax.swing.JTextPane Quantity_field;
    private javax.swing.JButton REMOVE_BTN;
    private javax.swing.JButton SEARCH_BTN;
    private javax.swing.JButton SORT_BTN;
    private javax.swing.JRadioButton Sort_Alpha;
    private javax.swing.JRadioButton Sort_Ascen;
    private javax.swing.JRadioButton Sort_Desc;
    private javax.swing.JButton TOTAL_BTN;
    private javax.swing.JTable Table;
    private javax.swing.JTextPane Title_field;
    private javax.swing.JButton UNDO_BTN;
    private javax.swing.JButton UPDATE_BTN;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    // End of variables declaration//GEN-END:variables
}
