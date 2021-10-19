package com.timbuchalka;

import com.timbuchalka.model.Datasource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

    public static void main(String[] args) {
        Datasource datasource = new Datasource();
        if(!datasource.open()) {
            System.out.println("Can't open datasource");
            return;
        }

        datasource.close();
    }
}
