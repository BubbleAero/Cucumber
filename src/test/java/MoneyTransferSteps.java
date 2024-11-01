
import io.cucumber.java.ru.Пусть;
import io.cucumber.java.ru.Тогда;
import io.cucumber.java.ru.Когда;
import ru.netology.DashboardPage;
import ru.netology.DataHelper;
import ru.netology.LoginPage;


import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTransferSteps {
    private DashboardPage dashboardPage;
    private int initialFirstCardBalance;
    private int initialSecondCardBalance;

    @Пусть("пользователь залогинен с именем {string} и паролем {string}")
    public void пользователь_залогинен_с_именем_и_паролем(String login, String password) {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = new DataHelper.AuthInfo(login, password);
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCode();
        dashboardPage = verificationPage.validVerify(verificationCode);
        initialFirstCardBalance = dashboardPage.getCardBalance(DataHelper.getFirstCardInfo());
        initialSecondCardBalance = dashboardPage.getCardBalance(DataHelper.getSecondCardInfo());
    }

    @Когда("пользователь переводит {int} рублей с карты с номером {string} на свою {int} карту с главной страницы")
    public void пользователь_переводит_рублей_с_карты_с_номером_на_свою_карту_с_главной_страницы(int amount, String cardNumber, int cardIndex) {
        DataHelper.CardInfo sourceCard = DataHelper.getSecondCardInfo();
        if (cardNumber.equals(DataHelper.getFirstCardInfo().getCardNumber())) {
            sourceCard = DataHelper.getFirstCardInfo();
        }
        var transferPage = dashboardPage.selectCardToTransfer(DataHelper.getFirstCardInfo());
        dashboardPage = transferPage.makeValidTransfer(String.valueOf(amount), sourceCard);
    }

    @Тогда("баланс его {int} карты из списка на главной странице должен стать {int} рублей")
    public void баланс_его_карты_из_списка_на_главной_странице_должен_стать(int cardIndex, int expectedBalance) {
        int actualBalance = dashboardPage.getCardBalance(cardIndex - 1);
        assertEquals(expectedBalance, actualBalance, "Баланс карты не совпадает с ожидаемым");
    }
}
