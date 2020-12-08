package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static sample.Controller.*;


public class ControllerInstance  implements Initializable  {
public static String name;
    @FXML
    TableView<Result> tableOneClause;
   /* @FXML
    TableView<Result> tableClause;
    @FXML
    TableColumn<Result, Integer> colRates;
    @FXML
    TableColumn<Result, Double> colSats;
    @FXML
    TableColumn<Result, Double> colTimes;

    parameters params = new parameters(100, 100, 20, 60);
    ObservableList<Result> listeOneClause = FXCollections.observableArrayList();
*/

   @FXML Text textFile;
   @FXML Text textClause;
   @FXML
   TableColumn<Result, Integer> colRates;
    @FXML
    TableColumn<Result, Double> colSats;
    @FXML
    TableColumn<Result, Double> colTimes;
    @FXML TableColumn<File, Integer> tableInstances;
      ObservableList<Result> listeOneClausess = FXCollections.observableArrayList();
    @FXML Text textBest ;
    public void myFunction(String text,ObservableList<Result> listeOneClauses ) {
        textFile.setText(text);
        Double sumSat=0.0;
        int max=0;
        for (int i = 0; i < listeOneClauses.size(); i++) {
            if (listeOneClauses.get(i).getInstance().equals(text)) {
                listeOneClausess.add(listeOneClauses.get(i));
                sumSat=sumSat+listeOneClauses.get(i).getAverageSat();
            if(max<(int)listeOneClauses.get(i).getAverageSat()) max=(int)listeOneClauses.get(i).getAverageSat();
            }

        }Double res=sumSat/listeOneClausess.size();
        textClause.setText(""+res) ;
        textBest.setText(""+max);
        tableOneClause.setItems(listeOneClausess);


    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        colRates.setCellValueFactory(new PropertyValueFactory<>("averageRate"));
        colSats.setCellValueFactory(new PropertyValueFactory<>("averageSat"));
        colTimes.setCellValueFactory(new PropertyValueFactory<>("averageTime"));
        tableInstances.setCellValueFactory(new PropertyValueFactory<>("instance"));
        tableOneClause.setItems(listeOneClausess);

    }

    void ajouterClause(ActionEvent actionEvent) throws Exception  {
  /*      Controller c = new Controller();
        String filename = "";

                        try {
  SATInstance instance = SATInstance.loadClausesFromDimacsFile("UF75.325.100/"+c.tableClauses.getSelectionModel().getSelectedItem().getInstance());
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
                                double t = ((double) exeTime / (1000));
                                int sat = instance.getNumberOfClausesSatisfied(sol);
                                double rate = (double) sat / instance.getNumberOfClauses();
                                sumRate += rate;
                                sumSat += sat;
                                sumTime += t;
                                listeOneClause.add(new Result(sumSat, sumRate, sumTime));

                            }
if(listeOneClause.size()==10) {tableOneClause.setItems(listeOneClause);
}

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
  */
    }
                }



