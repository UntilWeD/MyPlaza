package com.untilwed.plaza.user.repository;

import com.untilwed.plaza.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
public class UserRepositoryImpl implements UserRepository{

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepositoryImpl(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public User saveUser(User user) {
        log.info("savedUser = {}", user);

        String sql = "INSERT INTO user(number, id, password, email, username) VALUES(?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                user.getNumber(), user.getId(), user.getPassword(), user.getEmail(), user.getUsername());

        log.info("해당 user의 정보를 mysql 데이터베이스 저장하였습니다 :  {}", user);


        return user;
    }

    @Override
    public Optional<User> findUser(User user) {
        return Optional.empty();
    }

    @Override
    public Optional<User> findByIdUser(User user) {
        return Optional.empty();
    }

    @Override
    public List<User> findAllUser() {
        return null;
    }

    @Override
    public Optional<User> deleteUser(User user) {
        return Optional.empty();
    }
}
