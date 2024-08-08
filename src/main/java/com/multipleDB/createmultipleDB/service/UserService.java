package com.multipleDB.createmultipleDB.service;

import com.multipleDB.createmultipleDB.dto.UsersDTO;

public interface UserService  {

    void save(UsersDTO usersDTO);

    void createDataBase(String companyName);
}
