package com.company;
import java.util.List;
import java.util.ArrayList;

public class PhoneBook {
    List<Contact> ContactList = new ArrayList<>();

    public void addNewContact(String name, String surname, TypeOfContact type, String num) {
        Contact temp = new Contact(name, surname, type, num);
        if (!find_contact(name, surname))
            ContactList.add(temp);
        else
            throw new IllegalArgumentException("Contact with this name and surname already exists");

    }

    public List<Contact> findContact(String part) {
        List<Contact> found = new ArrayList<>();
        for (Contact temp : ContactList) {
            if (temp.getName().contains(part) || temp.getSurname().contains(part))
                found.add(temp);
        }
        return found;
    }

    public Contact findContact(String name, String surname) {
        for (Contact temp : ContactList) {
            if (temp.getName().equals(name) && temp.getSurname().equals(surname))
                return temp;
        }
        return null;
    }

    public boolean find_contact(String name, String surname) {
        for (Contact temp : ContactList) {
            if (temp.getName().equals(name) && temp.getSurname().equals(surname))
                return true;
        }
        return false;
    }

    public List<Contact> findNumbers(String part) {
        List<Contact> temporary = new ArrayList<>();
        for (Contact temp : ContactList) {
            temporary.add(temp.find(part));
        }
        return temporary;

    }

    public void deleteContact(String name, String surname) {

        Contact temp = findContact(name, surname);
        if (temp == null)
            throw new IllegalArgumentException("Contact with this name and surname does not exist");

        ContactList.remove(temp);
    }

    public void addNumber(String name, String surname, TypeOfContact type, String num) {
        Contact temp = findContact(name, surname);
        if (temp == null)
            throw new IllegalArgumentException("Contact with this name and surname does not exist");
        temp.addNumber(type, num);
    }

    public void editName(String oldName, String oldSurname, String newName, String newSurname) {
        Contact temp = findContact(oldName, oldSurname);
        if (temp == null)
            throw new IllegalArgumentException("Contact with this name and surname does not exist");
        temp.setName(newName, newSurname);
    }

    public void addAll(List<Contact> added) {
        ContactList = new ArrayList<>(added);
    }

    public void deleteNumber(String num) {
        boolean wasdeleted = false;
        for (Contact temp : ContactList) {
            if (temp.deleteNumber(num))
                wasdeleted = true;
        }
        if (!wasdeleted)
            throw new IllegalArgumentException("Contact with this number does not exist");
    }

    public List <Contact> getContactList() {
        return ContactList;
    }
}
