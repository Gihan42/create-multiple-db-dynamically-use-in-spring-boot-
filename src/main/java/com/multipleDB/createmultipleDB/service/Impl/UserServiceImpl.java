package com.multipleDB.createmultipleDB.service.Impl;

import com.multipleDB.createmultipleDB.DataSource.DatabaseContextHolder;
import com.multipleDB.createmultipleDB.dto.UsersDTO;
import com.multipleDB.createmultipleDB.entity.Users;
import com.multipleDB.createmultipleDB.repo.UserRepo;
import com.multipleDB.createmultipleDB.service.UserService;
import com.multipleDB.createmultipleDB.util.DatabaseUtil;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jdk.jfr.Threshold;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    UserRepo userRepo;

    @Override
    public void save(UsersDTO usersDTO) {

        DatabaseContextHolder.setCurrentCompany(usersDTO.getCompanyName());

        Users userById = userRepo.getUserById(usersDTO.getId());
        if (userById == null) {
            if (usersDTO.getCompanyName() == null || usersDTO.getCompanyName().isEmpty()) {
                throw new IllegalArgumentException("Company name cannot be null or empty");
            }

            Users map = modelMapper.map(usersDTO, Users.class);
            Users savedUser = userRepo.save(map);

            createDataBase(savedUser.getCompanyName());
        } else {
            throw new IllegalArgumentException("User already exists");
        }


        DatabaseContextHolder.clear();

    }

    @Override
    public void createDataBase(String companyName) {

        String dbName = companyName.toLowerCase().replaceAll("\\s+", "_");


        String createDatabaseSql = "CREATE DATABASE IF NOT EXISTS `" + dbName + "`";
        jdbcTemplate.execute(createDatabaseSql);


        DatabaseUtil databaseUtil = new DatabaseUtil();
        EntityManager entityManager = databaseUtil.createEntityManager(dbName);

        try {
            entityManager.getTransaction().begin();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }
    }
}
