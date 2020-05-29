package test.spring.repo;


import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import test.spring.model.UserInput;


public interface UserInputInterface{

    Long size();
    List<UserInput> findAll(int page, int limit);
    UserInput findById(@NotNull Long id);    
    boolean save(@NotNull UserInput userInput);
    boolean update(@NotNull Long id, @NotBlank String  user_name, @NotBlank String user_password);
    boolean destroy(@NotNull Long id);




}