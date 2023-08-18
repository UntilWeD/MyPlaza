package com.untilwed.plaza.user.repository;

import com.untilwed.plaza.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
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
    public Optional<User> findByIdUser(String id) {

        String sql = "SELECT * FROM user WHERE id = ?";
        Optional<User> findUser = null;

        try{
            findUser = Optional.of(jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), id));
        } catch (Exception ex){
            log.info("findUser를 찾는 도중 오류가 발생하였습니다.");
        }

        return findUser;
    }

    @Override
    public List<User> findAllUser() {
        return null;
    }

    @Override
    public Optional<User> deleteUser(User user) {
        return Optional.empty();
    }

    //이메일 성공 로직
    @Override
    public Optional<User> setEmailVerifiedByNumber(Long number) {
        String sql = "UPDATE user SET emailVerified = true WHERE number ? AND emailVerified = false";
        jdbcTemplate.update(sql, number);

        log.info("값을 변경하였습니다.");

        sql = "SELECT * FROM user WHERE number = ?";
        Optional<User> findUser = null;

        try{
            findUser = Optional.of(jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), number));
            return findUser;
        } catch (Exception e){
            log.info("findUser를 찾는 도중 오류가 발생하였습니다.");
            return Optional.empty();
        }
    }
}
