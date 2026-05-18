package ru.yandex.practicum.gym;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.Collections;


public class Timetable {

    private final Map<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        List<TrainingSession> trainingList;
        TreeMap<TimeOfDay, List<TrainingSession>> trainingSchedule;
        if (timetable.containsKey(trainingSession.getDayOfWeek())) {
            trainingSchedule = timetable.get(trainingSession.getDayOfWeek());
            trainingList = trainingSchedule.get(trainingSession.getTimeOfDay());
            if (trainingList == null) {
                trainingList = new ArrayList<>();
                trainingList.add(trainingSession);
            } else {
                trainingList.add(trainingSession);
            }
        } else {
            trainingSchedule = new TreeMap<>();
            trainingList = new ArrayList<>();
            trainingList.add(trainingSession);
        }
        trainingSchedule.put(trainingSession.getTimeOfDay(), trainingList);
        timetable.put(trainingSession.getDayOfWeek(), trainingSchedule);
    }

    public TreeMap<TimeOfDay, List<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        TreeMap<TimeOfDay, List<TrainingSession>> schedule = timetable.get(dayOfWeek);

        if (schedule == null) {
            return new TreeMap<>();
        } else {
            return schedule;
        }
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        TreeMap<TimeOfDay, List<TrainingSession>> schedule = timetable.get(dayOfWeek);
        if (schedule == null) {
            return new ArrayList<>();
        }
        if (schedule.get(timeOfDay) == null) {
            return new ArrayList<>();
        }
        return schedule.get(timeOfDay);
    }

    public List<CounterOfTrainings> getCountByCoaches() {
        Map<Coach, Integer> coachTrainingSessionMap = new HashMap<>();
        for (DayOfWeek dayOfWeek : timetable.keySet()) {
            TreeMap<TimeOfDay, List<TrainingSession>> listOfTraining = getTrainingSessionsForDay(dayOfWeek);
            for (TimeOfDay timeOfDay : listOfTraining.keySet()) {
                List<TrainingSession> listTrainingSession = getTrainingSessionsForDayAndTime(dayOfWeek, timeOfDay);
                for (TrainingSession trainingSession: listTrainingSession) {
                    if (coachTrainingSessionMap.containsKey(trainingSession.getCoach())) {
                        coachTrainingSessionMap.put(trainingSession.getCoach(),
                                coachTrainingSessionMap.get(trainingSession.getCoach()) + 1);
                    } else {
                        coachTrainingSessionMap.put(trainingSession.getCoach(), 1);
                    }
                }
            }
        }

        List<CounterOfTrainings> counterOfTrainings = new ArrayList<>();
        for (Map.Entry<Coach,Integer> entry: coachTrainingSessionMap.entrySet()) {
            counterOfTrainings.add(new CounterOfTrainings(entry.getKey(), entry.getValue()));
        }
        Collections.sort(counterOfTrainings);
        return counterOfTrainings;
    }
}
