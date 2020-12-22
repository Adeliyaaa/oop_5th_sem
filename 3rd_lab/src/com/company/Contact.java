package com.company;
import java.util.List;
import java.util.ArrayList;

public class Contact {

    private String name;
    private String surname;
    List <info> ListOfContactInfo;

    public Contact(String name, String surname, TypeOfContact type, String num) {
        this.name = name;
        this.surname = surname;
        this.ListOfContactInfo = new ArrayList<>();
        info data = new info(type, num);
        ListOfContactInfo.add(data);
    }

    public Contact (String name, String surname, List <info> ListOfContactInfo){
        this.name = name;
        this.surname = surname;
        this.ListOfContactInfo = new ArrayList<>(ListOfContactInfo);
    }

    public String getName() {
        return name;
    }

    public String getSurname(){
        return surname;
    }

    public void setName(String newName, String newSurname) {
        name = newName;
        surname = newSurname;
    }

    public void addNumber(TypeOfContact type, String num) {
        info data = new info(type, num);
        ListOfContactInfo.add(data);
    }

    public Contact find (String part){
        List <info> found = new ArrayList<>();
        found = findNumber(part);
        Contact temporary;
        if (found.size() > 0){
            temporary = new Contact(name, surname, found);
            return temporary;
        }
        else
            return null;
    }

    public info findOne (String number) {
        for (info temp : ListOfContactInfo)
            if (temp.getNumber().equals(number))
                return temp;
        return null;
    }

    public List <info> findNumber(String part){
        List <info> found = new ArrayList<>();
        for (info looking : ListOfContactInfo){
            if (looking.getNumber().contains(part)){
                found.add(looking);
            }
        }
        return found;
    }

    public boolean deleteNumber(String num){
        info deleting = findOne(num);
        if (deleting != null){
            ListOfContactInfo.remove(deleting);
            return true;
        }

        else
            return false;
    }

    public void editNumber(String oldNumber, String newNumber){
        info temp = findOne(oldNumber);
        if (temp != null){
            temp.setNumber(newNumber);
        }
        else
            throw new IllegalArgumentException("Contact with this number does not exist");

    }

    @Override
    public String toString() {
        return name + ' ' + surname + ' ' + ListOfContactInfo.toString();
    }
}

class info {
    private final TypeOfContact type;
    private String number;

    info(TypeOfContact type, String number) {
        this.type = type;
        if ((type == TypeOfContact.TG_ALIAS || type == TypeOfContact.VK_ALIAS) && number.charAt(0) != '@')
            throw new IllegalArgumentException("An undeclared contact type was entered");
        if (type == TypeOfContact.EMAIL && !number.contains("@"))
            throw new IllegalArgumentException("An undeclared contact type was entered");
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String newNumber){
        this.number = newNumber;
    }

    @Override
    public String toString() {
        return ("Info: " + number + " Type: " + type);
    }
}


enum TypeOfContact{
    HOME_PHONE,
    MOBILE_PHONE,
    WORK,
    EMAIL,
    TG_ALIAS,
    VK_ALIAS
}