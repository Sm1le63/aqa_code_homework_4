import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import static com.codeborne.selenide.Selenide.*;

public class AppCardDeliveryTest {

    String generateDate(int days){
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }


    @Test
    void shouldTestForm(){
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Самара"); //data-test-id="city"
        String date = generateDate(10);   // генерация даты — не ранее трёх дней с текущей даты
        SelenideElement data = $("[data-test-id='date'] input"); //data-test-id="date"
        data.sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        data.setValue(date);
        $("[data-test-id='name'] input").setValue("Иван Иванов");
        $("[data-test-id='phone'] input").setValue("+79270070707");
        $("[data-test-id='agreement']").click();
        $$("button").find(Condition.exactText("Забронировать")).click();
        $("[data-test-id='notification'] button").shouldBe(Condition.visible, Duration.ofSeconds(15));
        $(".notification__content").shouldHave(Condition.text("Встреча успешно забронирована на " + date), Duration.ofSeconds(15));
    }
}

