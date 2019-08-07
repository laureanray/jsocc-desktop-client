package com.fozf.jsocc;

import com.fozf.jsocc.controllers.LoginController;
import com.fozf.jsocc.utils.App;
import com.github.javafaker.Faker;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.loadui.testfx.GuiTest;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import java.util.NoSuchElementException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;


public class MainTest extends ApplicationTest {
    Faker faker;
    @Override
    public void start (Stage stage) throws Exception {
        FXMLLoader loader =  new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
        Parent root = loader.load();
        stage.setTitle(App.name);
        stage.setScene(new Scene(root, App.SM_WIDTH, App.SM_HEIGHT));
        LoginController controller = loader.getController();
        stage.setOnCloseRequest(e -> controller.close());
        this.faker = new Faker();
        stage.show();

    }

    @Before
    public void setUp () throws Exception {
    }

    @After
    public void tearDown () throws Exception {
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
    }

    private void showRegisterWindow(){
        clickOn("#registerLink");
        sleep(1, TimeUnit.SECONDS);
        GuiTest.findStageByTitle("Register");
    }

    private String usernameGenerator(String firstName, String lastName){
        return firstName.substring(0, 2).toLowerCase() + lastName.toLowerCase();
    }

    @Test
    public void mustRegisterAndLogin(){
        String fn = faker.name().firstName();
        String ln = faker.name().lastName();
        showRegisterWindow();
        clickOn("#firstName");
        write(fn);
        clickOn("#lastName");
        write(ln);
        clickOn("#email");
        write(usernameGenerator(fn, ln) + "@domain.com");
        press(KeyCode.TAB);
        write(usernameGenerator(fn, ln));
        moveTo("#password");
        clickOn("#password");
        write("asd");
        clickOn("#confirmPassword");
        write("asd");
        clickOn("#registerBtn");
        sleep(1, TimeUnit.SECONDS);
        press(KeyCode.ENTER);
        sleep(1, TimeUnit.SECONDS);
        GuiTest.findStageByTitle("Student Dashboard");
        assertThat(App.isStudent, is(true));
        assertNotNull(App.student);
        assertThat(App.student.getFirstName(), is(fn));
    }

    @Test
    public void mustShowErrorIfInvalidCredentials(){
        clickOn("#username");
        write("asdasd");
        clickOn("#password");
        write("asdasdsa");
        clickOn("#loginButton");
        sleep(1, TimeUnit.SECONDS);
        GuiTest.find("#errorText");
    }

    @Test
    public void mustShowErrorIfPasswordsArentTheSame() throws InterruptedException {
        String fn = faker.name().firstName();
        String ln = faker.name().lastName();
        showRegisterWindow();
        clickOn("#firstName");
        write(fn);
        clickOn("#lastName");
        write(ln);
        clickOn("#email").
        write("a@domain.com");
        press(KeyCode.TAB);
        write("laureanray");
        moveTo("#password");
        clickOn("#password");
        write("asd");
        clickOn("#confirmPassword");
        write("asqdz");
        clickOn("#registerBtn");

        Text text = (Text) GuiTest.find("#errorText");

        if(text == null){
            throw new RuntimeException();
        }

        assertThat(text.getText(), is("Passwords don't match."));

    }


    @Test
    public void mustSuccessfullyRegisterInstructor() throws Exception {
        String fn = faker.name().firstName();
        String ln = faker.name().lastName();
        showRegisterWindow();
        clickOn("#firstName");
        write(fn);
        clickOn("#lastName");
        write(ln);
        clickOn("#email");
        write(usernameGenerator(fn, ln) + "@gmail.com");
        press(KeyCode.TAB);
        write(usernameGenerator(fn, ln));
        clickOn("#password");
        write("P@$$w0rd");
        clickOn("#confirmPassword");
        write("P@$$w0rd");
        moveTo("#checkbox");
        clickOn("#checkbox");
        clickOn("#registerBtn");
        // Wait for atleast 2 seconds.
        sleep(2, TimeUnit.SECONDS);

        GuiTest.findStageByTitle("Success");
    }

}
