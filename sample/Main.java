package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableRow;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;


public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("satisfiabilite solveur");
        primaryStage.setScene(new Scene(root, 1000, 640));
        primaryStage.show();



    }

    public static void main(String[] args) {
System.out.println("hello");

        int RateMutation=20;
        int RateCro=60;
        int siz=100;

        new File(parameters.rootPath).mkdirs();
        int numberOfInstances = 10;
        int tryPerInstance = 10;
        PrintWriter writer = null;
        launch(args);
        try {
            writer = new PrintWriter(parameters.rootPath + "stats.csv");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        writer.write("number of instances per parameter;" + numberOfInstances + "\n");
        writer.write("try per instance;" + tryPerInstance + "\n\n\n");

        writer.write("maxIterations;sizepop;ratecroissement;ratemutation;average sat;average rate;average time (s)\n");

        parameters params = new parameters(100,100, RateMutation,RateCro);

        Result res = null;
       try {
            res = Result.createCSV(params, numberOfInstances, tryPerInstance);
        } catch (Exception e) {
            e.printStackTrace();
        }

        writer.write("maxIterations" + ";" + "sizepop "+ ";"  + ";" + "RateCroissement" + ";" + "RateMutation"
                + ";" + res.getAverageRate() + ";" + res.getAverageRate() + ";" + res.getAverageTime() + "\n");

        writer.close();

    }

}
