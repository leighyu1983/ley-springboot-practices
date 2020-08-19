package com.ley.data;

import com.ley.entity.Person;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Leigh Yu
 * @date 2020/8/18 22:22
 */
public class SampleData {

    private static List<Person> personList = new ArrayList<>();
    static {
        personList.add(new Person("01", "tom", "tomp", "active"));
        personList.add(new Person("02", "jerry", "jerryp", "active"));
        personList.add(new Person("03", "dog", "dogp", "active"));

    }

    public static Optional<Person> getPerson(String username) {
        return personList.stream().filter(x -> x.getUsername().equals(username)).findFirst();
    }
}
