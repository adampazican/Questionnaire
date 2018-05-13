package sk.pazican.adam.databaseItems;

import sk.pazican.adam.ResponseObject;

public class Question extends ResponseObject {
    private String id;
    private String title;
    private String categoryName;
    private String answer1;
    private String answer2;
    private String answer3;
    private String realAnswer;

    public Question(String title, String categoryName, String answer1, String answer2, String answer3, String realAnswer) {
        super(200, null);
        this.title = title;
        this.categoryName = categoryName;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.realAnswer = realAnswer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getAnswer1() {
        return answer1;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public void setAnswer3(String answer3) {
        this.answer3 = answer3;
    }

    public String getRealAnswer() {
        return realAnswer;
    }

    public void setRealAnswer(String realAnswer) {
        this.realAnswer = realAnswer;
    }
}
