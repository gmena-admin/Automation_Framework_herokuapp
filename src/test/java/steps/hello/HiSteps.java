package steps.hello;

import io.cucumber.java.en.*;

public class HiSteps {
    
    @Given("Say Hi!")
    public void say_Hi(){
        System.out.println("Hi!");
    }

}
