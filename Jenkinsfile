#!/usr/bin/env groovy
/*
 * aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
 * Copyright (C) 2021, 2022, 2023, 2024, 2025, 2026  AO Industries, Inc.
 *     support@aoindustries.com
 *     7262 Bull Pen Cir
 *     Mobile, AL 36695
 *
 * This file is part of aoweb-struts.
 *
 * aoweb-struts is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * aoweb-struts is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with aoweb-struts.  If not, see <https://www.gnu.org/licenses/>.
 */

// Parent, Extensions, Plugins, Direct and BOM Dependencies
binding.setVariable('upstreamProjects', [
  // Parent
  'oss/parent', // <groupId>com.aoapps</groupId><artifactId>ao-oss-parent</artifactId>
  // Parent Plugin Dependencies (Avoid cyclic dependency)
  'oss/pgp-keys-map', // <groupId>com.aoapps</groupId><artifactId>pgp-keys-map</artifactId>
  'oss/javadoc-offline', // <groupId>com.aoapps</groupId><artifactId>ao-javadoc-offline</artifactId>
  'oss/javadoc-resources', // <groupId>com.aoapps</groupId><artifactId>ao-javadoc-resources</artifactId>
  'oss/ant-tasks', // <groupId>com.aoapps</groupId><artifactId>ao-ant-tasks</artifactId>
  'oss/checkstyle-config', // <groupId>com.aoapps</groupId><artifactId>ao-checkstyle-config</artifactId>

  // Plugins
  // No Jenkins: <groupId>org.codehaus.gmaven</groupId><artifactId>groovy-maven-plugin</artifactId>

  // "development" profile
  // Plugins
  // No Jenkins: <groupId>org.apache.struts</groupId><artifactId>struts2-config-browser-plugin</artifactId>
  // Own -devel package
  'aoweb-struts-devel', // <groupId>com.aoindustries</groupId><artifactId>aoweb-struts-devel</artifactId>
  // Direct
  // No -devel: <groupId>com.aoapps</groupId><artifactId>ao-collections-devel</artifactId>
  'oss/encoding-devel', // <groupId>com.aoapps</groupId><artifactId>ao-encoding-devel</artifactId>
  // No -devel: <groupId>com.aoapps</groupId><artifactId>ao-encoding-servlet-devel</artifactId>
  'oss/encoding-taglib-devel', // <groupId>com.aoapps</groupId><artifactId>ao-encoding-taglib-devel</artifactId>
  // No -devel: <groupId>com.aoapps</groupId><artifactId>ao-fluent-html-devel</artifactId>
  'oss/fluent-html-any-devel', // <groupId>com.aoapps</groupId><artifactId>ao-fluent-html-any-devel</artifactId>
  // No -devel: <groupId>com.aoapps</groupId><artifactId>ao-fluent-html-servlet-devel</artifactId>
  // No -devel: <groupId>com.aoapps</groupId><artifactId>ao-fluent-html-util-devel</artifactId>
  'oss/hodgepodge-devel', // <groupId>com.aoapps</groupId><artifactId>ao-hodgepodge-devel</artifactId>
  // No -devel: <groupId>com.aoapps</groupId><artifactId>ao-io-buffer-devel</artifactId>
  'oss/lang-devel', // <groupId>com.aoapps</groupId><artifactId>ao-lang-devel</artifactId>
  'oss/net-types-devel', // <groupId>com.aoapps</groupId><artifactId>ao-net-types-devel</artifactId>
  'oss/payments/api-devel', // <groupId>com.aoapps</groupId><artifactId>ao-payments-api-devel</artifactId>
  // No -devel: <groupId>com.aoapps</groupId><artifactId>ao-security-devel</artifactId>
  // No -devel: <groupId>com.aoapps</groupId><artifactId>ao-servlet-filter-devel</artifactId>
  // No -devel: <groupId>com.aoapps</groupId><artifactId>ao-servlet-last-modified-devel</artifactId>
  'oss/servlet-util-devel', // <groupId>com.aoapps</groupId><artifactId>ao-servlet-util-devel</artifactId>
  'oss/sql-devel', // <groupId>com.aoapps</groupId><artifactId>ao-sql-devel</artifactId>
  // No -devel: <groupId>com.aoapps</groupId><artifactId>ao-style-devel</artifactId>
  'oss/taglib-devel', // <groupId>com.aoapps</groupId><artifactId>ao-taglib-devel</artifactId>
  // No -devel: <groupId>com.aoapps</groupId><artifactId>ao-tempfiles-servlet-devel</artifactId>
  // No -devel: <groupId>com.aoapps</groupId><artifactId>ao-web-resources-registry-devel</artifactId>
  // No -devel: <groupId>com.aoapps</groupId><artifactId>ao-web-resources-renderer-devel</artifactId>
  // No -devel: <groupId>com.aoapps</groupId><artifactId>ao-web-resources-servlet-devel</artifactId>
  'aoserv/client-devel', // <groupId>com.aoindustries</groupId><artifactId>aoserv-client-devel</artifactId>
  // No -devel: <groupId>com.aoindustries</groupId><artifactId>aoserv-credit-cards-devel</artifactId>
  // No -devel: <groupId>com.aoindustries</groupId><artifactId>aoserv-daemon-client-devel</artifactId>
  // No -devel: <groupId>org.apache.commons</groupId><artifactId>commons-collections4-devel</artifactId>
  // No -devel: <groupId>commons-validator</groupId><artifactId>commons-validator-devel</artifactId>
  // No -devel: <groupId>com.google.api</groupId><artifactId>gax-devel</artifactId>
  // No -devel: <groupId>com.google.auth</groupId><artifactId>google-auth-library-credentials-devel</artifactId>
  // No -devel: <groupId>com.google.auth</groupId><artifactId>google-auth-library-oauth2-http-devel</artifactId>
  // No -devel: <groupId>com.google.cloud</groupId><artifactId>google-cloud-recaptchaenterprise-devel</artifactId>
  // No -devel: <groupId>jakarta.mail</groupId><artifactId>jakarta.mail-api-devel</artifactId>
  // No -devel: <groupId>jakarta.servlet</groupId><artifactId>jakarta.servlet-api-devel</artifactId>
  // No -devel: <groupId>jakarta.servlet.jsp</groupId><artifactId>jakarta.servlet.jsp-api-devel</artifactId>
  // No -devel: <groupId>jakarta.websocket</groupId><artifactId>jakarta.websocket-api-devel</artifactId>
  // No -devel: <groupId>jakarta.websocket</groupId><artifactId>jakarta.websocket-client-api-devel</artifactId>
  // No -devel: <groupId>com.google.api.grpc</groupId><artifactId>proto-google-cloud-recaptchaenterprise-v1-devel</artifactId>
  'semanticcms-2.x/core/taglib-devel', // <groupId>com.semanticcms</groupId><artifactId>semanticcms-core-taglib-devel</artifactId>
  // No -devel: <groupId>io.github.weblegacy</groupId><artifactId>struts-core-devel</artifactId>
  // No -devel: <groupId>io.github.weblegacy</groupId><artifactId>struts-taglib-devel</artifactId>
  // No -devel: <groupId>org.apache.struts</groupId><artifactId>struts2-convention-plugin-devel</artifactId>
  // No -devel: <groupId>org.apache.struts</groupId><artifactId>struts2-core-devel</artifactId>
  // Transitive
  // No -devel: <groupId>org.codehaus.mojo</groupId><artifactId>animal-sniffer-annotations-devel</artifactId>
  // No -devel: <groupId>org.antlr</groupId><artifactId>antlr4-runtime-devel</artifactId>
  // No -devel: <groupId>com.aoapps</groupId><artifactId>ao-tempfiles-devel</artifactId>
  // No -devel: <groupId>com.aoapps</groupId><artifactId>ao-tlds-devel</artifactId>
  // No -devel: <groupId>com.google.api</groupId><artifactId>api-common-devel</artifactId>
  // No -devel: <groupId>org.ow2.asm</groupId><artifactId>asm-devel</artifactId>
  // No -devel: <groupId>org.ow2.asm</groupId><artifactId>asm-commons-devel</artifactId>
  // No -devel: <groupId>org.ow2.asm</groupId><artifactId>asm-tree-devel</artifactId>
  // No -devel: <groupId>com.google.auto.value</groupId><artifactId>auto-value-annotations-devel</artifactId>
  // No -devel: <groupId>com.github.ben-manes.caffeine</groupId><artifactId>caffeine-devel</artifactId>
  // No -devel: <groupId>org.checkerframework</groupId><artifactId>checker-qual-devel</artifactId>
  // No -devel: <groupId>commons-beanutils</groupId><artifactId>commons-beanutils-devel</artifactId>
  // No -devel: <groupId>io.github.weblegacy</groupId><artifactId>commons-chain-devel</artifactId>
  // No -devel: <groupId>io.github.weblegacy</groupId><artifactId>commons-chain-web-devel</artifactId>
  // No -devel: <groupId>io.github.weblegacy</groupId><artifactId>commons-chain-web-jakarta-devel</artifactId>
  // No -devel: <groupId>io.github.weblegacy</groupId><artifactId>commons-chain-web-jakarta-servlet-devel</artifactId>
  // No -devel: <groupId>commons-codec</groupId><artifactId>commons-codec-devel</artifactId>
  // No -devel: <groupId>commons-collections</groupId><artifactId>commons-collections-devel</artifactId>
  // No -devel: <groupId>commons-digester</groupId><artifactId>commons-digester-devel</artifactId>
  // No -devel: <groupId>org.apache.commons</groupId><artifactId>commons-fileupload2-core-devel</artifactId>
  // No -devel: <groupId>org.apache.commons</groupId><artifactId>commons-fileupload2-jakarta-devel</artifactId>
  // No -devel: <groupId>org.apache.commons</groupId><artifactId>commons-fileupload2-jakarta-servlet6-devel</artifactId>
  // No -devel: <groupId>commons-io</groupId><artifactId>commons-io-devel</artifactId>
  // No -devel: <groupId>org.apache.commons</groupId><artifactId>commons-lang3-devel</artifactId>
  // No -devel: <groupId>commons-logging</groupId><artifactId>commons-logging-devel</artifactId>
  // No -devel: <groupId>org.apache.commons</groupId><artifactId>commons-text-devel</artifactId>
  // No -devel: <groupId>org.conscrypt</groupId><artifactId>conscrypt-openjdk-uber-devel</artifactId>
  // No -devel: <groupId>com.google.errorprone</groupId><artifactId>error_prone_annotations-devel</artifactId>
  // No -devel: <groupId>com.google.guava</groupId><artifactId>failureaccess-devel</artifactId>
  // No -devel: <groupId>org.freemarker</groupId><artifactId>freemarker-devel</artifactId>
  // No -devel: <groupId>com.google.api</groupId><artifactId>gax-grpc-devel</artifactId>
  // No -devel: <groupId>com.google.api</groupId><artifactId>gax-httpjson-devel</artifactId>
  // No -devel: <groupId>com.google.http-client</groupId><artifactId>google-http-client-devel</artifactId>
  // No -devel: <groupId>com.google.http-client</groupId><artifactId>google-http-client-gson-devel</artifactId>
  // No -devel: <groupId>io.grpc</groupId><artifactId>grpc-alts-devel</artifactId>
  // No -devel: <groupId>io.grpc</groupId><artifactId>grpc-api-devel</artifactId>
  // No -devel: <groupId>io.grpc</groupId><artifactId>grpc-auth-devel</artifactId>
  // No -devel: <groupId>io.grpc</groupId><artifactId>grpc-context-devel</artifactId>
  // No -devel: <groupId>io.grpc</groupId><artifactId>grpc-core-devel</artifactId>
  // No -devel: <groupId>io.grpc</groupId><artifactId>grpc-grpclb-devel</artifactId>
  // No -devel: <groupId>io.grpc</groupId><artifactId>grpc-inprocess-devel</artifactId>
  // No -devel: <groupId>io.grpc</groupId><artifactId>grpc-netty-shaded-devel</artifactId>
  // No -devel: <groupId>io.grpc</groupId><artifactId>grpc-protobuf-devel</artifactId>
  // No -devel: <groupId>io.grpc</groupId><artifactId>grpc-stub-devel</artifactId>
  // No -devel: <groupId>com.google.code.gson</groupId><artifactId>gson-devel</artifactId>
  // No -devel: <groupId>com.google.guava</groupId><artifactId>guava-devel</artifactId>
  // No -devel: <groupId>org.apache.httpcomponents</groupId><artifactId>httpclient-devel</artifactId>
  // No -devel: <groupId>org.apache.httpcomponents</groupId><artifactId>httpcore-devel</artifactId>
  // No -devel: <groupId>com.google.j2objc</groupId><artifactId>j2objc-annotations-devel</artifactId>
  // No -devel: <groupId>jakarta.activation</groupId><artifactId>jakarta.activation-api-devel</artifactId>
  // No -devel: <groupId>jakarta.el</groupId><artifactId>jakarta.el-api-devel</artifactId>
  // No -devel: <groupId>jakarta.servlet.jsp.jstl</groupId><artifactId>jakarta.servlet.jsp.jstl-api-devel</artifactId>
  // No -devel: <groupId>org.javassist</groupId><artifactId>javassist-devel</artifactId>
  // No -devel: <groupId>javax.annotation</groupId><artifactId>javax.annotation-api-devel</artifactId>
  // No -devel: <groupId>org.slf4j</groupId><artifactId>jcl-over-slf4j-devel</artifactId>
  // No -devel: <groupId>com.google.code.findbugs</groupId><artifactId>jsr305-devel</artifactId>
  // No -devel: <groupId>com.google.guava</groupId><artifactId>listenablefuture-devel</artifactId>
  // No -devel: <groupId>org.apache.logging.log4j</groupId><artifactId>log4j-api-devel</artifactId>
  // No -devel: <groupId>ognl</groupId><artifactId>ognl-devel</artifactId>
  // No -devel: <groupId>io.opencensus</groupId><artifactId>opencensus-api-devel</artifactId>
  // No -devel: <groupId>io.opencensus</groupId><artifactId>opencensus-contrib-http-util-devel</artifactId>
  // No -devel: <groupId>com.google.api.grpc</groupId><artifactId>proto-google-cloud-recaptchaenterprise-v1beta1-devel</artifactId>
  // No -devel: <groupId>com.google.api.grpc</groupId><artifactId>proto-google-common-protos-devel</artifactId>
  // No -devel: <groupId>com.google.protobuf</groupId><artifactId>protobuf-java-devel</artifactId>
  // No -devel: <groupId>com.google.protobuf</groupId><artifactId>protobuf-java-util-devel</artifactId>
  // No -devel: <groupId>com.semanticcms</groupId><artifactId>semanticcms-core-model-devel</artifactId>
  // No -devel: <groupId>org.slf4j</groupId><artifactId>slf4j-api-devel</artifactId>
  // No -devel: <groupId>org.threeten</groupId><artifactId>threetenbp-devel</artifactId>
  // Runtime Direct
  // No -devel: <groupId>com.aoapps</groupId><artifactId>ao-mime-mappings-devel</artifactId>
  // No -devel: <groupId>org.glassfish.web</groupId><artifactId>jakarta.servlet.jsp.jstl-devel</artifactId>
  // No -devel: <groupId>org.apache.logging.log4j</groupId><artifactId>log4j-core-devel</artifactId>
  'semanticcms-2.x/core/servlet-devel', // <groupId>com.semanticcms</groupId><artifactId>semanticcms-core-servlet-devel</artifactId>
  // No -devel: <groupId>org.slf4j</groupId><artifactId>slf4j-jdk14-devel</artifactId>
  // Runtime Transitive
  // No -devel: <groupId>org.eclipse.angus</groupId><artifactId>angus-activation-devel</artifactId>
  // No -devel: <groupId>com.google.android</groupId><artifactId>annotations-devel</artifactId>
  // No -devel: <groupId>com.aoapps</groupId><artifactId>ao-concurrent-devel</artifactId>
  // No -devel: <groupId>com.aoapps</groupId><artifactId>ao-servlet-subrequest-devel</artifactId>
  // No -devel: <groupId>io.grpc</groupId><artifactId>grpc-googleapis-devel</artifactId>
  // No -devel: <groupId>io.grpc</groupId><artifactId>grpc-protobuf-lite-devel</artifactId>
  // No -devel: <groupId>io.grpc</groupId><artifactId>grpc-services-devel</artifactId>
  // No -devel: <groupId>io.grpc</groupId><artifactId>grpc-util-devel</artifactId>
  // No -devel: <groupId>io.grpc</groupId><artifactId>grpc-xds-devel</artifactId>
  // No -devel: <groupId>io.perfmark</groupId><artifactId>perfmark-api-devel</artifactId>
  // No -devel: <groupId>com.google.re2j</groupId><artifactId>re2j-devel</artifactId>
  // Imports
  // No -devel: <groupId>com.aoapps</groupId><artifactId>jakartaee-web-profile-devel</artifactId>
  // No -devel: <groupId>com.google.cloud</groupId><artifactId>libraries-bom-devel</artifactId>

  // Direct
  'oss/collections', // <groupId>com.aoapps</groupId><artifactId>ao-collections</artifactId>
  'oss/encoding', // <groupId>com.aoapps</groupId><artifactId>ao-encoding</artifactId>
  'oss/encoding-servlet', // <groupId>com.aoapps</groupId><artifactId>ao-encoding-servlet</artifactId>
  'oss/encoding-taglib', // <groupId>com.aoapps</groupId><artifactId>ao-encoding-taglib</artifactId>
  'oss/fluent-html', // <groupId>com.aoapps</groupId><artifactId>ao-fluent-html</artifactId>
  'oss/fluent-html-any', // <groupId>com.aoapps</groupId><artifactId>ao-fluent-html-any</artifactId>
  'oss/fluent-html-servlet', // <groupId>com.aoapps</groupId><artifactId>ao-fluent-html-servlet</artifactId>
  'oss/fluent-html-util', // <groupId>com.aoapps</groupId><artifactId>ao-fluent-html-util</artifactId>
  'oss/hodgepodge', // <groupId>com.aoapps</groupId><artifactId>ao-hodgepodge</artifactId>
  'oss/io-buffer', // <groupId>com.aoapps</groupId><artifactId>ao-io-buffer</artifactId>
  'oss/lang', // <groupId>com.aoapps</groupId><artifactId>ao-lang</artifactId>
  'oss/net-types', // <groupId>com.aoapps</groupId><artifactId>ao-net-types</artifactId>
  'oss/payments/api', // <groupId>com.aoapps</groupId><artifactId>ao-payments-api</artifactId>
  'oss/security', // <groupId>com.aoapps</groupId><artifactId>ao-security</artifactId>
  'oss/servlet-filter', // <groupId>com.aoapps</groupId><artifactId>ao-servlet-filter</artifactId>
  'oss/servlet-last-modified', // <groupId>com.aoapps</groupId><artifactId>ao-servlet-last-modified</artifactId>
  'oss/servlet-util', // <groupId>com.aoapps</groupId><artifactId>ao-servlet-util</artifactId>
  'oss/sql', // <groupId>com.aoapps</groupId><artifactId>ao-sql</artifactId>
  'oss/style', // <groupId>com.aoapps</groupId><artifactId>ao-style</artifactId>
  'oss/taglib', // <groupId>com.aoapps</groupId><artifactId>ao-taglib</artifactId>
  'oss/tempfiles-servlet', // <groupId>com.aoapps</groupId><artifactId>ao-tempfiles-servlet</artifactId>
  'oss/web-resources/registry', // <groupId>com.aoapps</groupId><artifactId>ao-web-resources-registry</artifactId>
  'oss/web-resources/renderer', // <groupId>com.aoapps</groupId><artifactId>ao-web-resources-renderer</artifactId>
  'oss/web-resources/servlet', // <groupId>com.aoapps</groupId><artifactId>ao-web-resources-servlet</artifactId>
  'aoserv/client', // <groupId>com.aoindustries</groupId><artifactId>aoserv-client</artifactId>
  'aoserv/credit-cards', // <groupId>com.aoindustries</groupId><artifactId>aoserv-credit-cards</artifactId>
  'aoserv/daemon-client', // <groupId>com.aoindustries</groupId><artifactId>aoserv-daemon-client</artifactId>
  // No Jenkins: <groupId>org.apache.commons</groupId><artifactId>commons-collections4</artifactId>
  // No Jenkins: <groupId>commons-validator</groupId><artifactId>commons-validator</artifactId>
  // No Jenkins: <groupId>com.google.api</groupId><artifactId>gax</artifactId>
  // No Jenkins: <groupId>com.google.auth</groupId><artifactId>google-auth-library-credentials</artifactId>
  // No Jenkins: <groupId>com.google.auth</groupId><artifactId>google-auth-library-oauth2-http</artifactId>
  // No Jenkins: <groupId>com.google.cloud</groupId><artifactId>google-cloud-recaptchaenterprise</artifactId>
  // No Jenkins: <groupId>jakarta.mail</groupId><artifactId>jakarta.mail-api</artifactId>
  // No Jenkins: <groupId>jakarta.servlet</groupId><artifactId>jakarta.servlet-api</artifactId>
  // No Jenkins: <groupId>jakarta.servlet.jsp</groupId><artifactId>jakarta.servlet.jsp-api</artifactId>
  // No Jenkins: <groupId>jakarta.websocket</groupId><artifactId>jakarta.websocket-api</artifactId>
  // No Jenkins: <groupId>jakarta.websocket</groupId><artifactId>jakarta.websocket-client-api</artifactId>
  // No Jenkins: <groupId>com.google.api.grpc</groupId><artifactId>proto-google-cloud-recaptchaenterprise-v1</artifactId>
  'semanticcms-2.x/core/taglib', // <groupId>com.semanticcms</groupId><artifactId>semanticcms-core-taglib</artifactId>
  // No Jenkins: <groupId>io.github.weblegacy</groupId><artifactId>struts-core</artifactId>
  // No Jenkins: <groupId>io.github.weblegacy</groupId><artifactId>struts-taglib</artifactId>
  // No Jenkins: <groupId>org.apache.struts</groupId><artifactId>struts2-convention-plugin</artifactId>
  // No Jenkins: <groupId>org.apache.struts</groupId><artifactId>struts2-core</artifactId>

  // Runtime Direct
  'oss/mime-mappings', // <groupId>com.aoapps</groupId><artifactId>ao-mime-mappings</artifactId>
  // No Jenkins: <groupId>org.glassfish.web</groupId><artifactId>jakarta.servlet.jsp.jstl</artifactId>
  // No Jenkins: <groupId>org.apache.logging.log4j</groupId><artifactId>log4j-core</artifactId>
  'semanticcms-2.x/core/servlet', // <groupId>com.semanticcms</groupId><artifactId>semanticcms-core-servlet</artifactId>

  // BOM
  'oss/jakartaee-web-profile-bom', // <groupId>com.aoapps</groupId><artifactId>jakartaee-web-profile-bom</artifactId>
])

