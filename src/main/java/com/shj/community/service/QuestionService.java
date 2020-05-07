package com.shj.community.service;

import com.shj.community.dto.PageDTO;
import com.shj.community.dto.QuestionDTO;
import com.shj.community.mapper.QuestionMapper;
import com.shj.community.mapper.UserMapper;
import com.shj.community.model.Question;
import com.shj.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {


    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    public PageDTO list(Integer page, Integer size) {
        //size*(page-1)
        PageDTO pageDTO = new PageDTO();
        Integer totalCount = questionMapper.count();
        pageDTO.setPagination(totalCount,page,size);
        if(page<1){
            page=1;
        }

        if(page>pageDTO.getTotalPage()){
            page=pageDTO.getTotalPage();

        }
        Integer offset=size*(page-1);
        List<Question> list = questionMapper.list(offset,size);
        List<QuestionDTO> questionDTOList=new ArrayList<>();

        for (Question question : list) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO=new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        pageDTO.setQuestionDTOS(questionDTOList);

        return pageDTO;
    }
}
