package major.adam;

public class Contact {
    private String name;
    private long phoneNumber;

    public Contact(String name, long phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public static Contact createContact(String name, long phoneNumber) {
        return new Contact(name, phoneNumber);
    }
}
