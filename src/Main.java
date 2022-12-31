import java.util.*;
import java.util.stream.Collectors;

public class Main {
    static List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
    static List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
    static Collection<Person> persons = new ArrayList<>();

    public static void main(String[] args) {
        for (int i = 0; i < 10_000_000; i++) {
            persons.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    families.get(new Random().nextInt(families.size())),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)])
            );
        }

        long juveniles = persons.stream()
                .filter(person -> person.getAge() < 18)
                .count();
        System.out.println("количество несовершеннолетних: " + juveniles);

        //список фамилий призывников:
        List<String> conscripts = persons.stream()
                .filter(person -> person.getSex() == Sex.MAN)
                .filter(person -> person.getAge() > 18 && person.getAge() < 27)
                .map(person -> person.getFamily())
                .collect(Collectors.toList());

        //список потенциально работоспособных людей с высшим образованием
        List<Person> potentiallyAbleToWork = persons.stream()
                .filter(person -> person.getEducation() == Education.HIGHER)
                .filter(person ->
                        (person.getSex() == Sex.MAN && person.getAge() > 18 && person.getAge() < 65)
                                || (person.getSex() == Sex.WOMAN && person.getAge() > 18 && person.getAge() < 60))
                .sorted(Comparator.comparing(person -> person.getFamily()))
                .collect(Collectors.toList());
    }
}
