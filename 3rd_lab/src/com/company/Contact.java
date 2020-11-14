package com.company;
import java.util.List;
import java.util.ArrayList;

public class Contact {

    private String name;
    private String surname;
    List <info> ListOfNumbers = new ArrayList<>();

    public Contact(String name, String surname, TypeOfContact type, String num) {
        this.name = name;
        this.surname = surname;
        info data = new info(type, num);
        ListOfNumbers.add(data);
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
        ListOfNumbers.add(data);
    }

    public boolean find (String part){
        boolean result = false;
        List <info> found = new ArrayList<>();
        found = findNumber(part);
        if (found.size() > 0) {
            result = true;
            found.forEach(info -> System.out.println(info.getNumber() + ' ' + name + ' ' + surname));
            return true;
        }
        return false;
    }

    public info findOne (String number) {
        for (info temp : ListOfNumbers)
            if (temp.getNumber().equals(number))
                return temp;
        return null;
    }

    public List findNumber(String part){
        List <info> found = new ArrayList<>();
        for (info looking : ListOfNumbers){
            if (looking.getNumber().contains(part)){
                found.add(looking);
            }
        }
        return found;
    }

    public boolean deleteNumber(String num){
        info deleting = findOne(num);
        if (deleting != null){
            ListOfNumbers.remove(deleting);
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
        for (info num : ListOfNumbers){
            System.out.println(num.toString());
        }
    }
}

 class info {
        private TypeOfContact type;
        private String number;

        info(TypeOfContact type, String number) {
            this.type = type;
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
         return ("Phone number: " + number + " Type of number: " + type);
     }
 }


enum TypeOfContact{
    HOME_PHONE,
    MOBILE_PHONE,
    WORK,
    HOME_EMAIL,
    WORK_EMAIL
}


