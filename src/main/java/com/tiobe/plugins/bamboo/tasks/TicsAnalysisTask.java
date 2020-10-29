package com.tiobe.plugins.bamboo.tasks;

import com.atlassian.bamboo.build.logger.BuildLogger;
import com.atlassian.bamboo.process.ProcessService;
import com.atlassian.bamboo.task.TaskContext;
import com.atlassian.bamboo.task.TaskException;
import com.atlassian.bamboo.task.TaskResult;
import com.atlassian.bamboo.task.TaskResultBuilder;
import com.atlassian.bamboo.task.TaskType;
import com.atlassian.bamboo.process.ExternalProcessBuilder;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class TicsAnalysisTask implements TaskType {
    @ComponentImport
    private final ProcessService processService;

    public TicsAnalysisTask(final ProcessService processService) {
        this.processService = processService;
    }

    @NotNull
    @Override
    public TaskResult execute(@NotNull final TaskContext taskContext) throws TaskException {

        final BuildLogger buildLogger = taskContext.getBuildLogger();

        final String branchDir = taskContext.getConfigurationMap().get("branchDir");
        final String buildServer = taskContext.getConfigurationMap().get("buildServer");
        final String fileServer = taskContext.getConfigurationMap().get("fileServer");
        final String envVars = taskContext.getConfigurationMap().get("envVars");

        //set FileServer path
        final Map<String, String> envMap = new HashMap<>();
        if (!StringUtils.isEmpty(fileServer)) {
            envMap.put("TICS", fileServer);
        }

        //set env vars
        if (envVars != null && !envVars.isEmpty()) {
            final Map<String, String> intermediate = Arrays.stream(envVars.split("\n"))
                    .map(String::trim)
                    .map((s) -> s.split("=", 2))
                    .filter(ar -> ar.length >= 2)
                    .collect(Collectors.toMap(e -> e[0], e -> e[1]));

            envMap.putAll(intermediate);
        }

        //If branchDir specified, invoke TICSMaintenance to create a new branch
        if (!StringUtils.isEmpty(branchDir) && !StringUtils.isWhitespace(branchDir)) {
            buildLogger.addBuildLogEntry("Invoking TICSMaintenance with branchDir " + branchDir);
            final ExternalProcessBuilder ticsMaintenanceProcessBuilder = new ExternalProcessBuilder()
                    .workingDirectory(taskContext.getWorkingDirectory())
                    .env(envMap)
                    .path(buildServer);
            executeTICSMaintenance(ticsMaintenanceProcessBuilder, taskContext);

        }

        //run TICSQServer analysis
        final ExternalProcessBuilder ticsProcessBuilder = new ExternalProcessBuilder()
                .workingDirectory(taskContext.getWorkingDirectory())
                .path(buildServer)
                .env(envMap);
        return executeTICSQServer(ticsProcessBuilder, taskContext);


    }

    public TaskResult executeTICSMaintenance(final ExternalProcessBuilder processBuilder, final TaskContext taskContext) {

        final List<String> ticsMaintenanceCommand = new ArrayList<>();
        ticsMaintenanceCommand.addAll(Arrays.asList("TICSMaintenance", "-project", taskContext.getConfigurationMap().get("projectName")));

        ticsMaintenanceCommand.add("-branchName");
        ticsMaintenanceCommand.add(taskContext.getConfigurationMap().get("branchName"));

        ticsMaintenanceCommand.add("-branchdir");
        ticsMaintenanceCommand.add(taskContext.getConfigurationMap().get("branchDir"));

        processBuilder.command(ticsMaintenanceCommand);

        return TaskResultBuilder.newBuilder(taskContext)
                .checkReturnCode(processService.executeExternalProcess(taskContext, processBuilder))
                .build();
    }

    public TaskResult executeTICSQServer(final ExternalProcessBuilder processBuilder, final TaskContext taskContext) {
        final BuildLogger buildLogger = taskContext.getBuildLogger();
        taskContext.getBuildLogger().addBuildLogEntry("ProcessBuilder path : " + processBuilder.getPaths());
        buildLogger.addBuildLogEntry("ProcessBuilder env : " + processBuilder.getEnv());

        final List<String> ticsCommand = new ArrayList<>();
        ticsCommand.addAll(Arrays.asList("TICSQServer", "-project", taskContext.getConfigurationMap().get("projectName")));

        ticsCommand.add("-branchName");
        ticsCommand.add(taskContext.getConfigurationMap().get("branchName"));

        final String tmpDirEnabled = taskContext.getConfigurationMap().get("tmpDirEnabled");
        final String tmpDirLoc = taskContext.getConfigurationMap().get("tmpDirLoc");
        buildLogger.addBuildLogEntry("tmpdir enabled : " + tmpDirEnabled);
        if (tmpDirEnabled.equals("true")) {
            ticsCommand.add("-tmpDir");
            ticsCommand.add(tmpDirLoc);
        }

        final String extraArgs = taskContext.getConfigurationMap().getOrDefault("extraArgs", "");
        if (!StringUtils.isEmpty(extraArgs)) {
            ticsCommand.addAll(Arrays.asList(extraArgs.split(" ")));
        }

        buildLogger.addBuildLogEntry("ticsCommand args: " + ticsCommand.toString());
        processBuilder.command(ticsCommand);

        return TaskResultBuilder.newBuilder(taskContext)
                .checkReturnCode(processService.executeExternalProcess(taskContext, processBuilder))
                .build();
    }
}
