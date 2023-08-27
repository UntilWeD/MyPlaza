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

        String sql = "INSERT INTO user(number, id, password, email, username, emailverified) VALUES(?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                user.getNumber(), user.getId(), user.getPassword(), user.getEmail(), user.getUsername(), user.isEmailverified());

        log.info("[유저리포지토리] 해당 user의 정보를 mysql 데이터베이스 저장하였습니다 :  {}", user);


        return user;
    }

    @Override
    public User updateUser(User user, Long userNumber) {
        log.info("[유저리포지토리] 유저업데이트메서드를 실행합니다.");
        log.info("[유저리포지토리] user = {}, userNumber = {}", user, userNumber);
        String sql = "UPDATE user SET id = ?, password = ?, email = ?, username = ? WHERE number = ?";
        jdbcTemplate.update(
                sql,
                user.getId(),
                user.getPassword(),
                user.getEmail(),
                user.getUsername(),
                userNumber
        );

        User updatedUser = findByIdUser(user.getId()).get();

        return updatedUser;
    }

    @Override
    public User deleteUser(User user) {
        String sql = "DELETE FROM user WHERE number = ?";

        jdbcTemplate.update(sql, user.getNumber());

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
            log.info("[유저리포지토리] findUser를 찾는 도중 오류가 발생하였습니다.");
        }

        return findUser;
    }

    @Override
    public List<User> findAllUser() {
        return null;
    }

    //이메일 성공 로직
    @Override
    public Optional<User> setEmailVerifiedByNumber(Long number) {
        log.info("[유저리포지토리] setEmailVerifiedByNumber메서드를 실행하겠습니다. number : {}", number);

        String sql = "UPDATE user SET emailVerified = true WHERE number = ? AND emailVerified = false";
        jdbcTemplate.update(sql, number);

        log.info("값을 변경하였습니다.");

        sql = "SELECT * FROM user WHERE number = ?";
        Optional<User> findUser = null;

        try{
            findUser = Optional.of(jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), number));
            return findUser;
        } catch (Exception e){
            log.info("[유저리포지토리] findUser를 찾는 도중 오류가 발생하였습니다.");
            return Optional.empty();
        }
    }

    @Override
    public long findNumberByEmail(String email) {
        log.info("[유저리포지토리]findNumberByEmail메서드를 실행하였습니다. ");
        String sql = "SELECT number FROM user WHERE email = ?";
        Long userNumber = jdbcTemplate.queryForObject(sql, Long.class, email);

        return userNumber;
    }


    @Override
    public Optional<User> findUserByEmail(String email) {
        String sql = "SELECT * FROM user WHERE email = ?";

        Optional<User> findUser = null;

        try{
            findUser = Optional.of(jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), email));
            return findUser;
        } catch (Exception ex){
            log.info("[유저리포지토리] findUser를 찾는 도중 오류가 발생하였습니다.");
        }

        return Optional.empty();
    }
}
