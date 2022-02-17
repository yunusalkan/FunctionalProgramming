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
        List<String> namesOfOlderThan30 =
        createPerson().stream()
                .filter(p -> p.getAge() > 30)
                .map(Person::getName)
                .map(String::toUpperCase)
                .reduce(new ArrayList<String>(),
                        (names, name) -> {
                            names.add(name);
                            return names;
                        },
                        (names1, names2) -> {
                            names1.addAll(names2);
                            return names1;
                        }
                       );
        System.out.println(namesOfOlderThan30);

        // or use collectors
        namesOfOlderThan30 = createPerson().parallelStream()
                .filter(p -> p.getAge() > 30)
                .map(Person::getName)
                .map(String::toUpperCase)
                .collect(Collectors.toList());

        System.out.println(namesOfOlderThan30);

        // to create map from list...
        System.out.println(createPerson().stream()
                //.collect(Collectors.toMap(person -> person.getName(), person -> person.getAge()))
                .collect(Collectors.toMap(Person::getName, Person::getAge))
        );

        // to create immutable list
        List<Integer> ages = createPerson().stream()
                .map(Person::getAge)
                .collect(Collectors.toUnmodifiableList());

        // This line throws RuntimeException beacouse of ages is immutable and so can't be changed
        // ages.add(12);

        System.out.println(ages);

    }

    public static void printlnPersonWhoOlderThan30WithOldStreamWay() {
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
        System.out.println(createPerson().stream()
                            .filter(person -> person.getAge() > 30));
    }

    public static void printlnPersonWhoOlderThan30WithComma() {
        System.out.println(createPerson().stream()
                            .filter(person -> person.getAge() > 30)
                            .map(Person::getName)
                            .map(String::toUpperCase)
                            .collect(Collectors.joining(", ")));
    }
}
