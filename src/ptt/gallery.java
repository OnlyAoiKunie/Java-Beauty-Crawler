package ptt;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;

public class gallery extends Application {
    private List<Image> images = new ArrayList<>();
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        loadImages();
        primaryStage.setWidth(1200);
        primaryStage.setHeight(800);
        primaryStage.setTitle("圖片畫廊");
        ImageView mainImageView = new ImageView();
        mainImageView.setImage(images.get(0)); //預設將最大預覽圖為第一張圖片
        mainImageView.setFitWidth(primaryStage.getWidth() - primaryStage.getWidth() / 4);
        mainImageView.setFitHeight(primaryStage.getHeight() - primaryStage.getHeight() / 4);
        mainImageView.setPreserveRatio(true);
        HBox imagesStore = new HBox(4);
        for (int i = 0; i < images.size(); ++i) {
            ImageView imageView = new ImageView();
            imageView.setOnMouseClicked(event ->
                    mainImageView.setImage(imageView.getImage())
            );
            imageView.setImage(images.get(i)); //設定圖片
            imageView.setFitWidth(primaryStage.getWidth() / 4);
            imageView.setFitHeight(primaryStage.getHeight() / 4 - 50);
            imageView.setPreserveRatio(true);
            imagesStore.getChildren().add(imageView);
        }
        ScrollPane scrollPane = new ScrollPane(imagesStore);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setMaxWidth(primaryStage.getWidth());
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(mainImageView);
        borderPane.setBottom(scrollPane);
        primaryStage.setScene(new Scene(borderPane));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    private void loadImages() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File(UI.path));
        directoryChooser.setTitle("Choose Directory");
        File directory = directoryChooser.showDialog(null);
        if (directory != null) {
            File[] files = directory.listFiles();
            String fileName;
            for (int i = 0; i < files.length; ++i) {
                fileName = files[i].getName().toLowerCase();
                if (files[i] != null && (fileName.endsWith(".jpg") || fileName.endsWith(".png") ||
                        fileName.endsWith(".bmp"))) {
                    try {
                        images.add(SwingFXUtils.toFXImage(ImageIO.read(files[i]), null));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            System.exit(1);
        }
    }
    public void go() {
        launch();
    }
}
