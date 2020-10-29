package com.tiobe.plugins.bamboo.tasks;

import com.atlassian.bamboo.collections.ActionParametersMap;
import com.atlassian.bamboo.task.AbstractTaskConfigurator;
import com.atlassian.bamboo.task.TaskDefinition;
import com.atlassian.bamboo.utils.error.ErrorCollection;
import com.atlassian.bamboo.utils.i18n.I18nBean;
import com.atlassian.bamboo.utils.i18n.I18nBeanFactory;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class TicsQualityGateTaskConfigurator extends AbstractTaskConfigurator {
    private final ImmutableList<String> PROP_KEYS = ImmutableList.of("projectName", "branchName", "viewerApi", "userName", "password");
    private final ImmutableList<String> REQUIRED_FIELDS = ImmutableList.of("projectName", "branchName", "viewerApi");
    private final I18nBean i18nBean;

    TicsQualityGateTaskConfigurator(@ComponentImport final I18nBeanFactory i18nBeanFactory) {
        this.i18nBean = i18nBeanFactory.getI18nBean();
    }

    @NotNull
    @Override
    public Map<String, String> generateTaskConfigMap(@NotNull final ActionParametersMap params, @Nullable final TaskDefinition previousTaskDefinition) {
        final Map<String, String> config = super.generateTaskConfigMap(params, previousTaskDefinition);
        for (String key : PROP_KEYS) {
            config.put(key, params.getString(key));
        }
        return config;
    }

    @Override
    public void validate(@NotNull final ActionParametersMap params, @NotNull final ErrorCollection errorCollection) {
        super.validate(params, errorCollection);
        for (String requiredStr : REQUIRED_FIELDS) {
            if (StringUtils.isBlank(params.getString(requiredStr))) {
                errorCollection.addError(requiredStr, this.i18nBean.getText(requiredStr + ".required"));
            }
        }
    }

    @Override
    public void populateContextForCreate(@NotNull final Map<String, Object> context) {
        super.populateContextForCreate(context);
    }

    @Override
    public void populateContextForEdit(@NotNull final Map<String, Object> context, @NotNull final TaskDefinition taskDefinition) {
        super.populateContextForEdit(context, taskDefinition);
        for (String key : PROP_KEYS) {
            context.put(key, taskDefinition.getConfiguration().get(key));
        }
    }

}
