package com.company;
import java.util.List;
import java.util.ArrayList;

public class Contact {

    private String name;
    private String surname;
    List <info> ListOfContactInfo = new ArrayList<>();

    public Contact(String name, String surname, TypeOfContact type, String num) {
        this.name = name;
        this.surname = surname;
        info data = new info(type, num);
        ListOfContactInfo.add(data);
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

    public boolean find (String part){
        List <info> found = new ArrayList<>();
        found = findNumber(part);
        if (found.size() > 0) {
            found.forEach(info -> System.out.println(info.getNumber() + ' ' + name + ' ' + surname));
            return true;
        }
        return false;
    }

    public info findOne (String number) {
        for (info temp : ListOfContactInfo)
            if (temp.getNumber().equals(number))
                return temp;
        return null;
    }

    public List findNumber(String part){
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
            System.out.println("The number " + num + " was deleted from the contact of " + name + ' ' + surname);
            return true;
        }
        else
            return false;
    }

    public void editNumber(String oldNumber, String newNumber){
        info temp = findOne(oldNumber);
        if (temp != null){
            temp.setNumber(newNumber);
            System.out.println("This number " + oldNumber + " was edited to " + newNumber);
        }
        else
            throw new IllegalArgumentException("Contact with this number does not exist");

    }

    public void showEverything(){
        System.out.println("Contact name: " + name + ' ' + surname);
        showAllNumbers();
    }

    public void showAllNumbers(){
        System.out.println("All the numbers of " + name + ' ' + surname);
        for (info num : ListOfContactInfo){
            System.out.println(num.toString());
        }
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
         return ("Contact info: " + number + " Type of contact info: " + type);
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


