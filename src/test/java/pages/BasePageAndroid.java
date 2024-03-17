package pages;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.appium.java_client.touch.WaitOptions.waitOptions;
import static io.appium.java_client.touch.offset.PointOption.point;
import static utils.DriverMobile.getDriver;


public class BasePageAndroid {


    private static final Logger LOG = LoggerFactory.getLogger(BasePageAndroid.class);


    private org.openqa.selenium.WebDriver driver;


    public static void scrollToTop(AppiumDriver driver, int x, int y) throws InterruptedException {
        TouchAction touch = new TouchAction((PerformsTouchActions) getDriver());
        touch.press(PointOption.point(x, y))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
                .moveTo(PointOption.point(x, 0))
                .release()
                .perform();
    }

    private static int calculateOffsetForUpAndDownScroll(int scrollAmount) {
        Dimension dimension = getDriver().manage().window().getSize();
        return ((scrollAmount * dimension.height) / 100);
    }

    public static void scrollUp(WebElement element, int scrollPercentage) {
        Actions actions = new Actions(getDriver());
        actions.clickAndHold(element)
                .moveByOffset(0, (calculateOffsetForUpAndDownScroll(scrollPercentage)))
                .release()
                .perform();

    }


    public void scrollFromButtomToUp(AppiumDriver driver) throws InterruptedException {

        Dimension dimension = driver.manage().window().getSize();
        int start_x = (int) (dimension.width * 0.5);
        int start_y = (int) (dimension.height * 0.8);
        int end_x = (int) (dimension.width * 0.5);
        int end_y = (int) (dimension.height * 0.5);

        TouchAction touch = new TouchAction((PerformsTouchActions) getDriver());
        touch.press(point(start_x, start_y)).waitAction(waitOptions(Duration.ofSeconds(1))).moveTo(point(end_x, end_y)).release().perform();
        Thread.sleep(3000);

    }


    public void scrollFromUpToButtom(AppiumDriver driver) throws InterruptedException {

        Dimension dimension = getDriver().manage().window().getSize();
        int start_x = (int) (dimension.width * 0.2);
        int start_y = (int) (dimension.height * 0.2);
        int end_x = (int) (dimension.width * 0.2);
        int end_y = (int) (dimension.height * 0.5);

        TouchAction touch = new TouchAction((PerformsTouchActions) getDriver());
        touch.press(point(start_x, start_y)).waitAction(waitOptions(Duration.ofSeconds(1))).moveTo(point(end_x, end_y)).release().perform();
        Thread.sleep(3000);
    }


    public void scrollDown(AppiumDriver driver) throws InterruptedException {

        Dimension dimension = driver.manage().window().getSize();
        int start_x = (int) (dimension.width * 0.5);
        int start_y = (int) (dimension.height * 0.5);

        int end_x = (int) (dimension.width * 0.2);
        int end_y = (int) (dimension.height * 0.2);

        TouchAction touch = new TouchAction((PerformsTouchActions) getDriver());
        touch.press(point(start_x, start_y)).waitAction(waitOptions(Duration.ofSeconds(1))).moveTo(point(end_x, end_y)).release().perform();
        Thread.sleep(3000);
    }

    public int extractHours(String text) {
        if (text != null && !text.isEmpty()) {
            String[] parts = text.split("[^\\d]");
            for (String part : parts) {
                if (!part.isEmpty()) {
                    try {
                        return Integer.parseInt(part);
                    } catch (NumberFormatException e) {
                        // Handle the exception or log the error if needed
                    }
                }
            }
        }
        return 0;
    }


    public int extractMinutes(String text) {
        String[] parts = text.split("[^\\d]");
        if (parts.length > 1) {
            return Integer.parseInt(parts[1]);
        }
        return 0;
    }

    public void performSwipe(int x, int y) throws MalformedURLException {
        Dimension dimension = getDriver().manage().window().getSize();

        int start_x = (int) (dimension.width * 0.5);
        int start_y = x;

        int end_x = (int) (dimension.width * 0.2);
        int end_y = y;

        TouchAction touch = new TouchAction((PerformsTouchActions) getDriver());
        touch.press(point(start_x, start_y))
                .waitAction(waitOptions(Duration.ofSeconds(1)))
                .moveTo(point(end_x, end_y))
                .release()
                .perform();
    }

