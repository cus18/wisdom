/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2016 abel533@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.wisdom.user.service;

import com.wisdom.user.dto.AnswerDTO;
import com.wisdom.user.mapper.SurveyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * @author liuzh
 * @since 2015-12-19 11:09
 */
@Service
@Transactional(readOnly = false)
public class SurveyService {

    @Autowired
    private SurveyMapper surveyMapper;

    public List<AnswerDTO> findData() {
        AnswerDTO answerDTO = new AnswerDTO();
        //answerDTO.setId("008e91f1-40e8-4881-9d26-78ecc59d31ee");
        answerDTO.setWorker_name("李丛蓉");
        List<AnswerDTO> answerDTOList = surveyMapper.findSurveyAnswer(answerDTO);
        return answerDTOList;
    }

    public void createData() {

        AnswerDTO answerDTO = new AnswerDTO();
        answerDTO.setId(UUID.randomUUID().toString());
        answerDTO.setWorker_phone("13121237551");
        answerDTO.setWorker_name("李丛蓉");
        answerDTO.setUpdate_date(new Date());
        answerDTO.setElder_identity_num("11223344");
        answerDTO.setQuestion_id(15);
        answerDTO.setQuestion_answer("AAAA");
        answerDTO.setQuestion_name("你的爱好");

        surveyMapper.createSurveyAnswer(answerDTO);
    }
}
