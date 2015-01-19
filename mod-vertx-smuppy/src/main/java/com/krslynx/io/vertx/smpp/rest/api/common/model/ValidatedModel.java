package com.krslynx.io.vertx.smpp.rest.api.common.model;

import java.util.ArrayList;
import java.util.List;

public abstract class ValidatedModel {

    /**
     * The default warning message
     */
    protected static final String WARNING_MSG = "Warning raised when validating model";

    /**
     * The default critical error message
     */
    protected static final String CRITICAL_MSG = "Critical error when validating model";

    /**
     * The default critical validation error for null fields.
     */
    protected static final String CRITICAL_MSG_NULL_FIELD = CRITICAL_MSG + ", required field was null: ";

    /**
     * The default critical validation error for empty fields.
     */
    protected static final String CRITICAL_MSG_EMPTY_FIELD = CRITICAL_MSG + ", required field was empty: ";

    /**
     * Storage for errors in the model.
     */
    private List<String> errors = new ArrayList<>();

    /**
     * Storage for warnings in the model.
     */
    private List<String> warnings = new ArrayList<>();

    /**
     * The database identifier for an entity.
     */
    public String _id;

    /**
     * Whether or not the entity is valid.
     */
    public boolean valid;

    /**
     * Behavioral setting on whether or not empty strings are checked for critical error validation
     */
    public static boolean checkEmptyStrings = true;

    public abstract boolean isValid();

    protected boolean addError(String error) {
        return this.errors.add(error);
    }

    protected boolean addWarning(String warning) {
        return this.warnings.add(warning);
    }

    protected void validateField(Object field, String fieldName) {
        this.validateField(field, fieldName, false);
    }

    private void validateField(Object field, String fieldName, boolean isWarning) {

        if(isWarning) {
            // todo: support warning messages
            return;
        }

        if(field == null) {
            this.addError(CRITICAL_MSG_NULL_FIELD + "name");
            this.valid = false;
            return;
        }

        if(field instanceof String && checkEmptyStrings) {
            validateStringField((String) field, fieldName);
        }
    }

    private void validateStringField(String field, String fieldName) {
        if(field.isEmpty()) {
            this.addError(CRITICAL_MSG_EMPTY_FIELD + fieldName);
            this.valid = false;
        }
    }

}
