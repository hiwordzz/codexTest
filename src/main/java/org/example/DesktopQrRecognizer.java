package org.example;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import javax.imageio.ImageIO;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 抓取整个桌面截图并识别二维码。
 */
public class DesktopQrRecognizer {

    public static void main(String[] args) throws Exception {
        BufferedImage screenshot = captureVirtualScreen();
        Path outputPath = saveScreenshot(screenshot);

        Result result = decodeQrFromImage(screenshot);
        if (result == null) {
            System.out.println("未识别到二维码。你可以打开保存的截图，用微信【扫一扫】再次识别：");
            System.out.println(outputPath.toAbsolutePath());
            return;
        }

        System.out.println("识别成功：");
        System.out.println("内容：" + result.getText());
        System.out.println("格式：" + result.getBarcodeFormat());
        System.out.println("截图已保存，可用微信扫一扫验证：");
        System.out.println(outputPath.toAbsolutePath());
    }

    private static BufferedImage captureVirtualScreen() throws Exception {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] devices = ge.getScreenDevices();

        Rectangle allScreens = new Rectangle();
        for (GraphicsDevice device : devices) {
            GraphicsConfiguration config = device.getDefaultConfiguration();
            allScreens = allScreens.union(config.getBounds());
        }

        Robot robot = new Robot();
        return robot.createScreenCapture(allScreens);
    }

    private static Result decodeQrFromImage(BufferedImage image) {
        BinaryBitmap bitmap = new BinaryBitmap(
                new HybridBinarizer(new BufferedImageLuminanceSource(image))
        );

        MultiFormatReader reader = new MultiFormatReader();
        try {
            return reader.decode(bitmap);
        } catch (NotFoundException e) {
            return null;
        }
    }

    private static Path saveScreenshot(BufferedImage image) throws Exception {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        Path path = Path.of("desktop-qr-" + timestamp + ".png");
        ImageIO.write(image, "png", new File(path.toString()));
        return path;
    }
}
