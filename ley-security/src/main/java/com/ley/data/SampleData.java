package com.ley.data;

import com.ley.entity.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Leigh Yu
 * @date 2020/8/18 22:22
 */
public class SampleData {

    private static List<Person> personList = new ArrayList<>();
    static {
        personList.add(new Person("01", "tom", "tomp"));
        personList.add(new Person("02", "jerry", "jerryp"));
        personList.add(new Person("03", "dog", "dogp"));

    }

    public static Person getPerson(String username) {
        return personList.stream().filter(x -> x.getUsername().equals(username)).findFirst().get();
    }
}
