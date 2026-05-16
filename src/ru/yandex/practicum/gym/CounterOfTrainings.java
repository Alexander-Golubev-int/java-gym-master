package ru.yandex.practicum.gym;

public class CounterOfTrainings implements Comparable<CounterOfTrainings>  {
    private Coach coach;
    private int countOfTrainings = 0;

    public CounterOfTrainings(Coach coach, int countOfTrainings) {
        this.coach = coach;
        this.countOfTrainings = countOfTrainings;
    }

    public void addNewCountOfTraining() {
        countOfTrainings++;
    }

    @Override
    public int compareTo(CounterOfTrainings o) {
        if (countOfTrainings > o.countOfTrainings) {
            return -1;
        } else if (countOfTrainings < o.countOfTrainings) {
            return 1;
        }
        return 0;
    }

    public Coach getCoach() {
        return coach;
    }

    public int getCountOfTrainings() {
        return countOfTrainings;
    }

    @Override
    public String toString() {
        return coach.getSurname() + " " + coach.getName();
    }
}
