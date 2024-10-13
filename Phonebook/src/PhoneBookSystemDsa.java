import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.ListIterator;

// Contact class to hold name and phone number
class Contact {
    String name;
    String phoneNumber;

    // Constructor
    Contact(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }
}

// PhoneBook class that uses a LinkedList to manage contacts
class PhoneBook {
    private final LinkedList<Contact> contacts;

    // Constructor
    public PhoneBook() {
        contacts = new LinkedList<>();
    }

    // Add a new contact to the phonebook
    public void addContact(String name, String phoneNumber) {
        Contact newContact = new Contact(name, phoneNumber);
        contacts.add(newContact);
        JOptionPane.showMessageDialog(null, "Contact added: " + name + " - " + phoneNumber);
    }

    // Search for a contact to update
    public Contact searchForUpdate(String name) {
        for (Contact contact : contacts) {
            if (contact.name.equalsIgnoreCase(name)) {
                return contact;
            }
        }
        JOptionPane.showMessageDialog(null, "Contact not found: " + name);
        return null; // Contact not found
    }

    // Update contact details
    public void updateContact(String oldName, String newName, String newPhoneNumber) {
        Contact original = searchForUpdate(oldName);
        if (original == null) {
            return; // Exit method if contact is not found
        }

        original.name = newName;
        original.phoneNumber = newPhoneNumber;
        JOptionPane.showMessageDialog(null, "Contact updated: " + newName + " - " + newPhoneNumber);
    }

    // Delete a contact by name
    public void deleteContact(String name) {
        ListIterator<Contact> iterator = contacts.listIterator();
        while (iterator.hasNext()) {
            Contact current = iterator.next();
            if (current.name.equalsIgnoreCase(name)) {
                iterator.remove();
                JOptionPane.showMessageDialog(null, "Contact deleted: " + name);
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Contact not found: " + name);
    }

    // Search for a contact by name
    public String searchContact(String name) {
        for (Contact contact : contacts) {
            if (contact.name.equalsIgnoreCase(name)) {
                return "Contact found: " + contact.name + " - " + contact.phoneNumber;
            }
        }
        return "Contact not found: " + name;
    }

    // Display all contacts
    public String displayContacts() {
        if (contacts.isEmpty()) {
            return "Phonebook is empty.";
        }
        StringBuilder contactList = new StringBuilder("Phonebook contacts:\n");
        for (Contact contact : contacts) {
            contactList.append(contact.name).append(" - ").append(contact.phoneNumber).append("\n");
        }
        return contactList.toString();
    }
}

// Main class with GUI implementation
public class PhoneBookSystemDsa extends JFrame implements ActionListener {
    private final PhoneBook phoneBook;
    private final JTextArea outputArea;
    private final JTextField nameField, phoneField;

    public PhoneBookSystemDsa() {
        phoneBook = new PhoneBook();

        // Setting up the frame
        setTitle("PhoneBook Management System");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Output area
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        add(new JScrollPane(outputArea), BorderLayout.CENTER);

        // Input panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("Name:"), gbc);
        
        gbc.gridx = 1;
        nameField = new JTextField(15);
        inputPanel.add(nameField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("Phone Number:"), gbc);
        
        gbc.gridx = 1;
        phoneField = new JTextField(15);
        inputPanel.add(phoneField, gbc);
        
        // Buttons
        gbc.gridx = 0;
        gbc.gridy = 2;
        JButton addButton = new JButton("Add Contact");
        addButton.addActionListener(this);
        addButton.setActionCommand("add");
        inputPanel.add(addButton, gbc);

        gbc.gridx = 1;
        JButton deleteButton = new JButton("Delete Contact");
        deleteButton.addActionListener(this);
        deleteButton.setActionCommand("delete");
        inputPanel.add(deleteButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        JButton updateButton = new JButton("Update Contact");
        updateButton.addActionListener(this);
        updateButton.setActionCommand("update");
        inputPanel.add(updateButton, gbc);

        gbc.gridx = 1;
        JButton searchButton = new JButton("Search Contact");
        searchButton.addActionListener(this);
        searchButton.setActionCommand("search");
        inputPanel.add(searchButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        JButton displayButton = new JButton("Display All Contacts");
        displayButton.addActionListener(this);
        displayButton.setActionCommand("display");
        inputPanel.add(displayButton, gbc);

        add(inputPanel, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String name = nameField.getText().trim();
        String phoneNumber = phoneField.getText().trim();

        switch (e.getActionCommand()) {
            case "add":
                if (!name.isEmpty() && !phoneNumber.isEmpty()) {
                    phoneBook.addContact(name, phoneNumber);
                } else {
                    JOptionPane.showMessageDialog(this, "Please enter both name and phone number.");
                }
                break;

            case "delete":
                if (!name.isEmpty()) {
                    phoneBook.deleteContact(name);
                } else {
                    JOptionPane.showMessageDialog(this, "Please enter a name to delete.");
                }
                break;

            case "update":
                if (!name.isEmpty()) {
                    String newName = JOptionPane.showInputDialog("Enter new name for " + name + ":");
                    String newPhone = JOptionPane.showInputDialog("Enter new phone number for " + name + ":");
                    if (newName != null && newPhone != null) {
                        phoneBook.updateContact(name, newName.trim(), newPhone.trim());
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Please enter a name to update.");
                }
                break;

            case "search":
                String searchResult = phoneBook.searchContact(name);
                JOptionPane.showMessageDialog(this, searchResult);
                break;

            case "display":
                outputArea.setText(phoneBook.displayContacts());
                break;
        }

        // Clear input fields after action
        nameField.setText("");
        phoneField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PhoneBookSystemDsa gui = new PhoneBookSystemDsa();
            gui.setVisible(true);
        });
    }
}
