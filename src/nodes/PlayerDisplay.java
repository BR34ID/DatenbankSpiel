package nodes;

import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;

public class PlayerDisplay extends Pane {

	private ScrollPane content;
	private Pane innerContent;
	public PlayerDisplay(ScrollPane content) {
		this.innerContent = new Pane();
		this.content = content;
		super.getChildren().add(content);
		this.content.setContent(innerContent);
		super.setLayoutX(14);
		super.setLayoutY(35);
		super.setWidth(198);
		super.setHeight(500);
		this.innerContent.setPrefSize(198, 498);
		//this.innerContent.setPrefSize(USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
		
		this.content.setPrefSize(200, 500);
		this.innerContent.maxWidth(198);
		//this.content.getChildren().add(innerContent);
		this.setStyle("-fx-base: orange;");
	}
	public void addPlayer(PlayerInfo pInfo)
	{
		pInfo.setLayoutX(0);
		pInfo.setLayoutY(this.innerContent.getChildren().size() * pInfo.getPrefHeight());
		this.innerContent.setPrefHeight((this.innerContent.getChildren().size() + 1) * pInfo.getPrefHeight());
		this.innerContent.getChildren().add(pInfo);
		
	}
}
