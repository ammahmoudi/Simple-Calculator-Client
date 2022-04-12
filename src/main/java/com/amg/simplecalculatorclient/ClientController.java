package com.amg.simplecalculatorclient;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;

import static javafx.collections.FXCollections.*;

public class ClientController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private StackPane bottom_stack_pane;

    @FXML
    private HBox bottom_h_box;

    @FXML
    private JFXTextField server_ip_text_field;

    @FXML
    private JFXTextField server_port_text_field;

    @FXML
    private JFXButton connect_button;

    @FXML
    private FontIcon connect_icon;

    @FXML
    private TextArea console_text_area;

    @FXML
    private VBox top_vbox;

    @FXML
    private HBox top_hbox;

    @FXML
    private JFXTextField number_1_text_field;

    @FXML
    private JFXComboBox<String> operator_combo_box;

    @FXML
    private JFXTextField number_2_text_field;

    @FXML
    private JFXButton send_button;

    @FXML
    private FontIcon send_icon;

    @FXML
    private Label status_label;

    private  static  TextArea console;
    private  static  Label status;
    @FXML
    void OnConnectButtonAction(ActionEvent event) {
        if(connect_button.getText().equals("connect")){
        ServerHandler.setServer(server_ip_text_field.getText(),Integer.parseInt(server_port_text_field.getText()));
       if( ServerHandler.connectToServer()){
           send_button.setDisable(false);
           send_button.setText("Start");
            writeOnStatus("press start button to ask for a calculation.");
            server_port_text_field.setDisable(true);
            server_ip_text_field.setDisable(true);
            connect_button.setText("disconnect");

       }}
        else{
            String response =ServerHandler.transmitter("exit");
            if(response.equals("exit")){
                writeOnStatus("First connect to a server");
                server_port_text_field.setDisable(false);
                server_ip_text_field.setDisable(false);
                send_button.setDisable(true);
                number_1_text_field.setDisable(true);
                number_2_text_field.setDisable(true);
                operator_combo_box.setDisable(true);
                connect_button.setText("connect");
                send_button.setText("start");
                ServerHandler.disconnectFromServer();

            }
        }

    }

    @FXML
    void OnSendButtonAction(ActionEvent event) {

        if(number_1_text_field.isDisable()&&number_2_text_field.isDisable()&operator_combo_box.isDisable()){
          String response=ServerHandler.transmitter("start");
          if(response.equals("OK")){
             number_1_text_field.setDisable(false);
             writeOnStatus("Insert first number");
             send_button.setText("send first number");
          }

        }
        else if(!number_1_text_field.isDisable()){

            String response=ServerHandler.transmitter(number_1_text_field.getText());
            if(response.equals("first number accepted")){
                number_1_text_field.setDisable(true);
                number_2_text_field.setDisable(false);
                send_button.setText("send second number");
                writeOnStatus("Insert second number");
            }
        }
        else if(!number_2_text_field.isDisable()) {
            String response = ServerHandler.transmitter(number_2_text_field.getText());
            if (response.equals("second number accepted")) {
                number_2_text_field.setDisable(true);
                operator_combo_box.setDisable(false);
                writeOnStatus("Chose the operator");
                send_button.setText("send operator");

            }
        }else{
            String response = ServerHandler.transmitter(operator_combo_box.getValue());
                writeOnStatus("answer is : "+response);
                send_button.setText("start");
                operator_combo_box.setDisable(true);
        }
    }




    @FXML
    void initialize() {
        String[] operators={"+","-","*","/","^"};
        ObservableList<String> observableList=observableArrayList(operators);
        operator_combo_box.setItems(observableList);
        console=console_text_area;
status=status_label;
    send_button.setDisable(true);
    number_1_text_field.setDisable(true);
    number_2_text_field.setDisable(true);
    operator_combo_box.setDisable(true);

    server_ip_text_field.setText(ServerHandler.SERVER_IP);
    server_port_text_field.setText(String.valueOf(ServerHandler.SERVER_PORT));
    writeOnStatus("First Connect to the server.");

    }
    public static void writeOnConsole(String message){
        console.appendText(message+"\n");
    }
    public static void writeOnStatus(String message){
        status.setText(message);
    }

}
