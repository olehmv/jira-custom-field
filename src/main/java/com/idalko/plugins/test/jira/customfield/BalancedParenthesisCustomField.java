package com.idalko.plugins.test.jira.customfield;

import com.atlassian.plugin.spring.scanner.annotation.component.Scanned;
import com.atlassian.plugin.spring.scanner.annotation.imports.JiraImport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.atlassian.jira.issue.customfields.impl.TextCFType;
import com.atlassian.jira.issue.customfields.manager.GenericConfigManager;
import com.atlassian.jira.issue.customfields.persistence.CustomFieldValuePersister;
import com.atlassian.jira.issue.customfields.impl.FieldValidationException;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.fields.CustomField;
import com.atlassian.jira.issue.fields.config.FieldConfig;
import com.atlassian.jira.issue.fields.layout.field.FieldLayoutItem;

import java.util.List;
import java.util.Map;

import com.atlassian.jira.issue.customfields.impl.AbstractSingleFieldType;

import java.math.BigDecimal;

import com.atlassian.jira.issue.customfields.persistence.PersistenceFieldType;
import static com.idalko.plugins.test.jira.customfield.BalancedParan.areParenthesisBalanced;

@Scanned
public class BalancedParenthesisCustomField extends AbstractSingleFieldType<String> {
    private static final Logger log = LoggerFactory.getLogger(BalancedParenthesisCustomField.class);


    public BalancedParenthesisCustomField(
            @JiraImport CustomFieldValuePersister customFieldValuePersister,
            @JiraImport GenericConfigManager genericConfigManager) {
        super(customFieldValuePersister, genericConfigManager);
    }

    @Override
    public String getStringFromSingularObject(final String singularObject) {
        if (singularObject == null)
            return null;
        else
            return singularObject.toString();
    }

    @Override
    public String getSingularObjectFromString(final String string) throws FieldValidationException {
        if (string == null)
            return null;

        if (!areParenthesisBalanced(string.toCharArray()))
            throw new FieldValidationException("Parenthesis Not Balanced!!!");
        else{
            return string;
        }
    }

    @Override
    protected PersistenceFieldType getDatabaseType() {
        return PersistenceFieldType.TYPE_LIMITED_TEXT;
    }

    @Override
    protected String getObjectFromDbValue(final Object databaseValue)
            throws FieldValidationException {
        return getSingularObjectFromString((String) databaseValue);
    }

    @Override
    protected Object getDbValueFromObject(final String customFieldObject) {
        return getStringFromSingularObject(customFieldObject);
    }
}