package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.io.FileNotFoundException;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.shape.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.java.surelyhuman.jdrone.control.physical.tello.TelloDrone;


public class FXMLController implements Initializable{
	
	@FXML
	public AnchorPane root;

	@FXML
	public TextField screen;
	
	@FXML
	public ImageView drone;

	@FXML
	public Rectangle commandCenter = new Rectangle();

	@FXML
	public Label commandLabel;
	
	@FXML
	public Button add_itemContainer;
	
	@FXML
	public Button add_item;

	@FXML
	public Button refresh;

	@FXML
	public RadioButton gotoItem;

	@FXML
	public RadioButton scanFarm;

	ToggleGroup group = new ToggleGroup();

	DroneSim sim_drone = new DroneSim();

	@FXML
	public TreeView<String> tree = new TreeView<>();

	public TreeItem<String> rootItem = new TreeItem<>("Farm Items");


	//finds type of item
	private FarmItems findItem(String tempItem) {
		FarmItems item1 = new Items();
		FarmItems item_container1 = new ItemContainer();

		if (FarmItems.getItemData().toString().contains(tempItem)) {
			for (FarmItems item : FarmItems.getItemData()) {
				if (item.getName().equals(tempItem)) {
					item1 = item;
					return item1;
				}
			}
		} else{
			for (FarmItems item : FarmItems.getItemContainerData()) {
				if (item.getName().equals(tempItem)) {
					item_container1 = item;
					return item_container1;
				}
			}
		}
		return null;
	}

	//searches arraylist to see if item is in it
	private FarmItems searchItem(String tempItem){
		FarmItems items = new Items();

		for (FarmItems item : FarmItems.getItemData()) {
			if (item.getName().equals(tempItem)) {
				items = item;
				return items;
			}
		}
		return null;
	}


	@FXML
	private void handleLaunchSimulationButton(ActionEvent event) {

		if (scanFarm.isSelected()) handleScanFarm();


		if (gotoItem.isSelected()) handleGoToItem();


	}

	private void handleGoToItem() {
		// TODO: Make drone hover for a few seconds before returning to Command Center

		try {
			FarmItems tempItem = findItem(tree.getSelectionModel().getSelectedItem().getValue());
			assert tempItem != null;

			sim_drone.gotoXY((int)tempItem.getXLocation() - constants.DRONEOFFSET + tempItem.getWidth() / 2, (int)tempItem.getYLocation() - constants.DRONEOFFSET + tempItem.getLength() / 2,1);
			sim_drone.turnCW(360);

			FarmItems tempDrone = findItem("Drone");
			tempDrone.setXLocation((int)tempItem.getXLocation()); // sets drone X after visiting item/container
			tempDrone.setYLocation((int)tempItem.getYLocation()); // sets drone Y after visiting item/container

			sim_drone.gotoXY(0, 0, 1); // Drone returns to Command Center

			tempDrone.setXLocation(0);
			tempDrone.setYLocation(0);

			sim_drone.animation.play();
			sim_drone.animation.getChildren().clear();

		} catch(Exception ex) {
			screen.setText("Please select item for drone travel");
		}
	}

	private void handleScanFarm() {

		sim_drone.gotoXY(0, 500, 2); // drone goes to bottom left corner
		sim_drone.turnCCW(90); // drone turns left 90 degrees

		int yCount = 500;
		for (int i = 0; i < 5; i++) {

			sim_drone.gotoXY(700, yCount, 2); // drone moves right
			sim_drone.turnCCW(90); // drone turns left 90 degrees

			sim_drone.gotoXY(700, yCount -= 50, 1); // drone moves up 50
			sim_drone.turnCCW(90); // drone turns left 90 degrees

			sim_drone.gotoXY(0, yCount, 2); // drone moves to left
			sim_drone.turnCW(90); // drone turns right 90 degrees

			sim_drone.gotoXY(0, yCount -= 50, 1); // drone moves up 50
			sim_drone.turnCW(90); // drone turns right 90 degrees
		}

		sim_drone.turnCW(90); // turns drone to default orientation in Command Center after scanFarm

		sim_drone.animation.play(); // plays Sequential Transition with all rotations and translations
		sim_drone.animation.getChildren().clear(); // clears Sequential Transition for later use


	}


	@FXML
	private void handleLaunchDroneButton(ActionEvent event) {

		if (scanFarm.isSelected()) handleDroneScanFarm();

		if (gotoItem.isSelected()) handleDroneGoToItem();
	}

