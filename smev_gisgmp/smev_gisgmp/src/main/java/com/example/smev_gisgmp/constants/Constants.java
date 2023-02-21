package com.example.smev_gisgmp.constants;

public class Constants {

    public final static String SELECT_INFORMATION_QUERY = "SELECT * FROM information WHERE vehicleCertificate=?";
    public final static String SELECT_INFORMATION_QUERY_FROM_QUEUE = "SELECT * FROM information";
    public final static String SELECT_PENALTY_QUERY = "SELECT * FROM penalties WHERE vehicleCertificate = ?";
    public final static String SELECT_PENALTY_TO_RESPONSE = "SELECT * FROM penaltyToResponse";
    public final static String SELECT_PENALTY_TO_RESPONSE_TEST = "SELECT * FROM penaltyToResponse";

    public final static String INSERT_INFORMATION = "INSERT INTO information VALUES(?, ?)";
    public final static String INSERT_PENALTIES_TEST = "INSERT INTO penalties VALUES (?, ?, ?, ?, ?, ?, ?)";
    public final static String INSERT_PENALTY_TO_RESPONSE = "INSERT INTO penaltyToResponse VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    public final static String INSERT_PENALTY_TO_RESPONSE_TEST = "INSERT INTO penaltyToResponse VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    public final static String DELETE_INFORMATION = "DELETE FROM information WHERE vehicleCertificate = ?";
    public final static String DELETE_ALL_PENALTIES_TO_RESPONSE = "DELETE FROM penaltyToResponse WHERE responseId = ?";
}
