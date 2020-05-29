package test.spring.controller;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.annotation.Put;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.validation.Validated;
import io.micronaut.http.annotation.*;
import java.util.HashMap;
import java.util.List;

import com.google.gson.*;
import test.spring.model.UserInput;

import org.json.JSONException;
import org.json.JSONObject;

import javax.annotation.Nullable;
import javax.persistence.RollbackException;




import test.spring.repo.UserInputRepository;
//import api.test.repository.UserInputInterface;


@Validated
@Controller("/input_user")
public class UserInputController{

    private UserInputRepository repository;

    UserInputController(UserInputRepository repository) {
        this.repository = repository;
    }
    
    @Get(produces = MediaType.APPLICATION_JSON)
    public String index(@QueryValue int page, @QueryValue int limit) {
        final HashMap<String, Object> data = new HashMap<>();
        try {
            final List<UserInput> userInput = repository.findAll(page, limit);
            data.put("page", Math.ceil(repository.size() / limit));
            data.put("status", "ok");
            data.put("message", "Data Classes");
            data.put("data", userInput);
            return (new Gson().toJson(data));
        } catch (Exception e) {
            data.put("status", "error");
            data.put("message", e.getMessage());
            return (new Gson()).toJson(data);
        }
    }

    @Get("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String show(@PathVariable Long id) {
        return (new Gson()).toJson(repository.findById(id));
    }
/*
    @Post(consumes=MediaType.APPLICATION_JSON)
    public String save(@Body UserInput t) {
        HashMap<String, Object> data = new HashMap<>();
        if (repository.save(t)) {
            data.put("status", "ok");
        } else {
            data.put("status", "fail");
        }
        return (new Gson()).toJson(data);
    }
    */
    @Post(consumes = MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String save(@Body UserInput userInput) throws JSONException {
        HashMap<String, Object> data = new HashMap<>();
        try {
            String result = repository.save(userInput);
            UserInput user = gson.fromJson(result, UserInput.class);
            data.put("status", "ok");
            data.put("message", "data teacher berhasil disimpan");
            data.put("data", user);
        } catch (RollbackException e) {
            data.put("status", "error");
            data.put("message", "data teacher gagal disimpan");
            data.put("data", e.getMessage());
        }
        return gson.toJson(data);
    }


    @Put(consumes=MediaType.APPLICATION_JSON)
    public String update(@Body UserInput c) {
        HashMap<String, Object> data = new HashMap<>();
        if (repository.update(c.getId(), c.getUserName(), c.getUserPassword())) {
            data.put("status", "ok");
        } else {
            data.put("status", "fail");
        }
        return (new Gson()).toJson(data);
    }

    @Delete("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String destroy(@PathVariable Long id) {
        HashMap<String, Object> data = new HashMap<>();
        if (repository.destroy(id)) {
            data.put("status", "ok");
        } else {
            data.put("status", "fail");
        }
        return (new Gson()).toJson(data);
    }

}