package ru.javawebinar.topjava.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserMealsUtilTest {

    private List<UserMeal> meals;
    private LocalTime startTime;
    private LocalTime endTime;
    private final int caloriesPerDay = 2000;

    @BeforeEach
    void setUp() {
        meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );
    }

    @Test
    void testFilteredByCycles_between_0000_21000() {
        startTime = LocalTime.of(0, 0);
        endTime = LocalTime.of(21, 0);
        List<UserMealWithExcess> meals = UserMealsUtil.filteredByCycles(this.meals, startTime, endTime, caloriesPerDay);
        assertNotNull(meals);
        assertEquals(7, meals.size());

    }

    @Test
    void testFilteredByCycles_between_0700_1400() {
        startTime = LocalTime.of(7, 0);
        endTime = LocalTime.of(14, 0);

        List<UserMealWithExcess> meals = UserMealsUtil.filteredByCycles(this.meals, startTime, endTime, caloriesPerDay);
        assertNotNull(meals);
        assertEquals(4, meals.size());
        meals.sort(Comparator.comparing(UserMealWithExcess::getDateTime));

        UserMealWithExcess firstDish = meals.get(0);
        assertEquals(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), firstDish.getDateTime());
        assertEquals("Завтрак", firstDish.getDescription());
        assertEquals(500, firstDish.getCalories());

        UserMealWithExcess lastDish = meals.get(meals.size() - 1);
        assertEquals(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), lastDish.getDateTime());
        assertEquals("Обед", lastDish.getDescription());
        assertEquals(500, lastDish.getCalories());
        meals.forEach(System.out::println);

    }
}