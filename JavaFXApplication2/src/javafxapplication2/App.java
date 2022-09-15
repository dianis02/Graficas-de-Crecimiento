/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
//actualización git
package javafxapplication2;

import java.io.File;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import static javafxapplication2.HoveredThresholdNode.borrarPuntos;
import javax.imageio.ImageIO;

public class App extends Application {

    StackPane root = new StackPane(); //panel principal
    NumberAxis xAxis = new NumberAxis(); //ejes
    NumberAxis yAxis = new NumberAxis();
    LineChart chart = new LineChart(xAxis, yAxis); //grafica de lineas
    
    double[] arr = {24, 11, 30, 11.5, 43, 15, 60, 20, 77, 25, 90, 29}; //base
    
    
    //Graficas mayores 2 años
    PesoxEdadMayores graficaPeso = new PesoxEdadMayores();
    EstaturaxEdadMayores graficaEstatura = new EstaturaxEdadMayores();
    IMC graficaIMC = new IMC();
    //Graficas menores 2 años
    LongitudxEdadMenores graficaLongitudEdad = new LongitudxEdadMenores();
    LongitudxPesoMenores graficaLongitudPeso = new LongitudxPesoMenores();
    PesoxEdadMenores graficaPesoxEdad = new PesoxEdadMenores();
    PerimetroCefalico graficaPerimetroCefalico = new PerimetroCefalico();
    int contador = -1;
    //en meses
    int edadActual = 20;
    String sexo = "mujer";

    public void start(Stage primaryStage) {

        //cambiar dependiendo la edad
        if (GrowthChartManager.menorEdad(edadActual)) {
            chart = graficaPesoxEdad.Grafica(root, this.chart, arr, sexo);
            
        } else {
            chart = graficaPeso.Grafica(root, this.chart, arr, sexo);
        }
        
        Button button = new Button("Cambiar gráfica");
        

        //cambiar dependiendo la edad
        if (GrowthChartManager.menorEdad(edadActual)) {
            EventHandler<MouseEvent> handler = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    switch (contador) {
                        case 0:
                            root.getChildren().clear();
                            chart = graficaLongitudEdad.Grafica(root, chart, arr, sexo);
                            contador++;
                            break;
                        case 1:
                            root.getChildren().clear();
                            chart = graficaLongitudPeso.Grafica(root, chart, arr, sexo);
                            contador++;
                            break;

                        case 2:
                            root.getChildren().clear();
                            chart = graficaPerimetroCefalico.Grafica(root, chart, arr, sexo);
                            contador++;
                            break;

                        case 3:
                            root.getChildren().clear();
                            chart = graficaPesoxEdad.Grafica(root, chart, arr, sexo);
                            contador -= 3;
                            break;

                    }

                    root.getChildren().add(chart);
                }
            };
            button.setOnMouseClicked(handler);
        } else {
            EventHandler<MouseEvent> handler = new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    switch (contador) {
                        case 0:
                            root.getChildren().clear();
                            chart = graficaEstatura.Grafica(root, chart, arr, sexo);
                            contador++;
                            break;
                        case 1:
                            root.getChildren().clear();
                            chart = graficaIMC.Grafica(root, chart, arr, sexo);
                            contador++;
                            break;
                        case 2:
                            root.getChildren().clear();
                            chart = graficaPeso.Grafica(root, chart, arr, sexo);
                            contador -= 2;
                            break;

                    }

                    root.getChildren().add(chart);

                }
            };
            button.setOnMouseClicked(handler);
        }

        if (contador == -1) {
            root.getChildren().add(chart);
            contador++;
        }
        
       
        Button button2 = new Button("Imprimir Gráfica");
        root.setPadding(new Insets(5, 5, 5, 5));
        StackPane spButton = new StackPane();
        spButton.getChildren().add(button);
         spButton.getChildren().add(button2);
  StackPane.setAlignment(button, Pos.BASELINE_LEFT);
  
  StackPane.setAlignment(button2, Pos.BASELINE_RIGHT);

        VBox vbox = new VBox();
        VBox.setVgrow(root, Priority.ALWAYS);//Make line chart always grow vertically
        vbox.getChildren().addAll(root, spButton);
        
        

        Scene scene = new Scene(vbox, 800, 600);
        
         //Codigo encargado de transformar la grafica en imagen
         FileChooser fileChooser = new FileChooser();
      fileChooser.setTitle("Save");
      fileChooser.getExtensionFilters().addAll(new ExtensionFilter("All Files", "*.*"));
         
         
         
         button2.setOnMouseClicked((event)->{
                WritableImage image = scene.snapshot(null);
     File file = fileChooser.showSaveDialog(null);
      try{
        
      ImageIO.write(SwingFXUtils.fromFXImage(image, null), "PNG", file);
      
      }catch(Exception e){
          System.out.println(e);
      }
            });
       
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch();
    }

}
