/**
 * Copyright 2014 Meruvian
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
package org.meruvian.yama.webapi;

import java.io.FileNotFoundException;

import javax.inject.Inject;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.social.SocialWebAutoConfiguration;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.jetty.JettyEmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.jetty.JettyServerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.util.ResourceUtils;

/**
 * @author Dian Aditya
 *
 */
@SpringBootApplication
@EnableAutoConfiguration(exclude = { SocialWebAutoConfiguration.class })
@ComponentScan({ "org.meruvian.yama" })
public class Application {
	public static final String PROFILE_DEV = "dev";
	public static final String PROFILE_PROD = "prod";
	public static final String PROFILE_WEB = "web";
	
	@Inject
	private Environment env;
	
	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(Application.class);
		application.setShowBanner(false);
		
		application.run(args);
	}
	
	@Bean
	public EmbeddedServletContainerCustomizer servletContainerCustomizer() throws FileNotFoundException {
		final String absoluteKeystoreFile = ResourceUtils.getFile(env.getProperty("keystore.file")).getAbsolutePath();
		final String absoluteTruststoreFile = ResourceUtils.getFile(env.getProperty("truststore.file")).getAbsolutePath();
		
	    return new EmbeddedServletContainerCustomizer() {

	        @Override
	        public void customize(ConfigurableEmbeddedServletContainer container) {
	            if (container instanceof JettyEmbeddedServletContainerFactory) {
	                customizeJetty((JettyEmbeddedServletContainerFactory) container);
	            }
	        }

	        private void customizeJetty(JettyEmbeddedServletContainerFactory factory) {
	            factory.addServerCustomizers(new JettyServerCustomizer() {

	                @Override
	                public void customize(Server server) {
	                	SslContextFactory sslContextFactory = new SslContextFactory();
                        sslContextFactory.setKeyStorePath(absoluteKeystoreFile);
                        sslContextFactory.setKeyStorePassword(env.getProperty("keystore.password"));
                        sslContextFactory.setKeyStoreType("PKCS12");
                        sslContextFactory.setTrustStorePath(absoluteTruststoreFile);
                        sslContextFactory.setTrustStorePassword(env.getProperty("truststore.password"));
                        sslContextFactory.setTrustStoreType("JKS");
                        sslContextFactory.setNeedClientAuth(true);

                        ServerConnector sslConnector = new ServerConnector(	server, sslContextFactory);
                        sslConnector.setPort(8444);
                        server.setConnectors(new Connector[] { sslConnector });
	                }
	            });
	        }
	    };
	}
}
