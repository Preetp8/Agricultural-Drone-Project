package application;

import com.sun.source.tree.Tree;
import javafx.fxml.FXML;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

import javafx.stage.Stage;


public class FXMLEditController implements Initializable{
	
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
	private Button cancelButton;


    private Stage dialogStage;

	private boolean okClicked = false;

    public TreeView<String> tree;

    public TreeItem<String> selected_item;

    public void selectedTreePass(TreeView<String> treeView, TreeItem<String> selected) {
        tree = treeView;
        selected_item = selected;
    }


    private FarmItems findItemContainer(String tempItemContainer){
        FarmItems item_container1 = new ItemContainer();

        for (FarmItems item : FarmItems.getItemContainerData()) {
            if (item.getName().equals(tempItemContainer)) {
                item_container1 = item;
                return item_container1;
            }
        }
        return null;
    }

    public void setItemContainer(TreeView<String> treeView) {
        FarmItems item_container1 = new ItemContainer("",0.0,0.0,0.0,0,0,0);

        String tempitem = treeView.getSelectionModel().getSelectedItem().getValue();

//        boolean isItem = FarmItems.getItemData().contains(tempitem);

        for(FarmItems item : FarmItems.getItemContainerData()){
            if(item.getName().equals(tempitem)){
//                System.out.println(item);
                item_container1 = item;
            }
        }


        nameField.setText(item_container1.getName());
        priceField.setText(Double.toString(item_container1.getPrice()));
        xField.setText(Double.toString(item_container1.getXLocation()));
        yField.setText(Double.toString(item_container1.getYLocation()));
        lengthField.setText(Integer.toString(item_container1.getLength()));
        widthField.setText(Integer.toString(item_container1.getWidth()));
        heightField.setText(Integer.toString(item_container1.getHeight()));


    }
	
	public boolean isOkClicked() {
		return okClicked;
	}


    @FXML
    private void handleOk(ActionEvent event) {

        if(validInput()) {
            if (FarmItems.getItemContainerData().contains(findItemContainer(nameField.getText()))) {

                FarmItems item_container1 = new ItemContainer();

                for (FarmItems item : FarmItems.getItemContainerData()) {
                    if (item.getName().equals(nameField.getText())) {
                        item_container1 = item;
                    }
                }

                item_container1.setName(nameField.getText());
                item_container1.setPrice(Double.parseDouble(priceField.getText()));
                item_container1.setXLocation(Double.parseDouble(xField.getText()));
                item_container1.setYLocation(Double.parseDouble(yField.getText()));
                item_container1.setLength(Integer.parseInt(lengthField.getText()));
                item_container1.setWidth(Integer.parseInt(widthField.getText()));
                item_container1.setHeight(Integer.parseInt(heightField.getText()));


                okClicked = true;
                Stage stage = (Stage) cancelButton.getScene().getWindow();
                stage.close();

            } else if (validInput()) {

                FarmItems itemContainer = new ItemContainer();
                itemContainer.setName(nameField.getText());
                itemContainer.setPrice(Double.parseDouble(priceField.getText()));
                itemContainer.setXLocation(Double.parseDouble(xField.getText()));
                itemContainer.setYLocation(Double.parseDouble(yField.getText()));
                itemContainer.setLength(Integer.parseInt(lengthField.getText()));
                itemContainer.setWidth(Integer.parseInt(widthField.getText()));
                itemContainer.setHeight(Integer.parseInt(heightField.getText()));

                TreeItem<String> treeContainer = new TreeItem<>(itemContainer.getName());
                tree.getSelectionModel().getSelectedItem().getChildren().add(treeContainer);

                FarmItems.addItemContainer(itemContainer);
                tree.refresh();


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
            errorMessage += "Invalid Width!\n";
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
