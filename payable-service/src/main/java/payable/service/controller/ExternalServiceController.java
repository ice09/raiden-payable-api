package payable.service.controller;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.annotation.Produces;

import javax.annotation.Nullable;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

@Controller("/external")
public class ExternalServiceController {

    @Get("/service/text")
    @Produces(MediaType.TEXT_PLAIN)
    public String serviceText(@Header("identifier") @Nullable String identifier) {
        return "This answer has just cost you some Tokens. The header 'identifier' was [" + ((identifier == null) ? "EMPTY" : identifier) + "]. Have fun!";
    }

    @Get("/service/image")
    @Produces(MediaType.ALL)
    public byte[] serviceImage(@Header("identifier") @Nullable String identifier) throws IOException {
        File imageFile = new File("src/main/resources/bauhaus.png");
        BufferedImage image = ImageIO.read(imageFile);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", outputStream);
        return outputStream.toByteArray();
    }
}
