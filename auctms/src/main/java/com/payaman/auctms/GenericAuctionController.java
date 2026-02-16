package com.payaman.auctms;
import com.payaman.auctms.model.Auction;
import com.payaman.auctms.service.AuctionService;
import com.payaman.auctms.model.Item;
import com.payaman.auctms.service.ItemService;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.Window;
import lombok.Setter;
import javafx.util.StringConverter;
import java.net.URL;
import javafx.scene.Node;
import javafx.event.ActionEvent;

import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.util.Locale;

public class GenericAuctionController implements Initializable{
	@Setter
	CreateAuctionController createAuctionController;

	@Setter
	DeleteAuctionController deleteAuctionController ;

	@Setter
	EditAuctionController editAuctionController;

	@Setter
	ManageAuctionController manageAuctionController;

	@Setter
	Stage stage;

	@Setter
	Scene splashScene;

	@Setter
	Scene manageScene;

	@Setter
	public ListView<Auction> lvAuctions;

	@Setter
	public static Auction selectedItem;
	public TextField txtId;
	public ComboBox<Auction> cmbAuction;
	public ComboBox<Item> cmbItem;
	public TextField txtStartingPrice;
	public TextField txtCurrentPrice;
	public DatePicker dtStartTime;
	public DatePicker dtEndTime;
	public TextField txtStatus;
	public DatePicker dtCreatedAt;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		Auction[] auctions =  (Auction[]) AuctionService.getService().getAll();
		cmbAuction.getItems().addAll(auctions);
		StringConverter<Auction> auctionConverter = new StringConverter<Auction>() {
			@Override
			public String toString(Auction auction) {
			if(auction==null)
				return "";
			else
				return auction.toString();
			}
			@Override
			public Auction fromString(String s) {
				if(!s.isEmpty()){
					for (Auction auction : auctions) {
						if (s.equals(auction.getName())){
							return auction;
						}
					}
				}
				return null;
			}
		};
		cmbAuction.setConverter(auctionConverter);
		Item[] items =  (Item[]) ItemService.getService().getAll();
		cmbItem.getItems().addAll(items);
		StringConverter<Item> itemConverter = new StringConverter<Item>() {
			@Override
			public String toString(Item item) {
			if(item==null)
				return "";
			else
				return item.toString();
			}
			@Override
			public Item fromString(String s) {
				if(!s.isEmpty()){
					for (Item item : items) {
						if (s.equals(item.getName())){
							return item;
						}
					}
				}
				return null;
			}
		};
		cmbItem.setConverter(itemConverter);
		init();
	}
	protected void init(){
		System.out.println("Invoked from Generic Controller");
	}
	protected Auction toObject(boolean isEdit){
		Auction auction= new Auction();
		try {
			if(isEdit) {
				auction.setId(Integer.parseInt(txtId.getText()));
			}
			Auction auction = cmbAuction.getSelectionModel().getSelectedItem();
			auction.setAuctionId(auction.getId());
			auction.setAuctionName(auction.getName());
			Item item = cmbItem.getSelectionModel().getSelectedItem();
			auction.setItemId(item.getId());
			auction.setItemName(item.getName());
			auction.setStartingPrice(txtStartingPrice.getText());
			auction.setCurrentPrice(txtCurrentPrice.getText());
			auction.setStartTime(toDate(dtStartTime.getValue()));
			auction.setEndTime(toDate(dtEndTime.getValue()));
			auction.setStatus(txtStatus.getText());
			auction.setCreatedAt(toDate(dtCreatedAt.getValue()));
		}catch (Exception e){
			showErrorDialog("Error" ,e.getMessage());
		}
		return auction;
	}
	protected void setFields(String action){
		String formattedDate;
		Auction auction = GenericAuctionController.selectedItem;
		SimpleDateFormat formatter = new SimpleDateFormat("mm/dd/yyyy", Locale.ENGLISH);
		txtId.setText(Integer.toString(auction.getId()));
		Auction auction = AuctionService.getService().get(auction.getAuctionId());
		cmbAuction.getSelectionModel().select(auction);
		if(action.equals("Create") || action.equals("Edit")){
			cmbAuction.setVisible(true);
			txtAuctionName.setVisible(false);
			cmbAuction.getSelectionModel().select(auction);
		}
		else{
			cmbAuction.setVisible(false);
			txtAuctionName.setVisible(true);
			txtAuctionName.setText(auction.getName());
		}
		Item item = ItemService.getService().get(auction.getItemId());
		cmbItem.getSelectionModel().select(item);
		if(action.equals("Create") || action.equals("Edit")){
			cmbItem.setVisible(true);
			txtItemName.setVisible(false);
			cmbItem.getSelectionModel().select(item);
		}
		else{
			cmbItem.setVisible(false);
			txtItemName.setVisible(true);
			txtItemName.setText(item.getName());
		}
		txtStartingPrice.setText(auction.getStartingPrice());
		txtCurrentPrice.setText(auction.getCurrentPrice());
		dtStartTime.setValue(toLocalDate(auction.getStartTime()));
		dtEndTime.setValue(toLocalDate(auction.getEndTime()));
		txtStatus.setText(auction.getStatus());
		dtCreatedAt.setValue(toLocalDate(auction.getCreatedAt()));
	}

	protected void clearFields(String action){
		txtId.setText("");
		cmbAuction.getSelectionModel().clearSelection();
		txtAuctionName.setText("");
		if(action.equals("Create") || action.equals("Edit")){
			cmbAuction.setVisible(true);
			txtAuctionName.setVisible(false);
		}
		else{
			cmbAuction.setVisible(false);
			txtAuctionName.setVisible(true);
		}
		cmbItem.getSelectionModel().clearSelection();
		txtItemName.setText("");
		if(action.equals("Create") || action.equals("Edit")){
			cmbItem.setVisible(true);
			txtItemName.setVisible(false);
		}
		else{
			cmbItem.setVisible(false);
			txtItemName.setVisible(true);
		}
		//txtStartingPrice.setText("");
		//txtCurrentPrice.setText("");
		//dtStartTime.setText("");
		//dtEndTime.setText("");
		//txtStatus.setText("");
		//dtCreatedAt.setText("");
	}

	protected void enableFields(boolean enable){
		cmbAuction.editableProperty().set(enable);
		txtAuctionName.editableProperty().set(enable);
		cmbItem.editableProperty().set(enable);
		txtItemName.editableProperty().set(enable);
		txtStartingPrice.editableProperty().set(enable);
		txtCurrentPrice.editableProperty().set(enable);
		dtStartTime.editableProperty().set(enable);
		dtEndTime.editableProperty().set(enable);
		txtStatus.editableProperty().set(enable);
		dtCreatedAt.editableProperty().set(enable);
	}

	public int getId(){
		return Integer.parseInt(txtId.getText());
	}

	protected void showErrorDialog(String message, String expandedMessage){
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setHeaderText(message);
		alert.getDialogPane().setExpandableContent(new ScrollPane(new TextArea(expandedMessage)));
		alert.showAndWait();
	}
	public void onBack(ActionEvent actionEvent) {
		Node node = ((Node) (actionEvent.getSource()));
		Window window = node.getScene().getWindow();
		window.hide();
		stage.setScene(manageScene);
		stage.show();
	}
	public void onClose(ActionEvent actionEvent) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Exit and loose changes? " , ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
		alert.showAndWait();
		if (alert.getResult() == ButtonType.YES) {
			Platform.exit();
		}
	}
	LocalDate toLocalDate(Date date){
		Instant instant = date.toInstant();
		ZoneId z = ZoneId.of("Singapore");
		ZonedDateTime zdt = instant.atZone( z );
		return zdt.toLocalDate();
	}
	protected Date toDate(LocalDate ld){
		ZoneId z = ZoneId.of("Singapore");
		ZonedDateTime zdt = ld.atStartOfDay(z);
		Instant instant  = zdt.toInstant();
		return Date.from(instant);
	}
}

