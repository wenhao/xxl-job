package com.xxl.job.executor.repository;

import com.xxl.job.executor.entity.GitCommit;
import org.springframework.data.repository.CrudRepository;

public interface GitCommitRepository extends CrudRepository<GitCommit, String> {

}
