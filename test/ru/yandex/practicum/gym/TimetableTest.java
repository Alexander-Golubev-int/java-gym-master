package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class TimetableTest {

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник вернулось одно занятие
        TreeMap<TimeOfDay, List<TrainingSession>> trainingListOfMonday =
                timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        Assertions.assertEquals(1, trainingListOfMonday.size());
        //Проверить, что за вторник не вернулось занятий
        TreeMap<TimeOfDay, List<TrainingSession>> trainingListOfTuesday =
                timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        Assertions.assertTrue(trainingListOfTuesday.isEmpty());

        //Проверить, что за понедельник вернулось два занятия
        TrainingSession secondTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(14, 0));
        timetable.addNewTrainingSession(secondTrainingSession);
        trainingListOfMonday = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        Assertions.assertEquals(2, trainingListOfMonday.size());
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        Coach coach2 = new Coach("Пистолетов", "Александр", "Пиратович");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach2,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        TrainingSession saturdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);
        timetable.addNewTrainingSession(saturdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        // Проверить, что за понедельник вернулось одно занятие
        TreeMap<TimeOfDay, List<TrainingSession>> trainingListOfMonday =
                timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        Assertions.assertEquals(1, trainingListOfMonday.size());

        // Проверить, что за четверг вернулось два занятия в правильном порядке: сначала в 13:00, потом в 20:00
        TreeMap<TimeOfDay, List<TrainingSession>> trainingListOfThursday =
                timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);
        NavigableSet<TimeOfDay> keysOfTrainingOfThursday = trainingListOfThursday.navigableKeySet();
        List<TimeOfDay> timeOfDaysOfThursday = new ArrayList<>();
        List<TimeOfDay> keysTimeOfDaysOfThursday = new ArrayList<>(keysOfTrainingOfThursday);
        timeOfDaysOfThursday.add(new TimeOfDay(13, 0));
        timeOfDaysOfThursday.add(new TimeOfDay(20, 0));
        Assertions.assertEquals(timeOfDaysOfThursday, keysTimeOfDaysOfThursday);

        // Проверить, что за вторник не вернулось занятий
        TreeMap<TimeOfDay, List<TrainingSession>> trainingListOfTuesday =
                timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        Assertions.assertTrue(trainingListOfTuesday.isEmpty());

    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        Group group2 = new Group("Акробатика для взрослых", Age.ADULT, 60);
        Coach coach2 = new Coach("Пистолетов", "Александр", "Пиратович");
        TrainingSession secondTrainingSession = new TrainingSession(group2, coach2,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник в 13:00 вернулось одно занятие
        List<TrainingSession> trainingListOfMonday = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY,
                new TimeOfDay(13, 0));
        Assertions.assertEquals(1, trainingListOfMonday.size());

        //Проверить, что за понедельник в 14:00 не вернулось занятий
        List<TrainingSession> trainingListOfTuesday = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.TUESDAY,
                new TimeOfDay(14, 0));
        Assertions.assertTrue(trainingListOfTuesday.isEmpty());

        //Проверить, что за понедельник в 13:00 вернулось два занятия
        timetable.addNewTrainingSession(secondTrainingSession);
        trainingListOfMonday = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY,
                new TimeOfDay(13, 0));
        Assertions.assertEquals(2, trainingListOfMonday.size());
    }

    @Test
    void testGetCountByCoaches() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession firstTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession secondTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.TUESDAY, new TimeOfDay(13, 0));
        timetable.addNewTrainingSession(firstTrainingSession);
        timetable.addNewTrainingSession(secondTrainingSession);

        //Проверить, что вернулся тренер с корректным количеством тренировок
        List<CounterOfTrainings> listCounterOfTrainings = timetable.getCountByCoaches();
        int expectedCountOfTraining = 2;
        int currentCountOfTraining = listCounterOfTrainings.getFirst().getCountOfTrainings();
        Assertions.assertEquals(expectedCountOfTraining, currentCountOfTraining);

        //Проверка сортировки по убыванию с двумя тренерами
        Coach coach2 = new Coach("Пистолетов", "Александр", "Пиратович");
        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach2,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));
        TrainingSession saturdatAdultTrainingSession = new TrainingSession(groupAdult, coach2,
                DayOfWeek.SATURDAY, new TimeOfDay(15, 0));
        TrainingSession mondayAdultTrainingSession = new TrainingSession(groupAdult, coach2,
                DayOfWeek.MONDAY, new TimeOfDay(10, 0));
        timetable.addNewTrainingSession(thursdayAdultTrainingSession);
        timetable.addNewTrainingSession(saturdatAdultTrainingSession);
        timetable.addNewTrainingSession(mondayAdultTrainingSession);
        listCounterOfTrainings = timetable.getCountByCoaches();
        List<Integer> expectedIntegerList = new ArrayList<>();
        expectedIntegerList.add(3);
        expectedIntegerList.add(2);
        List<Integer> currentIntegerList = new ArrayList<>();
        for (CounterOfTrainings counterOfTrainings : listCounterOfTrainings) {
            currentIntegerList.add(counterOfTrainings.getCountOfTrainings());
        }
        Assertions.assertEquals(expectedIntegerList, currentIntegerList);

        //Проверка сортировки по убыванию с тремя тренерами и выводами их фио
        Coach coach3 = new Coach("Артемов", "Андрей", "Викторович");
        Group groupAdult2 = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession wednesdayAdultTrainingSession = new TrainingSession(groupAdult2, coach3,
                DayOfWeek.WEDNESDAY, new TimeOfDay(20, 0));
        expectedIntegerList = new ArrayList<>(Arrays.asList(3, 2, 1));
        timetable.addNewTrainingSession(wednesdayAdultTrainingSession);
        listCounterOfTrainings = timetable.getCountByCoaches();
        currentIntegerList = new ArrayList<>();
        for (CounterOfTrainings counterOfTrainings : listCounterOfTrainings) {
            currentIntegerList.add(counterOfTrainings.getCountOfTrainings());
        }
        Assertions.assertEquals(expectedIntegerList, currentIntegerList);
        String expectedCoachFirst = "Пистолетов Александр";
        String expectedCoachSecond = "Васильев Николай";
        String expectedCoachLast = "Артемов Андрей";

        Assertions.assertEquals(expectedCoachFirst, listCounterOfTrainings.getFirst().toString());
        Assertions.assertEquals(expectedCoachSecond, listCounterOfTrainings.get(1).toString());
        Assertions.assertEquals(expectedCoachLast, listCounterOfTrainings.getLast().toString());

    }

}
