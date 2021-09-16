package major.adam;

import java.util.ArrayList;

public class MobilePhone {
    ArrayList<Contact> contacts = new ArrayList<Contact>();

    public MobilePhone() {
    }

    public MobilePhone(ArrayList<Contact> contacts) {
        this.contacts = contacts;
    }

    public  boolean addContact(Contact contact) {
        return addContact(contact.getName(), contact.getPhoneNumber());
    }

    public  boolean addContact(String name, long phoneNumber) {
        boolean shouldAdd = true;

        if (!validPhoneNumber(phoneNumber)) System.out.println("Invalid Phone Number...");

        for (Contact contactInList: contacts
        ) {
            boolean nameIsSame = name == contactInList.getName();
            boolean phoneNumberIsSame = phoneNumber == contactInList.getPhoneNumber();

            if (nameIsSame && phoneNumberIsSame) {
                shouldAdd = false;
                break;
            }
        }
        if (shouldAdd) {
           contacts.add(Contact.createContact(name, phoneNumber));
        }
        return shouldAdd;
    }

    public  boolean removeContact(String name) {
        int index = 0;
        boolean shouldRemove = false;

        for (Contact contactInList: contacts
        ) {
            boolean nameIsSame = name == contactInList.getName();
            if (nameIsSame) {
                shouldRemove = true;
                break;
            }
            index++;
        }
        if (shouldRemove) contacts.remove(index);
        return shouldRemove;
    }

    public  boolean removeContact(long phoneNumber) {
        int index = 0;
        boolean shouldRemove = false;

        for (Contact contactInList: contacts
        ) {
            boolean phoneNumberIsSame = phoneNumber == contactInList.getPhoneNumber();
            if (phoneNumberIsSame) {
                shouldRemove = true;
                break;
            }
            index++;
        }
        if (shouldRemove) contacts.remove(index);
        return shouldRemove;
    }

    public  boolean removeContact(Contact contact) {
        int index = 0;

        boolean shouldRemove = false;
        for (Contact contactInList: contacts
             ) {
            boolean nameIsSame = contact.getName() == contactInList.getName();
            boolean phoneNumberIsSame = contact.getPhoneNumber() == contactInList.getPhoneNumber();
            if (nameIsSame && phoneNumberIsSame) {
                shouldRemove = true;
                break;
            }
            index++;

        }
        if (shouldRemove) contacts.remove(index);
        return shouldRemove;
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
