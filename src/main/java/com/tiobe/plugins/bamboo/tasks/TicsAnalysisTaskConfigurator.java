package com.tiobe.plugins.bamboo.tasks;

import com.atlassian.bamboo.utils.i18n.I18nBean;
import com.atlassian.bamboo.utils.i18n.I18nBeanFactory;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import com.atlassian.bamboo.task.AbstractTaskConfigurator;
import com.atlassian.bamboo.task.TaskDefinition;
import com.atlassian.bamboo.utils.error.ErrorCollection;
import com.atlassian.bamboo.collections.ActionParametersMap;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class TicsAnalysisTaskConfigurator extends AbstractTaskConfigurator {

    final List<String> paramStrings = Arrays.asList("projectName", "branchName", "branchDir", "fileServer", "buildServer",
            "tmpDirEnabled", "tmpDirLoc", "extraArgs", "envVars", "advancedOpts");
    private final I18nBean i18nBean;

    TicsAnalysisTaskConfigurator(@ComponentImport final I18nBeanFactory i18nBeanFactory) {
        this.i18nBean = i18nBeanFactory.getI18nBean();
    }

    public Map<String, String> generateTaskConfigMap(@NotNull final ActionParametersMap params, @Nullable final TaskDefinition previousTaskDefinition) {
        final Map<String, String> config = super.generateTaskConfigMap(params, previousTaskDefinition);
        for (String paramString : paramStrings) {
            config.put(paramString, params.getString(paramString));
        }
        return config;
    }

    public void validate(@NotNull final ActionParametersMap params, @NotNull final ErrorCollection errorCollection) {
        super.validate(params, errorCollection);

        if (StringUtils.isBlank(params.getString("projectName"))) {
            errorCollection.addError("projectName", i18nBean.getText("projectName.required"));
        }
        if (StringUtils.isBlank(params.getString("branchName"))) {

            errorCollection.addError("branchName", i18nBean.getText("branchName.required"));
        }
    }

    @Override
    public void populateContextForCreate(@NotNull final Map<String, Object> context) {
        super.populateContextForCreate(context);
    }

    @Override
    public void populateContextForEdit(@NotNull final Map<String, Object> context, @NotNull final TaskDefinition taskDefinition) {
        super.populateContextForEdit(context, taskDefinition);
        for (String paramString : paramStrings) {
            context.put(paramString, taskDefinition.getConfiguration().get(paramString));
        }
        //hide advanced opts for edit
        context.put("advancedOpts", "false");
    }
}
