//CONTACT Class

package fr.epita;

public class Contact {

//    name,last_name,company_name,address,city,county,state,zip,phone1,phone,email
    private String name;
    private String last_name;
    private String company_name;
    private String address;
    private String city;
    private String country;
    private String state;
    private String zip;
    private String phone1;
    private String phone;
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}




//CONTACT CSV READER Class
//The delimiter in the csv file is ','
package fr.epita;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ContactReader {

    public List<Contact> readAllLines() throws IOException {

        String line = "";
        String[] parts = {};
        List<Contact> contacts = new ArrayList<>();

        Contact contact = new Contact();
        File file = new File("src/main/resources/17-contacts.csv");
        Scanner scanner = new Scanner(file);

        scanner.nextLine();
        while (scanner.hasNext()){
            line = scanner.nextLine();
            parts = line.split(",");

            contact.setName(parts[0]);
            contact.setLast_name(parts[1]);
            contact.setCompany_name(parts[2]);
            contact.setAddress(parts[3]);
            contact.setCity(parts[4]);
            contact.setCountry(parts[5]);
            contact.setState(parts[6]);
            contact.setZip(parts[7]);
            contact.setPhone1(parts[8]);
            contact.setPhone(parts[9]);
            contact.setEmail(parts[10]);
            contacts.add(contact);
            System.out.println(line);
        }
        return contacts;
    }

}






//MAIN Class
import fr.epita.ContactReader;

import java.io.IOException;

public class TestMVN2 {
    public static void main(String[] args) throws IOException {

        ContactReader contactReader = new ContactReader();
        contactReader.readAllLines();
    }
    
}

