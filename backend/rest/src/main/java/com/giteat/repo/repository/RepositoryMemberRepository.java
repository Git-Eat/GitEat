package com.giteat.repo.repository;

import com.giteat.repo.entity.RepositoryEntity;
import com.giteat.repo.entity.RepositoryMemberEntity;
import com.giteat.repo.entity.UsersEntity;
import com.giteat.user.entity.OAuthEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RepositoryMemberRepository extends JpaRepository<RepositoryMemberEntity, Long> {

//    @Query("select o from RepositoryMemberEntity rm join OAuthEntity o on rm.id.userId = o.userId " +
//    "where rm.id.repoId = :repoId")
//    List<OAuthEntity> findAllOauthTokensByRepoId(@Param("repoId") Integer repoId);
    Optional<RepositoryMemberEntity> findByRepoId(int repoId);
}
