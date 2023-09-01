package TelephoneFileDirectory;

import javax.swing.*;

import TelephoneFileDirectory.DirectoryManager;
import TelephoneFileDirectory.ScreenLoading;
import TelephoneFileDirectory.TelephoneDirectory;
import TelephoneFileDirectory.TelephoneDirectoryEntry;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class TelephoneDirectory extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private ArrayList<TelephoneDirectoryEntry> directory;
    private static final String FILE_PATH = "telephone_directory.txt";

    private JTextArea displayArea;  //display the output of insert and deletion in the file
    private JTextField nameField, addressField, phoneField;    //field for getting the information
    private JButton insertButton, deleteButton;   //insertion and deletion button
    
    private JButton refreshButton; // View All Records and Refresh buttons
    private DirectoryManager directoryManager;

    //consctructor for the Telephone directory
    public TelephoneDirectory() {
        directory = new ArrayList<>();   //initialize an array list
        
        directoryManager = new DirectoryManager();

        //basic info for the panel directory
        setTitle("Telephone Directory");
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        //configuration for the display area
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);
        add(scrollPane, BorderLayout.CENTER);

        //creating main panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(4, 2));
        inputPanel.add(new JLabel("Name (Last, First, Middle):"));

        //configuration for nameTextField
        nameField = new JTextField();
        nameField.setBackground(new Color(135, 206, 235));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Address:"));

        //configuration for addressTextField
        addressField = new JTextField();
        addressField.setBackground(new Color(135, 206, 235));
        inputPanel.add(addressField);
        inputPanel.add(new JLabel("Phone Number:"));

        //configuration for phoneField
        phoneField = new JTextField();
        phoneField.setBackground(new Color(135, 206, 235));
        inputPanel.add(phoneField);
        inputPanel.setBackground(new Color(135, 206, 235));

        //insert button
        insertButton = new JButton("I - Insert");
        insertButton.addActionListener(this);

        //delete button
        deleteButton = new JButton("D - Delete");
        deleteButton.addActionListener(this);

        //adding insert and delete buttons to a sub-panel
        JPanel insertDeletePanel = new JPanel();
        insertDeletePanel.setLayout(new GridLayout(1, 2));
        insertDeletePanel.add(insertButton);
        insertDeletePanel.add(deleteButton);

       
        //refresh button
        refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(this);

        //adding refresh buttons to another sub-panel
        JPanel viewRefreshPanel = new JPanel();
        viewRefreshPanel.setLayout(new GridLayout(1, 2));
        viewRefreshPanel.add(refreshButton);

        //adding both sub-panels to the main input panel
        inputPanel.add(insertDeletePanel);
        inputPanel.add(viewRefreshPanel);
        
        
        add(inputPanel, BorderLayout.SOUTH);

        //load the directory from the file on startup
        loadDirectoryFromFile();
        
    }
    
    

    @Override
    public void actionPerformed(ActionEvent e) {
    	if (e.getSource() == insertButton) {
            String name = nameField.getText().trim();
            String address = addressField.getText().trim();
            String phone = phoneField.getText().trim();

            //validation for insertion 
            if (!name.isEmpty() && !address.isEmpty() && !phone.isEmpty()) {
                directory.add(new TelephoneDirectoryEntry(name, address, phone));
                updateDisplay();
                clearFields();
                saveDirectoryToFile(); //save the updated directory to the file
                JOptionPane.showMessageDialog(this, "Data inserted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Please fill all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } 
    	
    	//validation for deletion
    	else if (e.getSource() == deleteButton) {
            String nameToDelete = nameField.getText().trim();
            if (!nameToDelete.isEmpty()) {
                boolean removed = directory.removeIf(entry -> ((String) entry.name).equalsIgnoreCase(nameToDelete));
                updateDisplay();
                clearFields();
                saveDirectoryToFile(); //save the updated directory to the file
                
                if (removed) {
                    JOptionPane.showMessageDialog(this, "Record deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Name not found in the directory.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please enter the name to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    	 
       else if (e.getSource() == refreshButton) {
	    	directoryManager.refreshDirectory(this);
	    	
	    	//reload the directory data from the file
	        loadDirectoryFromFile();
	        

	        //update the display with the loaded data
	        updateDisplay();
	        clearFields();
	        JOptionPane.showMessageDialog(this, "Directory refreshed!", "Success", JOptionPane.INFORMATION_MESSAGE);
	        
	    }
    }
    
    

    //method for loading the directory
    private void loadDirectoryFromFile() {
    	directory.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 3) {
                    String name = parts[0].trim();
                    String address = parts[1].trim();
                    String phone = parts[2].trim();
                    directory.add(new TelephoneDirectoryEntry(name, address, phone));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //method for saving records into a file
    private void saveDirectoryToFile() {
    	 try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
    	        //write the headings to the file
    	        writer.write("Name\t\t\tAddress\t\tPhone\t\n");

    	        //write the individual entries to the file
    	        for (TelephoneDirectoryEntry entry : directory) {
    	            writer.write(entry.toString() + "\n");
    	        }
    	    } catch (IOException e) {
    	        e.printStackTrace();
    	    }
    }

    //method for updateing the display from the inputs
    private void updateDisplay() {
        Collections.sort(directory, Comparator.comparing(entry -> entry.name.toLowerCase()));
        StringBuilder sb = new StringBuilder();
        sb.append("Name\t\t\tAddress\t\tPhone\t\n");
        for (TelephoneDirectoryEntry entry : directory) {
            sb.append(entry.toString()).append("\n");
        }
        displayArea.setText(sb.toString());
    }

    //method for clear the textfields
    private void clearFields() {
        nameField.setText("");
        addressField.setText("");
        phoneField.setText("");
    }
    

    //main method
    public static void main(String[] args){
    	SwingUtilities.invokeLater(() -> {

            //initialize the loading class
            ScreenLoading loading = new ScreenLoading();
            loading.setVisible(true);

            loading.startLoading(() -> {

                //initialize the TelephoneDirectory
                TelephoneDirectory gui = new TelephoneDirectory();
                gui.setVisible(true);

                
            });
        });
    }

}