    public static void dragAndDrop(double endX, double endY) {
        ((JavascriptExecutor) getDriver()).executeScript(
                "mobile: dragGesture",
                ImmutableMap.of(
                        "endX", endX,
                        "endY", endY,
                        "speed", 5000
                ));
    }

    public static void scroll(WebElement element, String direction) {

        ((JavascriptExecutor) getDriver()).executeScript("mobile: scrollGesture",
                ImmutableMap.of(
                        "elementId", ((RemoteWebElement) element).getId(),
                        "direction", direction.toLowerCase(),
                        "percent", 3
                ));
    }

    public int parseTimeToMinutes(String timeText) {
        try {
            String[] timeParts = timeText.split(":");
            int hour = 0;
            int minute = 0;
            if (timeParts.length >= 1 && !timeParts[0].isEmpty()) {
                hour = Integer.parseInt(timeParts[0].replaceAll("\\D+", ""));
            }
            if (timeParts.length >= 2 && !timeParts[1].isEmpty()) {
                minute = Integer.parseInt(timeParts[1].replaceAll("\\D+", ""));
            }
            return hour * 60 + minute;
        } catch (NumberFormatException e) {
            throw new RuntimeException("Geçersiz saat formatı: " + timeText);
        }
    }


    public void contrôleHorlogePriview() throws MalformedURLException, InterruptedException {
        // Trouver l'élément de l'heure de départ
        List<WebElement> departureElements = getDriver().findElements(MobileBy.className("XCUIElementTypeStaticText"));
        WebElement departureElement = null;
        for (WebElement element : departureElements) {
            String label = element.getAttribute("label");
            if (label != null && label.matches("\\d{2}:\\d{2}")) {
                departureElement = element;
                break;
            }
        }

        // Trouver l'élément de l'heure d'arrivée
        List<WebElement> arrivalElements = getDriver().findElements(MobileBy.className("XCUIElementTypeStaticText"));
        WebElement arrivalElement = null;
        for (WebElement element : arrivalElements) {
            String label = element.getAttribute("label");
            if (label != null && label.matches("\\d{2}:\\d{2}") && !label.equals(departureElement.getAttribute("label"))) {
                arrivalElement = element;
                break;
            }
        }

// Trouver l'élément de la différence
        List<WebElement> differenceElements = getDriver().findElements(MobileBy.className("XCUIElementTypeStaticText"));
        WebElement differenceElement = null;
        for (WebElement element : differenceElements) {
            String label = element.getAttribute("label");
            if (label != null && label.matches("\\d+min") && !label.equals(departureElement.getAttribute("label")) && !label.equals(arrivalElement.getAttribute("label"))) {
                differenceElement = element;
                break;
            }
        }

        if (departureElement != null && arrivalElement != null && differenceElement != null) {
            // Obtenir la valeur de l'heure de départ
            String departureValue = departureElement.getAttribute("label");
            // Obtenir la valeur de l'heure d'arrivée
            String arrivalValue = arrivalElement.getAttribute("label");
            // Obtenir la valeur de la différence
            String differenceValue = differenceElement.getAttribute("label");

            System.out.println("Départ: " + departureValue);
            System.out.println("Arrivée: " + arrivalValue);
            System.out.println("Difference: " + differenceValue);

            // Effectuer le calcul de la différence
            String[] departureParts = departureValue.split(":");
            String[] arrivalParts = arrivalValue.split(":");
            int departureHour = Integer.parseInt(departureParts[0]);
            int departureMinute = Integer.parseInt(departureParts[1]);
            int arrivalHour = Integer.parseInt(arrivalParts[0]);
            int arrivalMinute = Integer.parseInt(arrivalParts[1]);

            int differenceHour = arrivalHour - departureHour;
            int differenceMinute = arrivalMinute - departureMinute;
            if (differenceMinute < 0) {
                differenceHour--;
                differenceMinute += 60;
            }

            System.out.println("Arrivée - Départ = " + differenceHour + "h " + differenceMinute + "min");

            // Verifier la difference
            int calculatedDifference = differenceHour * 60 + differenceMinute;
            String[] differenceParts = differenceValue.split("min");
            int actualDifference = Integer.parseInt(differenceParts[0].trim());

            if (calculatedDifference == actualDifference) {
                System.out.println("Le calcul de la différence est correct : " + calculatedDifference + " min");
            } else {
                System.out.println("Le calcul de la différence est incorrect. La valeur attendue est : " + actualDifference + " min, mais le calcul a donné : " + calculatedDifference + " min");
            }
        }
    }


