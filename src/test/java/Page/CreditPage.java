package Page;

import com.codeborne.selenide.Condition;


public class CreditPage extends MainPage{
    public CreditPage() {}

    public void checkOperationApproved() { notificationSuccess.waitUntil(Condition.visible, 15000); }

    public void checkOperationDeclined() {
        notificationError.waitUntil(Condition.visible, 15000);
    }
}