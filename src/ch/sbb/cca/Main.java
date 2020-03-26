package ch.sbb.cca;

import ch.sbb.cca.domain.Gender;
import ch.sbb.cca.domain.Person;
import ch.sbb.cca.domain.PersonBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        String filePath = "C:/GitProjects/processor26/data/MOCK_DATA-4.csv";
        System.out.println(String.format("processor26 %s", filePath));
        List<String> dataFromCSV = readDataFromCSV(filePath);
        List<Person> peopleList = convertDataToPerson(dataFromCSV);
        printPersonStatistics(peopleList);
    }

    /**
     * Read data from a comma separated file
     *
     * @param filePath String path to file
     * @return List of Strings
     */
    private static List<String> readDataFromCSV(String filePath) {
        List<String> data = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(filePath), "UTF-8")) {
            // readLines
            while (scanner.hasNextLine()) {
                data.add(scanner.nextLine());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }

        return data;
    }

    /**
     * Convert CSV-Strings to Person Object
     *
     * @param data List of Strings containing CSV Strings
     * @return List of Person Objects
     */
    private static List<Person> convertDataToPerson(List<String> data) {
        List<Person> personList = new ArrayList<>();

        data.remove(0);

        data.forEach(string -> {
            List<String> stringData = Arrays.asList(string.split(","));

            try {
                Person person = new PersonBuilder()
                        .setId(Integer.parseInt(stringData.get(0)))
                        .setFirstName(stringData.get(1))
                        .setLastName(stringData.get(2))
                        .seteMail(stringData.get(3))
                        .setGender(Gender.valueOf(stringData.get(4).toUpperCase()))
                        .setHeight(Double.parseDouble(stringData.get(5)))
                        .setWeight(Double.parseDouble(stringData.get(6)))
                        .setBirthDate(new SimpleDateFormat("dd.MM.yy").parse(stringData.get(7)))
                        .createPerson();
                try {
                    person.setBMI(Double.parseDouble(stringData.get(8)));
                } catch (IndexOutOfBoundsException e) {
                    person.setBMI(0);
                }

                personList.add(person);
            } catch (ParseException e) {
                System.out.println("Error formatting data: " + e.getMessage());
            }
        });

        return personList;
    }

    /**
     * Console print statistics for List of Person objects
     *
     * @param personList List of Person objects
     */
    private static void printPersonStatistics(List<Person> personList) {
        // count all objects
        System.out.println(String.format("Total Anzahl Personen: %s", personList.size()));

        // count genders
        System.out.println(String.format("Davon Frauen: %s", countByGender(personList, Gender.FEMALE)));
        System.out.println(String.format("Davon Männer: %s", countByGender(personList, Gender.MALE)));

        // youngest person
        Person youngest = getByAge(personList, "min");
        System.out.println(String.format("Jüngste Person: %s %s, Alter: %s", youngest.getFirstName(),youngest.getLastName(), LocalDate.now().getYear() - getAge(youngest.getBirthday())));
        // oldest person
        Person oldest = getByAge(personList, "max");
        System.out.println(String.format("Älteste Person: %s %s, Alter: %s", oldest.getFirstName(), oldest.getLastName(), LocalDate.now().getYear() - getAge(oldest.getBirthday())));

        // print first 5 persons sorted by BMI (ascending)
        System.out.printf("%-6s %-15s %-15s %-6s %-7s %-7s %-8s %-4s\n", "Id", "Name", "Vorname", "Gender", "Grösse", "Gewicht", "Birthdate", "BMI");
        List<Person> people = sortByBMI(personList, 5);
        people.forEach(person -> {
            System.out.printf("%-6s %-15s %-15s %-6s %-7s %-7s %-8s %-4s\n",
                    person.getId(),
                    person.getFirstName(),
                    person.getLastName(),
                    person.getGender(),
                    person.getHeight(),
                    person.getWeight(),
                    new SimpleDateFormat("dd.MM.yy").format(person.getBirthday()),
                    Math.round(person.getBMI()));
        });
    }

    /**
     * Calculate Time period from x to now
     *
     * @param paramDate Start date
     * @return int passed years
     */
    private static int getAge(Date paramDate) {
        LocalDate now = LocalDate.now();
        LocalDate date = LocalDate.of(paramDate.getYear(), paramDate.getMonth(), paramDate.getDate());

        Period age = Period.between(date, now);

        return age.getYears();
    }

    /**
     * Filter List of Persons by gender and return count
     *
     * @param personList List of Person Objects
     * @param gender Gender of Person
     * @return long Count of Persons for searched gender
     */
    private static long countByGender(List<Person> personList, Gender gender) {
        return personList.stream()
                .filter(person -> person.getGender() == gender)
                .count();
    }

    /**
     * Get person defined by search requirement
     *
     * @param personList List of Person Objects
     * @param requirement String requirement
     * @return Person Object
     */
    private static Person getByAge(List<Person> personList, String requirement) {
        switch (requirement) {
            case "min":
                Person youngest = (personList.get(0));
                for(int i = 0; i < personList.size(); i++) {
                    if (youngest.getBirthday().compareTo(personList.get(i).getBirthday()) > 0) {
                        youngest = personList.get(i);
                    }
                }
                return youngest;

            case "max":
                Person oldest = (personList.get(0));
                for(int i = 0; i < personList.size(); i++) {
                    if (oldest.getBirthday().compareTo(personList.get(i).getBirthday()) < 0) {
                        oldest = personList.get(i);
                    }
                }
                return oldest;

            default:
                break;
        }
        return null;
    }

    /**
     * Get n-Amount of Persons, sorted by their ascending BMI
     *
     * @param personList List of Person Objects
     * @param amount How many People should be returned
     * @return List of Person Objects sorted by BMI (ascending)
     */
    private static List<Person> sortByBMI(List<Person> personList, int amount) {
        List<Person> invalidBMI = new ArrayList<>();
        personList.forEach(person -> {
            if (person.getBMI() == 0) {
                invalidBMI.add(person);
            }
        });

        personList.removeAll(invalidBMI);

        return personList.stream()
                .sorted(Comparator.comparing(Person::getBMI))
                .limit(amount)
                .collect(Collectors.toList());
    }
}
