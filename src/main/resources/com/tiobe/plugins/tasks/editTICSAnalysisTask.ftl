[@ww.textfield labelKey="projectName.label" name="projectName" descriptionKey="projectName.description" required='true' cssClass='long-field'/]
[@ww.textfield labelKey="branchName.label" name="branchName" descriptionKey="branchName.description" required='true' cssClass='long-field'/]

<hr/>

[@ww.checkbox id="advancedOptsChk" labelKey="advancedOpts.label" name="advancedOpts" required='false' onchange="advanceOptsChanged()" /]
<div id="advancedOptions" style="list-style-type:none" hidden=true>
    [@ww.textfield labelKey="branchDir.label" name="branchDir" descriptionKey="branchDir.description" required='false' cssClass='long-field'/]
    [@ww.textfield labelKey="fileServer.label" name="fileServer" descriptionKey="fileServer.description" required='false' cssClass='long-field'/]
    [@ww.textfield labelKey="buildServer.label" name="buildServer" descriptionKey="buildServer.description" required='false' cssClass='long-field'/]
    [@ww.checkbox labelKey="tmpDirEnabled.label" name="tmpDirEnabled" descriptionKey="tmpDirEnabled.description" required='false'/]
    [@ww.textfield labelKey="tmpDirLoc.label" name="tmpDirLoc" descriptionKey="tmpDirLoc.description" required='false' cssClass='long-field'/]
    [@ww.textfield labelKey="extraArgs.label" name="extraArgs" descriptionKey="extraArgs.description" required='false' cssClass='long-field'/]
    [@ww.textarea labelKey="envVars.label" name="envVars" descriptionKey="envVars.description" required='false'/]

</div>

<script type="text/javascript">

    function advanceOptsChanged(){
        const chkBoxAdv = document.getElementById('advancedOptsChk');
        if(chkBoxAdv.checked==true)
            advancedOptions.style.display = "block";
        else
            advancedOptions.style.display = "none";
    }
</script>
