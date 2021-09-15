package major.adam;

import java.util.ArrayList;
import java.util.Arrays;

public class MobilePhone {
    ArrayList<Contact> contacts = new ArrayList<Contact>();

    public MobilePhone() {
    }

    public MobilePhone(ArrayList<Contact> contacts) {
        this.contacts = contacts;
    }

    public  void addContact(Contact contact) {
        contacts.add(contact);
    }

    public  void removeContact(String name) {
        int index = 0;
        for (Contact contactInList: contacts
        ) {
            boolean nameIsSame = name == contactInList.getName();
            if (nameIsSame) {
                break;
            }
            index++;
        }
        contacts.remove(index);
    }

    public  void removeContact(long phoneNumber) {
        int index = 0;
        for (Contact contactInList: contacts
        ) {
            boolean phoneNumberIsSame = phoneNumber == contactInList.getPhoneNumber();
            if (phoneNumberIsSame) {
                break;
            }
            index++;
        }
        contacts.remove(index);
    }

    public  void removeContact(Contact contact) {
        int index = 0;
        for (Contact contactInList: contacts
             ) {
            boolean nameIsSame = contact.getName() == contactInList.getName();
            boolean phoneNumberIsSame = contact.getPhoneNumber() == contactInList.getPhoneNumber();
            if (nameIsSame && phoneNumberIsSame) {
                break;
            }
            index++;
        }
        contacts.remove(index);
    }

    public ArrayList<Contact> getContacts() {
        return contacts;
    }

    public void printContacts() {
        System.out.println("");
        for (Contact contact: contacts
        ) {
            System.out.println("Name: " + contact.getName() + " and phone number: " + contact.getPhoneNumber());
        }
    }

    private boolean validPhoneNumber(long phoneNumber) {
        if (phoneNumber < 100000000L || phoneNumber > 9999999999L) return false;
        return true;
    }
}
