package main;

import Interfaces.WarehouseInterface;
import administration.Customer;
import alert.AlertMessage;
import customer.CustomerStorage;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import cargo.Cargo;

import javafx.util.Pair;
import jbs.CustomJBP;
import jbs.WarehouseJBP;
import jos.CustomJOS;
import jos.WarehouseJOS;
import warehouse.Warehouse;
import warehouse.WarehouseCustomer;
import warehouse.WarehouseManagement;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;



public class MainScreen extends Application {

    WarehouseManagement warehouseManagement;

    public MainScreen() {
        warehouseManagement = new WarehouseManagement();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        // Create buttons
        Button insertButton = new Button("Switch to insert mode");
        Button deleteButton = new Button("Switch to deletion mode");
        Button displayButton = new Button("Switch to display mode");
        Button changeButton = new Button("Switch to update mode");
        Button persistantButton = new Button("Switch to persistence mode");
        Button quitButton  = new Button("Quit");
        
        insertButton.setOnAction(e -> handleInsertButton(primaryStage));
        displayButton.setOnAction(e -> handleReadButton(primaryStage));
        deleteButton.setOnAction(e -> handleDeleteButton(primaryStage));
        persistantButton.setOnAction(e -> handlePersistantButton(primaryStage));
        changeButton.setOnAction((e) -> handleUpdateButton(primaryStage));
        quitButton.setOnAction(e -> handleQuitButton());


        VBox vbox = new VBox(10); // 10 pixels spacing
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(insertButton,deleteButton,displayButton,changeButton,persistantButton,quitButton);


        // Create a scene with the VBox as the root node
        Scene scene = new Scene(borderPane(vbox));


        // Set the scene and show the stage
        primaryStage.setTitle("Warehouse Management");
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    public BorderPane borderPane(VBox vBox) {
        VBox customerVBx = getCustomerVBOX();

        customerVBx.maxWidth(50);
        customerVBx.prefWidth(50);
        customerVBx.minWidth(50);
        customerVBx.maxWidth(50);


        VBox cargoVBx = getCargoVBox();
        cargoVBx.setMinWidth(850);



        BorderPane borderPane = new BorderPane();
        borderPane.setLeft(vBox);

        borderPane.setCenter(cargoVBx);
        borderPane.setRight(customerVBx);
        return borderPane;
    }

    public VBox getCargoVBox() {
        List<Cargo> cargoList = warehouseManagement.readAllCargo();

        Collection<Pair<String,Integer>> customersWithTotalCargo = warehouseManagement.getCustomersWithTotalCargo();

        ObservableList<CargoDetail> data = cargoList.stream()
                .map((cargo) -> {
                    String cargoType = cargo.getClass().getName();
                    if(cargoType.startsWith("cargo.")) {
                        cargoType = cargoType.substring(6);
                    }
                    if(cargoType.startsWith("eventListeners."))
                    {
                        cargoType = cargoType.substring(15);
                    }
                    if(cargoType.endsWith("Impl")) {
                        cargoType = cargoType.substring(0,cargoType.length()-4);
                    }
                    return  new CargoDetail(cargoType,cargo.getValue().toString(),cargo.getOwner().getName(),cargo.getStorageLocation(),cargo.getLastInspectionDate(),cargo.getDurationOfStorage(),cargo.getHazards());
                })
                .collect(Collectors.toCollection(FXCollections::observableArrayList));

        TableView<CargoDetail> table = new TableView<>();



        TableColumn<CargoDetail, String> cargoType = new TableColumn<>("Cargo Type");
        cargoType.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));

