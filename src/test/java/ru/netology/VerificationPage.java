package ru.netology;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {
    private final SelenideElement codeField = $("[data-test-id=code] input");
    private  final SelenideElement verifyButton = $("[data-test-id=action-verify]");

    public VerificationPage() { codeField.shouldBe(visible); }

    public ru.netology.DashboardPage validVerify(DataHelper.VerificationCode verificationCode) {
        codeField.setValue(verificationCode.getCode());
        verifyButton.click();
        return new ru.netology.DashboardPage();

    }
}
