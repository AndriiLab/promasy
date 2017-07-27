package com.github.andriilab.promasy.model.models;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ConnectionSettingsModel implements Serializable {
    private Map<String, String> props;


    public ConnectionSettingsModel(String server, String database, String schema, int portNumber, String user, String password) {
        props = new HashMap<>();

        props.put("_server", server);
        props.put("_database", database);
        props.put("_portNumber", String.valueOf(portNumber));

        props.put("javax.persistence.jdbc.user", user);
        props.put("javax.persistence.jdbc.password", password);
        props.put("hibernate.default_schema", schema);
    }
	public String getSchema() {
        return props.get("hibernate.default_schema");
    }
	public String getServer() {
        return props.get("_server");
    }
	public String getDatabase() {
        return props.get("_database");
    }
	public int getPortNumber() {
        return Integer.parseInt(props.get("_portNumber"));
    }
	public String getUser() {
        return props.get("javax.persistence.jdbc.user");
    }
	public String getPassword() {
        return props.get("javax.persistence.jdbc.password");
    }

    public Map<String, String> getProperties() {
        props.put("javax.persistence.jdbc.url", getUrl());
        return props;
    }
    public String getUrl() {
        StringBuilder sb = new StringBuilder();
        sb.append("jdbc:postgresql://");
        sb.append(props.get("_server"));
        sb.append(":");
        sb.append(props.get("_portNumber"));
        sb.append("/");
        sb.append(props.get("_database"));
        return sb.toString();
    }
}