	private void handleDroneScanFarm(){
		screen.setText("Handling Drone Scan Farm");
		try {
			TelloDrone tello_drone = new TelloDrone();
			DroneTelloAdapter telloAdapter = new DroneTelloAdapter(tello_drone);
			int yCount = 500;

			telloAdapter.takeoff();
			// TODO: Increase Altitude?
			telloAdapter.gotoXY(0, (int)(yCount / constants.PIX_TO_CM), 2);
			telloAdapter.turnCCW(90);

			for (int i = 0; i < 5; i++) {

				telloAdapter.gotoXY((int)(700 / constants.PIX_TO_CM), (int)(yCount / constants.PIX_TO_CM), 2);
				telloAdapter.turnCCW(90);

				telloAdapter.gotoXY((int)(700 / constants.PIX_TO_CM), (int)((yCount -= 50) / constants.PIX_TO_CM), 1);
				telloAdapter.turnCCW(90);

				telloAdapter.gotoXY(0, (int)(yCount / constants.PIX_TO_CM), 2);
				telloAdapter.turnCW(90);

				telloAdapter.gotoXY(0, (int)((yCount -= 50) / constants.PIX_TO_CM), 1);
				telloAdapter.turnCW(90);

			}

			telloAdapter.turnCW(90);

		} catch (SocketException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void handleDroneGoToItem(){
		FarmItems tempItem = findItem(tree.getSelectionModel().getSelectedItem().getValue());
		assert tempItem != null;
		screen.setText("Handling Drone Go To Item");
		try {

			TelloDrone tello_drone = new TelloDrone();
			DroneTelloAdapter telloAdapter = new DroneTelloAdapter(tello_drone);

			telloAdapter.activateSDK();
			telloAdapter.takeoff();
			telloAdapter.gotoXYZ((int)(tempItem.getXLocation() / constants.PIX_TO_CM), (int)(tempItem.getYLocation() / constants.PIX_TO_CM), (int) (tempItem.getHeight() / constants.PIX_TO_CM + 30), 2 );
			telloAdapter.turnCW(360);
			telloAdapter.hoverInPlace(3);
			telloAdapter.gotoXY(0, 0, 2);
			telloAdapter.land();
			telloAdapter.end();

		} catch (SocketException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		// **DRONE DEMO CODE**
//		try {
//			screen.setText("Running Test flight");
//			TelloDrone tello_drone = new TelloDrone();
//			tello_drone.activateSDK();
//			tello_drone.takeoff();
//			tello_drone.flip("b");
//			tello_drone.land();
//			tello_drone.end();
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch(InterruptedException e) {
//			e.printStackTrace();
//		}
	}


	private boolean isItem(TreeItem<String> item) {
		return FarmItems.getItemData().toString().contains(item.getValue());
	}


	@FXML
	private void handleAddItemContainer(ActionEvent event) {

		try {
			TreeItem<String> selectedTreeItem = tree.getSelectionModel().getSelectedItem();

			if (selectedTreeItem != null && !isItem(selectedTreeItem)) {
				FXMLLoader edititem = new FXMLLoader(getClass().getResource("../view/EditItemContainer.fxml"));
				Parent root1 = edititem.load();

				FXMLEditController editController = edititem.getController();
				editController.selectedTreePass(tree, selectedTreeItem);

				Stage dialogStage = new Stage();
				dialogStage.setScene(new Scene(root1));
				dialogStage.show();
			}
			else
				screen.setText("error: please select container to add to");
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}


	@FXML
	private void handleAddItemButton(ActionEvent event) {
		try {
			TreeItem<String> selectedTreeItem = tree.getSelectionModel().getSelectedItem();

			if (selectedTreeItem != null && !isItem(selectedTreeItem)) {
				FXMLLoader edititem = new FXMLLoader(getClass().getResource("../view/EditItem.fxml"));
				Parent root1 = edititem.load();

				FXMLItemEditController editController = edititem.getController();
				editController.selectedTreePass(tree, selectedTreeItem);

				Stage dialogStage = new Stage();
				dialogStage.setScene(new Scene(root1));
				dialogStage.show();
			}
			else
				screen.setText("error: please select container to add to");
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}


	@FXML
	private void handleDeleteButton(ActionEvent event) {
		try {
			TreeItem<String> item = tree.getSelectionModel().getSelectedItem();
			for (TreeItem child : item.getChildren()) {
				removeItem(child);
				screen.setText("Deleted");
			}
			item.getParent().getChildren().remove(item);
			removeItem(item);
		} catch(Exception ex) {
			screen.setText("error: please select item to delete");
		}
	}


	@FXML
	private void handleEditButton(ActionEvent event) {
		// https://openjfx.io/javadoc/14/javafx.controls/javafx/scene/control/TreeView.EditEvent.html ?
		try {
			if (tree.getSelectionModel().getSelectedItem() != null) {
				TreeItem<String> selected = tree.getSelectionModel().getSelectedItem();

				if(FarmItems.getItemData().contains(searchItem(selected.getValue()))) {
					FXMLLoader edititem = new FXMLLoader(getClass().getResource("../view/EditItem.fxml"));
					Parent root1 = edititem.load();

					FXMLItemEditController editController = edititem.getController();


					editController.selectedTreePass(tree, selected);
					editController.setItem(tree);
					Stage dialogStage = new Stage();
					dialogStage.setScene(new Scene(root1));
					dialogStage.show();

				} else{
					FXMLLoader edititem = new FXMLLoader(getClass().getResource("../view/EditItemContainer.fxml"));
					Parent root1 = edititem.load();

					FXMLEditController editController = edititem.getController();

					editController.selectedTreePass(tree, selected);
					editController.setItemContainer(tree);

					Stage dialogStage = new Stage();
					dialogStage.setScene(new Scene(root1));
					dialogStage.show();

				}
			}
			else
				screen.setText("error: please select container to edit");
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}


	@FXML
	private void handleMarketValueButton(ActionEvent event){
		MarketValueVisitor v = new MarketValueVisitor();
		try {
			FarmItems tempItem = findItem(tree.getSelectionModel().getSelectedItem().getValue());
			assert tempItem != null;
			if (tempItem instanceof Items) {
				tempItem.accept(v);
				screen.setText("Market Value: " + v.visitItem(tempItem));
			} else {
				screen.setText("Market Value " + tempItem.accept(v));
			}
		} catch(Exception ex) {
			screen.setText("error: please select valid item");
		}
	}


	@FXML
	private void handlePurchasePriceButton(ActionEvent event) {
		PricingVisitor v = new PricingVisitor();

		try {
			FarmItems tempItem = findItem(tree.getSelectionModel().getSelectedItem().getValue());
			assert tempItem != null;
			if (tempItem instanceof Items) {
				tempItem.accept(v);
				screen.setText("Purchase Price: " + v.visitItem(tempItem));
			} else {
				screen.setText("Purchase Price: " + v.visitItemContainer(tempItem));
			}
		} catch(Exception ex) {
			screen.setText("error: please select valid item");
		}
	}

	@FXML
	public void handleRefreshButton() {
		clearFarm();

		for (FarmItems item : FarmItems.getItemData()) {
			drawItem(item);
		}

		for (FarmItems container : FarmItems.getItemContainerData()) {
			drawItem(container);
		}
	}


	public void drawItem(FarmItems item) {
		Rectangle rectangle = new Rectangle();

		if (item.getName().equals("Drone")) {
			drone.setX(item.getXLocation());
			drone.setY(item.getYLocation());
			drone.setFitHeight(item.getHeight());
			drone.setFitWidth(item.getWidth());
			root.getChildren().add(drone);
		}

		else if (item.getName().equals("Command Center")) {
			commandCenter.setX(item.getXLocation());
			commandCenter.setY(item.getYLocation());
			commandCenter.setHeight(item.getHeight());
			commandCenter.setWidth(item.getWidth());
			commandCenter.setFill(null);
			commandCenter.setStroke(Color.BLACK);


			root.getChildren().addAll(commandCenter, commandLabel);
		}

		else {
			rectangle.setFill(null);
			rectangle.setStroke(Color.BLUEVIOLET);
			rectangle.setHeight(item.getLength());
			rectangle.setWidth(item.getWidth());
			rectangle.setX(item.getXLocation());
			rectangle.setY(item.getYLocation());
			Text itemText = new Text(item.getName());
			itemText.setX(item.getXLocation() + item.getWidth() + 7); // puts text (item name) a little to the right of item/container
			itemText.setY(item.getYLocation() + item.getLength() / 2); // puts text halfway down the right side of item/container

			root.getChildren().addAll(rectangle, itemText);
		}
	}


	public void clearFarm() {
		root.getChildren().clear();
	}


	public void removeItem(TreeItem<String> treeItem) {
		if (FarmItems.getItemContainerData() != null) {
			FarmItems.getItemContainerData().removeIf(item_container -> item_container.getName().equals(treeItem.getValue()));
		}

		if (FarmItems.getItemData() != null) {
			FarmItems.getItemData().removeIf(item -> item.getName().equals(treeItem.getValue()));
		}
	}



	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tree.setRoot(rootItem);
		tree.getRoot().setExpanded(true);

		FarmItems.addItem(new Items("Drone", 0, drone.getX(), drone.getY(), 0, 67, 50, 0, 0));
		FarmItems.addItemContainer(new ItemContainer("Command Center", 0, 1, 3, 0, 110, 81));

		scanFarm.setToggleGroup(group);
		gotoItem.setToggleGroup(group);

		sim_drone.dronePass(drone);
	}
}
