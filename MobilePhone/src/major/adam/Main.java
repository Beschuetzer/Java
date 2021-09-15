package major.adam;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        Contact contact1 = new Contact("Adam", 1231112222);
        Contact contact2 = new Contact("Tom", 1235);
        Contact contact3 = new Contact("Andrew", 1236);
        Contact contact4 = new Contact("Jen", 1237);
        MobilePhone mobilePhone = new MobilePhone();
        mobilePhone.addContact(contact1);
        mobilePhone.printContacts();
        mobilePhone.addContact(contact2);
        mobilePhone.printContacts();
        mobilePhone.addContact(contact3);
        mobilePhone.printContacts();
        mobilePhone.addContact(contact4);
        mobilePhone.printContacts();
        mobilePhone.removeContact(new Contact("Adam", 1234));
        mobilePhone.printContacts();
    }


}
