package com.felzan.iapps.repository;

import com.felzan.iapps.model.EPaper;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface EPaperRepository extends QueryByExampleExecutor<EPaper>, PagingAndSortingRepository<EPaper, Long>, CrudRepository<EPaper, Long> {
}
