import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.openqa.selenium.Keys.BACK_SPACE;

public class CardDeliveryOrderTests {

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        Selenide.open("http://localhost:9999/");

    }

    public String dataGenerate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @Test
    void fieldsAreFilledCorrectly() {
        $("[placeholder='Город']").setValue("Волгоград");
        $("[data-test-id='date'] .input__control").click();
        $("[data-test-id='date'] .input__control").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id='date'] .input__control").sendKeys(BACK_SPACE);
        $("[data-test-id='date'] .input__control").setValue(dataGenerate(3));
        $("[name='name']").setValue("Иванов Иван");
        $("[name='phone']").setValue("+79876543211");
        $("[data-test-id='agreement']").click();
        $(".button__text").click();
        $("div .notification__content").should(visible, Duration.ofSeconds(15));
        $("div .notification__content").shouldHave(exactText("Встреча успешно забронирована на " + dataGenerate(3)));

    }

}