        TableColumn<CargoDetail, String> cargoOwner = new TableColumn<>("Cargo Owner");
        cargoOwner.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOwner()));

        TableColumn<CargoDetail, String> cargoValue = new TableColumn<>("Cargo Value");
        cargoValue.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue()));

        TableColumn<CargoDetail, Integer> storageLocation = new TableColumn<>("Storage Location");
        storageLocation.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getStorageLocation()).asObject());

        TableColumn<CargoDetail, String> lastInspectionDate = new TableColumn<>("Last Inspection Date");
        lastInspectionDate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLastInspectionDate().toString()));

        TableColumn<CargoDetail, String> storageDuration = new TableColumn<>("Storage Duration");
        storageDuration.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStorageDuration().toString()));

        TableColumn<CargoDetail,String> hazards = new TableColumn<>("Hazards");
        hazards.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getHazardSet().toString()));



        table.getColumns().addAll(cargoType,cargoOwner,cargoValue,storageLocation,lastInspectionDate,storageDuration,hazards);
        table.setItems(data);



        VBox vBox = new VBox(table);
        return vBox;
    }

    private void handleUpdateButton(Stage primaryStage) {
        TextField storageLocationInput = new TextField();
        Label storageLocationToInspectLabel = new Label("Enter storage location to inspect");
        Button back = new Button("Back");
        back.setOnAction((e) -> {
            try {
                start(primaryStage);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        Button submitButton = new Button("Inspect");
        storageLocationInput.setMinWidth(100);
        storageLocationInput.setMaxWidth(100);


        submitButton.setOnAction(e -> {
            String input = storageLocationInput.getText();

            try {
                Cargo cargo = warehouseManagement.getWarehouse().getCargo(Integer.parseInt(input));
                if(cargo == null) {
                    AlertMessage.showErrorAlert("No cargo found that matches " + input  + " storage location");
                }
                else {
                    warehouseManagement.inspectCargo(input);
                    start(primaryStage);
                }

            }
            catch (Exception ex) {
                AlertMessage.showErrorAlert("error in inspecting cargo, no cargo at give loction");
            }

        });

        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(storageLocationToInspectLabel,storageLocationInput,submitButton,back);

        Scene scene = new Scene(borderPane(vBox));

        primaryStage.setScene(scene);
    }

    private void handlePersistantButton(Stage primaryStage) {
        Button saveJos = new Button("Save JOS");
        Button loadJOS = new Button("Load JOS");
        Button saveJBP = new Button("Save JBS");
        Button LoadJBP = new Button("Load JBP");

        Button backButton = new Button("Back");

        saveJos.setOnAction((e) -> {

            try {
                CustomJOS.persistJOS("persisteddata/customerjosdata.txt", (CustomerStorage) this.warehouseManagement.getCustomerStorage());
                WarehouseJOS.persistWithJOS("persisteddata/warehousejosdata.txt",this.warehouseManagement.getWarehouse());
                AlertMessage.showSuccessAlert("data saved through jos");
            }
            catch (Exception ex) {
                AlertMessage.showErrorAlert("error persisting through JOS");
            }

        });

        saveJBP.setOnAction((e) -> {
            try {
                CustomJBP.persistJBP("persisteddata/customerjbpdata.txt",this.warehouseManagement.getCustomerStorage());
                WarehouseJBP.persistJBP("persisteddata/warehousejbpdata.txt",this.warehouseManagement.getWarehouse());
                AlertMessage.showSuccessAlert("data saved through jbp");
            }
            catch (Exception ex) {
                AlertMessage.showErrorAlert("error persisting through JBP");
                ex.printStackTrace();
            }
        });

        loadJOS.setOnAction((e) -> {

            try {
                CustomerStorage customerStorage =   CustomJOS.initializeJOS("persisteddata/customerjosdata.txt");
                System.out.println(customerStorage);
                warehouseManagement.setCustomerStorage(customerStorage);
                Warehouse warehouse = WarehouseJOS.initializeWithJOS("persisteddata/warehousejosdata.txt");
                System.out.println("warehouse :" + warehouse);
                warehouseManagement.setWarehouse(warehouse);
                AlertMessage.showSuccessAlert("data initialized through JOS");
                start(primaryStage);
            }
            catch (Exception ex) {
                AlertMessage.showErrorAlert("error loading data though JOS");
            }

        });

        LoadJBP.setOnAction((e) -> {
            try {
                CustomerStorage customerStorage =   CustomJBP.initializeJBP("persisteddata/customerjbpdata.txt");
                System.out.println(customerStorage);
                warehouseManagement.setCustomerStorage(customerStorage);
                WarehouseInterface warehouse = WarehouseJBP.initializeJBP("persisteddata/warehousejbpdata.txt");

                //warehouseManagement.setCustomerStorage(customerStorage);
                System.out.println(warehouse);

                warehouseManagement.setWarehouse(warehouse);
                AlertMessage.showSuccessAlert("data initialized through JBP");
                start(primaryStage);
            }
            catch (Exception ex) {
                ex.printStackTrace();
                AlertMessage.showErrorAlert("error loading data through JBP");
            }


        });


        backButton.setOnAction((e) -> {
            try {
                start(primaryStage);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });



        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(saveJos,loadJOS,saveJBP,LoadJBP,backButton);

        Scene scene = new Scene(borderPane(vBox));

        primaryStage.setScene(scene);
    }

    private void handleDeleteButton(Stage primaryStage) {

        Button deleteCustomer = new Button("Delete Customer");
        Button deleteCargo = new Button("Delete Cargo");
        Button backButton = new Button("Back");
        deleteCustomer.setOnAction((e) -> handleDeleteCustomer(primaryStage));
        backButton.setOnAction((e) -> {
            try {
                start(primaryStage);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        deleteCargo.setOnAction((e) -> deleteCargoButton(primaryStage));


        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(deleteCustomer,deleteCargo,backButton);

        Scene scene = new Scene(borderPane(vBox));

        primaryStage.setScene(scene);
    }

    private void deleteCargoButton(Stage primaryStage) {
        TextField nameInput = new TextField();
        Label nameLabel = new Label("Enter cargo position:");
        Button back = new Button("Back");

        back.setOnAction((e) -> handleDeleteButton(primaryStage));

        Button submitButton = new Button("Delete");
        nameInput.setMinWidth(100);
        nameInput.setMaxWidth(100);


        submitButton.setOnAction(e -> {
            String cargoPosition = nameInput.getText();

            Cargo cargo = warehouseManagement.getWarehouse().getCargo(Integer.valueOf(cargoPosition));
            if(cargo == null) {
                AlertMessage.showErrorAlert("cargo does not exist");
                handleDeleteButton(primaryStage);
            }
            else {

                warehouseManagement.removeCargo(cargoPosition);
                handleDeleteButton(primaryStage);
            }

        });

        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(nameLabel,nameInput,submitButton,back);

        Scene scene = new Scene(borderPane(vBox));

        primaryStage.setScene(scene);
    }

    private void handleDeleteCustomer(Stage primaryStage) {
        TextField nameInput = new TextField();
        Label nameLabel = new Label("Enter name:");
        Button backButton = new Button("Back");

        Button submitButton = new Button("Delete");
        nameInput.setMinWidth(100);
        nameInput.setMaxWidth(100);

        backButton.setOnAction((e) -> handleDeleteButton(primaryStage));


        submitButton.setOnAction(e -> {
            String name = nameInput.getText();

            Customer customer = warehouseManagement.getCustomerStorage().getCustomer(name);
            if(customer == null) {
                AlertMessage.showErrorAlert("customer name does not exist");
                handleDeleteButton(primaryStage);
            }
            else {

                warehouseManagement.removeCustomer(name);
                handleDeleteButton(primaryStage);
                AlertMessage.showSuccessAlert("customer " + customer.getName() + " deleted successfully");
            }

        });

        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(nameLabel,nameInput,submitButton,backButton);

        Scene scene = new Scene(borderPane(vBox));

        primaryStage.setScene(scene);
    }

    private void handleReadButton(Stage primaryStage) {

        Button customerDetails = new Button(" View Customer Details");
        Button cargoDetails = new Button("View Cargo Details");
        Button hazardIncludedDetails = new Button("View Included hazard Details");
        Button hazardsExcludedDetails = new Button("View Excluded Hazard Details");
        Button backButton = new Button("Back");

        backButton.setOnAction((e) -> {
            try {
                start(primaryStage);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        customerDetails.setOnAction((e) -> displayCustomerDetails(primaryStage));
        cargoDetails.setOnAction((e) -> displayCargoDetails(primaryStage));
        hazardIncludedDetails.setOnAction((e) -> displayHazardIncludedDetails(primaryStage));
        hazardsExcludedDetails.setOnAction((e) -> displayHazardsExcludedDetails(primaryStage));





        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(customerDetails,cargoDetails, hazardIncludedDetails, hazardsExcludedDetails, backButton);

        Scene scene = new Scene(borderPane(vBox));

        primaryStage.setScene(scene);
    }

    private void displayHazardsExcludedDetails(Stage primaryStage) {
        Label label = new Label("Excluded Hazards = " + warehouseManagement.getExcludedHazards());
        Button backButton = new Button("Back");

        backButton.setOnAction((e) -> {
            try {
                handleReadButton(primaryStage);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(label, backButton);

        Scene scene = new Scene(borderPane(vBox));

        primaryStage.setScene(scene);
    }

    private void displayHazardIncludedDetails(Stage primaryStage) {
        Label label = new Label("Included Hazard = " + warehouseManagement.getIncludedHazards());
        Button backButton = new Button("Back");

        backButton.setOnAction((e) -> {
            try {
                handleReadButton(primaryStage);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(label, backButton);

        Scene scene = new Scene(borderPane(vBox));

        primaryStage.setScene(scene);
    }

    private void displayCargoDetails(Stage primaryStage) {

        Button backButton  = new Button("Back");

        backButton.setOnAction((e) -> {
            try {
                handleDeleteButton(primaryStage);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        List<Cargo> cargoList = warehouseManagement.readAllCargo();

        //Collection<Pair<String,Integer>> customersWithTotalCargo = warehouseManagement.getCustomersWithTotalCargo();

        ObservableList<CargoDetail> data = cargoList.stream()
                .map((cargo) -> {
                    String cargoType = cargo.getClass().getName();
                    if(cargoType.startsWith("cargo.")) {
                        cargoType = cargoType.substring(6);
                    }
                    if(cargoType.startsWith("eventListeners."))
                    {
                        cargoType = cargoType.substring(15);
                    }
                    if(cargoType.endsWith("Impl")) {
                        cargoType = cargoType.substring(0,cargoType.length()-4);
                    }
                   return  new CargoDetail(cargoType,cargo.getValue().toString(),cargo.getOwner().getName(),cargo.getStorageLocation(),cargo.getLastInspectionDate(),cargo.getDurationOfStorage(),cargo.getHazards());
                })
                .collect(Collectors.toCollection(FXCollections::observableArrayList));

        TableView<CargoDetail> table = new TableView<>();




        TableColumn<CargoDetail, String> cargoType = new TableColumn<>("Cargo Type");
        cargoType.setPrefWidth(50);
        cargoType.setMaxWidth(50);
        cargoType.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));

        TableColumn<CargoDetail, String> cargoOwner = new TableColumn<>("Cargo Owner");
        cargoOwner.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOwner()));

        TableColumn<CargoDetail, String> cargoValue = new TableColumn<>("Cargo Value");
        cargoValue.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue()));

        TableColumn<CargoDetail, Integer> storageLocation = new TableColumn<>("Storage Location");
        storageLocation.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getStorageLocation()).asObject());

        TableColumn<CargoDetail, String> lastInspectionDate = new TableColumn<>("Last Inspection Date");
        lastInspectionDate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLastInspectionDate().toString()));

        TableColumn<CargoDetail, String> storageDuration = new TableColumn<>("Storage Duration");
        storageDuration.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStorageDuration().toString()));

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setMinWidth(1000);

        table.getColumns().addAll(cargoType,cargoOwner,cargoValue,storageLocation,lastInspectionDate,storageDuration);
        table.setItems(data);



        VBox vBox = new VBox();
        vBox.getChildren().addAll(backButton);

        Scene scene = new Scene(borderPane(vBox));

        primaryStage.setScene(scene);
    }


    private void getCargoVBOX() {

    }

    private VBox getCustomerVBOX() {
        Collection<Pair<String,Integer>> customersWithTotalCargo = warehouseManagement.getCustomersWithTotalCargo();

        ObservableList<CustomerDetail> data = customersWithTotalCargo.stream()
                .map(pair -> new CustomerDetail(pair.getKey(), pair.getValue()))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));

        TableView<CustomerDetail> table = new TableView<>();



        TableColumn<CustomerDetail, String> customerNameCol = new TableColumn<>("Customer Name");
        customerNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));

        TableColumn<CustomerDetail, Integer> totalCargoCol = new TableColumn<>("Total Cargo");
        totalCargoCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getCargoSize()).asObject());

        table.getColumns().addAll(customerNameCol, totalCargoCol);
        table.setItems(data);



        VBox vBox = new VBox(table);
        return vBox;
    }

    private void displayCustomerDetails(Stage primaryStage) {

        Button backButton  = new Button("Back");

        backButton.setOnAction((e) -> {
            try {
                handleReadButton(primaryStage);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        Collection<Pair<String,Integer>> customersWithTotalCargo = warehouseManagement.getCustomersWithTotalCargo();

        ObservableList<CustomerDetail> data = customersWithTotalCargo.stream()
                .map(pair -> new CustomerDetail(pair.getKey(), pair.getValue()))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));

        TableView<CustomerDetail> table = new TableView<>();



        TableColumn<CustomerDetail, String> customerNameCol = new TableColumn<>("Customer Name");
        customerNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));

        TableColumn<CustomerDetail, Integer> totalCargoCol = new TableColumn<>("Total Cargo");
        totalCargoCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getCargoSize()).asObject());

        table.getColumns().addAll(customerNameCol, totalCargoCol);
        table.setItems(data);



        VBox vBox = new VBox();
        vBox.getChildren().addAll(backButton);

        Scene scene = new Scene(borderPane(vBox));

        primaryStage.setScene(scene);
    }

    public void handleInsertButton(Stage primaryStage) {
        //Label label = new Label("Customer size = " + warehouseManagement.getCustomerStorage().getAllCustomers().size());
        //Label label1 = new Label("cargo size " + warehouseManagement.getWarehouse().readAll().size());
        Button insertCustomer = new Button("Insert Customer");
        Button insertCargo = new Button("Insert Cargo");
        Button backButton = new Button("Back");

        insertCustomer.setOnAction((e) -> handleCustomerButton(primaryStage));
        insertCargo.setOnAction((e) -> handleCargoButton(primaryStage));
        backButton.setOnAction((e) -> {
            try {
                start(primaryStage);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });


        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll( insertCustomer,insertCargo,backButton);


        Scene scene = new Scene(borderPane(vBox));

        primaryStage.setScene(scene);

    }

    private void handleCargoButton(Stage primaryStage) {
        TextField nameInput = new TextField();
        Label nameLabel = new Label("Enter Cargo details:");

        Button submitButton = new Button("Submit");
        Button backButton = new Button("Back");
        nameInput.setMinWidth(100);
        nameInput.setMaxWidth(100);

        submitButton.setOnAction(e -> {
            String cargoDetails = nameInput.getText();

            try {
                warehouseManagement.insertCargo(cargoDetails);

                handleInsertButton(primaryStage);
            }
            catch (Exception ex) {
                if(ex.getMessage().length() < 10) {
                    AlertMessage.showErrorAlert("Something went wrong, Please check data");
                }
                else {
                    AlertMessage.showErrorAlert(ex.getMessage());
                }


            }

        });

        backButton.setOnAction((e) -> handleInsertButton(primaryStage));

        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(nameLabel,nameInput,submitButton,backButton);

        Scene scene = new Scene(borderPane(vBox));

        primaryStage.setScene(scene);
    }

    private void handleCustomerButton(Stage primaryStage) {

        TextField nameInput = new TextField();
        Label nameLabel = new Label("Enter name:");

        Button submitButton = new Button("Submit");

        Button back = new Button("Back");
        nameInput.setMinWidth(100);
        nameInput.setMaxWidth(100);

        submitButton.setOnAction(e -> {
            String name = nameInput.getText();

            Customer customer = warehouseManagement.getCustomerStorage().getCustomer(name);
            if(customer != null) {
                AlertMessage.showErrorAlert("customer name already exists");
            }
            else {
                Customer newCustomer = new WarehouseCustomer(name);
                warehouseManagement.getCustomerStorage().addCustomer(newCustomer);
                handleInsertButton(primaryStage);
            }

        });
        back.setOnAction((e) -> handleInsertButton(primaryStage));

        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(nameLabel,nameInput,submitButton,back);


        Scene scene = new Scene(borderPane(vBox));

        primaryStage.setScene(scene);
    }

    public void handleQuitButton() {

        try {
            CustomJOS.persistJOS("persisteddata/customerjosdata.txt", (CustomerStorage) this.warehouseManagement.getCustomerStorage());
            WarehouseJOS.persistWithJOS("persisteddata/warehousejosdata.txt",this.warehouseManagement.getWarehouse());
        }
        catch (Exception ex) {
            AlertMessage.showErrorAlert("error persisting through JOS");
        }

        Platform.exit();
    }
}
