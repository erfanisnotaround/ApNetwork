package org.example.clientofnetwork.model.passingAndRecievingData;

import org.example.clientofnetwork.model.passingAndRecievingData.sameInfoes.CommandResponseStatus;
import org.example.clientofnetwork.model.passingAndRecievingData.sameInfoes.CommandType;
import org.example.clientofnetwork.model.passingAndRecievingData.sameInfoes.KindOfCommunication;

public class EnvelopeData <PassType , RecType>{

    private String userName;
    private String password;
    private KindOfCommunication kindOfCommunication;
    private CommandType commandType;
    private String token;
    private String message;
    private CommandResponseStatus status;
    private String code;

    private PassType dataPas;

    private RecType dataRec;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public KindOfCommunication getKindOfCommunication() {
        return kindOfCommunication;
    }

    public void setKindOfCommunication(KindOfCommunication kindOfCommunication) {
        this.kindOfCommunication = kindOfCommunication;
    }

    public CommandType getCommandType() {
        return commandType;
    }

    public void setCommandType(CommandType commandType) {
        this.commandType = commandType;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public RecType getDataRec() {
        return dataRec;
    }

    public void setDataRec(RecType dataRec) {
        this.dataRec = dataRec;
    }

    public PassType getDataPas() {
        return dataPas;
    }

    public void setDataPas(PassType dataPas) {
        this.dataPas = dataPas;
    }

    public CommandResponseStatus getStatus() {
        return status;
    }

    public void setStatus(CommandResponseStatus status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
