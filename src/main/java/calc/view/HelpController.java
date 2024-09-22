package calc.view;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.InputStream;

public class HelpController {

    @FXML
    private ImageView helpImage;

    @FXML
    public void initialize() {
        try {
            InputStream imageStream = getClass().getResourceAsStream("/HelpButtonImage.jpg");
            if (imageStream == null) {
                System.out.println("Изображение не найдено!");
            } else {
                Image image = new Image(imageStream);
                helpImage.setImage(image);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}