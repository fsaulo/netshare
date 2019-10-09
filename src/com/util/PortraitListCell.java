package com.util;

import com.var.PostVar;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;

import java.util.List;
import java.util.ArrayList;
import java.net.URL;
import java.util.ResourceBundle;
import java.sql.SQLException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author fsaulo
 */
public class PortraitListCell extends ListCell<PostVar> {

	@FXML private Label reactsLabel;
	@FXML private Label commentsLabel;
	@FXML private Label dateLabel;
	@FXML private Label name;

	@FXML private BorderPane pane;
	@FXML private GridPane grid;

	@FXML private FXMLLoader loader;

	@FXML private ImageView imagePost;

	private PostVar post;
	private int idUser;

	public PortraitListCell(int session, PostVar postList) {
		this.idUser = session;
		this.post = postList;
	}

	@Override
	public void updateItem(PostVar post, boolean empty) {
		super.updateItem(post, empty);

		if (empty || post == null) {
			setText(null);
			setGraphic(null);
		}

		else {

			if (loader == null) {
				loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("../../src/com/util/PortraitCell.fxml"));
				loader.setController(this);

				try {
					loader.load();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}

			if (post.getImagePath() != null) {

				Image image = new Image(post.getImagePath());
				imagePost.setFitHeight(260);
				imagePost.setFitWidth(600);
				imagePost.setImage(image);

			} else {

				Image noimage = new Image("file:/home/felix/Dropbox/Documents/workspace/projects/netshare/"+
				"external/icons/no-image-icon-15.png");
				imagePost.setFitHeight(110);
				imagePost.setFitWidth(100);
				imagePost.setImage(noimage);

			}

			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			dateLabel.setText(dateFormat.format(post.getDate()));

			reactsLabel.setText(Integer.toString(post.getCountReacts()));
			commentsLabel.setText(Integer.toString(post.getCountComments()));

			setText(null);
			setGraphic(grid);
		}
	}
}
