package com.payaman.auctms;
import com.payaman.auctms.model.Auction;
import com.payaman.auctms.service.AuctionService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.stage.Window;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lombok.Setter;

public class ManageAuctionController extends GenericAuctionController{
	@Setter
	Stage stage;

	@Setter
	Scene createViewScene;

	@Setter
	Scene editViewScene;

	@Setter
	Scene deleteViewScene;

	public ImageView auctionImage;
	@FXML
	public Button btnCreate;

	@FXML
	public Button btnEdit;

	@FXML
	public Button btnDelete;

	@FXML
	public Button btnClose;

	@FXML
	public Button imageButton;
	Auction selectedItem;

	@FXML
	private ListView<Auction> lvAuctions;

		public void refresh() {
			Auction[] auctions = AuctionService.getService().getAll();
			lvAuctions.getItems().clear();
			lvAuctions.getItems().addAll(auctions);
			enableFields(false);
		}

	@Override
	public void init() {
		try {
			refresh();
		}
		catch (Exception e){
			showErrorDialog("Message: ", e.getMessage());
		}
	}

	public void onAction(MouseEvent mouseEvent) {
		GenericAuctionController.selectedItem = lvAuctions.getSelectionModel().getSelectedItem();
		if(GenericAuctionController.selectedItem == null) {
			return;
		}
		setFields("Manage");
	}
	public void onCreate(ActionEvent actionEvent)  throws Exception {
		Node node = ((Node) (actionEvent.getSource()));
		Scene currentScene = node.getScene();
		Window window = currentScene.getWindow();
		window.hide();
		if(createViewScene == null){
			FXMLLoader fxmlLoader = new FXMLLoader(ManageAuctionJFXApp.class.getResource("create-auct-view.fxml"));
			Parent root = fxmlLoader.load();
			CreateAuctionController controller = fxmlLoader.getController();
			controller.setStage(stage);
			createViewScene = new Scene(root, 300, 720);
			controller.setManageAuctionController(this);
			controller.setManageScene(manageScene);
			controller.setSplashScene(splashScene);
		}
		stage.setTitle("Create Auction");
		stage.setScene(createViewScene);
		stage.show();
	}
	public void onEdit(ActionEvent actionEvent)  throws Exception {
		if(GenericAuctionController.selectedItem == null){
			showErrorDialog("Please select an auction from the list", "Cannot edit");
		return;
		}
		Node node = ((Node) (actionEvent.getSource()));
		Scene currentScene = node.getScene();
		Window window = currentScene.getWindow();
		window.hide();
		if(editViewScene == null){
			FXMLLoader fxmlLoader = new FXMLLoader(ManageAuctionJFXApp.class.getResource("edit-auct-view.fxml"));
			Parent root = fxmlLoader.load();
			EditAuctionController controller = fxmlLoader.getController();
			controller.setStage(stage);
			editViewScene = new Scene(root, 300, 720);
			controller.setManageAuctionController(this);
			controller.setManageScene(manageScene);
			controller.setSplashScene(splashScene);
		}
		stage.setTitle("Edit Auction");
		stage.setScene(editViewScene);
		stage.show();
	}
	public void onDelete(ActionEvent actionEvent)  throws Exception {
		if(GenericAuctionController.selectedItem == null){
			showErrorDialog("Please select an auction from the list", "Cannot delete");
		return;
		}
		Node node = ((Node) (actionEvent.getSource()));
		Scene currentScene = node.getScene();
		Window window = currentScene.getWindow();
		window.hide();
		if(deleteViewScene == null){
			FXMLLoader fxmlLoader = new FXMLLoader(ManageAuctionJFXApp.class.getResource("delete-auct-view.fxml"));
			Parent root = fxmlLoader.load();
			DeleteAuctionController controller = fxmlLoader.getController();
			controller.setStage(stage);
			deleteViewScene = new Scene(root, 300, 720);
			controller.setManageAuctionController(this);
			controller.setManageScene(manageScene);
			controller.setSplashScene(splashScene);
		}
		stage.setTitle("Delete Auction");
		stage.setScene(deleteViewScene);
		stage.show();
	}
	public void onClose(ActionEvent actionEvent) {
		super.onClose(actionEvent);
	}
}
