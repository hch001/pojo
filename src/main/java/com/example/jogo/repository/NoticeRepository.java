package com.example.jogo.repository;

import com.example.jogo.Entity.Notice;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface NoticeRepository extends MongoRepository<Notice,String> {
    List<Notice> findAllByTeamIdAndProjectId(String teamId,String projectId);
    boolean deleteBy_id(String _id);
    boolean deleteAllByTeamIdAndProjectId(String teamId,String projectId);
}
