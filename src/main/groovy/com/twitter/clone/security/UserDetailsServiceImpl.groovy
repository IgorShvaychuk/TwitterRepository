package com.twitter.clone.security

import com.twitter.clone.model.User
import com.twitter.clone.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository

    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow { new UsernameNotFoundException("User not found with username: " + username) }

        new org.springframework.security.core.userdetails.User(user.username, user.password, new ArrayList<>())
    }
}
