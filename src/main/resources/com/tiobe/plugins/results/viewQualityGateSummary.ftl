[#if ticsQualityGateResult??]
<h2 style = "display: inline-block;">TICS Quality Gate Summary</h2>
    [#if ticsQualityGateResult.passed]
        <span style="background:rgba(85, 163, 98, 1);color:white;padding:2px 4px 2px 4px;border-radius:5px;margin-right:5px">
            passed
        </span>
    [#else]
        <span style="background:rgba(205,74,69,1);color:white;padding:2px 4px 2px 4px;border-radius:5px;margin-right:5px">
            failed
        </span>
    [/#if]
    <br/>

    <dl>
        <dt>[@ww.label labelKey='result.summary' value=': ${ticsQualityGateResult.message}'/]</dt>
        <dt>[@ww.label labelKey='projectName.label' value=': ${projectName}'/]</dt>
        <dt>[@ww.label labelKey='branchName.label' value=': ${branchName}'/]</dt>
    </dl>


    [#list ticsQualityGateResult.gates as gate]
        <hr/>

        <h4 style = "display: inline-block;">
            [@ww.label labelKey='result.gate' value=': ${gate.name} ' /]
        </h4>
        [#if gate.passed]
            <span style="background:rgba(85, 163, 98, 1);color:white;padding:2px 4px 2px 4px;border-radius:5px;">
                passed
            </span>
         [#else]
            <span style="background:rgba(205,74,69,1);color:white;padding:2px 4px 2px 4px;border-radius:5px;">
                failed
            </span>
         [/#if]

        <br/><br/>
        <table>
            [#list gate.conditions as condition]
                <tr>
                     <td>
                        [#if condition.passed]
                             <span style="color: rgba(85, 163, 98, 1)">passed</span>
                        [#else]
                             <span style="color: rgba(205,74,69,1)"> failed </span>
                        [/#if]
                     </td>
                     <td> ${condition.message}</td>
                </tr>
            [/#list]
        </table>
        </br>
    [/#list]

    <br/>
    <a href="${ticsQualityGateResult.url}" target="_blank"> See results in TICS Viewer</a>
[/#if]
