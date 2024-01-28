package com.husqvarna.todoapp.constant;

/**
 * Constant class for application
 */
public final class TodoApiConstants {
    public static final String MISSING_OR_INVALID_PARAMETERS_ERROR_CODE = "MISSING_OR_INVALID_PARAMETERS_001";
    public static final String MISSING_OR_INVALID_PARAMETERS_ERROR_MESSAGE= "Please re-check the service contract and ensure required fields are provided and in valid format";
    public static final String INVALID_ITEM_ID_ERROR_CODE = "INVALID_ITEM_ID_002";
    public static final String INVALID_ITEM_ID_ERROR_MESSAGE = "Invalid id provided for the detailed request. Please check the id in the list of items response";
    public static final String ITEM_DOES_NOT_EXISTS_ERROR_CODE = "ITEM_DOES_NOT_EXISTS_003";
    public static final String ITEM_DOES_NOT_EXISTS_ERROR_MESSAGE = "Item not found";
    /**
     * Private constructor for the constant class
     */
    private TodoApiConstants()
    {
        throw new IllegalStateException("Please use class name to access static fields");
    }
}
