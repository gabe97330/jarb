package org.jarb.violation;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Representation of the constraint violation that occurred.
 * 
 * @author Jeroen van Schagen
 * @since 16-05-2011
 */
public final class ConstraintViolation {
    private final ConstraintViolationType type;
    private String constraintName;
    private String tableName;
    private String columnName;

    private Object value;
    private String valueType;
    private String expectedType;
    private Long maximumLength;

    private ConstraintViolation(ConstraintViolationType type) {
        this.type = type;
    }

    public ConstraintViolationType getType() {
        return type;
    }

    public String getConstraintName() {
        return constraintName;
    }

    public String getTableName() {
        return tableName;
    }

    public String getColumnName() {
        return columnName;
    }

    public Object getValue() {
        return value;
    }

    public String getValueType() {
        return valueType;
    }

    public String getExpectedType() {
        return expectedType;
    }

    public Long getMaximumLength() {
        return maximumLength;
    }

    public static class Builder {
        private final ConstraintViolationType type;
        private String constraintName;
        private String tableName;
        private String columnName;

        private Object value;
        private String valueType;
        private String expectedType;
        private Long maximumLength;

        public Builder(ConstraintViolationType type) {
            this.type = type;
        }

        public Builder setConstraintName(String constraintName) {
            this.constraintName = constraintName;
            return this;
        }

        public Builder setTableName(String tableName) {
            this.tableName = tableName;
            return this;
        }

        public Builder setColumnName(String columnName) {
            this.columnName = columnName;
            return this;
        }

        public Builder setValue(Object value) {
            this.value = value;
            return this;
        }

        public Builder setValueType(String valueType) {
            this.valueType = valueType;
            return this;
        }

        public Builder setExpectedType(String expectedType) {
            this.expectedType = expectedType;
            return this;
        }

        public Builder setMaximumLength(Long maximumLength) {
            this.maximumLength = maximumLength;
            return this;
        }

        public ConstraintViolation build() {
            ConstraintViolation violation = new ConstraintViolation(type);
            violation.constraintName = constraintName;
            violation.tableName = tableName;
            violation.columnName = columnName;
            violation.value = value;
            violation.valueType = valueType;
            violation.expectedType = expectedType;
            violation.maximumLength = maximumLength;
            return violation;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
