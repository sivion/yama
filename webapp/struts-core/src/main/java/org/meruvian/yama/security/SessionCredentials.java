/**
 * Copyright 2012 Meruvian
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0 
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.meruvian.yama.security;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.meruvian.yama.role.Role;

/**
 * @author Dian Aditya
 * 
 */
public class SessionCredentials {
	public static final String CIMANDE_SECURITY_USER = "cimandeSecurityUser";
	public static final String CIMANDE_SECURITY_ROLE = "cimandeSecurityRole";
	public static final String CIMANDE_SECURITY_GRANTED_URL = "cimandeSecurityGrantedUrl";
	public static final String CIMANDE_SECURITY_MENUS = "cimandeSecurityMenus";
	public static final String SPRING_SECURITY_USER = "springSecurityUser";

	public static HttpSession session() {
		return ServletActionContext.getRequest().getSession(true);
	}

	public static User currentUser() {
		return (User) session().getAttribute(CIMANDE_SECURITY_USER);
	}

	public static Role currentRole() {
		return (Role) session().getAttribute(CIMANDE_SECURITY_ROLE);
	}
}