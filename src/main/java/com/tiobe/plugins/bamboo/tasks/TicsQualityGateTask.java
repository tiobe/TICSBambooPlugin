package com.tiobe.plugins.bamboo.tasks;

import com.atlassian.bamboo.build.logger.BuildLogger;
import com.atlassian.bamboo.task.TaskContext;
import com.atlassian.bamboo.task.TaskException;
import com.atlassian.bamboo.task.TaskResult;
import com.atlassian.bamboo.task.TaskResultBuilder;
import com.atlassian.bamboo.task.TaskType;
import com.atlassian.bamboo.v2.build.CurrentBuildResult;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.tiobe.plugins.bamboo.results.TicsQualityGateResult;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;


public class TicsQualityGateTask implements TaskType {
    public static final Logger logger = Logger.getLogger(TicsQualityGateResult.class);

    @NotNull
    @Override
    public TaskResult execute(@NotNull final TaskContext taskContext) throws TaskException {

        final CurrentBuildResult buildResult = taskContext.getBuildContext().getBuildResult();
        final TaskResult taskResult;

        final BuildLogger buildLogger = taskContext.getBuildLogger();

        final String projectName = taskContext.getConfigurationMap().get("projectName");
        final String branchName = taskContext.getConfigurationMap().get("branchName");
        final String viewerApi = taskContext.getConfigurationMap().get("viewerApi");
        final String userName = taskContext.getConfigurationMap().get("userName");
        final String password = taskContext.getConfigurationMap().get("password");

        final String qualityGateAPI = viewerApi + "/api/public/v1/QualityGateStatus?"
                + "project=" + projectName
                + "&branch=" + branchName;

        buildLogger.addBuildLogEntry("Quality gate request sent to : " + qualityGateAPI);

        final CredentialsProvider provider = new BasicCredentialsProvider();
        provider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(userName, password));

        final String responseBody;
        try (CloseableHttpClient client = HttpClientBuilder.create()
                .setDefaultCredentialsProvider(provider)
                .build()) {


            final HttpGet qualityGateRequest = new HttpGet(qualityGateAPI);
            final CloseableHttpResponse response = client.execute(qualityGateRequest);
            responseBody = EntityUtils.toString(response.getEntity(), "UTF-8");

        } catch (final IOException e) {
            throw new RuntimeException("Error while performing API request to " + qualityGateAPI + ": \n " + e.toString());
        }

        buildResult.getCustomBuildData().put("viewerApi", viewerApi);
        buildResult.getCustomBuildData().put("ticsQualityGateResult", responseBody);
        buildResult.getCustomBuildData().put("projectName", projectName);
        buildResult.getCustomBuildData().put("branchName", branchName);

        logger.debug("Received Quality Gate Result: \n" + responseBody);

        try {
            final Gson gson = new Gson();
            final TicsQualityGateResult qualityGateResult = gson.fromJson(responseBody, TicsQualityGateResult.class);

            if (qualityGateResult.isPassed()) {
                buildLogger.addBuildLogEntry("Quality Gate Passed!");
                taskResult = TaskResultBuilder.newBuilder(taskContext).success().build();
            } else {
                buildLogger.addBuildLogEntry("Quality Gate Failed!");
                taskResult = TaskResultBuilder.newBuilder(taskContext).failed().build();
            }
        } catch (final JsonSyntaxException e) {
            throw new RuntimeException("Failed to parse server response. \n " + responseBody, e);
        }
        return taskResult;
    }
}
