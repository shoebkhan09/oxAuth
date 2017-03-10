/*
 * oxAuth is available under the MIT License (2008). See http://opensource.org/licenses/MIT for full text.
 *
 * Copyright (c) 2014, Gluu
 */

package org.xdi.oxauth.service.token;

import org.apache.commons.lang.StringUtils;
import org.xdi.util.StringHelper;
import javax.enterprise.context.ApplicationScoped;
import org.jboss.seam.annotations.AutoCreate;
import javax.inject.Inject;
import javax.inject.Named;
import org.jboss.seam.annotations.Scope;
import org.xdi.oxauth.model.common.AuthorizationGrant;
import org.xdi.oxauth.model.common.AuthorizationGrantList;

/**
 * Token specific service methods
 *
 * @author Yuriy Movchan Date: 10/03/2012
 */
@Stateless
@Named("tokenService")
@AutoCreate
public class TokenService {

//	@Inject
//    private Logger log;
    @Inject
    private AuthorizationGrantList authorizationGrantList;

	public String getTokenFromAuthorizationParameter(String authorizationParameter) {
        final String prefix = "Bearer ";
        if (StringHelper.isNotEmpty(authorizationParameter) && authorizationParameter.startsWith(prefix)) {
        	return authorizationParameter.substring(prefix.length());
        }

        return null;
	}

    public AuthorizationGrant getAuthorizationGrant(String p_authorization) {
        final String token = getTokenFromAuthorizationParameter(p_authorization);
        if (StringUtils.isNotBlank(token)) {
    	    return authorizationGrantList.getAuthorizationGrantByAccessToken(token);
        }
        return null;
    }

    public String getClientDn(String p_authorization) {
        final AuthorizationGrant grant = getAuthorizationGrant(p_authorization);
        if (grant != null) {
            return grant.getClientDn();
        }
        return "";
    }

}
