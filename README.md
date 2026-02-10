# 桌面二维码识别（Java）

这个示例会做两件事：
1. 用 Java 自动截取整个桌面（支持多显示器）。
2. 尝试直接识别截图中的二维码内容。

如果程序暂时识别不到，它会把截图保存到当前目录。你可以用手机微信的 **扫一扫** 打开这张图片继续识别。

## 运行环境
- JDK 17+
- Maven 3.8+

## 运行步骤
```bash
mvn -q -DskipTests package
java -cp target/desktop-qr-recognizer-1.0-SNAPSHOT.jar org.example.DesktopQrRecognizer
```

## 运行结果示例
- 识别成功：输出二维码内容与格式。
- 识别失败：输出截图路径，例如：`desktop-qr-20260101_101010.png`。

## 用微信扫一扫识别
1. 程序运行后会保存一张桌面截图。
2. 在手机微信里打开「扫一扫」。
3. 选择从相册识别刚保存的截图。
