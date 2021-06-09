FROM jboss/wildfly:17.0.1.Final
USER root
ENV WILDFLY_HOME ${JBOSS_HOME}

COPY ./db-driver /tmp 
COPY ./jboss-config.cli /${WILDFLY_HOME}/customization/jboss-config.cli 
RUN ${WILDFLY_HOME}/bin/add-user.sh admin nimda && \
${WILDFLY_HOME}/bin/jboss-cli.sh  --file=${WILDFLY_HOME}/customization/jboss-config.cli \
&& chmod +rwx /tmp \
&& rm -rf /tmp

EXPOSE 8080 8443 

ENTRYPOINT /opt/jboss/wildfly/bin/standalone.sh -b 0.0.0.0 -bmanagement=0.0.0.0 --debug 0.0.0.0:8787