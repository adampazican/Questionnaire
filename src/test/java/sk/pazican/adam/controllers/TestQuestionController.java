package sk.pazican.adam.controllers;

import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.Assert;
import sk.pazican.adam.databaseItems.Question;

import java.io.IOException;

public class TestQuestionController {
    private HttpClient client;

    public TestQuestionController() {
        this.client = HttpClientBuilder.create().build();
    }

    @Test
    public void testCreateNewQuestion() throws IOException {
        HttpUriRequest request = new HttpPost("http://localhost:4567/questions/");

        request.setHeader("title", "Rozloha Slovenska");
        request.setHeader("answer1", "10000km");
        request.setHeader("answer2", "20000km");
        request.setHeader("answer3", "30000km");
        request.setHeader("realAnswer", "30000km");
        request.setHeader("categoryId", "1");

        HttpResponse response = this.client.execute(request);
        Assert.assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
    }

    @Test
    public void testTryToCreateNewQuestionWithNonValidCategoryId() throws IOException {
        HttpUriRequest request = new HttpPost("http://localhost:4567/questions/");

        request.setHeader("title", "Rozloha Slovenska");
        request.setHeader("answer1", "10000km");
        request.setHeader("answer2", "20000km");
        request.setHeader("answer3", "30000km");
        request.setHeader("realAnswer", "30000km");
        request.setHeader("categoryId", "2400");

        HttpResponse response = this.client.execute(request);
        Assert.assertEquals(HttpStatus.SC_BAD_REQUEST, response.getStatusLine().getStatusCode());
    }

    @Test
    public void testTryToCreateNewQuestionWithNullParams() throws IOException {
        HttpUriRequest request = new HttpPost("http://localhost:4567/questions/");

        HttpResponse response = this.client.execute(request);
        Assert.assertEquals(HttpStatus.SC_BAD_REQUEST, response.getStatusLine().getStatusCode());
    }

    @Test
    public void testGetQuestionWithId1() throws IOException {
        HttpUriRequest request = new HttpGet("http://localhost:4567/questions/1");
        HttpResponse response = this.client.execute(request);

        Assert.assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
    }

    @Test
    public void testGetQuestionWithId1ReturnsJSONWithTitle() throws IOException {
        HttpUriRequest request = new HttpGet("http://localhost:4567/questions/1");
        HttpResponse response = this.client.execute(request);

        HttpEntity httpEntity = response.getEntity();
        String output = EntityUtils.toString(httpEntity);

        Gson gson = new Gson();
        Question question = gson.fromJson(output, Question.class);
        Assert.assertEquals("Rozloha afriky", question.getTitle());
    }

    @Test
    public void testGetQuestionWithNonExistentId() throws IOException {
        HttpUriRequest request = new HttpGet("http://localhost:4567/questions/9999");
        HttpResponse response = this.client.execute(request);

        Assert.assertEquals(HttpStatus.SC_NOT_FOUND, response.getStatusLine().getStatusCode());
    }

    @Test
    public void testGetAllQuestions() throws IOException {
        HttpUriRequest request = new HttpGet("http://localhost:4567/questions/");
        HttpResponse response = this.client.execute(request);

        Assert.assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
    }

    @Test
    public void testUpdateQuestionWithId() throws IOException {
        HttpUriRequest request = new HttpPut("http://localhost:4567/questions/6");

        request.setHeader("title", "Rozloha afriky");

        HttpResponse response = this.client.execute(request);
        Assert.assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
    }

    @Test
    public void testUpdateQuestionWithInvalidId() throws IOException {
        HttpUriRequest request = new HttpPut("http://localhost:4567/questions/99999");

        request.setHeader("title", "Rozlohaa Slovenska");

        HttpResponse response = this.client.execute(request);
        Assert.assertEquals(HttpStatus.SC_NOT_FOUND, response.getStatusLine().getStatusCode());
    }

    @Test
    public void testDeleteQuestion() throws IOException {
        HttpUriRequest request = new HttpDelete("http://localhost:4567/questions/24");
        HttpResponse response = this.client.execute(request);

        Assert.assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
    }

    @Test
    public void testDeleteQuestionWithInvalidId() throws IOException {
        HttpUriRequest request = new HttpDelete("http://localhost:4567/questions/9999");
        HttpResponse response = this.client.execute(request);

        Assert.assertEquals(HttpStatus.SC_NOT_FOUND, response.getStatusLine().getStatusCode());
    }
}
