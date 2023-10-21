package com.github.andriilab.promasy.app.controller;

import lombok.Getter;

@Getter
public class CommandLineSettings {

    public CommandLineSettings(String[] args){
        for (String arg : args) {
            if (arg.contains("-c") || arg.contains("--config")) {
                showConnectionSettingsDialog = true;
            }
            if (arg.contains("-g") || arg.contains("--generate")) {
                enableModifyTableSchemas = true;
            }
            if (arg.contains("-s") || arg.contains("--statistics")) {
                showConnectionStatistics = true;
            }
            if (arg.contains("-l") || arg.contains("--log")) {
                saveLogToFile = true;
            }
        }
    }

    private boolean enableModifyTableSchemas;

    private boolean showConnectionSettingsDialog;

    private boolean showConnectionStatistics;

    private boolean saveLogToFile;
}