// Java 17
binding.setVariable('buildJdks', ['17', '21']) // Changes must be copied to matrix axes!
binding.setVariable('testJdks', ['17', '21']) // Changes must be copied to matrix axes!

/******************************************************************************************
 *                                                                                        *
 * Everything below this line is identical for all projects, except the copied matrix     *
 * axes and any "Begin .*custom" / "End .*custom" blocks (see filter_custom script).      *
 *                                                                                        *
 *****************************************************************************************/

// Load ao-jenkins-shared-library
// TODO: Put @Library on import once we have our first library class
// TODO: Replace master with a specific tag version number once working
@Library('ao@master') _
ao.setVariables(binding, currentBuild, scm, params)

pipeline {
  agent any
  options {
    ansiColor('xterm')
    disableConcurrentBuilds(abortPrevious: true)
    quietPeriod(quietPeriod)
    skipDefaultCheckout()
    timeout(time: PIPELINE_TIMEOUT, unit: TIMEOUT_UNIT)
    // Only allowed to copy build artifacts from self
    // See https://plugins.jenkins.io/copyartifact/
    copyArtifactPermission("/${JOB_NAME}")
  }
  parameters {
    string(
      name: 'BuildPriority',
      defaultValue: "$buildPriority",
      description: BuildPriority_description
    )
    booleanParam(
      name: 'abortOnUnreadyDependency',
      defaultValue: true,
      description: abortOnUnreadyDependency_description
    )
    booleanParam(
      name: 'requireLastBuild',
      defaultValue: true,
      description: requireLastBuild_description
    )
    booleanParam(
      name: 'mavenDebug',
      defaultValue: false,
      description: mavenDebug_description
    )
    choice(
      name: 'sonarQubeAnalysis',
      choices: sonarQubeAnalysis_choices,
      description: sonarQubeAnalysis_description
    )
  }
  triggers {
    upstream(
      threshold: hudson.model.Result.SUCCESS,
      upstreamProjects: "${prunedUpstreamProjects.join(', ')}"
    )
  }
  stages {
    stage('Setup') {
      steps {
        script {
          // Additional setup that cannot be done in options inside declarative pipeline
          ao.setupBuildDiscarder()
        }
      }
    }
    stage('Check Ready') {
      when {
        expression {
          return (params.abortOnUnreadyDependency == null) ? true : params.abortOnUnreadyDependency
        }
      }
      steps {
        script {
          ao.checkReadySteps()
        }
      }
    }
    stage('Workaround Git #27287') {
      when {
        expression {
          ao.continueCurrentBuild() && projectDir != '.' && fileExists('.gitmodules')
        }
      }
      steps {
        script {
          ao.workaroundGit27287Steps(scmUrl, scmBranch, scmBrowser, sparseCheckoutPaths, disableSubmodules)
        }
      }
    }
    stage('Checkout SCM') {
      when {
        expression {
          ao.continueCurrentBuild()
        }
      }
      steps {
        script {
          ao.checkoutScmSteps(projectDir, niceCmd, scmUrl, scmBranch, scmBrowser, sparseCheckoutPaths, disableSubmodules)
        }
      }
    }
    stage('Builds') {
      matrix {
        when {
          expression {
            ao.continueCurrentBuild()
          }
        }
        axes {
          axis {
            name 'jdk'
            values '17', '21' // buildJdks
          }
        }
        stages {
          stage('Build') {
            steps {
              script {
                ao.buildSteps(projectDir, niceCmd, maven, deployJdk, mavenOpts, mvnCommon, jdk, buildPhases, testWhenExpression, testJdks)
              }
            }
          }
        }
      }
    }
    stage('Tests') {
      matrix {
        when {
          expression {
            ao.continueCurrentBuild() && testWhenExpression.call()
          }
        }
        axes {
          axis {
            name 'jdk'
            values '17', '21' // buildJdks
          }
          axis {
            name 'testJdk'
            values '17', '21' // testJdks
          }
        }
        stages {
          stage('Test') {
            steps {
              script {
                ao.testSteps(projectDir, niceCmd, deployJdk, maven, mavenOpts, mvnCommon, jdk, testJdk)
              }
            }
          }
        }
      }
    }
    stage('Deploy') {
      when {
        expression {
          ao.continueCurrentBuild()
        }
      }
      steps {
        script {
          ao.deploySteps(projectDir, niceCmd, deployJdk, maven, mavenOpts, mvnCommon)
        }
      }
    }
    stage('SonarQube analysis') {
      when {
        expression {
          ao.continueCurrentBuild() && sonarqubeWhenExpression.call()
        }
      }
      steps {
        script {
          ao.sonarQubeAnalysisSteps(projectDir, niceCmd, deployJdk, maven, mavenOpts, mvnCommon)
        }
      }
    }
    stage('Quality Gate') {
      when {
        expression {
          ao.continueCurrentBuild() && sonarqubeWhenExpression.call()
        }
      }
      steps {
        script {
          ao.qualityGateSteps()
        }
      }
    }
    stage('Analysis') {
      when {
        expression {
          ao.continueCurrentBuild()
        }
      }
      steps {
        script {
          ao.analysisSteps()
        }
      }
    }
  }
  post {
    failure {
      script {
        ao.postFailure(failureEmailTo)
      }
    }
  }
}
