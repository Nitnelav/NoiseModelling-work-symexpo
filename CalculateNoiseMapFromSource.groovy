package org.noise_planet.noisemodelling.work

import org.h2gis.functions.factory.H2GISDBFactory
import org.h2gis.utilities.JDBCUtilities
import org.noise_planet.noisemodelling.wps.NoiseModelling.Noise_level_from_source

import java.sql.Connection

class CalculateNoiseMapFromSource {

    public static void main(String[] args) {

        Connection connection;
        String dbName = "file:///home/valoo/Projects/IFSTTAR/scenario_25_percent.db"
        // String dbFilePath = (new File(dbName)).getAbsolutePath();
        // connection = DriverManager.getConnection("jdbc:h2:" + dbFilePath + ";LOCK_MODE=0;LOG=0;DB_CLOSE_DELAY=5", "sa", "sa");
        connection = JDBCUtilities.wrapConnection(H2GISDBFactory.openSpatialDataBase(dbName));

        calculateNoiseMap(connection);
    }

    public static void calculateNoiseMap(Connection connection) {
        calculateNoiseMap(connection, [
                "tableBuilding": "BUILDINGS",
                "tableReceivers" : "ACTIVITIES_RECEIVERS",
                "tableSources" : "SOURCES_0DB",
                "confMaxSrcDist": 150,
                "confMaxReflDist": 50,
                "confReflOrder": 0,
                "confSkipLevening": true,
                "confSkipLnight": true,
                "confSkipLden": true,
                "confThreadNumber": 14,
                "confExportSourceId": true,
                "confDiffVertical": false,
                "confDiffHorizontal": false
        ])
    }

    public static void calculateNoiseMap(Connection connection, options) {
        println "-------------------------------"
        println "Calculate Noise Map From Source - " + options.get("tableSources")
        println "-------------------------------"
        new Noise_level_from_source().exec(connection, options)
    }
}