    public void contrôleHorlogeVélo() throws MalformedURLException, InterruptedException {

// Trouver l'élément de l'heure de départ
        List<WebElement> departureElements = getDriver().findElements(By.xpath("(//android.widget.TextView)[16]"));
        WebElement departureElement = departureElements.get(0); // Assumptions: there is only one departure element, and it's the first element

// Trouver l'élément de l'heure d'arrivée
        List<WebElement> arrivalElements = getDriver().findElements(By.xpath("(//android.widget.TextView)[17]"));
        WebElement arrivalElement = arrivalElements.get(0); // Assumptions: there is only one arrival element, and it's the first element

// Trouver l'élément de la différence
        List<WebElement> differenceElements = getDriver().findElements(By.xpath("(//android.widget.TextView)[15]"));
        WebElement differenceElement = differenceElements.get(0); // Assumptions: there is only one difference element, and it's the first element

        if (departureElement != null && arrivalElement != null && differenceElement != null) {
            // Obtenir la valeur de l'heure de départ
            String departureValue = departureElement.getText();
            // Obtenir la valeur de l'heure d'arrivée
            String arrivalValue = arrivalElement.getText();
            // Obtenir la valeur de la différence
            String differenceValue = differenceElement.getText();

            System.out.println("Départ: " + departureValue);
            System.out.println("Arrivée: " + arrivalValue);
            System.out.println("Difference: " + differenceValue);

            // Effectuer le calcul de la différence
            String[] departureParts = departureValue.split(":");
            String[] arrivalParts = arrivalValue.split(":");
            int departureHour = Integer.parseInt(departureParts[0]);
            int departureMinute = Integer.parseInt(departureParts[1]);
            int arrivalHour = Integer.parseInt(arrivalParts[0]);
            int arrivalMinute = Integer.parseInt(arrivalParts[1]);

            int differenceHour = arrivalHour - departureHour;
            int differenceMinute = arrivalMinute - departureMinute;
            if (differenceMinute < 0) {
                differenceHour--;
                differenceMinute += 60;
            }

            // Vérifier la différence
            int calculatedDifference = differenceHour * 60 + differenceMinute;
            String[] differenceParts = differenceValue.split("min");
            String numericDifferenceString = differenceParts[0].trim();

            int actualDifference;
            try {
                actualDifference = Integer.parseInt(numericDifferenceString);
            } catch (NumberFormatException e) {
                System.out.println("Unable to parse the difference value as an integer: " + numericDifferenceString);
                return; // or handle the error appropriately
            }

            if (calculatedDifference == actualDifference) {
                System.out.println("Le calcul de la différence est correct : " + calculatedDifference + " min");
            } else {
                System.out.println("Le calcul de la différence est incorrect. La valeur attendue est : " + actualDifference + " min, mais le calcul a donné : " + calculatedDifference + " min");
            }
        } else {
            System.out.println("Départ, Arrivée ou Difference pas trouvée.");
        }


    }

    public Map<String, String> frDayMap = new HashMap<String, String>() {{
        put("Lun.", "01");
        put("Mar.", "02");
        put("Mer.", "03");
        put("Jeu.", "04");
        put("Ven.", "05");
        put("Sam.", "06");
        put("Dim.", "07");
    }};


    public String getExpectedDate(String dayOfWeek, String dayOfMonth, String month) {
        String dayNumber = frDayMap.get(dayOfWeek);
        return dayOfWeek + dayNumber + "." + month;
    }

    // Gün numarasını alarak dönüştüren yardımcı metot
    public String getFormattedDate(String dayOfWeek, String dayOfMonth, String month) {
        String dayNumber = frDayMap.get(dayOfWeek);
        return dayOfWeek + dayNumber + " " + month;
    }


}




