<div class="col-sm-4 col-md-4" id="basis-topic">
    <div class="panel">
        <div class="panel-heading">
            <div class="panel-title">Records by identification verification status<i class="fa fa-info-circle pull-right hidden"></i></div>
        </div>
        <div class="panel-body">
            <g:if test="${identificationVerificationStatus.error.asBoolean()}">
                <p class="error" title="${identificationVerificationStatus.reason}">${identificationVerificationStatus.error}</p>
            </g:if>
            <table class="table table-condensed table-striped table-hover">
                <tbody>
                <g:each in="${identificationVerificationStatus.facets[0..Math.min(5, identificationVerificationStatus.facets.size() - 1)]}" var="id">
                    <tr class="link">
                        <td id="br-${id.facet}">${id.display}</td>
                        <td class="numberColumn"><span class="count">${id.formattedCount}</span></td>
                    </tr>
                </g:each>
                 </tbody>
            </table>
            <g:if test="${identificationVerificationStatus.facets.size() > 6}">
                <div id="moreIdentification" class="initiallyHidden">
                    <table class="table table-condensed table-striped table-hover">
                        <g:each in="${identificationVerificationStatus.facets[6..-1]}" var="id">
                            <tr class="link">
                                <td id="br-${id.facet}">${id.display}</td>
                                <td class="numberColumn"><span class="count">${id.formattedCount}</span></td>
                            </tr>
                        </g:each>
                    </table>
                </div>
            </g:if>
            <g:if test="${identificationVerificationStatus.facets.size() > 6}">
                <p class="paragraph"><button id="moreIdentificationLink" class="btn btn-default btn-small moreLink">More</button></p>
            </g:if>

        </div>
    </div>
</div>