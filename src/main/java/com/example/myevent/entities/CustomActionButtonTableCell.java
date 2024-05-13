package com.example.myevent.entities;

import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.cell.PropertyValueFactory;

public class CustomActionButtonTableCell<S> extends TableCell<S, Void> {
    private final Button actionButton = new Button("Action");

    public CustomActionButtonTableCell() {
        actionButton.setOnAction(event -> {
            S rowData = getTableView().getItems().get(getIndex());
            // Ajoutez ici la logique de votre action
            System.out.println("Action button clicked for row: " + rowData);
        });
    }

    @Override
    protected void updateItem(Void item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setGraphic(null);
        } else {
            setGraphic(actionButton);
        }
    }
}
