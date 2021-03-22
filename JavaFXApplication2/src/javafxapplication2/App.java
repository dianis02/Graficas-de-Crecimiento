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
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.collections.*;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.*;
import javafx.geometry.Side;

public class App extends Application {
    Pane root = new Pane(); //panel principal
    NumberAxis xAxis = new NumberAxis(); //ejes
    NumberAxis yAxis = new NumberAxis();
    LineChart chart = new LineChart(xAxis, yAxis); //grafica de lineas


    @Override
    public void start(Stage primaryStage) {
        //datos del paciente provisionales, se debe jalar de la base
        double[] arr ={24,11,30,11.5,43,15,60,20,77,25,90,29};
        //Manejador datos csv
        GrowthChartManager manager = new GrowthChartManager();
        //lector del csv
        ReadExcelFile reader = new ReadExcelFile();
        //arreglo con toda la información de csv

        int edadActual = 30; //provisional, esto se jalara de la base
        int sexo = 1; //provisonal, esto se jalara de la base
        String archivo = null;

        //dependiendo la edad y el sexo se elige el csv a leer
        if(manager.edad(edadActual)){
            archivo = manager.elegirTablaBebes(sexo);
        }else{
            archivo = manager.elegirTablaEstatura(sexo);
        }
        

        ArrayList<String> arrli = reader.leer(archivo);


        //Creamos las series de todos los percentiles estandar
        XYChart.Series series1 = new XYChart.Series();
        XYChart.Series series2 = new XYChart.Series();
        XYChart.Series series3 = new XYChart.Series();
        XYChart.Series series4 = new XYChart.Series();
        XYChart.Series series5 = new XYChart.Series();
        XYChart.Series series6 = new XYChart.Series();
        XYChart.Series series7 = new XYChart.Series();

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

        //Estilo ejes
        //NumberAxis xAxis = new NumberAxis();
        //NumberAxis yAxis = new NumberAxis();
        xAxis.setAutoRanging(true);
        //xAxis.setForceZeroInRange(false);
        yAxis.setAutoRanging(true);
        //yAxis.setForceZeroInRange(false);
        xAxis.setLabel("Edad");
        yAxis.setLabel("Peso");

        //Creamos la grafica con el crecimiento del paciente
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


        //lo obtenemos y lo eliminamos para añadirlo al final
        //porque lo dibuja atras de lo contrario
        XYChart.Series series = (XYChart.Series)chart.getData().get(0);
        chart.getData().remove(0,1);
        chart.getData().addAll(series1, series2, series3,series4,series5,series6,series7,series);
        //estilo grafica
        chart.setAnimated(false);
        chart.setLegendSide(Side.RIGHT);
        chart.setCursor(Cursor.CROSSHAIR);
        chart.setPrefSize(1000, 700);

        //lineas con puntos o solo la línea
        for (XYChart.Series<Double, Double> ser :(ObservableList<XYChart.Series<Double, Double>> )chart.getData()) {
            if (ser.getName().equals("crecimiento")) //if Name is "blue" then continue
            continue;

            //for all series, take date, each data has Node (symbol) for representing point
            for (XYChart.Data<Double, Double> data : ser.getData()) {
                Node stackPane = data.getNode();
                stackPane.setVisible(false);
            }
        }



        root.getChildren().add(chart);
        root.setPadding(new Insets(5,5,5,5));
        Scene scene = new Scene(root,1000,800);
        primaryStage.setScene(scene);

        //Estilo linea
        Node line = series.getNode().lookup(".chart-series-line");
        Node line1 = series1.getNode().lookup(".chart-series-line");
        Node line2 = series2.getNode().lookup(".chart-series-line");
        Node line3 = series3.getNode().lookup(".chart-series-line");
        Node line4 = series4.getNode().lookup(".chart-series-line");
        Node line5 = series5.getNode().lookup(".chart-series-line");
        Node line6 = series6.getNode().lookup(".chart-series-line");
        Node line7 = series7.getNode().lookup(".chart-series-line");




        if(manager.sexo(sexo)){
            line.setStyle("-fx-stroke: #FF00FF;");
        }else{
            line.setStyle("-fx-stroke: #0000FF;");
        }


        line1.setStyle("-fx-stroke: #FF0000;");
        line2.setStyle("-fx-stroke: #FFFF00;");
        line3.setStyle("-fx-stroke: #00FF00;");
        line4.setStyle("-fx-stroke: #00FF00;");
        line5.setStyle("-fx-stroke: #00FF00;");
        line6.setStyle("-fx-stroke: #FFFF00;");
        line7.setStyle("-fx-stroke: #FF0000;");


        //calculo zscore y percentil
        //System.out.println(manager.Zscore(16.80719583, 30.5,arrli)+","+ manager.percentil(16.80719583, 30.5,arrli));
        //System.out.println(manager.Zscore(84.51558277, 30.5,arrli)+","+ manager.percentil(84.51558277, 30.5,arrli));
        //System.out.println(manager.Zscore(2.4, 45,arrli)+","+ manager.percentil(2.4, 45,arrli));

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
            series8.setName("guia1");
            series9.setName("guia2");
                    setOnMouseEntered(new EventHandler<MouseEvent>(){
                @Override public void handle(MouseEvent mouseEvent) {
                    chart.getData().addAll(series8,series9);
                         //solo lineas (lineas guia)
        for (XYChart.Series<Double, Double> ser :(ObservableList<XYChart.Series<Double, Double>> )chart.getData()) {
            if (ser.getName().equals("guia1")||ser.getName().equals("guia2")){ //if Name is "blue" then continue


            //for all series, take date, each data has Node (symbol) for representing point
            for (XYChart.Data<Double, Double> data : ser.getData()) {
                Node stackPane = data.getNode();
                stackPane.setVisible(false);
            }
            }
        }



                    //System.out.println(mouseEvent.getSceneX()+","+  mouseEvent.getSceneY()+","+  xAxis.getDisplayPosition(30)+","+ yAxis.getDisplayPosition(11.5));

                 
                      Node line8 = series8.getNode().lookup(".chart-series-line");
            Node line9 = series9.getNode().lookup(".chart-series-line");
            line8.setStyle("-fx-stroke: #000000;-fx-stroke-dash-array: 2 12;");
            line9.setStyle("-fx-stroke: #000000;-fx-stroke-dash-array: 2 12;");
                    getChildren().setAll(label);
                    setCursor(Cursor.NONE);
                    toFront();

                }
            });
            setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override public void handle(MouseEvent mouseEvent) {
                    chart.getData().remove(series8);
                    chart.getData().remove(series9);
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
            label.setStyle("-fx-font-size: 15; -fx-font-weight: bold;");

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
