package br.com.dashboardjtrac;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Start extends Application{
	
		public static void main(String[] args) {
			launch(args);
		}
		
		@Override
		public void start(Stage primaryStage) throws Exception {


//	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/view/DesktopView.fxml"));
	        //FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/pesquisaDoc.fxml"));
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/chamadosView.fxml"));
	        Parent parent = loader.load();
	        
	        //PaginaPrincipalController controller = loader.getController();
	        
//	        Scene scene = new Scene(parent, Color.TRANSPARENT);
	        Scene scene = new Scene(parent, Color.BLUE);
	        
	        //primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/img/samsung_ico_512.png")));

//	        primaryStage.setOpacity(1.0);
	        primaryStage.setScene(scene);
	        primaryStage.initStyle(StageStyle.DECORATED);
	        primaryStage.setResizable(true);
	        primaryStage.setMaximized(true);
	        
	        
	        primaryStage.setOnHidden(e -> {
	           //controller.shutdown();
	            Platform.exit();
	        });
	        
	        primaryStage.show();
	        
	        

	    }

}
