# TICS Bamboo Plugin

## About

This plugin adds [TICS](https://tiobe.com/tics/fact-sheet) Quality Analysis and Quality Gating features to your bamboo plans. To view the full documentation visit 
[here](https://portal.tiobe.com/2020.4/docs/#doc=bamboo/index.html)

You can download the plugin from the [Atlassian Market Place](https://marketplace.atlassian.com/apps/1224035/tics-bamboo-plugin)

## Building

In order to build the plugin, you will need the following:

* Java Development Kit (8 or higher)
* Atlassian plugin SDK

In order to build, run `atlas-package -Dversion=xxxxx`, where xxxxx is the version that will be used for the plugin. The built plugin can be found in the target folder under the name TICSBamboo-xxxxx.jar (with xxxxx replaced by the version that was specified during the build).
