package com.github.andriilab.promasy.app.controller;

import lombok.Getter;

@Getter
public class CommandLineSettings {

    public CommandLineSettings(String[] args){
        for (String arg : args) {
            if (arg.equals("-c") || arg.equals("--config")) {
                showConnectionSettingsDialog = true;
            }
            if (arg.equals("-g") || arg.equals("--generate")) {
                enableModifyTableSchemas = true;
            }
            if (arg.equals("-s") || arg.equals("--statistics")) {
                showConnectionStatistics = true;
            }
            if (arg.equals("-l") || arg.equals("--log")) {
                saveLogToFile = true;
            }
        }
    }

    private boolean enableModifyTableSchemas;

    private boolean showConnectionSettingsDialog;

    private boolean showConnectionStatistics;

    private boolean saveLogToFile;
}
