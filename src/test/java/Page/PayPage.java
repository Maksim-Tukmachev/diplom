package Page;

import com.codeborne.selenide.Condition;

public class PayPage extends MainPage{

    public PaymentPage() {}

    public void checkOperationApproved() {
        notificationSuccess.waitUntil(Condition.visible, 15000);
        notificationError.waitUntil(Condition.not(Condition.visible), 15000);
    }

    public void checkOperationDeclined() {
        notificationError.waitUntil(Condition.visible, 15000);
        notificationSuccess.waitUntil(Condition.not(Condition.visible), 15000);
    }
}
