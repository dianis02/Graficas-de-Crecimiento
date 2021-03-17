/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package javafxapplication2;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Pane;
import java.util.*;
import javafx.beans.InvalidationListener;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.collections.*;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.*;
import javafx.geometry.Side;
import javafx.scene.chart.Axis;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.shape.Line;

public class App extends Application {
Pane root = new Pane();
 
NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();

LineChart chart = new LineChart(xAxis, yAxis);
//XYChart.Series series8 = new XYChart.Series();
       
    @Override
    public void start(Stage primaryStage) {
        //panel principal
        //Pane root = new Pane();
        //Manejador datos csv
        GrowthChartManager manager = new GrowthChartManager();
        //lector del csv
        ReadExcelFile reader = new ReadExcelFile();
        //arreglo con toda la información de csv
        int edadActual = 30; //provisional, esto se jalara de la base
        int sexo = 1; //provisonal, esto se jalara de la base
        String archivo = null;
        if(manager.edad(edadActual)){
            archivo = manager.elegirTablaBebes(sexo);
        }else{
            archivo = manager.elegirTablaPeso(sexo);
        }
        
        ArrayList<String> arrli = reader.leer(archivo);
        System.out.println(archivo);
        

        //Creamos las series de todos los percentiles estandar
        XYChart.Series series1 = new XYChart.Series();
        XYChart.Series series2 = new XYChart.Series();
        XYChart.Series series3 = new XYChart.Series();
        XYChart.Series series4 = new XYChart.Series();
        XYChart.Series series5 = new XYChart.Series();
        XYChart.Series series6 = new XYChart.Series();
        XYChart.Series series7 = new XYChart.Series();
        //series8.getData().add(new XYChart.Data(30,0));
        //series8.getData().add(new XYChart.Data(30,11.5));
        
        //Llenamos cada serie
        for(int i =1; i<arrli.size();i++){
            ArrayList<String> values = manager.getRecordFromLine(arrli.get(i));
            for(int j=0; j<values.size(); j++){
                switch(j){
                    case 4:
                    series1.getData().add(new XYChart.Data(Double.parseDouble(values.get(0)),Double.parseDouble(values.get(j))));
                    break;
                    case 5:
                    series2.getData().add(new XYChart.Data(Double.parseDouble(values.get(0)),Double.parseDouble(values.get(j))));
                    break;
                    case 6:
                    series3.getData().add(new XYChart.Data(Double.parseDouble(values.get(0)),Double.parseDouble(values.get(j))));
                    break;
                    case 7:
                    series4.getData().add(new XYChart.Data(Double.parseDouble(values.get(0)),Double.parseDouble(values.get(j))));
                    break;
                    case 8:
                    series5.getData().add(new XYChart.Data(Double.parseDouble(values.get(0)),Double.parseDouble(values.get(j))));
                    break;
                    case 9:
                    series6.getData().add(new XYChart.Data(Double.parseDouble(values.get(0)),Double.parseDouble(values.get(j))));
                    break;
                    case 10:
                    series7.getData().add(new XYChart.Data(Double.parseDouble(values.get(0)),Double.parseDouble(values.get(j))));
                    break;
                }
            }
        }

        series1.setName("percentil3");
        series2.setName("percentil10");
        series3.setName("percentil25");
        series4.setName("percentil50");
        series5.setName("percentil75");
        series6.setName("percentil90");
        series7.setName("percentil97");

        double[] arr ={24,11,30,11.5,43,15,60,20,77,25,90,29};
        //NumberAxis xAxis = new NumberAxis();
        //NumberAxis yAxis = new NumberAxis();
        xAxis.setAutoRanging(true);
        //xAxis.setForceZeroInRange(false);
        yAxis.setAutoRanging(true);
        //yAxis.setForceZeroInRange(false);
        xAxis.setLabel("Edad");
        yAxis.setLabel("Peso");
         chart = new LineChart(
        xAxis, yAxis,
        FXCollections.observableArrayList(
        new XYChart.Series(
        "crecimiento",
        FXCollections.observableArrayList(
        plot(arr)
        )
        )
        )
        );

        chart.setCursor(Cursor.CROSSHAIR);

        chart.setPrefSize(1000, 700);
        XYChart.Series series = (XYChart.Series)chart.getData().get(0);
        chart.getData().remove(0,1);
        chart.getData().addAll(series1, series2, series3,series4,series5,series6,series7,series);
        chart.setAnimated(false);

        for (XYChart.Series<Double, Double> ser :(ObservableList<XYChart.Series<Double, Double>> )chart.getData()) {

            if (ser.getName().equals("crecimiento")) //if Name is "blue" then continue
            continue;

            //for all series, take date, each data has Node (symbol) for representing point
            for (XYChart.Data<Double, Double> data : ser.getData()) {
                Node stackPane = data.getNode();
                stackPane.setVisible(false);
            }
        }
        
        
//Line line44 = new Line(20, 100, 270, 100);
//line44.getStrokeDashArray().addAll(2d);

        root.getChildren().add(chart);
  //       root.getChildren().add(line44);
        root.setPadding(new Insets(5,5,5,5));
        Scene scene = new Scene(root,1000,800);
        primaryStage.setScene(scene);

        Node line = series.getNode().lookup(".chart-series-line");
        Node line1 = series1.getNode().lookup(".chart-series-line");
        Node line2 = series2.getNode().lookup(".chart-series-line");
        Node line3 = series3.getNode().lookup(".chart-series-line");
        Node line4 = series4.getNode().lookup(".chart-series-line");
        Node line5 = series5.getNode().lookup(".chart-series-line");
        Node line6 = series6.getNode().lookup(".chart-series-line");
        Node line7 = series7.getNode().lookup(".chart-series-line");

        Color color = Color.RED; // or any other color
        Color color1 = Color.BLUE; // or any other color

        /**String rgb = String.format("%d, %d, %d",
        (int) (color.getRed() * 255),
        (int) (color.getGreen() * 255),
        (int) (color.getBlue() * 255));

        String rgb1 = String.format("%d, %d, %d",
        (int) (135),
        (int) (206),
        (int) (250));
        String rgb2 = String.format("%d, %d, %d",
        (int) (135),
        (int) (206),
        (int) (235));
        String rgb3 = String.format("%d, %d, %d",
        (int) (65),
        (int) (105),
        (int) (225));
        String rgb4 = String.format("%d, %d, %d",
        (int) (color1.getRed() * 255),
        (int) (color1.getGreen() * 255),
        (int) (color1.getBlue() * 255));
        String rgb5 = String.format("%d, %d, %d",
        (int) (0),
        (int) (0),
        (int) (205));
        String rgb6 = String.format("%d, %d, %d",
        (int) (0),
        (int) (0),
        (int) (139));
        String rgb7 = String.format("%d, %d, %d",
        (int) (1),
        (int) (1),
        (int) (110));

        line.setStyle("-fx-stroke: rgba(" + rgb + ", 1.0);");
        line1.setStyle("-fx-stroke: rgba(" + rgb1+ ", 1.0);");
        line2.setStyle("-fx-stroke: rgba(" + rgb2+ ", 1.0);");
        line3.setStyle("-fx-stroke: rgba(" +rgb3 + ", 1.0);");
        line4.setStyle("-fx-stroke: rgba(" + rgb4 + ", 1.0);");
        line5.setStyle("-fx-stroke: rgba(" + rgb5 + ", 1.0);");
        line6.setStyle("-fx-stroke: rgba(" + rgb6 + ", 1.0);");
        line7.setStyle("-fx-stroke: rgba(" + rgb7 + ", 1.0);");*/
        chart.setLegendSide(Side.RIGHT);

        System.out.println(manager.Zscore(16.80719583, 30.5,arrli)+","+ manager.percentil(16.80719583, 30.5,arrli));
        //new ZoomManager(root, chart, series);
        primaryStage.show();
    }
    
  

