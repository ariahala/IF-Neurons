package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Random;

public class Main extends Application {

    public double iChange = 0;
    @Override
    public void start(Stage primaryStage) throws Exception {
        synchronized (this) {

            Group root = new Group();
            primaryStage.setTitle("Integrated and Fire Neurons");
            ArrayList<Neuron> brain = new ArrayList<Neuron>();
            for (int i = 0; i < 20 ; i++) {
                for (int j = 0; j < 20 ; j++) {
                    Circle tempCircle = new Circle(25 + 30 * i, 25 + 30 * j, 1);
                    root.getChildren().add(tempCircle);
                    brain.add(new Neuron(1, 0.5 , 10, 0.2, tempCircle , i , j));
                }
            }
            for (int i = 0; i < brain.size(); i++) {
                for (int j = 0; j < brain.size(); j++) {
                    int x1 = brain.get(i).x;
                    int y1 = brain.get(i).y;
                    int x2 = brain.get(j).x;
                    int y2 = brain.get(j).y;
                    if (Math.pow(x1-x2,2)+ Math.pow(y1-y2,2) <= 10 ){
                    brain.get(i).Neighbours.add(brain.get(j));
                    brain.get(i).Weights.add(new Double(1./40));
                    }
                }
            }
            Button pos = new Button("Positive");
            Button neg = new Button("Negetive");
            pos.setLayoutX(200);
            pos.setLayoutY(630);
            neg.setLayoutX(400);
            neg.setLayoutY(630);
            Button btn = new Button("start");
            Rectangle rect = new Rectangle(650,680, Paint.valueOf("TRANSPARENT"));
            btn.setLayoutX(300);
            btn.setLayoutY(630);
            Text amount = new Text("0");
            amount.setLayoutX(100);
            amount.setLayoutY(630);
            root.getChildren().add(amount);
            root.getChildren().add(rect);
            root.getChildren().add(pos);
            root.getChildren().add(neg);
            root.getChildren().add(btn);
            ArrayList<Thread> t = new ArrayList<Thread>();
            for (Neuron n : brain) {
                Thread u = new Thread(n);
                t.add(u);
            }
            Thread All = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (Thread o : t) {
                        o.start();
                    }
                }
            });

            pos.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    iChange += 0.5;
                    String y = ""+iChange;
                    amount.setText(y);
                }
            });

            neg.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    iChange -= 0.5;
                    String y = ""+iChange;
                    amount.setText(y);
                }
            });

            ArrayList<Neuron> tempIEx = new ArrayList<Neuron>();
            rect.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    for ( Neuron n : brain ){
                        if ( (n.circle.getCenterX()-event.getSceneX())*(n.circle.getCenterX()-event.getSceneX())+ (n.circle.getCenterY()-event.getSceneY())*(n.circle.getCenterY()-event.getSceneY()) < 2500){
                            tempIEx.add(n);
                            n.IExternal += iChange;
                        }
                    }
                }
            });

            Circle circ = new Circle(2500, Paint.valueOf("TRANSPARENT"));
            circ.setStroke(Paint.valueOf("RED"));


            rect.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    circ.setCenterY(event.getSceneY());
                    circ.setCenterX(event.getSceneX());
                    //root.getChildren().add(circ);

                }
            });

            rect.setOnMouseMoved(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    circ.setCenterY(event.getSceneY());
                    circ.setCenterX(event.getSceneX());

                }
            });

            rect.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    root.getChildren().remove(circ);
                }
            });

            btn.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    All.start();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                    while (true) {
                                for (Neuron p : brain) {
                                    p.circle.setRadius(p.u / p.uMax * 10);
                                    p.circle.setFill(p.color);
                                }
                                        try {
                                            Thread.sleep(100);
                                        } catch (Exception e) {

                                        }
                                    }

                            }
                        }).start();
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            while (true) {
//                                for (Neuron p : brain) {
//                                    p.circle.setFill(p.color);
//                                }
//                                try {
//                                    Thread.sleep(100);
//                                } catch (Exception e) {
//
//                                }
//                            }
//
//                        }
//                    }).start();


                }
            });
            primaryStage.setScene(new Scene(root, 650, 680));
            primaryStage.show();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
