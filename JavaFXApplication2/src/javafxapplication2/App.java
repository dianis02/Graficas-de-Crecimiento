/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication2;
import com.sun.javafx.charts.Legend;
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
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import static javafxapplication2.HoveredThresholdNode.borrarPuntos;

public class App extends Application {
    StackPane root = new StackPane(); //panel principal
    NumberAxis xAxis = new NumberAxis(); //ejes
    NumberAxis yAxis = new NumberAxis();
    LineChart chart = new LineChart(xAxis, yAxis); //grafica de lineas
    double[] arr ={24,11,30,11.5,43,15,60,20,77,25,90,29};
   PesoxEdadMayores graficaPeso = new PesoxEdadMayores();
   EstaturaxEdadMayores graficaEstatura = new EstaturaxEdadMayores();
    int contador = -1;
    public void start(Stage primaryStage){
        
    
        //GraficaPeso graficaPeso = new GraficaPeso();
        chart = graficaPeso.Grafica(root,this.chart,arr);
        
        setLegend();
    

        
         Button button = new Button("Cambiar gráfica");
        button.setOnMouseClicked((event)->{
            switch(contador){
                case 0:
                     root.getChildren().clear();
                    chart = graficaEstatura.Grafica(root,this.chart,arr);
                    setLegend();
                     lineasGuia();
                    contador++;
                    break;
                case 1:
                    root.getChildren().clear();
                    chart = graficaPeso.Grafica(root,this.chart,arr);
                     lineasGuia();
                     setLegend();
                    contador--;
                    break;
                    
            }
            
            root.getChildren().add(chart);
        
        });
        
        
        if(contador==-1){
           lineasGuia();
        root.getChildren().add(chart);
        contador++;
        }
        root.setPadding(new Insets(5,5,5,5));
        StackPane spButton = new StackPane();
        spButton.getChildren().add(button);

        VBox vbox = new VBox();
        VBox.setVgrow(root, Priority.ALWAYS);//Make line chart always grow vertically
        vbox.getChildren().addAll(root, spButton);



        Scene scene  = new Scene(vbox,800,600);
        
        
        
        
        primaryStage.setScene(scene);
        primaryStage.show();
        
    }
    
    private void lineasGuia(){
        for (XYChart.Series<Double, Double> ser :(ObservableList<XYChart.Series<Double, Double>> )chart.getData()) {
            if (ser.getName().equals("crecimiento")){
//if Name is "blue" then continue
            
//for all series, take date, each data has Node (symbol) for representing point
            for (XYChart.Data<Double, Double> data : ser.getData()) {
                final XYChart.Series series8 = HoveredThresholdNode.lineaGuiaX(data.getXValue(), data.getYValue(), "guia1");
        final XYChart.Series series9 = HoveredThresholdNode.lineaGuiaY(data.getXValue(), data.getYValue(), "guia2");
        
                data.getNode().setOnMouseExited(new EventHandler<MouseEvent>(){
                    @Override public void handle(MouseEvent mouseEvent) {
                       System.out.println("borrar");
                        HoveredThresholdNode.eliminarLineasGuia(chart,series8,series9);
                        setLegend();
                    }});
                    
                    data.getNode().setOnMouseEntered(new EventHandler<MouseEvent>(){
                    @Override public void handle(MouseEvent mouseEvent) {
                      System.out.println("dibujar");
               
                       chart.getData().addAll(series8,series9);
                       setLegend();

                //solo lineas (lineas guia)
                borrarPuntos(series8);
                borrarPuntos(series9);

                Node line8 = series8.getNode().lookup(".chart-series-line");
                Node line9 = series9.getNode().lookup(".chart-series-line");
               Node symbol8 = series8.getNode();
               
                line8.setStyle("-fx-stroke: #000000;-fx-stroke-dash-array: 2 12;");
                line9.setStyle("-fx-stroke: #000000;-fx-stroke-dash-array: 2 12;");
                System.out.println( data.getXValue()+","+ data.getYValue());
                    }
                
                    }
            );
        }
        }
        }
        
    }
    
    
    private void setLegend(){
    Legend legend = (Legend)chart.lookup(".chart-legend");  
    Legend.LegendItem li1=new Legend.LegendItem("percentil97", new Rectangle(10,4,Color.RED));
    Legend.LegendItem li2=new Legend.LegendItem("percentil90", new Rectangle(10,4,Color.YELLOW));
    Legend.LegendItem li3=new Legend.LegendItem("percentil75", new Rectangle(10,4,Color.GREEN));
     Legend.LegendItem li4=new Legend.LegendItem("percentil50", new Rectangle(10,4,Color.GREEN));
    Legend.LegendItem li5=new Legend.LegendItem("percentil25", new Rectangle(10,4,Color.GREEN));
    Legend.LegendItem li6=new Legend.LegendItem("percentil10", new Rectangle(10,4,Color.YELLOW));
     Legend.LegendItem li7=new Legend.LegendItem("percentil3", new Rectangle(10,4,Color.RED));
    Legend.LegendItem li=new Legend.LegendItem("crecimiento", new Rectangle(10,4,Color.BLUE));
    legend.getItems().setAll(li1,li2,li3,li4,li5,li6,li7,li);
    }
   

public static void main(String[] args) {
    launch();
}

}
