package com.example.demotopic03.repositories;

import com.example.demotopic03.models.Book;
import com.github.javafaker.Faker;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface BookRepository {

    @Select("select * from tb_book order by id asc")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "title", property = "title")
    })
    List<Book> getAll();

    @Select("select * from tb_book where id=#{id}")
    Book findOne( @Param("id") Integer id);

    @Update("update tb_book set title=#{title},author=#{author},publisher=#{publisher},thumbnail=#{thumbnail} where id=#{id}")
    boolean update(Book book);

    @Delete("delete from tb_book where id=#{id}")
    boolean delete(Integer id);

    @Insert("insert into tb_book(title,author,publisher,thumbnail) values(#{title},#{author},#{publisher},#{thumbnail})")
    boolean create(Book book);

}
