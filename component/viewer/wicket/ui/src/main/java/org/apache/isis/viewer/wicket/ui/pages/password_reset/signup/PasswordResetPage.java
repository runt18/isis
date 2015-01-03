/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */

package org.apache.isis.viewer.wicket.ui.pages.password_reset.signup;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;
import org.apache.isis.viewer.wicket.ui.errors.ExceptionModel;
import org.apache.isis.viewer.wicket.ui.pages.AccountManagementPageAbstract;

/**
 * A page used for resetting the password of an user.
 */
public class PasswordResetPage extends AccountManagementPageAbstract {
    
    private static final long serialVersionUID = 1L;

    private static final String ID_CONTENT_PANEL = "passwordResetPanel";

    public PasswordResetPage(final PageParameters parameters) {
        this(parameters, getAndClearExceptionModelIfAny());
    }

    public PasswordResetPage(final PageParameters parameters, ExceptionModel exceptionModel) {
        super(parameters, exceptionModel);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        StringValue uuidValue = getPageParameters().get(0);
        if (uuidValue.isEmpty()) {
            addPasswordResetEmailPanel(ID_CONTENT_PANEL);
        } else {
            String uuid = uuidValue.toString();

            // TODO ISIS-987 Check that there is a record in AccountConfirmationMap and an ApplicationUser
            // Show error feedback.
            // Otherwise show password reset panel

            addPasswordResetPanel(ID_CONTENT_PANEL, uuid);
        }
    }

    /**
     * Shows a panel with password reset form fields.
     *
     * @param id The component id
     * @param uuid A unique id used to identify the email of the user whose password will be reset
     * @return A panel with "password reset" form fields
     */
    protected PasswordResetPanel addPasswordResetPanel(String id, String uuid) {
        final PasswordResetPanel passwordResetPanel = new PasswordResetPanel(id, uuid);
        addOrReplace(passwordResetPanel);
        return passwordResetPanel;
    }

    /**
     * Shows a panel where where the user has to provide her email address.
     * An email with unique url will be sent to this email address. Once clicked
     * {@link #addPasswordResetPanel(String, String)} will be used to actually
     * change the password
     *
     * @param id The component id
     * @return A panel with "send email for password reset" functionality
     */
    protected PasswordResetEmailPanel addPasswordResetEmailPanel(String id) {
        final PasswordResetEmailPanel passwordResetEmailPanel = new PasswordResetEmailPanel(id);
        addOrReplace(passwordResetEmailPanel);
        return passwordResetEmailPanel;
    }
}
