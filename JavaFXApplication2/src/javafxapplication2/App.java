/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication2;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Pane;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import static javafxapplication2.HoveredThresholdNode.borrarPuntos;

public class App extends Application {
    Pane root = new Pane(); //panel principal
    NumberAxis xAxis = new NumberAxis(); //ejes
    NumberAxis yAxis = new NumberAxis();
    LineChart chart = new LineChart(xAxis, yAxis); //grafica de lineas
    double[] arr ={24,11,30,11.5,43,15,60,20,77,25,90,29};
    
    public void start(Stage primaryStage){
    
        GraficaPeso graficaPeso = new GraficaPeso();
        chart = graficaPeso.Grafica(root,xAxis,yAxis,this.chart,arr);
        for (XYChart.Series<Double, Double> ser :(ObservableList<XYChart.Series<Double, Double>> )chart.getData()) {
            if (ser.getName().equals("crecimiento")){
//if Name is "blue" then continue
            
//for all series, take date, each data has Node (symbol) for representing point
            for (XYChart.Data<Double, Double> data : ser.getData()) {
                final XYChart.Series series8 = HoveredThresholdNode.lineaGuiaX(data.getXValue(), data.getYValue(), "guia1");
        final XYChart.Series series9 = HoveredThresholdNode.lineaGuiaY(data.getXValue(), data.getYValue(), "guia2");
        
                data.getNode().setOnMouseClicked(new EventHandler<MouseEvent>(){
                    @Override public void handle(MouseEvent mouseEvent) {
                    if(mouseEvent.getClickCount()==2){
                         System.out.println("borrar");
                        HoveredThresholdNode.eliminarLineasGuia(chart,series8,series9);
              
                    }else if(mouseEvent.getClickCount()==1 && !chart.getData().contains(series8) &&!chart.getData().contains(series9)){
                        System.out.println("dibujar");
               
                       chart.getData().addAll(series8,series9);

                //solo lineas (lineas guia)
                borrarPuntos(series8);
                borrarPuntos(series9);

                Node line8 = series8.getNode().lookup(".chart-series-line");
                Node line9 = series9.getNode().lookup(".chart-series-line");
                line8.setStyle("-fx-stroke: #000000;-fx-stroke-dash-array: 2 12;");
                line9.setStyle("-fx-stroke: #000000;-fx-stroke-dash-array: 2 12;");
                System.out.println( data.getXValue()+","+ data.getYValue());
                    }
                
                    }
            });
        }
        }
        }
        
        root.getChildren().add(chart);
        root.setPadding(new Insets(5,5,5,5));
        Scene scene = new Scene(root,1000,800);
        primaryStage.setScene(scene);
        primaryStage.show();
        
    }
   

public static void main(String[] args) {
    launch();
}

}
