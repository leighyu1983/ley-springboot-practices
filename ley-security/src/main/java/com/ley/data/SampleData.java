package com.ley.data;

import com.ley.entity.Permission;
import com.ley.entity.Person;
import com.ley.entity.Role;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author Leigh Yu
 * @date 2020/8/18 22:22
 */
public class SampleData {

    private static List<Person> personList = new ArrayList<>();
    private static List<Role> roleList = new ArrayList<>();

    static {
        Permission pPersonNormal = new Permission("pPersonNormal", "/person/{id}");
        Permission pPersonAdmin = new Permission("pPersonAll", "/person/**");
        Permission pAll = new Permission("admin", "/**");

        Role rAdmin = new Role("admin", Arrays.asList(pAll));
        Role rPersonAdmin = new Role("rPersonAdmin", Arrays.asList(pPersonAdmin));
        Role rPersonNormal = new Role("rPersonNormal", Arrays.asList(pPersonNormal));
        roleList = Arrays.asList(rAdmin, rPersonAdmin, rPersonNormal);

        personList.add(new Person("01", "tom", "tomp", "active", Arrays.asList(rAdmin)));
        personList.add(new Person("02", "jerry", "jerryp", "active", Arrays.asList(rPersonNormal)));
        personList.add(new Person("03", "dog", "dogp", "active", Arrays.asList(rPersonAdmin)));

    }



    public static List<Role> getRoles() {
        return roleList;
    }

    public static Optional<Person> getPerson(String username) {
        return personList.stream().filter(x -> x.getUsername().equals(username)).findFirst();
    }

}
