package stepdefinitions;

import io.cucumber.java.fr.Alors;
import io.cucumber.java.fr.Et;
import io.cucumber.java.fr.Quand;
import io.cucumber.java.fr.Étantdonnéque;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.BasePageAndroid;
import pages.SomfyPages;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static utils.DriverMobile.getDriver;


public class PageStepDefsAndroid extends BasePageAndroid {

    private static final Logger LOG = LoggerFactory.getLogger(PageStepDefsAndroid.class);
    SomfyPages somfy = new SomfyPages();


    @Étantdonnéque("lutilisateur souhaite explorer les fonctionnalités de l'onglet Caméra Extérieure")
    public void lutilisateurSouhaiteExplorerLesFonctionnalitesDeLOngletCameraExterieure() {
        //TODO:
    }

    @Quand("il accède à l'application Somfy")
    public void ilAccedeALApplicationSomfy() {

        String expectedText= "Créer un compte";
        assertEquals(expectedText, somfy.textCréerUnCompte.getText());

    }

    @Et("il sélectionne  {string}")
    public void ilSelectionne(String expectedText) {
        somfy.btnDecouvrirSomfyProtect.click();
    }

    @Et("il sélectionne l'onglet {string}")
    public void ilSelectionneLOnglet(String expectedText) {

        scrollUp(somfy.btnSomfyOne, -40);

        somfy.btnSélectionnerCaméraExtériure.click();
    }

    @Alors("il doit être redirigé vers la page de contrôle de la caméra extérieure")
    public void ilDoitEtreRedirigeVersLaPageDeControleDeLaCameraExterieure() {
        String expectedText = "Voir plus";
        assertTrue(somfy.btnVoirPlus.getText().contains(expectedText));
        if (somfy.btnVoirPlus.isDisplayed()) {

            getDriver().navigate().back();

        }
    }

    @Et("il doit pouvoir vérifier les fonctionnalités de contrôle de la caméra")
    public void ilDoitPouvoirVerifierLesFonctionnalitesDeControleDeLaCamera() {
        somfy.textCaméraExt.isDisplayed();
       //somfy.btnVoirPlus.submit();
    }

}



