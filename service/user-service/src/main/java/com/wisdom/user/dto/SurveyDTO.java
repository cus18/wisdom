package com.wisdom.user.dto;

import java.util.List;

public class SurveyDTO {

    private String _id;

    //题目ID号
    private String questionId;

    //题目名称
    private String questionName;

    //题目类型
    /**
     * single 单选
     * multi 多选
     * input 填空
     *
     * */
    private String questionType;

    //题目的模版
    private String questionTemp;

    //题目的模版
    private String questionStatus;

    //来源
    private List<QuestionItemDTO> questionData;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getQuestionName() {
        return questionName;
    }

    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getQuestionTemp() {
        return questionTemp;
    }

    public void setQuestionTemp(String questionTemp) {
        this.questionTemp = questionTemp;
    }

    public List<QuestionItemDTO> getQuestionData() {
        return questionData;
    }

    public void setQuestionData(List<QuestionItemDTO> questionData) {
        this.questionData = questionData;
    }

    public String getQuestionStatus() {
        return questionStatus;
    }

    public void setQuestionStatus(String questionStatus) {
        this.questionStatus = questionStatus;
    }
}