package com.crud.security.interfaces;

import com.crud.entities.Leccion;
import com.crud.security.entities.User;

import java.util.List;
import java.util.Optional;

public interface IUserSerivce {
    public List<User> listUser();
    public Optional<User> findByUsername(String username);
    public Optional<User> findById(Long id);
    public void delete(Long id);
}
