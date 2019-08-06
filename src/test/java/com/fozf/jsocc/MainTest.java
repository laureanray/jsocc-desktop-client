package com.fozf.jsocc;

import com.fozf.jsocc.controllers.LoginController;
import com.fozf.jsocc.utils.App;
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
    @Override
    public void start (Stage stage) throws Exception {
        FXMLLoader loader =  new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
        Parent root = loader.load();
        stage.setTitle(App.name);
        stage.setScene(new Scene(root, App.SM_WIDTH, App.SM_HEIGHT));
        LoginController controller = loader.getController();
        stage.setOnCloseRequest(e -> controller.close());
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

    @Test
    public void mustLogin() {
        clickOn("#username");
        write("laureanray");
        press(KeyCode.TAB);
        write("P@$$w0rd");
        press(KeyCode.ENTER);

    }

    @Test
    public void typeOnLoginInput () {
        clickOn("#username");
        write("This is a test!");
        clickOn("#password");
        write("password");
    }

    @Test
    public void showRegisterWindow() {
        clickOn("#registerLink");
        Stage stage = GuiTest.findStageByTitle("Register");

        if(stage == null)
        {
            throw new NullPointerException();
        }
    }

    @Test
    public void mustShowErrorIfPasswordsArentTheSame() throws InterruptedException {
        showRegisterWindow();
        clickOn("#firstName");
        write("Laurean Ray");
        clickOn("#lastName");
        write("Bahala");
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
    public void mustSuccessfullyRegisterStudent(){
        showRegisterWindow();
        clickOn("#firstName");
        write("Laurean Ray");
        clickOn("#lastName");
        write("Bahala");
        clickOn("#email");
        write("laureanraybahala@gmail.com");
        press(KeyCode.TAB);
        write("laureanray");
        clickOn("#password");
        write("P@$$w0rd");
        clickOn("#confirmPassword");
        write("P@$$w0rd");
        clickOn("#registerBtn");

        Stage stage = GuiTest.findStageByTitle("Success");

        if(stage == null)
        {
            throw new NullPointerException();
        }

    }

    @Test
    public void mustSuccessfullyRegisterInstructor() throws Exception {
        showRegisterWindow();
        clickOn("#firstName");
        write("Arvin");
        clickOn("#lastName");
        write("Dela Cruz");
        clickOn("#email");
        write("dasdsa@gmail.com");
        press(KeyCode.TAB);
        write("aasdd");
        clickOn("#password");
        write("P@$$w0rd");
        clickOn("#confirmPassword");
        write("P@$$w0rd");
        clickOn("#registerBtn");

        // Wait for atleast 2 seconds.
        sleep(2, TimeUnit.SECONDS);

        GuiTest.findStageByTitle("Success");
    }
//    private void delay(int milliseconds) throws InterruptedException {
//        TimeUnit.MILLISECONDS.sleep(milliseconds);
//    }



}
