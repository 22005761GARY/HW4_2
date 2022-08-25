package com.example.springwebservice.model;

import com.example.springwebservice.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findById(int id);
    Long deleteById(int id);

//    User findByNameAndAge(String name, int age);//findBy+屬性() 可自己定義

    User findByAgeLessThan(int age);

//    User findByNameOrderByAsc(String name);//SQL : SELECT*FROM member WHERE name ?1 OrderBy ASC age
//
    List<User> findByOrderByAgeAsc();
    List<User> findByOrderByAgeDesc();
    List<User> findByAgeGreaterThanEqual(int age);

    @Query(value = "SELECT * FROM member WHERE name = ?1 AND age = ?2", nativeQuery = true)//?1:第一個參數，以此類推
    List<User> findByNameAndAge(String name, int age);

    @Modifying
    @Query(value = "INSERT INTO member VALUES (?1, ?2, ?3)",nativeQuery = true)
    void createUserBySql(int id, String name, int age);

    @Modifying
    @Transactional
    @Query(value = "UPDATE member SET age=?1, name=?2 WHERE id=?3", nativeQuery = true)
    int updateUserBySql(int age, String name, int id);

    @Modifying
    @Query(value = "DElETE FROM member WHERE name = ?1 AND age = ?2", nativeQuery = true)
    void deleteUserBySql(String name, int age);


}
