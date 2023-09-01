package TelephoneFileDirectory;

public class TelephoneDirectoryEntry {
	//basic information of the user
		String name;
	    String address;
	    String phone;

	    //constructor 
	    TelephoneDirectoryEntry(String name, String address, String phone) {
	        this.name = name;
	        this.address = address;
	        this.phone = phone;
	    }
	    
	    public String getName() {
	        return name;
	    }

	    //output to the program
	    @Override
	    public String toString() {
	        return name + "\t\t" + address + "\t\t" + phone;
	    }
}