    /**
     * Metodo para crear nodos para la grafica de crecimiento del paciente
     * @param y - arreglo de pesos y meses del paciente
     * @return 
     */
    public ObservableList<XYChart.Data<Double, Double>> plot(double[] y) {
        ObservableList<XYChart.Data<Double, Double>> dataset = FXCollections.observableArrayList();
        int i = 0;

        while (i < y.length) {
            final XYChart.Data<Double, Double> data = new XYChart.Data<>(y[i], y[i+1]);
            data.setNode(
            new HoveredThresholdNode(y[i],
            y[i+1]
            )
            );

            dataset.add(data);
            i+=2;
        }

        return dataset;
    }

    /** a node which displays a value on hover, but is otherwise empty */

    class HoveredThresholdNode extends Pane {
        
        /**
         * 
         * @param priorValue
         * @param value 
         */
        HoveredThresholdNode(double priorValue, double value) {
            setPrefSize(15, 15);
            final Label label = createDataThresholdLabel(priorValue, value);
            //lineas punteadas
              final XYChart.Series series8 = new XYChart.Series();
            series8.getData().add(new XYChart.Data(priorValue,0));
              series8.getData().add(new XYChart.Data(priorValue,value));
              
               final XYChart.Series series9 = new XYChart.Series();
            series9.getData().add(new XYChart.Data(0,value));
              series9.getData().add(new XYChart.Data(priorValue,value));
              

            setOnMouseEntered(new EventHandler<MouseEvent>(){
                @Override public void handle(MouseEvent mouseEvent) {
                  chart.getData().addAll(series8,series9);
                  System.out.println(mouseEvent.getSceneX()+","+  mouseEvent.getSceneY()+","+  xAxis.getDisplayPosition(30)+","+ yAxis.getDisplayPosition(11.5));
                    
                  
                    //currentLine.setEndX(mouseEvent.getX());
            //currentLine.setEndY(mouseEvent.getY());
                    getChildren().setAll(label);
                    setCursor(Cursor.NONE);
                    toFront();
              
                }
            });
            setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override public void handle(MouseEvent mouseEvent) {
                   chart.getData().remove(series8);
                   chart.getData().remove(series9);
                   //series8.getData().removeAll();
                    getChildren().clear();
                    setCursor(Cursor.CROSSHAIR);
                }
            });
        }
        
        /**
         * 
         * @param priorValue
         * @param value
         * @return 
         */
        private Label createDataThresholdLabel(double priorValue, double value) {
            String s=Double.toString(priorValue/12);
            String [] values = s.split("\\.");
            final Label label = new Label("Edad: " +values[0]+" años " +values[1].substring(0,1)+" meses" +"\n"+"Peso:"+value+ "kg");
            label.getStyleClass().addAll("default-color0", "chart-line-symbol", "chart-series-line");
            label.setStyle("-fx-font-size: 20; -fx-font-weight: bold;");

            if (priorValue == 0) {
                label.setTextFill(Color.DARKGRAY);
            } else if (value > priorValue) {
                label.setTextFill(Color.FORESTGREEN);
            } else {
                label.setTextFill(Color.FIREBRICK);
            }

            label.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
            return label;
        }
    }
    
    
    
    /**private Line createDotedLine(){
        return currentLne;
    }*/
    public static void main(String[] args) {
        launch();
    }

}
