package TelephoneFileDirectory;

import javax.swing.*;

import TelephoneFileDirectory.TelephoneDirectoryEntry;

import java.io.*;
import java.util.*;

public class DirectoryManager {
	private ArrayList<TelephoneDirectoryEntry> directory;
    private static final String FILE_PATH = "telephone_directory.txt";

    //constructor
    public DirectoryManager() {
        directory = new ArrayList<>();
        loadDirectoryFromFile();
    }
    

    //refresh the directory
    public void refreshDirectory() {
        saveDirectoryToFile();
        JOptionPane.showMessageDialog(null, "Directory refreshed!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    //load the file from the directory
    private void loadDirectoryFromFile() {
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

    //save the inputs into the directory
    public void saveDirectoryToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            writer.write("Name\t\t\tAddress\t\tPhone\t\n");
            for (TelephoneDirectoryEntry entry : directory) {
                writer.write(entry.toString() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    //method if the directory is empty
    public boolean isDirectoryEmpty() {
        return directory.isEmpty();
    }


    //refresh confirmation
    public void refreshDirectory(JFrame frame) {
        int response = JOptionPane.showConfirmDialog(frame,
                "If you click refresh, the records will be overwritten. Do you wish to proceed?",
                "Confirm Refresh", JOptionPane.YES_NO_OPTION);

        if (response == JOptionPane.YES_OPTION) {
            saveDirectoryToFile();
            JOptionPane.showMessageDialog(frame, "Record is Refreshed", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
}
