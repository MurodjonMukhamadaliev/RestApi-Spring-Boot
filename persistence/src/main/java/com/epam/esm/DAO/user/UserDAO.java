package com.epam.esm.DAO.user;

import com.epam.esm.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserDAO {

    User get(UUID id);

    List<User> getAll(int limit, int offset);

}
