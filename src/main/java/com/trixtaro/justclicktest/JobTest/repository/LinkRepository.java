package com.trixtaro.justclicktest.JobTest.repository;

import com.trixtaro.justclicktest.JobTest.entity.Link;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LinkRepository extends CrudRepository<Link, Integer> {

    List<Link> findByValue(String value);

}
