/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package javafxapplication2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.chart.LineChart;

/**
*
* @author dianis
*/
public class HoveredThresholdNode extends Pane {

    /**
    *
    * @param priorValue
    * @param value
    */
    HoveredThresholdNode(double priorValue, double value,LineChart chart) {
        setPrefSize(15, 15);
        final Label label = createDataThresholdLabel(priorValue, value);
        setOnMouseEntered(new EventHandler<MouseEvent>(){
            @Override public void handle(MouseEvent mouseEvent) {
                getChildren().setAll(label);
                setCursor(Cursor.NONE);
                toFront();

            }
        });
        setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent mouseEvent) {
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

    public static XYChart.Series lineaGuiaX(double priorValue, double value, String name){
        XYChart.Series series = new XYChart.Series();
        series.getData().add(new XYChart.Data(priorValue,0));
        series.getData().add(new XYChart.Data(priorValue,value));
        series.setName(name);
        return series;
    }

    public static XYChart.Series lineaGuiaY(double priorValue, double value, String name){
        XYChart.Series series = new XYChart.Series();
        series.getData().add(new XYChart.Data(0,value));
        series.getData().add(new XYChart.Data(priorValue,value));
        series.setName(name);
        return series;
    }

    public static void borrarPuntos(XYChart.Series<Double, Double> ser){
        for (XYChart.Data<Double, Double> data : ser.getData()) {
            Node stackPane = data.getNode();
            stackPane.setVisible(false);
        }
    }

    public static void eliminarLineasGuia(LineChart chart, XYChart.Series<Double, Double> series8, XYChart.Series<Double, Double> series9){
    chart.getData().remove(series8);
    chart.getData().remove(series9);
   
   
    }
    
    public  static ObservableList<XYChart.Data<Double, Double>> plot(double[] y, LineChart chart) {
        ObservableList<XYChart.Data<Double, Double>> dataset = FXCollections.observableArrayList();
        int i = 0;

        while (i < y.length) {
            final XYChart.Data<Double, Double> data = new XYChart.Data<>(y[i], y[i+1]);
            data.setNode(
            new HoveredThresholdNode(y[i],
            y[i+1],
            chart)
            );

            dataset.add(data);
            i+=2;
        }

        return dataset;
    }
}
