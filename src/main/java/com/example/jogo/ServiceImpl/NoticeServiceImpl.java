package com.example.jogo.ServiceImpl;

import com.example.jogo.Entity.Notice;
import com.example.jogo.Service.NoticeService;
import com.example.jogo.repository.NoticeRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class NoticeServiceImpl implements NoticeService {
    @Resource
    private NoticeRepository noticeRepository;
    @Override
    public List<Notice> findAllByTeamIdAndProjectId(String teamId, String projectId) {
        return noticeRepository.findAllByTeamIdAndProjectId(teamId,projectId);
    }

    @Override
    public boolean deleteBy_id(String _id) {
        return noticeRepository.deleteBy_id(_id);
    }

    @Override
    public boolean deleteAllByTeamIdAndProjectId(String teamId, String projectId) {

        return noticeRepository.deleteAllByTeamIdAndProjectId(teamId,projectId);
    }

    @Override
    public void save(Notice notice) {
        noticeRepository.save(notice);
    }
}
