package com.github.andriilab.promasy.app.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class CommandLineSettingsTest {

    private static void assertFlags(CommandLineSettings settings, boolean config, boolean generate, boolean statistics, boolean log) {
        assertThat(settings.isShowConnectionSettingsDialog()).as("config").isEqualTo(config);
        assertThat(settings.isEnableModifyTableSchemas()).as("generate").isEqualTo(generate);
        assertThat(settings.isShowConnectionStatistics()).as("statistics").isEqualTo(statistics);
        assertThat(settings.isSaveLogToFile()).as("log").isEqualTo(log);
    }

    @Test
    void constructor_noArgs_enablesNothing() {
        assertFlags(new CommandLineSettings(new String[0]), false, false, false, false);
    }

    @ParameterizedTest
    @ValueSource(strings = {"-c", "--config"})
    void constructor_configFlag_showsConnectionDialog(String flag) {
        assertFlags(new CommandLineSettings(new String[]{flag}), true, false, false, false);
    }

    @ParameterizedTest
    @ValueSource(strings = {"-g", "--generate"})
    void constructor_generateFlag_enablesSchemaModification(String flag) {
        assertFlags(new CommandLineSettings(new String[]{flag}), false, true, false, false);
    }

    @ParameterizedTest
    @ValueSource(strings = {"-s", "--statistics"})
    void constructor_statisticsFlag_enablesStatistics(String flag) {
        assertFlags(new CommandLineSettings(new String[]{flag}), false, false, true, false);
    }

    @ParameterizedTest
    @ValueSource(strings = {"-l", "--log"})
    void constructor_logFlag_enablesSaveLog(String flag) {
        assertFlags(new CommandLineSettings(new String[]{flag}), false, false, false, true);
    }

    @Test
    void constructor_allFlags_enablesEverything() {
        assertFlags(new CommandLineSettings(new String[]{"-c", "--generate", "-s", "--log"}), true, true, true, true);
    }

    @ParameterizedTest
    @ValueSource(strings = {"my-log.txt", "-x", "--unknown", "config", "-cc", "--configuration", "some-config-file", ""})
    void constructor_unrelatedArgument_enablesNothing(String arg) {
        assertFlags(new CommandLineSettings(new String[]{arg}), false, false, false, false);
    }

    @ParameterizedTest
    @CsvSource({"my-log.txt, -g", "run-statistics, -l"})
    void constructor_unrelatedArgumentNextToFlag_onlyTheFlagCounts(String unrelated, String flag) {
        CommandLineSettings settings = new CommandLineSettings(new String[]{unrelated, flag});

        assertFlags(settings, false, flag.equals("-g"), false, flag.equals("-l"));
    }
}
