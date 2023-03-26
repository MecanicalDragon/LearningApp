package net.medrag.patterns.behaviour.actual;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Stanislav Tretyakov
 * 26.03.2023
 */
public class StrategyV3 {
    public static void main(String[] args) {
        final var searcher = new BestEmployeeSearcher(List.of(
                new BestMaleEmployeeSearchStrategy(),
                new BestFemaleEmployeeSearchStrategy(),
                new BestCoderEmployeeSearchStrategy(),
                new BestManagerEmployeeSearchStrategy(),
                new BestQaEmployeeSearchStrategy(),
                new HighestAgeEmployeeSearchStrategy(),
                new LongestNameEmployeeSearchStrategy()
        ));

        System.out.println(searcher.findTheBestEmployee(Gender.MALE));
        System.out.println(searcher.findTheBestEmployee(Gender.FEMALE));
        System.out.println(searcher.findTheBestEmployee(Duty.QA));
        System.out.println(searcher.findTheBestEmployee(Duty.CODER));
        System.out.println(searcher.findTheBestEmployee(Duty.MANAGER));
        System.out.println(searcher.findTheBestEmployee(new AgeSearchingData(55)));
        System.out.println(searcher.findTheBestEmployee(new NameSearchingData("Chupakabrius")));
    }
}

enum StrategyIdentifier {
    GENDER_MALE,
    GENDER_FEMALE,
    DUTY_MANAGER,
    DUTY_CODER,
    DUTY_QA,
    AGE,
    LONGEST_NAME
}

interface SearchingData {
    StrategyIdentifier id();
}

interface BestEmployeeFindingStrategy<T extends SearchingData> {
    StrategyIdentifier id();

    String findTheBest(T data);
}

enum Gender implements SearchingData {
    MALE(StrategyIdentifier.GENDER_MALE),
    FEMALE(StrategyIdentifier.GENDER_FEMALE);

    private final StrategyIdentifier id;

    Gender(StrategyIdentifier id) {
        this.id = id;
    }

    @Override
    public StrategyIdentifier id() {
        return this.id;
    }
}

enum Duty implements SearchingData {
    MANAGER(StrategyIdentifier.DUTY_MANAGER),
    CODER(StrategyIdentifier.DUTY_CODER),
    QA(StrategyIdentifier.DUTY_QA);

    private final StrategyIdentifier id;

    Duty(StrategyIdentifier id) {
        this.id = id;
    }

    @Override
    public StrategyIdentifier id() {
        return this.id;
    }
}

class AgeSearchingData implements SearchingData {
    int age;

    public AgeSearchingData(int age) {
        this.age = age;
    }

    @Override
    public StrategyIdentifier id() {
        return StrategyIdentifier.AGE;
    }
}

class NameSearchingData implements SearchingData {
    String name;

    public NameSearchingData(String name) {
        this.name = name;
    }

    @Override
    public StrategyIdentifier id() {
        return StrategyIdentifier.LONGEST_NAME;
    }
}

class BestMaleEmployeeSearchStrategy implements BestEmployeeFindingStrategy<Gender> {

    @Override
    public StrategyIdentifier id() {
        return StrategyIdentifier.GENDER_MALE;
    }

    @Override
    public String findTheBest(Gender data) {
        return "The best employee is Mike.";
    }
}

class BestFemaleEmployeeSearchStrategy implements BestEmployeeFindingStrategy<Gender> {

    @Override
    public StrategyIdentifier id() {
        return StrategyIdentifier.GENDER_FEMALE;
    }

    @Override
    public String findTheBest(Gender data) {
        return "The best employeeriess is Olga.";
    }
}

class BestManagerEmployeeSearchStrategy implements BestEmployeeFindingStrategy<Duty> {

    @Override
    public StrategyIdentifier id() {
        return StrategyIdentifier.DUTY_MANAGER;
    }

    @Override
    public String findTheBest(Duty data) {
        return "The best manager is Vasiliy.";
    }
}

class BestCoderEmployeeSearchStrategy implements BestEmployeeFindingStrategy<Duty> {

    @Override
    public StrategyIdentifier id() {
        return StrategyIdentifier.DUTY_CODER;
    }

    @Override
    public String findTheBest(Duty data) {
        return "The best coder is Ivan.";
    }
}

class BestQaEmployeeSearchStrategy implements BestEmployeeFindingStrategy<Duty> {

    @Override
    public StrategyIdentifier id() {
        return StrategyIdentifier.DUTY_QA;
    }

    @Override
    public String findTheBest(Duty data) {
        return "The best QA is Daria.";
    }
}

class HighestAgeEmployeeSearchStrategy implements BestEmployeeFindingStrategy<AgeSearchingData> {

    @Override
    public StrategyIdentifier id() {
        return StrategyIdentifier.AGE;
    }

    @Override
    public String findTheBest(AgeSearchingData data) {
        return "The best employee of " + data.age + " y.o. is Jack.";
    }
}

class LongestNameEmployeeSearchStrategy implements BestEmployeeFindingStrategy<NameSearchingData> {

    @Override
    public StrategyIdentifier id() {
        return StrategyIdentifier.LONGEST_NAME;
    }

    @Override
    public String findTheBest(NameSearchingData data) {
        return "The longest name has an employee " + data.name + " .";
    }
}

class BestEmployeeSearcher {
    final Map<StrategyIdentifier, BestEmployeeFindingStrategy> strategies;

    BestEmployeeSearcher(List<BestEmployeeFindingStrategy> strategies) {
        this.strategies = strategies.stream().collect(Collectors.toMap(BestEmployeeFindingStrategy::id, Function.identity()));
    }

    String findTheBestEmployee(SearchingData searchingData) {
        return strategies.get(searchingData.id()).findTheBest(searchingData);
    }
}