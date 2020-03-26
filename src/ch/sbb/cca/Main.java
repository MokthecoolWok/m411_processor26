package ch.sbb.cca;

import ch.sbb.cca.domain.Gender;
import ch.sbb.cca.domain.Person;
import ch.sbb.cca.domain.PersonBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        String filePath = "C:/GitProjects/processor26/data/MOCK_DATA-4.csv";
        System.out.println(String.format("processor26 %s", filePath));
        List<String> dataFromCSV = readDataFromCSV(filePath);
        List<Person> peopleList = convertDataToPerson(dataFromCSV);


    }

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
}
