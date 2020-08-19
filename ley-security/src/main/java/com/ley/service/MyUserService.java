package com.ley.service;

import com.ley.data.SampleData;
import com.ley.entity.Person;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

/**
 * @author Leigh Yu
 * @date 2020/8/18 22:14
 */
@Service
public class MyUserService implements UserDetailsService {
    @Override
    public Person loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> user = SampleData.getPerson(username);
        if (!user.isPresent()) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        System.out.println("username:"+user.get().getUsername()+";password:"+user.get().getPassword());
        return user.get();
    }
}
