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
import sk.pazican.adam.databaseItems.Category;

import java.io.IOException;

public class TestCategoryController {
    private HttpClient client;

    public TestCategoryController() {
        this.client = HttpClientBuilder.create().build();
    }

    @Test
    public void testCreateNewCategory() throws IOException {
        HttpUriRequest request = new HttpPost("http://localhost:4567/categories/");

        request.setHeader("name", "Matematika");

        HttpResponse response = this.client.execute(request);
        Assert.assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
    }

    @Test
    public void testTryToCreateNewCategoryWithNullParams() throws IOException {
        HttpUriRequest request = new HttpPost("http://localhost:4567/categories/");

        HttpResponse response = this.client.execute(request);
        Assert.assertEquals(HttpStatus.SC_BAD_REQUEST, response.getStatusLine().getStatusCode());
    }

    @Test
    public void testGetCategoryWithId1() throws IOException {
        HttpUriRequest request = new HttpGet("http://localhost:4567/categories/1");
        HttpResponse response = this.client.execute(request);

        Assert.assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
    }

    @Test
    public void testGetCategoryWithId1ReturnsJSONWithName() throws IOException {
        HttpUriRequest request = new HttpGet("http://localhost:4567/categories/1");
        HttpResponse response = this.client.execute(request);

        HttpEntity httpEntity = response.getEntity();
        String output = EntityUtils.toString(httpEntity);

        Gson gson = new Gson();
        Category category = gson.fromJson(output, Category.class);
        Assert.assertEquals("Slovencina", category.getName());
    }

    @Test
    public void testGetCategoryWithNonExistentId() throws IOException {
        HttpUriRequest request = new HttpGet("http://localhost:4567/categories/9999");
        HttpResponse response = this.client.execute(request);

        Assert.assertEquals(HttpStatus.SC_NOT_FOUND, response.getStatusLine().getStatusCode());
    }

    @Test
    public void testGetAllCategories() throws IOException {
        HttpUriRequest request = new HttpGet("http://localhost:4567/categories/");
        HttpResponse response = this.client.execute(request);

        Assert.assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
    }

    @Test
    public void testUpdateCategoryWithId() throws IOException {
        HttpUriRequest request = new HttpPut("http://localhost:4567/categories/2");

        request.setHeader("name", "Pokrocila Matematika");

        HttpResponse response = this.client.execute(request);
        Assert.assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
    }

    @Test
    public void testUpdateCategoryWithNonExistentId() throws IOException {
        HttpUriRequest request = new HttpPut("http://localhost:4567/categories/9999");

        request.setHeader("name", "Pokrocila Matematika");

        HttpResponse response = this.client.execute(request);
        Assert.assertEquals(HttpStatus.SC_NOT_FOUND, response.getStatusLine().getStatusCode());
    }

    @Test
    public void testDeleteCategory() throws IOException {
        HttpUriRequest request = new HttpDelete("http://localhost:4567/categories/16");
        HttpResponse response = this.client.execute(request);

        Assert.assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
    }

    @Test
    public void testDeleteCategoryWithInvalidId() throws IOException {
        HttpUriRequest request = new HttpDelete("http://localhost:4567/categories/9999");
        HttpResponse response = this.client.execute(request);

        Assert.assertEquals(HttpStatus.SC_NOT_FOUND, response.getStatusLine().getStatusCode());
    }
}
