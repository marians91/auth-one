package com.aruma.mock.authone.account.service.impl;

import com.aruma.mock.authone.account.beans.model.User;
import com.aruma.mock.authone.account.repository.UserRepository;
import com.aruma.mock.authone.account.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findByCf(String cf) {
        return userRepository.findByCodiceFiscale(cf);
    }

    @Override
    public User save(User body) {
        return userRepository.save(body);
    }

    @Override
    public User update(User body) {
        return null;
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow();
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
