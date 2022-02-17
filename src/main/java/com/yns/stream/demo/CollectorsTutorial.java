package com.yns.stream.demo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CollectorsTutorial {

    @AllArgsConstructor
    @Data
    static class Person {
        private String name;
        private int age;
    }

    static List<Person> createPerson() {
      return Arrays.asList(
          new Person("Sara", 20),
          new Person("Nancy", 22),
          new Person("Bob", 20),
          new Person("Paula", 32),
          new Person("Paul", 32),
          new Person("Jack", 3),
          new Person("Bill", 72),
          new Person("Jill", 11)
      );
    }

    public static void main(String[] args) {

        printlnPersonWhoOlderThan30WithCollectionWay();

        printlnPersonWhoOlderThan30WithOldStreamWay();

        printlnPersonWhoOlderThan30WithComma();

        printlnCollectedPersonMap();

        printlnImmutablePersonList();

    }

    public static void printlnPersonWhoOlderThan30WithOldStreamWay() {
        System.out.println("printlnPersonWhoOlderThan30WithOldStreamWay");
        System.out.println(createPerson().stream()
                           .filter(person -> person.getAge() > 30)
                           .map(Person::getName)
                           .map(String::toUpperCase)
                           .reduce(new ArrayList<String>(),
                                   (names, name) -> {
                                       names.add(name);
                                       return names;
                                   },
                                   (nameList, names) -> {
                                      nameList.addAll(names);
                                      return nameList;
                                   })

        );
    }

    public static void printlnPersonWhoOlderThan30WithCollectionWay() {
        System.out.println("printlnPersonWhoOlderThan30WithCollectionWay");
        System.out.println(createPerson().stream()
                            .filter(person -> person.getAge() > 30)
                            .map(Person::getName)
                            .map(String::toUpperCase)
                            .collect(Collectors.toList()));
    }

    public static void printlnPersonWhoOlderThan30WithComma() {
        System.out.println("printlnPersonWhoOlderThan30WithComma");
        System.out.println(createPerson().stream()
                            .filter(person -> person.getAge() > 30)
                            .map(Person::getName)
                            .map(String::toUpperCase)
                            .collect(Collectors.joining(", ")));
    }

    public static void printlnCollectedPersonMap() {
        System.out.println("printlnCollectedPersonMap");
        System.out.println(createPerson().stream()
                            // other way
                            //.collect(Collectors.toMap(person -> person.getName(), person -> person.getAge()))
                            .collect(Collectors.toMap(Person::getName, Person::getAge)));
    }

    public static void printlnImmutablePersonList() {
        List<Integer> personList = createPerson().stream()
                                    .map(Person::getAge)
                                    .collect(Collectors.toUnmodifiableList());
        System.out.println("printlnImmutablePersonList");
        System.out.println(personList);
        //If we add personList.add(anyInteger) we'l get runtimeException beacouse of our list is unmodifiable
        //personList.add(25);
    }
}
