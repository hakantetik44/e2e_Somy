package pages;


import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static utils.DriverMobile.getDriver;


public class SomfyPages {

    public SomfyPages() {

        PageFactory.initElements(new AppiumFieldDecorator(getDriver()), this);

    }


    @AndroidFindBy(id = "com.myfox.android.mss:id/txtDiscover")
    public WebElement btnDecouvrirSomfyProtect;

    @AndroidFindBy(id = "com.myfox.android.mss:id/btnSignUp")
    public WebElement textCréerUnCompte;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Somfy One']")
    public WebElement btnSomfyOne;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text=\"Caméra extérieure\"]")
    public WebElement textCaméraExt;

    @AndroidFindBy(id = "com.myfox.android.mss:id/bloc_soc")
    public WebElement btnSélectionnerCaméraExtériure;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text=\"Voir plus\"]")
    public WebElement btnVoirPlus;



}
