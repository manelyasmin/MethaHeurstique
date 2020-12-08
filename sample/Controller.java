package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class Controller implements Initializable {

     @FXML ComboBox<Double> comboMutation;
    @FXML ComboBox<Double> comboPopulation;
    @FXML ComboBox<Double> comboCrossever;
    @FXML ComboBox<String> comboInstance;
    @FXML ComboBox<String> comboMethode;
    @FXML ComboBox<String> comboType;
    @FXML LineChart<String, Number> lineChart;
    @FXML BarChart<String, Number> barChart;
    @FXML AreaChart<String, Number> areaChart;
    @FXML  TableView<Result> tableClauses;
    @FXML TableColumn<File, Integer> tableInstance;
    @FXML TableColumn<Result, Double> ColRate;
    @FXML TableColumn<Result, Double> ColSat;
    @FXML TableColumn<Result, Double> ColTime;
    @FXML CategoryAxis x;
    @FXML CategoryAxis y;
    @FXML Text textBest;
    @FXML Text textClause;
    ObservableList<? extends Number> listeC1 = FXCollections.observableArrayList(0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9,1.0,1.1,1.2,1.3,1.4,1.5,1.6,1.7,1.8,1.9,2.0);

    ObservableList<? extends Number> listeC2 = FXCollections.observableArrayList(0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9,1.0,1.1,1.2,1.3,1.4,1.5,1.6,1.7,1.8,1.9,2.0);
    ObservableList<? extends Number> listeWeight = FXCollections.observableArrayList(0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9,1.0,1.1,1.2,1.3,1.4,1.5,1.6,1.7,1.8,1.9,2.0);

    ObservableList<? extends Number> listePopulation = FXCollections.observableArrayList(20, 30, 40, 100, 200, 400, 600, 800, 1000);
    ObservableList<? extends Number> listeCrossever = FXCollections.observableArrayList(10, 20, 30, 40, 50, 60, 70, 80, 90, 100);
    ObservableList<? extends Number> listeMutation = FXCollections.observableArrayList(10, 20, 30, 40, 50, 60, 70, 80, 90, 100);
    ObservableList<String> listeInstance = FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
    ObservableList<String> listeType = FXCollections.observableArrayList("instance satisfiable par temps", "instancesaisfiable par clause", "instance sat par taux");
    parameters params = new parameters(100, 100, 20, 60);
    parameters param =new parameters(0.1,0.1,0.1);
   public ObservableList<Result> listeClauses = FXCollections.observableArrayList();
    ObservableList<String> listeMethode = FXCollections.observableArrayList("GA", "Bso", "Pso");

      ObservableList<Result> listeOneClause = FXCollections.observableArrayList();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comboInstance.setItems(listeInstance);
        comboMethode.setItems(listeMethode);
        ColRate.setCellValueFactory(new PropertyValueFactory<>("averageRate"));
        ColSat.setCellValueFactory(new PropertyValueFactory<>("averageSat"));
        ColTime.setCellValueFactory(new PropertyValueFactory<>("averageTime"));
        tableInstance.setCellValueFactory(new PropertyValueFactory<>("instance"));
        comboType.setItems(listeType);
        comboCrossever.setItems((ObservableList<Double>) listeCrossever);
        comboMutation.setItems((ObservableList<Double>)listeMutation);
        comboPopulation.setItems((ObservableList<Double>)listePopulation);
         comboPopulation.setVisible(false);
        comboMutation.setVisible(false);
        comboCrossever.setVisible(false);


    }

    public void geneticAction(Event event) {
        System.out.println(comboMethode.getSelectionModel().getSelectedIndex());
        if (comboMethode.getSelectionModel().getSelectedIndex() == 0) {

            comboMutation.setVisible(true);
            comboCrossever.setVisible(true);
            comboPopulation.setVisible(true);

        }
        if (comboMethode.getSelectionModel().getSelectedIndex() == 2) {
comboMutation.setPromptText("c1");
comboCrossever.setPromptText("c2");
comboPopulation.setPromptText("weight");
            comboCrossever.setItems((ObservableList<Double>) listeC2);
            comboMutation.setItems( (ObservableList<Double>)listeC1);
            comboPopulation.setItems( (ObservableList<Double>)listeWeight);

            comboMutation.setVisible(true);
            comboCrossever.setVisible(true);
            comboPopulation.setVisible(true);


        }

    }


    public void tracerLeGraphe(ActionEvent actionevent) throws Exception {
        barChart.getData().clear();
        lineChart.getData().clear();
        areaChart.getData().clear();
        // lineChart.setTitle("jcp");
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        if (comboType != null) {/*it menas linechart*/
            if (comboType.getSelectionModel().getSelectedIndex() == 0 /*taux satisfabilite par instance*/) {
                barChart.setVisible(false);
                areaChart.setVisible(false);
                lineChart.setVisible(true);
                if (tableClauses.getItems().size() > 0) { //instance time linechart
                    for (int i = 0; i < tableClauses.getItems().size(); i++) {
                        series.getData().add(new XYChart.Data<>(tableClauses.getItems().get(i).getInstance(), tableClauses.getItems().get(i).getAverageTime() * 60));
                        //      lineChart.getData().add(series);
                        lineChart.getData().add(series);
                    }
                    //   lineChart.getData().add(series);


                }
            }
        }
        if (comboType != null) {
            if (comboType.getSelectionModel().getSelectedIndex() == 1  /*taux satisfabilite par instance*/) {
                barChart.setVisible(true);
                areaChart.setVisible(false);
                lineChart.setVisible(false);
                if (tableClauses.getItems().size() > 0) { //instance par clauses bar chart
                    for (int i = 0; i < tableClauses.getItems().size(); i++) {
                        series.getData().add(new XYChart.Data<>(tableClauses.getItems().get(i).getInstance(), tableClauses.getItems().get(i).getAverageSat()));
                        barChart.getData().add(series);
                    }
                }
            }
        }
        if (comboType != null) {
            if (comboType.getSelectionModel().getSelectedIndex() == 2   /*taux satisfabilite par instance*/) {
                barChart.setVisible(false);
                areaChart.setVisible(true);
                lineChart.setVisible(false);
                if (tableClauses.getItems().size() > 0) { //instance par clauses bar chart
                    for (int i = 0; i < tableClauses.getItems().size(); i++) {
                        series.getData().add(new XYChart.Data<>(tableClauses.getItems().get(i).getInstance(), tableClauses.getItems().get(i).getAverageRate() * 100));
                        areaChart.getData().add(series);
                    }


                }
            }
        }

    }


    public void ButtonAction(ActionEvent actionEvent) throws Exception {
        FileChooser fc = new FileChooser();
        String filename = "";
        fc.setInitialDirectory(new File("C:\\Users\\hammo\\projetMethaAvecInterface"));
        List<File> selectedFiles = fc.showOpenMultipleDialog(null);

        if (selectedFiles != null && (comboMethode.getSelectionModel().getSelectedIndex() == 0)) {

            if ((comboPopulation.getSelectionModel().isEmpty() != true)
                    && (comboMutation.getSelectionModel().isEmpty() != true)
                    && (comboCrossever.getSelectionModel().isEmpty() != true)

                    ) {
 params.createDirectory();


                for (int j = 0; j <= comboInstance.getSelectionModel().getSelectedIndex(); j++) {

                    SATInstance instance = SATInstance.loadClausesFromDimacsFile(selectedFiles.get(j).getAbsolutePath());
                    double sumSat = 0;
                    double sumRate = 0;
                    double sumTime = 0;
                    int numberOfAttempts = 10;
                    GA genetic = new GA(instance);
                    genetic.setSizePop(params.getSizePop());
                    genetic.setRateMutation(params.getRateMutation());
                    genetic.setRateCroisement(params.getRateCroisement());
                    for (int i = 1; i <= numberOfAttempts; i++) {
                        long currentTime = System.currentTimeMillis();

                        SATSolution sol = genetic.Gen(600);
                        long exeTime = System.currentTimeMillis() - currentTime;
                        System.out.println("exe ga "+exeTime);
                        double t = ((double) exeTime / (1000));
                        int sat = instance.getNumberOfClausesSatisfied(sol);
                        double rate = (double) sat / instance.getNumberOfClauses();
                        listeOneClause.add(new Result(sat, rate, t, selectedFiles.get(j).getName()));
                        sumRate += rate;
                        sumSat += sat;
                        sumTime += t;

                    }

                    sumRate /= numberOfAttempts;
                    sumSat /= numberOfAttempts;
                    sumTime /= numberOfAttempts;
                    filename = selectedFiles.get(j).getName();
                    listeClauses.add(new Result(sumSat, sumRate, sumTime, filename));

                    tableClauses.setItems(listeClauses);
                    double averagesat = 0;
                    double min = 325;
                    for (int i = 0; i < listeClauses.size(); i++) {
                        if (listeClauses.get(i).getAverageSat() > averagesat)
                            averagesat = listeClauses.get(i).getAverageSat();
                        if (listeClauses.get(i).getAverageSat() < min) min = listeClauses.get(i).getAverageSat();
                    }
                    double finalAveragesat = averagesat;

                    double finalMin = min;
                    tableClauses.setRowFactory(tv -> new TableRow<Result>() {
                        @Override
                        public void updateItem(Result item, boolean empty) {
                            super.updateItem(item, empty);
                            if (item == null) {
                                setStyle("");
                            } else if (item.getAverageSat() == finalAveragesat) {
                                setStyle("-fx-background-color: darksalmon;");
                            } else if (item.getAverageSat() == finalMin) {
                                setStyle("-fx-background-color: salmon;");
                            } else if (item.getAverageSat() > finalMin && item.getAverageSat() < finalAveragesat) {

                            } else {
                                setStyle("-fx-background-color:lightpink;");
                            }
                        }
                    });
                }

            }


        }
        if (selectedFiles != null && comboMethode.getSelectionModel().getSelectedIndex() == 2) {


            if ((comboPopulation.getSelectionModel().isEmpty() != true)
                    && (comboMutation.getSelectionModel().isEmpty() != true)
                    && (comboCrossever.getSelectionModel().isEmpty() != true)

                    ) {
                param.createDirectory();
          //      param.setSizePop(90);
                param.setC1(comboMutation.getValue().doubleValue());
                param.setC2(comboCrossever.getValue().doubleValue());
                param.setWeight(comboPopulation.getValue().doubleValue());
                System.out.println(param.getRateCroisement() + "\t" + param.getRateMutation() + "\t" + comboPopulation.getSelectionModel());
                //params.setRateCroisement(1);
                //params.setMaxIter(5);
                for (int j = 0; j <= comboInstance.getSelectionModel().getSelectedIndex(); j++) {

                    SATInstance instance = SATInstance.loadClausesFromDimacsFile(selectedFiles.get(j).getAbsolutePath());

                  //  System.out.println(selectedFiles.get(j).getName());

                    double sumSat = 0;
                    double sumRate = 0;
                    double sumTime = 0;
                    int numberOfAttempts = 10;
                    PsoSat p = new PsoSat(instance);
                    p.setC1(param.getC1());
                    p.setC2(param.getC2());
                    p.setWeight(param.getWeight());
                    System.out.println(param.getC1()+"\t"+param.getC2()+"\t"+param.getWeight()+"\t"+param.getSizePop());
                            for (int i = 1; i <= numberOfAttempts; i++) {
                        long currentTime = System.currentTimeMillis();

                                SATSolution sol = p.PsoSat(600, 60, instance, param.getC1()  , param.getC2(), param.getWeight());
                        //  SATSolution sol = p.PsoSat(600, 60, instance, 0.4  , 1.5, 0.8);
                        long exeTime = System.currentTimeMillis() - currentTime;
                         double t = ((double) exeTime *0.001);
                         System.out.println("exe en seconde  "+t+"\t"+exeTime + "en milis");
                        int sat = instance.getNumberOfClausesSatisfied(sol);
                        double rate = (double) sat / instance.getNumberOfClauses();
                       listeOneClause.add(new Result(sat, rate, t, selectedFiles.get(j).getName()));
                        sumRate += rate;
                        sumSat += sat;
                        sumTime += t;
                    }

                    sumRate /= numberOfAttempts;
                    sumSat /= numberOfAttempts;
                    sumTime /= numberOfAttempts;
                    filename = selectedFiles.get(j).getName();
                    listeClauses.add(new Result(sumSat, sumRate, sumTime, filename));
                    tableClauses.setItems(listeClauses);
                    double averagesat = 0;
                    double min = 325;
                    for (int i = 0; i < listeClauses.size(); i++) {
                        if (listeClauses.get(i).getAverageSat() > averagesat)
                            averagesat = listeClauses.get(i).getAverageSat();
                        if (listeClauses.get(i).getAverageSat() < min) min = listeClauses.get(i).getAverageSat();
                    }
                    double finalAveragesat = averagesat;

                    double finalMin = min;
                    tableClauses.setRowFactory(tv -> new TableRow<Result>() {
                        @Override
                        public void updateItem(Result item, boolean empty) {
                            super.updateItem(item, empty);
                            if (item == null) {
                                setStyle("");
                            } else if (item.getAverageSat() == finalAveragesat) {
                                setStyle("-fx-background-color: darksalmon;");
                            } else if (item.getAverageSat() == finalMin) {
                                setStyle("-fx-background-color: salmon;");
                            } else if (item.getAverageSat() > finalMin) {
                                setStyle("-fx-background-color:lightpink;");
                            } else {
                                setStyle("");
                            }
                        }
                    });
                }

            }
        }
   if (selectedFiles != null && comboMethode.getSelectionModel().getSelectedIndex() == 1) {
/*

                param.createDirectory();

                for (int j = 0; j <= comboInstance.getSelectionModel().getSelectedIndex(); j++) {

                    SATInstance instance = SATInstance.loadClausesFromDimacsFile(selectedFiles.get(j).getAbsolutePath());

                    System.out.println(selectedFiles.get(j).getName());

                    double sumSat = 0;
                    double sumRate = 0;
                    double sumTime = 0;
                    int numberOfAttempts = 10;
                    int flip = 5;
                    int maxIterations = 100;
                    int numberOfBees = 20;
                    int maxChances = 6;
                    int maxLocalSearch = 30;
                    for (int i = 1; i <= numberOfAttempts; i++) {
                       long currentTime = System.currentTimeMillis();
                        SATSolution sol = BSOSat.searchBSOSAT(instance, maxIterations, flip, numberOfBees,
                                maxChances, maxLocalSearch, SATSolution.generateRandomSolution(instance));

                        long exeTime = System.currentTimeMillis() - currentTime;
                        double t = ((double) exeTime / (1000));
                        int sat = instance.getNumberOfClausesSatisfied(sol);
                        double rate = (double) sat / instance.getNumberOfClauses();
                        sumRate += rate;
                        sumSat += sat;
                        sumTime += t;
                    }

                    sumRate /= numberOfAttempts;
                    sumSat /= numberOfAttempts;
                    sumTime /= numberOfAttempts;
                    filename = selectedFiles.get(j).getName();
                    listeClauses.add(new Result(sumSat, sumRate, sumTime, filename));
                    tableClauses.setItems(listeClauses);
                    double averagesat = 0;
                    double min = 325;
                    for (int i = 0; i < listeClauses.size(); i++) {
                        if (listeClauses.get(i).getAverageSat() > averagesat)
                            averagesat = listeClauses.get(i).getAverageSat();
                        if (listeClauses.get(i).getAverageSat() < min) min = listeClauses.get(i).getAverageSat();
                    }
                    double finalAveragesat = averagesat;

                    double finalMin = min;
                    tableClauses.setRowFactory(tv -> new TableRow<Result>() {
                        @Override
                        public void updateItem(Result item, boolean empty) {
                            super.updateItem(item, empty);
                            if (item == null) {
                                setStyle("");
                            } else if (item.getAverageSat() == finalAveragesat) {
                                setStyle("-fx-background-color: darksalmon;");
                            } else if (item.getAverageSat() == finalMin) {
                                setStyle("-fx-background-color: salmon;");
                            } else if (item.getAverageSat() > finalMin) {
                                setStyle("-fx-background-color:lightpink;");
                            } else {
                                setStyle("");
                            }
                        }
                    });
                }

          */  }


    }


    @FXML
    void  handleTableAction(Event event) throws Exception {
        System.out.println("hhhh");

      if(tableClauses.getSelectionModel().getSelectedItem()!=null) {
          System.out.println("lllllllllllllllll");
          FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("instance.fxml"));
          Parent root1 = (Parent) fxmlLoader.load();
          ControllerInstance controllerInstance=fxmlLoader.getController();
          controllerInstance.myFunction(tableClauses.getSelectionModel().getSelectedItem().getInstance(),listeOneClause);

          Stage stage = new Stage();
          stage.initStyle(StageStyle.TRANSPARENT);
          stage.setScene(new Scene(root1));
          stage.show();


      }
    }


}