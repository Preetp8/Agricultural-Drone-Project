package application;

import javafx.fxml.FXML;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

import javafx.stage.Stage;



public class FXMLItemEditController implements Initializable{
	
	@FXML
	private TextField nameField;
	
	@FXML
	private TextField priceField;
	
	@FXML
	private TextField xField;
	
	@FXML
	private TextField yField;
	
	@FXML
	private TextField lengthField;
	
	@FXML
	private TextField widthField;
	
	@FXML
	private TextField heightField;

    @FXML
    private TextField marketValueField;
	
	@FXML
	private Button cancelButton;
	
    private Stage dialogStage;

	private boolean okClicked = false;

    public TreeView<String> tree;

    public TreeItem<String> selected_item;

    public void selectedTreePass(TreeView<String> treeView, TreeItem<String> selected) {
        tree = treeView;
        selected_item = selected;
    }




    private FarmItems findItem(String tempItem){
        FarmItems items = new Items();

        for (FarmItems item : FarmItems.getItemData()) {
            if (item.getName().equals(tempItem)) {
                items = item;
                return items;
            }
        }
        return null;
    }


    public void setItem(TreeView<String> treeView) {
        FarmItems item1 = new Items("",0.0,0.0,0.0,0,0,0,0.0 ,0.0);

        String tempitem = treeView.getSelectionModel().getSelectedItem().getValue();

//        boolean isItem = FarmItems.getItemData().contains(tempitem);

        for(FarmItems item : FarmItems.getItemData()){
            if(item.getName().equals(tempitem)){
//                System.out.println(item);
                item1 = item;
            }
        }

        nameField.setText(item1.getName());
        priceField.setText(Double.toString(item1.getPrice()));
        xField.setText(Double.toString(item1.getXLocation()));
        yField.setText(Double.toString(item1.getYLocation()));
        lengthField.setText(Integer.toString(item1.getLength()));
        widthField.setText(Integer.toString(item1.getWidth()));
        heightField.setText(Integer.toString(item1.getHeight()));
        marketValueField.setText(Double.toString(item1.getMarket_value()));

    }
	
	
	public boolean isOkClicked() {
		return okClicked;
	}



    @FXML
    private void handleOk(ActionEvent event) {
        // TODO: Add boundary errors so item cannot be bigger/outside container being added to
        if(validInput()) {
            if (FarmItems.getItemData().contains(findItem(nameField.getText()))) {

                FarmItems item1 = new Items();

                for (FarmItems item : FarmItems.getItemData()) {
                    if (item.getName().equals(nameField.getText())) {
                        item1 = item;
                    }
                }

                item1.setName(nameField.getText());
                item1.setPrice(Double.parseDouble(priceField.getText()));
                item1.setXLocation(Double.parseDouble(xField.getText()));
                item1.setYLocation(Double.parseDouble(yField.getText()));
                item1.setLength(Integer.parseInt(lengthField.getText()));
                item1.setWidth(Integer.parseInt(widthField.getText()));
                item1.setHeight(Integer.parseInt(heightField.getText()));
                item1.setMarket_value(Double.parseDouble(marketValueField.getText()));


                okClicked = true;
                Stage stage = (Stage) cancelButton.getScene().getWindow();
                stage.close();

            } else {

                FarmItems item1 = new Items();
                item1.setName(nameField.getText());
                item1.setPrice(Double.parseDouble(priceField.getText()));
                item1.setXLocation(Double.parseDouble(xField.getText()));
                item1.setYLocation(Double.parseDouble(yField.getText()));
                item1.setLength(Integer.parseInt(lengthField.getText()));
                item1.setWidth(Integer.parseInt(widthField.getText()));
                item1.setHeight(Integer.parseInt(heightField.getText()));
                item1.setMarket_value(Integer.parseInt(marketValueField.getText()));

                TreeItem<String> treeItem = new TreeItem<>(item1.getName());
                tree.getSelectionModel().getSelectedItem().getChildren().add(treeItem);

                FarmItems.addItem(item1);

                okClicked = true;
                Stage stage = (Stage) cancelButton.getScene().getWindow();
                stage.close();
            }
        }

    }
	


	
	@FXML
	public void handleCancel(ActionEvent event) {
		try {
		    Stage stage = (Stage) cancelButton.getScene().getWindow();
		    stage.close();
		
		} catch(Exception ex) {
			ex.printStackTrace();
		}

	}
	
	
	//TODO add typechecking visitor
	
	private boolean validInput() {
        String errorMessage = "";

        if (nameField.getText() == null || nameField.getText().length() == 0) {
            errorMessage += "Invalid name!\n"; 
        }
   

        if (priceField.getText() == null || priceField.getText().length() == 0) {
            errorMessage += "Invalid price!\n"; 
        } else {
            // try to parse the postal code into an double.
            try {
                Double.parseDouble(priceField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Invalid price (must be a number)!\n"; 
            }
        }
        
        if (xField.getText() == null || xField.getText().length() == 0) {
            errorMessage += "Invalid X Location!\n"; 
        } else {
            
            try {
                Double.parseDouble(xField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Invalid X Location (must be a number)!\n"; 
            }
        }

        
        if (yField.getText() == null || yField.getText().length() == 0) {
            errorMessage += "Invalid Y Location!\n"; 
        } else {
            
            try {
                Double.parseDouble(yField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Invalid Y Location (must be a number)!\n"; 
            }
        }


        if (lengthField.getText() == null || lengthField.getText().length() == 0) {
            errorMessage += "Invalid Length!\n"; 
        } else {
            
            try {
                Integer.parseInt(lengthField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Invalid Length (must be an number)!\n"; 
            }
        }
        
        if (widthField.getText() == null || widthField.getText().length() == 0) {
            errorMessage += "Invalid Length!\n"; 
        } else {
            
            try {
                Integer.parseInt(widthField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Invalid Width (must be an number)!\n";
            }
        }
        
        if (heightField.getText() == null || heightField.getText().length() == 0) {
            errorMessage += "Invalid Height!\n"; 
        } else {
            
            try {
                Integer.parseInt(heightField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Invalid Height (must be an number)!\n"; 
            }
        }

        if (marketValueField.getText() == null || marketValueField.getText().length() == 0) {
            errorMessage += "Invalid Market Value!\n";
        } else {

            try {
                Double.parseDouble(marketValueField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Invalid Market Value (must be an number)!\n";
            }
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);
            
            alert.showAndWait();
            
            return false;
        }
    }



	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

}
