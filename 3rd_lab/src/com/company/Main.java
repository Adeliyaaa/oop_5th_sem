package com.company;

public class Main {

    public static void main(String[] args) {
    PhoneBook test = new PhoneBook();
    test.addNewContact("Maria", "Lugovaya", TypeOfContact.HOME_PHONE, "88559552806");
    test.addNumber("Maria", "Lugovaya", TypeOfContact.WORK, "837894479");
    test.addNumber("Maria", "Lugovaya", TypeOfContact.HOME_EMAIL, "maria0303@gmail.com");
    test.addNewContact("Rufina", "Talalaeva", TypeOfContact.MOBILE_PHONE, "89238393892");
    test.addNewContact("Regina", "Tavabilova", TypeOfContact.WORK, "8928394223");
    test.showAll();
    test.editName("Rufina", "Talalaeva", "Rufina", "Rizatdinova");
    test.deleteContact("Regina", "Tavabilova");
    test.addNewContact("Olga", "Chernuhina", TypeOfContact.MOBILE_PHONE, "8989239883");
    test.deleteNumber("837894479");
    test.findNumbers("80909090");
    /* testing for an exception to come out
    test.editName("Anastasia", "Zhukova", "Ekaterina", "Varnava");
     */
    test.findContact("in");
    test.showAll();
    }

}

