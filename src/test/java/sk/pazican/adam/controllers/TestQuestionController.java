package sk.pazican.adam.controllers;

import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.Assert;
import sk.pazican.adam.databaseItems.Question;

import java.io.IOException;

public class TestQuestionController {
    //TODO: whole test suit: create, get, update, delete question (category)

    @Test
    public void testGetQuestionWithId1() throws IOException {
        HttpUriRequest request = new HttpGet("http://localhost:4567/questions/1");
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        Assert.assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
    }

    @Test
    public void testGetQuestionWithId1ReturnsJSONWithTitle() throws IOException{
        HttpUriRequest request = new HttpGet("http://localhost:4567/questions/1");
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        HttpEntity httpEntity = response.getEntity();
        String output = EntityUtils.toString(httpEntity);

        Gson gson = new Gson();
        Question question = gson.fromJson(output, Question.class);
        Assert.assertEquals("Rozloha afriky", question.getTitle());
    }

    @Test
    public void testGetQuestionWithNonExistentId() throws IOException{
        HttpUriRequest request = new HttpGet("http://localhost:4567/questions/20");
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        Assert.assertEquals(HttpStatus.SC_NOT_FOUND, response.getStatusLine().getStatusCode());
    }
}
