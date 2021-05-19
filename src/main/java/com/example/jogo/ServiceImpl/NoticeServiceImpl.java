package com.example.jogo.ServiceImpl;

import com.example.jogo.Entity.Notice;
import com.example.jogo.Service.NoticeService;
import com.example.jogo.repository.NoticeRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
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
        if(noticeRepository.findBy_id(_id)==null)
            return false;
        noticeRepository.deleteBy_id(_id);
        return true;
    }

    @Override
    public boolean deleteAllByTeamIdAndProjectId(String teamId, String projectId) {

        noticeRepository.deleteAllByTeamIdAndProjectId(teamId,projectId);
        return true;
    }

    @Override
    public void save(Notice notice) {
        noticeRepository.save(notice);
    }

    @Override
    public Notice notice(String teamId, String projectId,String title, String content,int priority) {
        Notice notice = new Notice();
        notice.setTeamId(teamId);
        notice.setProjectId(projectId);
        notice.setContent(content);
        notice.setTime(new Date());
        notice.setPriority(priority);
        notice.setTitle(title);

        return notice;
    }
}
