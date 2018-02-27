package com.xxl.job.executor.repository;

import com.xxl.job.executor.entity.GitRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

interface GitCommitRepository extends CrudRepository<GitRepository, String> {

    Optional<GitRepository> findByUrlAndBranch(String url, String branch);

}
