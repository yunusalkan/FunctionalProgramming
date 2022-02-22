package com.yns.stream.demo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.*;


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

    static List<Person> createContainingRepeatedNamesPerson() {
        return  List.of(
                new Person("Sara", 20),
                new Person("Sara", 22),
                new Person("Bob", 20),
                new Person("Paula", 32),
                new Person("Paul", 32),
                new Person("Jack", 3),
                new Person("Jack", 72),
                new Person("Jill", 11));
    }

    public static void main(String[] args) {

        printlnPersonWhoOlderThan30WithCollectionWay();

        printlnPersonWhoOlderThan30WithOldStreamWay();

        printlnPersonWhoOlderThan30WithComma();

        printlnCollectedPersonMap();

        printlnImmutablePersonList();

        printlnOddAndEvenAgedPersonWithCustomList();

        printlnGroupedPersonByName();

        printlnGroupedAgesByName();

        printlnCountOfNamesOfOccurencePersonList();

        printlnPersonWhoMaxMinBy();

        printlnAllPersonNamesCharsUsingFlatMap();

        printlnAllUniqueCharsOfPersonNamesGroupingByAgeUsingFlatMap();

    }

    private static void printlnGroupedPersonByName() {
        Map<String, List<Person>> byName = createContainingRepeatedNamesPerson().stream()
                                            .collect(Collectors.groupingBy(Person::getName));
        System.out.println("printlnGroupedPersonByName");
        System.out.println(byName);
        System.out.println();

    }

    private static void printlnGroupedAgesByName() {
        Map<String, List<Integer>> ageByName = createContainingRepeatedNamesPerson().stream()
                .collect(Collectors.groupingBy(Person::getName, mapping(Person::getAge, toList())));
        System.out.println("printlnGroupedPersonByName");
        System.out.println(ageByName);
        System.out.println();

    }

    private static void printlnOddAndEvenAgedPersonWithCustomList() {
        System.out.println("printlnOddAndEvenAgedPersonWithCustomList");
        System.out.println(
                createContainingRepeatedNamesPerson().stream()
               .collect(Collectors.partitioningBy(person -> person.getAge() % 2 == 0)));

        final Map<Boolean, List<Person>> collect = createContainingRepeatedNamesPerson().stream()
                .collect(Collectors.partitioningBy(person -> person.getAge() % 2 == 0));
        System.out.println();
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
        System.out.println();
    }

    public static void printlnPersonWhoOlderThan30WithCollectionWay() {
        System.out.println("printlnPersonWhoOlderThan30WithCollectionWay");
        System.out.println(createPerson().stream()
                            .filter(person -> person.getAge() > 30)
                            .map(Person::getName)
                            .map(String::toUpperCase)
                            .collect(toList()));
        System.out.println();
    }

    public static void printlnPersonWhoOlderThan30WithComma() {
        System.out.println("printlnPersonWhoOlderThan30WithComma");
        System.out.println(createPerson().stream()
                            .filter(person -> person.getAge() > 30)
                            .map(Person::getName)
                            .map(String::toUpperCase)
                            .collect(Collectors.joining(", ")));
        System.out.println();
    }

    public static void printlnCollectedPersonMap() {
        System.out.println("printlnCollectedPersonMap");
        System.out.println(createPerson().stream()
                            // other way
                            //.collect(Collectors.toMap(person -> person.getName(), person -> person.getAge()))
                            .collect(Collectors.toMap(Person::getName, Person::getAge)));
        System.out.println();
    }

    public static void printlnImmutablePersonList() {
        List<Integer> personList = createPerson().stream()
                                    .map(Person::getAge)
                                    .collect(Collectors.toUnmodifiableList());
        System.out.println("printlnImmutablePersonList");
        System.out.println(personList);
        //If we add personList.add(anyInteger) we'l get runtimeException beacouse of our list is unmodifiable
        //personList.add(25);
        System.out.println();
    }

    public static void printlnCountOfNamesOfOccurencePersonList() {

        Map<String, Long> repeatedNameCountMap = createContainingRepeatedNamesPerson().stream()
                                                .collect(groupingBy(Person::getName, counting()));

        System.out.println("printlnCountOfNamesOfOccurencePersonList");
        System.out.println(repeatedNameCountMap);
        System.out.println();

        Function<Long, Integer> intConverter = (number) -> number.intValue() * 2;
        //If we don't want to use Long type, instead of using Integer, we can first collect and then count
        Map<String, Integer> repeatedNamesCountByInteger = createContainingRepeatedNamesPerson().stream()
                                                            .collect(groupingBy(Person::getName, collectingAndThen(counting(), (number) -> number.intValue() * 2)));
        System.out.println("printlnCountOfNamesOfOccurencePersonListInteger");
        System.out.println(repeatedNamesCountByInteger);
        System.out.println();

    }

    private static void printlnPersonWhoMaxMinBy() {
        System.out.println("printlnPersonWhoMaxByAge");
        System.out.println(createContainingRepeatedNamesPerson().stream()
                .collect(maxBy(comparing(Person::getAge))));

        System.out.println("printlnPersonWhoMinByAge");
        System.out.println(createContainingRepeatedNamesPerson().stream()
                .collect(minBy(comparing(Person::getAge))));

        System.out.println("printlnPersonNameWhoMinByAge");
        String personName = createContainingRepeatedNamesPerson().stream().
                        collect(collectingAndThen(maxBy(comparing(Person::getAge)),
                                person -> person.map(Person::getName).orElse("")));
        System.out.println(personName);
        System.out.println();
    }

    private static void printlnGroupingAndFilteringPerson() {

        System.out.println("printlnGroupingAndFilteringPerson");
        System.out.println(createPerson().stream()
                            .collect(groupingBy(Person::getAge,
                                                mapping(Person::getName,
                                                        filtering(name -> name.length() > 4,
                                                        toList())))));
        System.out.println();

    }

    private static void printlnAllPersonNamesCharsUsingFlatMap() {
        //flatMap converts all objects like list, set, map vs etc. to single object
        System.out.println("printlnAllPersonNamesCharsUsingFlatMap");
        System.out.println(createPerson().stream()
                .map(Person::getName)
                .flatMap(name -> Stream.of(name.split(""))).collect(toList()));
        System.out.println();
    }

    private static void printlnAllUniqueCharsOfPersonNamesGroupingByAgeUsingFlatMap() {
        System.out.println("printlnAllUniqueCharsOfPersonNamesGroupingByAgeUsingFlatMap");
        System.out.println(
                createPerson().stream()
                        .collect(groupingBy(Person::getAge,
                                mapping(person -> person.getName().toUpperCase(Locale.ROOT),
                                        flatMapping(name -> Stream.of(name.split("")),
                                                toSet()))))
        );
        System.out.println();
    }

}
