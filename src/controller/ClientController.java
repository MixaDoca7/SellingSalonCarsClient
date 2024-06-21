/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import domain.Administrator;
import domain.Automobil;
import domain.Klijent;
import domain.Komponenta;
import domain.Marka;
import domain.Narudzbina;
import domain.Paket;
import domain.StavkaNarudzbine;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import session.Session;
import transfer.Request;
import transfer.Response;
import transfer.util.ResponseStatus;
import transfer.util.Operation;

/**
 *
 * @author PC
 */
public class ClientController {

    private static ClientController instance;

    private ClientController() {
    }

    public static ClientController getInstance() {
        if (instance == null) {
            instance = new ClientController();
        }
        return instance;
    }

    public Administrator login(Administrator administrator) throws Exception {
        return (Administrator) sendRequest(Operation.LOGIN, administrator);
    }

    public void addAutomobil(Automobil automobil) throws Exception {
        sendRequest(Operation.ADD_AUTOMOBIL, automobil);
    }

    public void addNarudzbina(Narudzbina narudzbina) throws Exception {
        sendRequest(Operation.ADD_NARUDZBINA, narudzbina);
    }

    public void deleteAutomobil(Automobil automobil) throws Exception {
        sendRequest(Operation.DELETE_AUTOMOBIL, automobil);
    }

    public void deleteNarudzbina(Narudzbina narudzbina) throws Exception {
        sendRequest(Operation.DELETE_NARUDZBINA, narudzbina);
    }

    public void updateAutomobil(Automobil automobil) throws Exception {
        sendRequest(Operation.UPDATE_AUTOMOBIL, automobil);
    }

    public void updateNarudzbina(Narudzbina narudzbina) throws Exception {
        sendRequest(Operation.UPDATE_NARUDZBINA, narudzbina);
    }

    public ArrayList<Automobil> getAllAutomobil() throws Exception {
        return (ArrayList<Automobil>) sendRequest(Operation.GET_ALL_AUTOMOBIL, null);
    }

    public ArrayList<Narudzbina> getAllNarudzbina() throws Exception {
        return (ArrayList<Narudzbina>) sendRequest(Operation.GET_ALL_NARUDZBINA, null);
    }

    public ArrayList<Paket> getAllPaket() throws Exception {
        return (ArrayList<Paket>) sendRequest(Operation.GET_ALL_PAKET, null);
    }

    public ArrayList<Marka> getAllMarka() throws Exception {
        return (ArrayList<Marka>) sendRequest(Operation.GET_ALL_MARKA, null);
    }

    public ArrayList<Klijent> getAllKlijent() throws Exception {
        return (ArrayList<Klijent>) sendRequest(Operation.GET_ALL_KLIJENT, null);
    }

    public ArrayList<StavkaNarudzbine> getAllStavkaNarudzbine() throws Exception {
        return (ArrayList<StavkaNarudzbine>) sendRequest(Operation.GET_ALL_STAVKA_NARUDZBINE, null);
    }
    
    public void addKomponenta(Komponenta komponenta) throws Exception {
        sendRequest(Operation.ADD_KOMPONENTA, komponenta);
    }

    public void deleteKomponenta(Komponenta komponenta) throws Exception {
        sendRequest(Operation.DELETE_KOMPONENTA, komponenta);
    }

    public void updateKomponenta(Komponenta komponenta) throws Exception {
        sendRequest(Operation.UPDATE_KOMPONENTA, komponenta);
    }
    
    public ArrayList<Komponenta> getAllKomponenta() throws Exception {
        return (ArrayList<Komponenta>) sendRequest(Operation.GET_ALL_KOMPONENTA, null);
    }
    
    
    private Object sendRequest(int operation, Object data) throws Exception {
        Request request = new Request(operation, data);

        ObjectOutputStream out = new ObjectOutputStream(Session.getInstance().getSocket().getOutputStream());
        out.writeObject(request);

        ObjectInputStream in = new ObjectInputStream(Session.getInstance().getSocket().getInputStream());
        Response response = (Response) in.readObject();

        if (response.getResponseStatus().equals(ResponseStatus.Error)) {
            throw response.getException();
        } else {
            return response.getData();
        }

    }
}
